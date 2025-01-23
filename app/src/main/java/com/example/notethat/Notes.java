package com.example.notethat;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Notes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_main);

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


}
