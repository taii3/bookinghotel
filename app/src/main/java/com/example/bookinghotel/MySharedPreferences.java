package com.example.bookinghotel;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreferences {
    private static final String MY_SHARED_FREFERENCES = "MY_SHARED_FREFERENCES";
    private Context mContext;
    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }
    public void putBooleanValue(String key, boolean value){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_FREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARED_FREFERENCES, 0);
        return sharedPreferences.getBoolean(key, false);
    }
}
