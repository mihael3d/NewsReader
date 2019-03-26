package com.example.michailgromtsev.newsreader.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class Storage {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_SHOW_INTRO_PAGE = "show intro page";

    public static   boolean needToShowIntro(@NonNull Context context){
        boolean result = true; // Show intro page at first time
        SharedPreferences  mySharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (mySharedPreferences.contains(APP_PREFERENCES_SHOW_INTRO_PAGE)) {
            result = mySharedPreferences.getBoolean(APP_PREFERENCES_SHOW_INTRO_PAGE,true);
    }
    return result;
    }

    public static void setToShowIntro(@NonNull Context context, @NonNull Boolean needToShowIntro){
        SharedPreferences  mySharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putBoolean(APP_PREFERENCES_SHOW_INTRO_PAGE, needToShowIntro);
        editor.commit();
    }
}
