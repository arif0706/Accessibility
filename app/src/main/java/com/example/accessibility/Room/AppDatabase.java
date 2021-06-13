package com.example.accessibility.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.accessibility.Model.Texts;

@Database(entities = Texts.class,version = 1)
public abstract class AppDatabase extends RoomDatabase {

    static String DB_NAME="Texts_DB";
    static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract TextsDao textsDao();

}
