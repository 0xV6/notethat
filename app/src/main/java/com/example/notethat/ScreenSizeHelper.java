package com.example.notethat;

import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenSizeHelper {

    public static int getScreenLines(Context context, float textSize) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        int screenHeightPixels = displayMetrics.heightPixels;

        Paint paint = new Paint();
        paint.setTextSize(textSize);
        float lineHeight = paint.getFontMetrics().bottom - paint.getFontMetrics().top;

        return (int) (screenHeightPixels / lineHeight);
    }
}

