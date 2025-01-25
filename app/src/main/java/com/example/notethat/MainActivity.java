package com.example.notethat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String arr[]= {"this", "is", "first", "line" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView = findViewById(R.id.listView);
        ArrayAdapter ad = new ArrayAdapter(this,android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(ad);

    }
    public void openActivity(View v){
        Intent intent = new Intent(this, Notes.class);
        startActivity(intent);
    }

    public void openSettings(View v){
        Intent intent = new Intent(this, SettingsMain.class);
        startActivity(intent);
    }
}