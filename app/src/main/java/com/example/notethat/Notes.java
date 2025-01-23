package com.example.notethat;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Connection;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Notes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);

        View layout = findViewById(R.id.scrollView);
        EditText noteEditText = findViewById(R.id.editTextTextMultiLine2);

        layout.setOnClickListener(v -> {
            noteEditText.requestFocus();
            InputMethodManager imm = (InputMethodManager)
            getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(noteEditText, InputMethodManager.SHOW_IMPLICIT);
        });

        Button button7 = findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
        float textSizeSp = 16f;

        float textSizePx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSizeSp, getResources().getDisplayMetrics());

        int lines = ScreenSizeHelper.getScreenLines(this, textSizePx);

        Toast.makeText(this, "Number of lines that fit on the screen: " + lines, Toast.LENGTH_LONG).show();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.action_one:
//                        // Handle action one
//                        return true;
//                    case R.id.action_two:
//                        // Handle action two
//                        return true;
//                    case R.id.action_three:
//                        // Handle action three
//                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void sendStringToServer(String message) {
        String url = "http://127.0.0.1:6000/save";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    Toast.makeText(this, "Error sending message", Toast.LENGTH_SHORT).show();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("message", message);
                return params;
            }
        };

        queue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        String message = "User pressed the back button";
        sendStringToServer(message);

        super.onBackPressed();
    }






}
