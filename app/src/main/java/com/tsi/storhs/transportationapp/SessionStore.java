package com.tsi.storhs.transportationapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;

import javax.inject.Singleton;

public class SessionStore {

    private SharedPreferences prefs;

    public SessionStore(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public String getusename() {
        String usename = prefs.getString("usename", "");
        return usename;
    }
}
