package com.rexindustries.firechat.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private final String PREF_NAME = "FireChat";
    private Context context;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public boolean saveToPref(String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String loadFromPref(String key) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(key, "");
    }

    public void clearPref() {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().apply();
    }
}
