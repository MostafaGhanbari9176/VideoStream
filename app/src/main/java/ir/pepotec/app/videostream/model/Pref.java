package ir.pepotec.app.videostream.model;

import android.content.SharedPreferences;

import ir.pepotec.app.videostream.G;

/**
 * Created by MAHNAZ on 12/2/2017.
 */

public class Pref {

    public static boolean getBollValue(String name, boolean defValue) {
        return G.preferences.getBoolean(name, defValue);
    }

    public static boolean saveBollValue(String name, boolean value) {
        SharedPreferences.Editor editor = G.preferences.edit();
        editor.putBoolean(name, value);
        return editor.commit();
    }

    public static String getStringValue(String name, String defValue) {
        return G.preferences.getString(name, defValue);
    }

    public static boolean saveStringValue(String name, String value) {
        SharedPreferences.Editor editor = G.preferences.edit();
        editor.putString(name, value);
       return editor.commit();
    }

    public static int getIntegerValue(String name, int defValue) {
        return G.preferences.getInt(name, defValue);
    }

    public static boolean saveIntegerValue(String name, int value) {
        SharedPreferences.Editor editor = G.preferences.edit();
        editor.putInt(name, value);
        return editor.commit();
    }


    public static Long getLongValue(String name, long defValue) {
        return G.preferences.getLong(name, defValue);
    }

    public static boolean saveLongValue(String name, long value) {
        SharedPreferences.Editor editor = G.preferences.edit();
        editor.putLong(name, value);
        return editor.commit();
    }

    public static boolean removeValue(String name){
        SharedPreferences.Editor editor = G.preferences.edit();
        editor.remove(name);
        return editor.commit();
    }
}
