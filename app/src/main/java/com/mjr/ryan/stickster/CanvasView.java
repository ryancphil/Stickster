package com.mjr.ryan.stickster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Ryan on 11/5/2014.
 */
public class CanvasView extends View {
    Paint paint;
    Canvas canvas;
    Context context;

    //Keeping track of bitmaps being drawn
    private ArrayList<Bitmap> mItemsCollection;
    private ArrayList<Point> mActiveDragPoints;
    private ArrayList<Bitmap> mActiveRects;
    int x_pos = 250;
    int y_pos = 250;

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

        mActiveRects = new ArrayList<Bitmap>(1);
        mActiveDragPoints = new ArrayList<Point>(1);
        mItemsCollection = new ArrayList<Bitmap>();

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
        mItemsCollection.add(bitmap);
//        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.football);
//        mItemsCollection.add(bitmap);
//        BitmapFactory.decodeResource(context.getResources(), R.drawable.monocle);
//        mItemsCollection.add(bitmap);


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
//        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//        canvas.drawBitmap(image, 100, 250, paint);
        Log.e("df", "AHHHHHHH");

        //canvas.drawColor(Color.BLUE, PorterDuff.Mode.CLEAR);
        for (Bitmap bitmap : mItemsCollection) {
            canvas.drawBitmap(bitmap, x_pos, y_pos, paint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int action = event.getActionMasked();
        final int pointer = event.getActionIndex();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Point touchDown = new Point((int) event.getX(), (int) event.getY());
                lookForIntersection(touchDown);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mActiveDragPoints.removeAll(mActiveDragPoints);
                mActiveRects.removeAll(mActiveRects);
                break;
            case MotionEvent.ACTION_MOVE:
                int count = 0;
                for (Bitmap bitmap : mActiveRects) {
                    Point currentPoint = new Point((int) event.getX(count), (int) event.getY(count));
                    moveRect(currentPoint, mActiveDragPoints.get(count), bitmap);
                    count++;
                }
                Log.d(getClass().getName(), "Active Rects" + mActiveRects.size());
                Log.d(getClass().getName(), "Active Points" + mActiveDragPoints.size());
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                touchDown = new Point((int) event.getX(pointer), (int) event.getY(pointer));
                lookForIntersection(touchDown);
                //Log.d(getClass().getName(), "ACTION_POINTER_DOWN" + pointer);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int index = getIntersectionRectIndex(new Point((int) event.getX(pointer), (int) event.getY(pointer)));
                if (index != (-1)) {
                    Bitmap bitmap = mItemsCollection.get(index);
                    mActiveDragPoints.remove(mActiveRects.indexOf(bitmap));
                    mActiveRects.remove(bitmap);
                }

                break;

            default:
                break;
        }
        return true;
    }

    private void lookForIntersection(Point touchDown)
    {
        final int index = getIntersectionRectIndex(touchDown);

        if( index != -1 )
        {
            final Bitmap bitmap = mItemsCollection.get(index);
            if( mActiveRects.indexOf(bitmap) == -1 )
            {
                mActiveDragPoints.add(touchDown);
                mActiveRects.add(mItemsCollection.get(index));
            }
        }
        Log.d(getClass().getName(), "Active Rects" + mActiveRects.size());
        Log.d(getClass().getName(), "Active Points" + mActiveDragPoints.size());

    }




    private int getIntersectionRectIndex(final Point point)
    {
        int index = -1;
        int topParam;
        int rightParam;
        int maxTopParam;
        int maxRightParam;
        for(Bitmap bitmap : mItemsCollection)
        {
            topParam =  x_pos;
            rightParam =  y_pos;
            maxTopParam = topParam + bitmap.getHeight();
            maxRightParam = rightParam + bitmap.getWidth();

            if( point.x > topParam && point.x < maxTopParam && point.y > rightParam && point.y < maxRightParam)
            {
                index = mItemsCollection.indexOf(bitmap);
                break;
            }
        }
        return index;
    }

    private void moveRect(Point currentPoint, Point prevPoint, final Bitmap bitmap)
    {
        int xMoved = currentPoint.x - prevPoint.x;
        int yMoved = currentPoint.y - prevPoint.y;
        x_pos = currentPoint.x;
        y_pos = currentPoint.y;
        mActiveDragPoints.set(mActiveDragPoints.indexOf(prevPoint), currentPoint);
    }

}
