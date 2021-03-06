package com.example.roomdatabinding.roomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MyUser.class},version = 1,exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract MyDao myDao();
}
