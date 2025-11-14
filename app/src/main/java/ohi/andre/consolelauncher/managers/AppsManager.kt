// Renamed to AppsManager.kt and augmented with legacy compatibility APIs.
package ohi.andre.consolelauncher.managers

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.os.Build
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ohi.andre.consolelauncher.MainManager
import ohi.andre.consolelauncher.UIManager
import ohi.andre.consolelauncher.commands.main.MainPack
import ohi.andre.consolelauncher.managers.xml.XMLPrefsManager
import ohi.andre.consolelauncher.managers.xml.options.Apps

class AppsManager(private val context: Context) : ohi.andre.consolelauncher.managers.xml.classes.XMLPrefsElement {

    override fun getValues(): ohi.andre.consolelauncher.managers.xml.classes.XMLPrefsList {
        val list = ohi.andre.consolelauncher.managers.xml.classes.XMLPrefsList()
        allApps.forEach { list.add(it.componentName.packageName, it.label) }
        return list
    }
    override fun write(save: ohi.andre.consolelauncher.managers.xml.classes.XMLPrefsSave, value: String) = Unit
    override fun delete(): Array<String> = emptyArray()
    override fun path(): String = PATH


    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val packageManager: PackageManager = context.packageManager
    private val prefs: SharedPreferences = context.getSharedPreferences("apps_prefs", Context.MODE_PRIVATE)

    @Volatile private var allApps: List<LaunchInfo> = emptyList()
    @Volatile private var hiddenApps: MutableSet<String> = mutableSetOf()

