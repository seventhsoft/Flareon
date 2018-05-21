package com.seventhsoft.kuni.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by olimpia on 19/12/16.
 */

public class ConexionInternetPreference {

    private static ConexionInternetPreference conexionInternetPreference;
    private static SharedPreferences sharedPreferences;

    public static ConexionInternetPreference getInstance(Context context) {
        if (conexionInternetPreference == null) {
            conexionInternetPreference = new ConexionInternetPreference(context);
        }
        return conexionInternetPreference;
    }
    private ConexionInternetPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("InternetPreference", Context.MODE_PRIVATE);
    }

    public static void saveData(String key,Boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public Boolean getData(String key) {
        boolean internet;
        if (sharedPreferences!= null) {
            internet= sharedPreferences.getBoolean(key, false);
        }else {
            internet = false;
        }
        return internet;
    }
}
