package com.example.accessibility.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.accessibility.Adapters.Chat_title_Adapter;
import com.example.accessibility.R;
import com.example.accessibility.Room.AppDatabase;

public class Chat_titles extends AppCompatActivity implements Chat_title_Adapter.ChatTitleClickListener{

Chat_title_Adapter chatTitleAdapter;
RecyclerView recyclerView;
AppDatabase appDatabase;
String appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_titles);

        appDatabase=AppDatabase.getInstance(this);
        recyclerView=findViewById(R.id.recycler_view);

        appName=getIntent().getStringExtra("app_name");


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appDatabase.textsDao().getChatNames(appName).observe(this,chats->{
            chatTitleAdapter=new Chat_title_Adapter(this,chats,this);
            recyclerView.setAdapter(chatTitleAdapter);
        });

    }

    @Override
    public void onChatClick(String chat_title) {
        Intent intent=new Intent(Chat_titles.this,AllTextsScreen.class);
        intent.putExtra("app_name",appName);
        intent.putExtra("chat_name",chat_title);
        startActivity(intent);
    }
}