    private val appsBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) { refreshApps() }
    }

    init {
        loadHiddenApps()
        refreshApps()
        registerAppReceiver()
        instance = this
        reloadGroups()
    }

    fun getVisibleApps(): List<LaunchInfo> {
        val sorting = XMLPrefsManager.getInt(Apps.app_groups_sorting)
        return allApps.filter { it.componentName.packageName !in hiddenApps }.sortedWith(getComparator(sorting))
    }
    fun getHiddenApps(): List<LaunchInfo> = allApps.filter { it.componentName.packageName in hiddenApps }
    
    fun getAllApps(): List<LaunchInfo> {
        val sorting = XMLPrefsManager.getInt(Apps.app_groups_sorting)
        return allApps.sortedWith(getComparator(sorting))
    }
    
    // Synchronous method for immediate loading - used by export
    fun getAllAppsNow(): List<LaunchInfo> {
        // If allApps is empty, load them synchronously
        if (allApps.isEmpty()) {
            val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
            val resolveInfoList: List<ResolveInfo> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                packageManager.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(0L))
            } else {
                @Suppress("DEPRECATION")
                packageManager.queryIntentActivities(intent, 0)
            }
            allApps = resolveInfoList.mapNotNull { resolveInfo ->
                resolveInfo.activityInfo?.let { ai ->
                    LaunchInfo(
                        resolveInfo.loadLabel(packageManager).toString(),
                        ComponentName(ai.packageName, ai.name),
                        prefs.getInt(ComponentName(ai.packageName, ai.name).flattenToString(), 0)
                    )
                }
            }
        }
        val sorting = XMLPrefsManager.getInt(Apps.app_groups_sorting)
        return allApps.sortedWith(getComparator(sorting))
    }

    // Legacy names
    fun shownApps(): List<LaunchInfo> = getVisibleApps()
    fun hiddenApps(): List<LaunchInfo> = getHiddenApps()

    fun getSuggestedApps(): Array<LaunchInfo> = getVisibleApps().sortedByDescending { it.launchedTimes }.take(5).toTypedArray()

    // Exposed groups list
    @JvmField var groups: MutableList<Group> = mutableListOf()
    private fun reloadGroups() {
        val groupNames = prefs.getStringSet("app_groups", emptySet()) ?: emptySet()
        groups = groupNames.map { name ->
            val apps = prefs.getStringSet("group_apps_$name", emptySet()) ?: emptySet()
            val bgColor = prefs.getString("group_bg_$name", null)
            val fgColor = prefs.getString("group_fg_$name", null)
            Group(name, apps.toMutableSet(), bgColor, fgColor)
        }.toMutableList()
    }

    fun getValuesMap(): HashMap<String, String> = HashMap<String, String>().apply { allApps.forEach { put(it.componentName.packageName, it.label) } }

    fun launch(info: LaunchInfo) {
        val currentCount = prefs.getInt(info.componentName.flattenToString(), 0)
        prefs.edit { putInt(info.componentName.flattenToString(), currentCount + 1) }
        context.startActivity(getIntent(info))
    }

    fun hideApp(info: LaunchInfo) { hiddenApps.add(info.componentName.packageName); saveHiddenApps(); broadcastAppListUpdate() }
    fun showApp(info: LaunchInfo) { hiddenApps.remove(info.componentName.packageName); saveHiddenApps(); broadcastAppListUpdate() }

    fun hideActivity(info: LaunchInfo) = hideApp(info)
    fun showActivity(info: LaunchInfo) = showApp(info)

    fun getIntent(info: LaunchInfo): Intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        component = info.componentName
    }

    fun writeLaunchTimes(info: LaunchInfo) { prefs.edit { putInt(info.componentName.flattenToString(), info.launchedTimes) } }

    fun createGroup(name: String): String {
        val gs = prefs.getStringSet("app_groups", mutableSetOf()) ?: mutableSetOf()
        return if (gs.add(name)) { prefs.edit { putStringSet("app_groups", gs) }; reloadGroups(); "Group '$name' created" } else "Group '$name' already exists"
    }
    fun removeGroup(name: String): String {
        val gs = prefs.getStringSet("app_groups", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        return if (gs.remove(name)) { prefs.edit { putStringSet("app_groups", gs); remove("group_apps_$name"); remove("group_bg_$name"); remove("group_fg_$name") }; reloadGroups(); "Group '$name' removed" } else "Group '$name' not found"
    }
    fun groupBgColor(name: String, color: String): String {
        if (color.isEmpty()) return prefs.getString("group_bg_$name", "No background color set") ?: "No background color set"
        prefs.edit { putString("group_bg_$name", color) }; return "Background color set for group '$name'"
    }
    fun groupForeColor(name: String, color: String): String {
        if (color.isEmpty()) return prefs.getString("group_fg_$name", "No foreground color set") ?: "No foreground color set"
        prefs.edit { putString("group_fg_$name", color) }; return "Foreground color set for group '$name'"
    }
    fun listGroups(): String = (prefs.getStringSet("app_groups", emptySet()) ?: emptySet()).let { if (it.isEmpty()) "No groups defined" else it.joinToString("\n") }
    fun listGroup(name: String): String = (prefs.getStringSet("group_apps_$name", emptySet()) ?: emptySet()).let { if (it.isEmpty()) "Group '$name' is empty" else it.joinToString("\n") }
    fun addAppToGroup(groupName: String, app: LaunchInfo): String {
        val apps = prefs.getStringSet("group_apps_$groupName", mutableSetOf())?.toMutableSet() ?: mutableSetOf(); return if (apps.add(app.componentName.flattenToString())) { prefs.edit { putStringSet("group_apps_$groupName", apps) }; reloadGroups(); "Added ${app.label} to group '$groupName'" } else "${app.label} already in group '$groupName'" }
    fun removeAppFromGroup(groupName: String, app: LaunchInfo): String {
        val apps = prefs.getStringSet("group_apps_$groupName", mutableSetOf())?.toMutableSet() ?: mutableSetOf(); return if (apps.remove(app.componentName.flattenToString())) { prefs.edit { putStringSet("group_apps_$groupName", apps) }; reloadGroups(); "Removed ${app.label} from group '$groupName'" } else "${app.label} not in group '$groupName'" }

    fun refreshApps() { coroutineScope.launch { loadAllApps(); broadcastAppListUpdate() } }
    fun fill() = refreshApps() // legacy alias used by refresh command

    @WorkerThread
    private suspend fun loadAllApps() {
        val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        val resolveInfoList: List<ResolveInfo> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { packageManager.queryIntentActivities(intent, PackageManager.ResolveInfoFlags.of(0L)) } else { @Suppress("DEPRECATION") packageManager.queryIntentActivities(intent, 0) }
        allApps = resolveInfoList.mapNotNull { resolveInfo -> resolveInfo.activityInfo?.let { ai -> LaunchInfo(resolveInfo.loadLabel(packageManager).toString(), ComponentName(ai.packageName, ai.name), prefs.getInt(ComponentName(ai.packageName, ai.name).flattenToString(), 0)) } }
    }

    private fun registerAppReceiver() { context.registerReceiver(appsBroadcastReceiver, IntentFilter().apply { addAction(Intent.ACTION_PACKAGE_ADDED); addAction(Intent.ACTION_PACKAGE_REMOVED); addDataScheme("package") }) }
    private fun broadcastAppListUpdate() { coroutineScope.launch(Dispatchers.Main) { LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(UIManager.ACTION_UPDATE_SUGGESTIONS)) } }
    private fun saveHiddenApps() { prefs.edit { putStringSet("hidden_apps_set", hiddenApps) } }
    private fun loadHiddenApps() { hiddenApps = prefs.getStringSet("hidden_apps_set", emptySet())?.toMutableSet() ?: mutableSetOf() }

    private fun getComparator(sortingMode: Int): Comparator<LaunchInfo> = when (sortingMode) { 4, 5 -> compareByDescending { it.launchedTimes }; 2, 3 -> compareBy(String.CASE_INSENSITIVE_ORDER) { it.label }; else -> compareBy(String.CASE_INSENSITIVE_ORDER) { it.label } }

    fun findLaunchInfoWithLabel(label: String, type: Int): LaunchInfo? {
        val list = when (type) { SHOWN_APPS -> getVisibleApps(); HIDDEN_APPS -> getHiddenApps(); else -> allApps }
        return list.find { it.publicLabel.equals(label, true) || it.componentName.packageName.equals(label, true) }
    }
    fun findLaunchInfoByFlatten(flat: String): LaunchInfo? = allApps.find { it.componentName.flattenToString() == flat }

    fun printApps(type: Int, name: String? = null): String {
        val list = when (type) { SHOWN_APPS -> getVisibleApps(); HIDDEN_APPS -> getHiddenApps(); else -> allApps }
        val filtered = if (!name.isNullOrEmpty()) list.filter { it.publicLabel.contains(name, true) } else list
        return filtered.joinToString("\n") { "${it.publicLabel} (${it.componentName.packageName})" }
    }
    @JvmOverloads fun printApps(type: Int): String = printApps(type, null)

    fun onDestroy() { try { context.unregisterReceiver(appsBroadcastReceiver) } catch (_: Exception) { } }

    // Legacy group wrapper
    inner class Group(
        private val groupName: String,
        private val appFlats: MutableSet<String>,
        private val bgColorHex: String?,
        private val fgColorHex: String?
    ) : MainManager.Group, it.andreuzzi.comparestring2.StringableObject {
        override fun name(): String = groupName
        override fun members(): List<GroupLaunchInfo> = appFlats.mapNotNull { findLaunchInfoByFlatten(it)?.let { li -> GroupLaunchInfo(li.publicLabel, li.componentName, li.launchedTimes) } }
        fun contains(info: LaunchInfo): Boolean = appFlats.contains(info.componentName.flattenToString())
        override fun use(mainPack: MainPack, input: String): Boolean {
            val li = members().find { it.publicLabel.equals(input, true) }
            if (li != null) { findLaunchInfoByFlatten(li.componentName.flattenToString())?.let { launch(it) }; return true }
            return false
        }
        fun getBgColor(): Int = parseColor(bgColorHex)
        fun getForeColor(): Int = parseColor(fgColorHex)
        fun bgColor(): String? = bgColorHex
        fun foreColor(): String? = fgColorHex
        fun apps(): Array<String> = appFlats.toTypedArray()
        override fun getLowercaseString(): String = groupName.lowercase()
        override fun getString(): String = groupName
        override fun toString(): String = groupName

        inner class GroupLaunchInfo(label: String, componentName: ComponentName, launchedTimes: Int) : LaunchInfo(label, componentName, launchedTimes), it.andreuzzi.comparestring2.StringableObject {
            override fun getLowercaseString(): String = publicLabel.lowercase()
            override fun getString(): String = publicLabel
            override fun toString(): String = publicLabel
        }
    }

    private fun parseColor(hex: String?): Int = try { if (hex.isNullOrBlank()) Color.TRANSPARENT else Color.parseColor(hex) } catch (_: Exception) { Color.TRANSPARENT }

    companion object {
        const val SHOWN_APPS = 10
        const val HIDDEN_APPS = 11
        @JvmField val PATH = "apps.xml"
        @JvmField var instance: AppsManager? = null
    }
}
