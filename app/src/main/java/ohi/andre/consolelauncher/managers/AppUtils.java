package ohi.andre.consolelauncher.managers;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ServiceInfo;
import java.util.ArrayList;
import java.util.List;
import ohi.andre.consolelauncher.tuils.Tuils;

public class AppUtils {
    
    public static String format(LaunchInfo info, PackageInfo packageInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("App: ").append(info.publicLabel).append("\n");
        sb.append("Package: ").append(info.componentName.getPackageName()).append("\n");
        sb.append("Component: ").append(info.componentName.getClassName()).append("\n");
        sb.append("Version: ").append(packageInfo.versionName).append("\n");
        sb.append("Launched: ").append(info.launchedTimes).append(" times\n");
        if (packageInfo.requestedPermissions != null && packageInfo.requestedPermissions.length > 0) {
            sb.append("\nPermissions (").append(packageInfo.requestedPermissions.length).append("):\n");
            for (String perm : packageInfo.requestedPermissions) sb.append("  - ").append(perm).append("\n");
        }
        if (packageInfo.activities != null && packageInfo.activities.length > 0) {
            sb.append("\nActivities (").append(packageInfo.activities.length).append("):\n");
            for (ActivityInfo activity : packageInfo.activities) sb.append("  - ").append(activity.name).append("\n");
        }
        if (packageInfo.services != null && packageInfo.services.length > 0) {
            sb.append("\nServices (").append(packageInfo.services.length).append("):\n");
            for (ServiceInfo service : packageInfo.services) sb.append("  - ").append(service.name).append("\n");
        }
        if (packageInfo.receivers != null && packageInfo.receivers.length > 0) {
            sb.append("\nReceivers (").append(packageInfo.receivers.length).append("):\n");
            for (ActivityInfo receiver : packageInfo.receivers) sb.append("  - ").append(receiver.name).append("\n");
        }
        return sb.toString();
    }

    public static List<String> labelList(List<LaunchInfo> infos, boolean includePackage) {
        List<String> labels = new ArrayList<>();
        if(infos == null) return labels;
        for(LaunchInfo li : infos) {
            labels.add(includePackage ? li.publicLabel + " (" + li.componentName.getPackageName() + ")" : li.publicLabel);
        }
        return labels;
    }

    public static String printApps(List<String> labels) {
        if(labels == null || labels.isEmpty()) return "[]";
        return Tuils.toPlanString(labels, Tuils.NEWLINE);
    }
}
