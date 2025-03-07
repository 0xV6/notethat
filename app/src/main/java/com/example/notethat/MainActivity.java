package com.example.notethat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
//    String arr[]= {"this", "is", "first", "line" };
//    String[] titles = {"note 1", "note 2", "note 3"};
//    String[] content = {"this is note 1", "this is note 2", "this is note 3"};

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> contents = new ArrayList<>();
    ArrayList<Integer> noteIds = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        try {
            listView = findViewById(R.id.listView);

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view, position, id) -> {
                try {
                    System.out.println("Item clicked at position: " + position);
                    System.out.println("Title: " + titles.get(position));
                    System.out.println("Content: " + contents.get(position));
                    System.out.println("Note ID: " + noteIds.get(position));

                    Intent intent = new Intent(MainActivity.this, NotesUpdate.class);
                    intent.putExtra("title", titles.get(position));
                    intent.putExtra("content", contents.get(position));
                    intent.putExtra("note_id", noteIds.get(position));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error opening note: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ThemesSettings), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

//            getNotes();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error initializing app: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
    private void getNotes() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://db6e-2401-4900-a17b-d7ce-e8c2-9e8b-5a67-8c47.ngrok-free.app/get-notes";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray notesArray = response.getJSONArray("data");

                        titles.clear();
                        contents.clear();
                        noteIds.clear();
                        ArrayList<String> displayItems = new ArrayList<>();

//                        int noteId = 0;
                        for (int i = 0; i < notesArray.length(); i++) {
                            JSONObject note = notesArray.getJSONObject(i);

                            String title = note.getString("title");
                            String description = note.getString("description");
                            int noteId = note.getInt("note_id");
                            noteIds.add(noteId);
                            titles.add(title);
                            contents.add(description);
                            displayItems.add(title + "\n" + description);

                        }

                        adapter.clear();
                        adapter.addAll(displayItems);
                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,
                                "Error loading notes",
                                Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(MainActivity.this,
                            "Cannot connect to server",
                            Toast.LENGTH_SHORT).show();
                }
        );
        queue.add(request);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        getNotes();
    }
    public void openActivity(View v){
        Intent intent = new Intent(this, Notes.class);
        startActivity(intent);
    }

    public void openSettings(View v){
        Intent intent = new Intent(this, SettingsMain.class);
        startActivity(intent);
    }

    public void updateNotes(View v){
        Intent intent = new Intent(this, NotesUpdate.class);
        startActivity(intent);
    }
}