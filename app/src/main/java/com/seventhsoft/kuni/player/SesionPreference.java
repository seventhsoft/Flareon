package com.seventhsoft.kuni.player;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by olimpia on 15/02/17.
 */

public class SesionPreference {
    private static SesionPreference sesionPreference;
    private static SharedPreferences sharedPreferences;


    public static SesionPreference getInstance(Context context) {
        if (sesionPreference == null) {
            sesionPreference = new SesionPreference(context);
        }
        return sesionPreference;
    }

    private SesionPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("SesionPreference", Context.MODE_PRIVATE);
    }

    public static void saveData(String key, Boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public Boolean getData(String key) {
        boolean internet;
        if (sharedPreferences != null) {
            internet = sharedPreferences.getBoolean(key, false);
        } else {
            internet = false;
        }
        return internet;
    }
}
