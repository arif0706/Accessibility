package com.example.accessibility.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Texts",indices = @Index(unique = true,value = {"name"}))
public class Texts {

    @PrimaryKey(autoGenerate = true)
     public int _id;

    @ColumnInfo(name = "name")
    public String text;

    @ColumnInfo(name = "app_name")
   public String app_name;

    @ColumnInfo(name="chat_name")
    public String chat_name;
}
