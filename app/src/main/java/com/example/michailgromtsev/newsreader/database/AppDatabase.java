package com.example.michailgromtsev.newsreader.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {NewsEntity.class}, version = 1)
@TypeConverters({DateConverter.class})

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase singelton;

    private static final String DATABASE_NAME = "NewsRoomDb.db";

    public abstract NewsDao newsDao();

    public static AppDatabase getAppDatavese(Context context) {
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
