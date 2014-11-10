package com.mjr.ryan.stickster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ryan on 11/5/2014.
 */
public class CanvasView extends View {
    Paint paint;
    Canvas canvas;
    Context context;
    int index;

    private float scaleFactor = 1.0f;
    private int degrees = 0;
    private ScaleGestureDetector scaleGestureDetector;

    //Keeping track of bitmaps being drawn
    public ArrayList<BitmapTriple> mItemsCollection;
    public ArrayList<Point> mActiveDragPoints;
    public BitmapTriple selectedBitmap;

    Point touchDown;
    int tdx;
    int tdy;


    public CanvasView(Context context) {
        super(context);
        this.context = context;
        Log.e("tag", "FIRST CONSTRUCTOR");
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        Log.e("tag", "SECOND CONSTRUCTOR");
        init();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        Log.e("tag", "THIRD CONSTRUCTOR");
        init();
    }

    public void init() {
        this.setDrawingCacheEnabled(true);
        mActiveDragPoints = new ArrayList<Point>();
        mItemsCollection = new ArrayList<BitmapTriple>();

        scaleGestureDetector = new ScaleGestureDetector(context,
                new ScaleListener());

        paint = new Paint();
        canvas = new Canvas();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        this.setBackground(new BitmapDrawable(getResources(), (Bitmap) null));
    }

    public Bitmap get(){
        return this.getDrawingCache();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //Draw the background
        canvas.drawBitmap(((MainActivity) context).photo, 0, 0, paint);
        //Draw all bitmaps inside the mItemsCollection array
        for (BitmapTriple bitmap : mItemsCollection) {
            //Draw the center of the bitmap at the user's finger
            canvas.drawBitmap(bitmap.bitmap, bitmap.x_position - (bitmap.bitmap.getWidth()/2), bitmap.y_position - (bitmap.bitmap.getHeight()/2), paint);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //action is the type of action on the screen UP, DOWN, ect.
        final int action = event.getActionMasked();
        try {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    touchDown = new Point((int) event.getX(), (int) event.getY());
                    lookForIntersection(touchDown);
                    tdx = selectedBitmap.x_position - touchDown.x;
                    tdy = selectedBitmap.y_position - touchDown.y;
                    break;
                case MotionEvent.ACTION_UP:
                    selectedBitmap.previousSet = false;
                    //selectedBitmap = null;
                    selectedBitmap.previousX = selectedBitmap.x_position;
                    selectedBitmap.previousY = selectedBitmap.y_position;
                case MotionEvent.ACTION_CANCEL:
                    mActiveDragPoints.removeAll(mActiveDragPoints);
                    break;
                case MotionEvent.ACTION_MOVE:
                    Point touchMove = new Point((int) event.getX(), (int) event.getY());
                    Log.e("fixed values", tdx + "    " + tdy);
                    int tempx = selectedBitmap.previousX - touchMove.x;
                    int tempy = selectedBitmap.previousY - touchMove.y;
                    Log.e("distance values", tempx + "    " + tempy);
                    if (!selectedBitmap.previousSet) {
                        tempx -= tdx;
                        tempy -= tdy;
                        selectedBitmap.previousSet = true;
                    }
                    selectedBitmap.previousX = (int)event.getX();
                    selectedBitmap.previousY = (int)event.getY();
                    //Point touchMove = new Point((int) event.getX(), (int) event.getY());
                    if(getIntersectionRectIndex(touchMove) != -1 ) {
                        moveBitmap(tempx, tempy, selectedBitmap);
                    }

                    int numPointers = event.getPointerCount();
                    for(int i = 0; i < numPointers; i++){
                        if(i == 1){
                            float dx = touchDown.x - event.getX(i);
                            float dy = touchDown.y - event.getY(i);

                            //This code rotates based on location of second finger around the bitmap
                            degrees = 4 * (int) (Math.toDegrees(Math.atan2(dy, dx)));
                        }
                    }
                    invalidate();

                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                default:
                    break;
            }
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private void lookForIntersection(Point touchDown)
    {
        //This method checks if the touch is over a bitmap on the canvas
        index = getIntersectionRectIndex(touchDown);
        if( index != -1 )
        {
            mActiveDragPoints.add(touchDown);
            selectedBitmap = mItemsCollection.get(index);
            //lastSelected = selectedBitmap;
        }
    }

    private int getIntersectionRectIndex(final Point point)
    {
        int index = -1;
        int leftX;
        int topY;
        int rightX;
        int bottomY;

        //iterate backwards to grab the bitmap on TOP
        for(int i = mItemsCollection.size()-1; i>= 0; i--)
        {
            BitmapTriple bitmap = mItemsCollection.get(i);
            leftX =  bitmap.x_position - (bitmap.bitmap.getWidth()/2);
            topY =  bitmap.y_position - (bitmap.bitmap.getHeight()/2);
            rightX = leftX + bitmap.bitmap.getWidth();
            bottomY = topY + bitmap.bitmap.getHeight();

            if( point.x > leftX && point.x < rightX && point.y > topY && point.y < bottomY)
            {
                index = mItemsCollection.indexOf(bitmap);
                break;
            }
        }
        return index;
    }

    private void moveBitmap(int x, int y, final BitmapTriple bitmap)
    {
        if(bitmap != null) {
            bitmap.x_position -= x;
            bitmap.y_position -= y;
        }
    }

    private void rotateBitmap(int degrees, BitmapTriple bitmap){
        Matrix matrix = new Matrix();

        matrix.postRotate(degrees);

        bitmap.bitmap = Bitmap.createBitmap(bitmap.bitmap , 0, 0, bitmap.bitmap.getWidth(), bitmap.bitmap.getHeight(), matrix, true);
//        bitmap.orig = Bitmap.createBitmap(bitmap.orig, 0, 0, bitmap.orig.getWidth(), bitmap.orig.getHeight(), matrix, true);
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();

            // don't let the object get too small or too large.
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            if(selectedBitmap != null) {
                selectedBitmap.bitmap = Bitmap.createScaledBitmap(selectedBitmap.orig, (int) (selectedBitmap.width * scaleFactor), (int)(selectedBitmap.height * scaleFactor), false);
                rotateBitmap(degrees,selectedBitmap);
            }
            invalidate();
            return true;
        }
    }
}
