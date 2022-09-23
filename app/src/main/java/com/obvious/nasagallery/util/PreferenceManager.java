package com.obvious.nasagallery.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager implements NasaGalleryConstants {

    // Shared Preference file name
    private static final String PREF_NAME = "NasaGalleryApp";

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Constructor
    public PreferenceManager(Context context) {
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(String key) {
        return pref.getString(key, "");
    }


    public void saveLongValue(String key, long value) {
        editor.putLong(key, (Long) value);
        editor.commit();
    }

    public long getLongValue(String key) {
        return pref.getLong(key, 0);
    }

}
