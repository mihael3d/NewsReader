package com.example.michailgromtsev.newsreader.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Storage {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_SHOW_INTRO = "show_intro_activity_state";

    //private SharedPreferences sharedPreferences;

    private static boolean needToShowIntro(){
       // SharedPreferences sharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        return true;
    }


}
