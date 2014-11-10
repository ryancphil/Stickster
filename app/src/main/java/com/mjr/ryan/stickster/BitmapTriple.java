package com.mjr.ryan.stickster;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Ryan on 11/6/2014.
 */
public class BitmapTriple {

    public Bitmap bitmap;
    public int x_position;
    public int y_position;

    public int height;
    public int width;

    public Bitmap orig;

    boolean previousSet = false;
    int previousX = 250;
    int previousY = 250;


    public BitmapTriple(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.orig = bitmap;
        x_position = 250;
        y_position = 250;
        height = bitmap.getHeight();
        width = bitmap.getWidth();
    }
}
