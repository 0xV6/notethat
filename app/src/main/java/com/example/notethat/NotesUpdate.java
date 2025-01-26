package com.example.notethat;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NotesUpdate extends AppCompatActivity {

    private EditText noteEditText;
    private EditText noteTitle;
    private ScrollView scrollView;

    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);

        noteId = getIntent().getIntExtra("note_id", -1);
        noteEditText = findViewById(R.id.noteText);
        noteTitle = findViewById(R.id.noteTitle);
        scrollView = findViewById(R.id.scrollView);

        scrollView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                noteEditText.requestFocus();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(noteEditText, InputMethodManager.SHOW_IMPLICIT);
            }
            return false;
        });

        Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(v -> showPopupMenu(v));

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        noteEditText.setText(content);
        noteTitle.setText(title);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            return false;
        });

        popupMenu.show();
    }


    private void updateNote() {
        EditText noteEditText = findViewById(R.id.noteText);
        EditText noteTitleText = findViewById(R.id.noteTitle);
        final String description = noteEditText.getText().toString().trim();
        final String title = noteTitleText.getText().toString().trim();
        final int noteId = this.noteId;

        String url = "https://d524-2401-4900-596b-e17b-9c1c-5906-d4f1-6fa8.ngrok-free.app/update";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (jsonResponse.getString("status").equals("success")) {
                            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        String errorMessage = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("note_id", String.valueOf(noteId));

                if (!title.isEmpty()) {
                    params.put("title", title);
                }
                if (!description.isEmpty()) {
                    params.put("description", description);
                }

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        String message = "User pressed the back button";
        updateNote();
        super.onBackPressed();
    }
}
