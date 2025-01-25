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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Notes extends AppCompatActivity {

    private EditText noteEditText;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);

        noteEditText = findViewById(R.id.noteText);
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
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            return false;
        });

        popupMenu.show();
    }

    private void sendStringToServer() {
        final String message = noteEditText.getText().toString().trim();

        if (!message.isEmpty()) {
            String url = "https://65ad-2401-4900-596b-e17b-88f5-71d9-9b83-61b9.ngrok-free.app/save";
            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();
                    }) {


                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("message", message);
                    return params;
                }
            };
            queue.add(stringRequest);
        } else {
            Toast.makeText(this, "Note is empty", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        String message = "User pressed the back button";
        sendStringToServer();

        super.onBackPressed();
    }
}
