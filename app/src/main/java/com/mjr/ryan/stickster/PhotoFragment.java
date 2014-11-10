package com.mjr.ryan.stickster;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ryan on 10/4/2014.
 * this fragment should get inflated when a photo is selected or taken by replacing the CameraFragment in the framelayout
 */
public class PhotoFragment extends Fragment{

    CanvasView canvasView;
    FileOutputStream fos = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("PhotoFragment", "onActivityCreated");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("PhotoFragment", "onCreate");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        Log.e("PhotoFragment", "onCreateView");
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("PhotoFragment", "onViewCreated");
        final Context context = this.getActivity();
        canvasView = (CanvasView)this.getActivity().findViewById(R.id.canvasView);
        canvasView.invalidate();

        //Implement button for test_sticker
        ImageButton launcher = (ImageButton) getView().findViewById(R.id.launcher);
        launcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("launcher", "launcher");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for lightsaber
        ImageButton lightsaber = (ImageButton) getView().findViewById(R.id.lightsaber);
        lightsaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("lightsaber", "lightsaber");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lightsaber);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for monocle
        ImageButton monocle = (ImageButton) getView().findViewById(R.id.monocle);
        monocle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("monocle", "monocle");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.monocle);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for sandwich
        ImageButton sandwich = (ImageButton) getView().findViewById(R.id.sandwich);
        sandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("sandwich", "sandwich");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sandwich);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for football
        ImageButton football = (ImageButton) getView().findViewById(R.id.football);
        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("football", "football");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.football);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for soccerball
        ImageButton soccerball = (ImageButton) getView().findViewById(R.id.soccerball);
        soccerball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("soccerball", "soccerball");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.soccerball);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for delete
        Button delete = (Button) getView().findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("delete", "delete");
                if(canvasView.mItemsCollection.size() > 0 && canvasView.selectedBitmap != null) {
                    int selectedIndex = canvasView.mItemsCollection.indexOf(canvasView.selectedBitmap);
                    canvasView.mItemsCollection.remove(selectedIndex);
                }
                canvasView.invalidate();
            }
        });

        Button save = (Button) getView().findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("save", "save");
                // Save Bitmap to File
                canvasView.invalidate();
                try {
                    fos = new FileOutputStream(getOutputMediaFile());
                    //Bitmap temp = ((MainActivity) context).photo;
                    Bitmap temp = canvasView.get();
                    temp.compress(Bitmap.CompressFormat.PNG, 100, fos);

                    fos.flush();
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                            fos = null;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Button flip = (Button) getView().findViewById(R.id.flip);
        flip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("flip", "flip");
                if(canvasView.selectedBitmap != null) {
                    Matrix m = new Matrix();
                    m.preScale(-1, 1);
                    Bitmap src = canvasView.selectedBitmap.bitmap;
                    Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
                    dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
                    canvasView.selectedBitmap.bitmap = dst;

                    //flip origin image as well for scaling
                    Bitmap src2 = canvasView.selectedBitmap.orig;
                    Bitmap dst2 = Bitmap.createBitmap(src2, 0, 0, src2.getWidth(), src2.getHeight(), m, false);
                    dst2.setDensity(DisplayMetrics.DENSITY_DEFAULT);
                    canvasView.selectedBitmap.orig = dst2;

                    canvasView.invalidate();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("PhotoFragment", "onDestroy");
        canvasView.destroyDrawingCache();
    }

    private File getOutputMediaFile(){

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Stickster");

        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.e("Camera Guide", "Required media storage does not exist");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        //mediaFile.mkdir();
        new SingleMediaScanner(getActivity(), mediaFile);

        //DialogHelper.showDialog( "Success!","Your picture has been saved!",getActivity());
        Log.e("path location", String.valueOf(mediaFile));

        return mediaFile;
    }
}


