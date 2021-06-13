package com.example.accessibility.View;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accessibility.Adapters.Adapter;
import com.example.accessibility.R;
import com.example.accessibility.Room.AppDatabase;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  implements  Adapter.AppTitleClickListener {

    AppDatabase appDatabase;
    RecyclerView recyclerView;
    LinearLayout linearLayout;

    Button turn_on;

    int accessEnabled = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        appDatabase=AppDatabase.getInstance(this);

        recyclerView=findViewById(R.id.recycler_view);
        linearLayout=findViewById(R.id.access_message);
        turn_on=findViewById(R.id.turn_on);



        showMessage(checkAccessibilityPermission());


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appDatabase.textsDao().getAppNames().observe(this,app_names->{
            Adapter adapter=new Adapter(this,app_names,this);
            recyclerView.setAdapter(adapter);
        });


        turn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Askpermission();
            }
        });



    }

    private void Askpermission() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    void showMessage(boolean show){
        if(show){
            linearLayout.setVisibility(View.VISIBLE);
        }
        else{
            linearLayout.setVisibility(View.GONE);
        }

    }
    public boolean checkAccessibilityPermission () {

        try {
            accessEnabled = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if (accessEnabled == 0) return true;
        else return false;
    }


    @Override
    public void onAppTitleClick(String app_name) {
        if(app_name.equals("com.whatsapp")) {
            Intent intent = new Intent(MainActivity.this, Chat_titles.class);
            intent.putExtra("app_name", app_name);
            startActivity(intent);
        }
        if(app_name.equals("com.instagram.android")){
            Intent intent=new Intent(MainActivity.this,AllTextsScreen.class);
            intent.putExtra("app_name",app_name);
            startActivity(intent);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
   showMessage(checkAccessibilityPermission());
    }
}