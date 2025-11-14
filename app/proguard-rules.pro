-keep public class ohi.andre.consolelauncher.commands.main.raw.* { *; }
-keep public abstract class ohi.andre.consolelauncher.commands.main.generals.* { public *; }
-keep public class ohi.andre.consolelauncher.commands.tuixt.raw.* { public *; }
-keep public class ohi.andre.consolelauncher.managers.notifications.NotificationService
-keep public class ohi.andre.consolelauncher.managers.notifications.KeeperService
-keep public class ohi.andre.consolelauncher.managers.options.**
-keep class ohi.andre.consolelauncher.tuils.libsuperuser.**
-keep class ohi.andre.consolelauncher.managers.suggestions.HideSuggestionViewValues
-keep public class it.andreuzzi.comparestring2.**

# Keep LaunchInfo and all its subclasses (including inner classes)
-keep class ohi.andre.consolelauncher.managers.LaunchInfo { *; }
-keep class ohi.andre.consolelauncher.managers.AppsManager { *; }
-keep class ohi.andre.consolelauncher.managers.AppsManager$** { *; }

# Keep ALL command execution classes - NO obfuscation allowed
-keep class ohi.andre.consolelauncher.commands.** { *; }
-keep class ohi.andre.consolelauncher.commands.main.MainPack { *; }
-keep class ohi.andre.consolelauncher.commands.ExecutePack { *; }
-keep class ohi.andre.consolelauncher.commands.CommandTuils { *; }
-keep class ohi.andre.consolelauncher.commands.CommandTuils$** { *; }

-dontwarn ohi.andre.consolelauncher.commands.main.raw.**

-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-dontwarn org.htmlcleaner.**
-dontwarn com.jayway.jsonpath.**
-dontwarn org.slf4j.**

-dontwarn org.jdom2.**