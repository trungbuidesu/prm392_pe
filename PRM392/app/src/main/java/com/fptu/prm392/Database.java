package com.fptu.prm392;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Job.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static Database INSTANCE;
    public abstract DAO Dao();
    public static Database getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "JobDatabase") //inMemoryDatabaseBuilder
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
    public static void destroyInstance() {
        INSTANCE = null;
    }
}
