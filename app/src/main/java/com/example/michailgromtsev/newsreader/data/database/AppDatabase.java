package com.example.michailgromtsev.newsreader.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NewsEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singelton;

    private static final String DATABASE_NAME = "NewsRoom.db";

    public abstract NewsDao newsDao();

    public static AppDatabase getInstance(Context context) {
        if (singelton == null) {
            synchronized (AppDatabase.class) {
                if (singelton == null) {
                    singelton = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return singelton;
    }
}
