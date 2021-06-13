package com.example.accessibility.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.accessibility.R;
import com.example.accessibility.Room.AppDatabase;
import com.example.accessibility.Adapters.TextsScreenAdapter;

public class AllTextsScreen extends AppCompatActivity {

    AppDatabase appDatabase;
    String app_name,chat_title;

    RecyclerView recyclerView;

    TextsScreenAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_texts_screen);


        recyclerView=findViewById(R.id.recycler_view);
        appDatabase=AppDatabase.getInstance(this);
        app_name=getIntent().getStringExtra("app_name");
        chat_title=getIntent().getStringExtra("chat_name");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(chat_title!=null) {
            appDatabase.textsDao().getTextOfWhatsapp(app_name, chat_title).observe(this, texts -> {
                adapter = new TextsScreenAdapter(this, texts);
                recyclerView.setAdapter(adapter);
            });
        }
        if(chat_title==null){
            appDatabase.textsDao().getTextOfInsta(app_name).observe(this,texts->{
                adapter=new TextsScreenAdapter(this,texts);
                recyclerView.setAdapter(adapter);
            });
        }



    }
}