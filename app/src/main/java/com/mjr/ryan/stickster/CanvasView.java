package com.mjr.ryan.stickster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Ryan on 11/5/2014.
 */
public class CanvasView extends View {
    Paint paint;
    Canvas canvas;
    Context context;

    public CanvasView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();

    }

    public void init() {

        paint = new Paint();
        canvas = new Canvas();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        this.setBackground(new BitmapDrawable(getResources(), (Bitmap) null));

        Log.e("INIT", "INIT CALLED HERE!");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(((MainActivity) context).photo, 0, 0, paint);
        //Below code needs to be modified to be called on button presses
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        canvas.drawBitmap(image, 100, 250, paint);
        Log.e("df","AHHHHHHH");
    }
}
