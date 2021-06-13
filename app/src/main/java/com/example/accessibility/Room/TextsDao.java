package com.example.accessibility.Room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.accessibility.Model.Texts;

import java.util.List;
@Dao
public interface TextsDao {


    @Query("Select distinct app_name from Texts")
    LiveData<List<String>> getAppNames();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertText(Texts text);

    @Query("Select distinct chat_name from Texts where app_name=:appName")
    LiveData<List<String>> getChatNames(String appName);

    @Query("Select name from Texts where app_name=:appName and chat_name=:chat_title")
    LiveData<List<String>> getTextOfWhatsapp(String appName,String chat_title);

    @Query("Select name from Texts where app_name=:appName")
    LiveData<List<String>> getTextOfInsta(String appName);

}
