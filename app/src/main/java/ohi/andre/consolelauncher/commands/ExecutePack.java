package ohi.andre.consolelauncher.commands;

import android.content.Context;

import java.util.ArrayList;

import ohi.andre.consolelauncher.managers.AppsManager;
import ohi.andre.consolelauncher.managers.LaunchInfo;
// You DO need to import the parent class to reference its nested class.
import ohi.andre.consolelauncher.managers.xml.classes.XMLPrefsSave;

@SuppressWarnings("deprecation")
public abstract class ExecutePack {

    public Object[] args;
    public Context context;
    public CommandGroup commandGroup;

    public int currentIndex = 0;

    public ExecutePack(CommandGroup group) {
        this.commandGroup = group;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> c) {
        return (T) get();
    }

    public <T> T get(Class<T> c, int index) {
        if(index < args.length) return (T) args[index];
        return null;
    }

    public Object get() {
        if(currentIndex < args.length) return args[currentIndex++];
        return null;
    }

    public String getString() {
        Object obj = get();
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    public int getInt() {
        Object obj = get();
        if (obj instanceof Integer) {
            return (int) obj;
        }
        return 0;
    }

    public boolean getBoolean() {
        Object obj = get();
        if (obj instanceof Boolean) {
            return (boolean) obj;
        }
        return false;
    }

    public ArrayList getList() {
        Object obj = get();
        if (obj instanceof ArrayList) {
            return (ArrayList) obj;
        }
        return null;
    }

    public XMLPrefsSave getPrefsSave() {
        Object obj = get();
        if (obj instanceof XMLPrefsSave) {
            return (XMLPrefsSave) obj;
        }
        return null;
    }

    // FIXED: The reference is now simply LaunchInfo.
    // This tells the Java compiler to look for a static nested class named 'LaunchInfo'
    // inside the 'AppsManager' class, which is exactly how Kotlin compiles it.
    public LaunchInfo getLaunchInfo() {
        Object obj = get();
        if (obj instanceof LaunchInfo) {
            return (LaunchInfo) obj;
        }
        // If obj is null or not a LaunchInfo, return null instead of throwing ClassCastException
        return null;
    }

    public void set(Object[] args) {
        this.args = args;
    }

    public void clear() {
        args = null;
        currentIndex = 0;
    }
}
