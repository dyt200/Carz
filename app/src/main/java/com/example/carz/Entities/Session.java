package com.example.carz.Entities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//TODO Implement this class for session
//https://stackoverflow.com/questions/20678669/how-to-maintain-session-in-android

public class Session {
    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserId(int userId) {
        prefs.edit().putInt("user_id", userId).apply();
    }

    public void setName(String name) {
        prefs.edit().putString("user_name", name).apply();
    }

    public int getUserId() {
        int userId = prefs.getInt("user_name", 0);
        return userId;
    }

    public String getName() {
        String name = prefs.getString("user_name", "");
        return name;
    }
}
