package com.example.trackingsurat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {

    public final static String KEY_USER_TEREGISTER = "user", KEY_PASS_TEREGISTER = "pass";
    public final static String KEY_USERNAME_SEDANG_LOGIN ="Username_logged_in";
    public final static String KEY_STATUS_SEDANG_LOGIN = "Status_logged_in";

    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getKeyUserTeregister() {
        return KEY_USER_TEREGISTER;
    }

    public static String getKeyPassTeregister() {
        return KEY_PASS_TEREGISTER;
    }

    public static String getKeyUsernameSedangLogin() {
        return KEY_USERNAME_SEDANG_LOGIN;
    }

    public static String getKeyStatusSedangLogin() {
        return KEY_STATUS_SEDANG_LOGIN;
    }
}
