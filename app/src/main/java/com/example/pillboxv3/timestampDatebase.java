package com.example.pillboxv3;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {timestamp.class}, version = 7, exportSchema = false)
public abstract class timestampDatebase extends RoomDatabase
{
    public abstract timestampDAO getTimestampDAO();
    public static volatile timestampDatebase instance;

    public static timestampDatebase getInstance(Context context)
    {
        if(instance == null)
        {
            synchronized (timestampDatebase.class)
            {
                if (instance == null)
                {
                    instance = Room.databaseBuilder(context.getApplicationContext(),timestampDatebase.class,"pillboxDatabase").fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}

