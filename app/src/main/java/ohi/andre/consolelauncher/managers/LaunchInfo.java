package ohi.andre.consolelauncher.managers;

import android.content.ComponentName;
import android.content.pm.ShortcutInfo;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LaunchInfo implements Parcelable, it.andreuzzi.comparestring2.StringableObject {
    public String label;
    public String publicLabel; // Same as label for display purposes
    public String unspacedLowercaseLabel; // label without spaces, lowercase
    public ComponentName componentName;
    public int launchedTimes;
    public List<ShortcutInfo> shortcuts; // populated when querying shortcuts (API >= N_MR1)

    public LaunchInfo(String label, ComponentName componentName, int launchedTimes) {
        this.label = label;
        this.publicLabel = label;
        this.unspacedLowercaseLabel = label == null ? "" : label.toLowerCase().replace(" ", "");
        this.componentName = componentName;
        this.launchedTimes = launchedTimes;
        this.shortcuts = null; // lazily loaded elsewhere
    }

    protected LaunchInfo(Parcel in) {
        label = in.readString();
        publicLabel = in.readString();
        unspacedLowercaseLabel = in.readString();
        componentName = in.readParcelable(ComponentName.class.getClassLoader());
        launchedTimes = in.readInt();
        shortcuts = in.readArrayList(ShortcutInfo.class.getClassLoader());
    }

    public static final Creator<LaunchInfo> CREATOR = new Creator<LaunchInfo>() {
        @Override
        public LaunchInfo createFromParcel(Parcel in) {
            return new LaunchInfo(in);
        }

        @Override
        public LaunchInfo[] newArray(int size) {
            return new LaunchInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeString(publicLabel);
        dest.writeString(unspacedLowercaseLabel);
        dest.writeParcelable(componentName, flags);
        dest.writeInt(launchedTimes);
        dest.writeList(shortcuts);
    }

    @Override
    public String getLowercaseString() {
        return publicLabel == null ? "" : publicLabel.toLowerCase();
    }

    @Override
    public String getString() {
        return publicLabel;
    }
}
