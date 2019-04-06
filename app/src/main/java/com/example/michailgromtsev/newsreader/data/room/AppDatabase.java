package com.example.michailgromtsev.newsreader.data.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {NewsEntity.class}, version = 2)
@TypeConverters({DateConverter.class})

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase singelton;

    private static final String DATABASE_NAME = "NewsRoomDb.db";

    public abstract NewsDao newsDao();

    public static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL ("ALTER TABLE news "
                    + "ADD COLUMN section TEXT");
//                    ("ALTER TABLE NewsEntity ADD COLUMN section TEXT");
        }
    };

    public static AppDatabase getAppDatavese(Context context) {
        if (singelton == null) {
            synchronized (AppDatabase.class) {
                if (singelton == null) {
                    singelton = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return singelton;
    }
}
