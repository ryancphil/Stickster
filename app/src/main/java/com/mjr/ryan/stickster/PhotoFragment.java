package com.mjr.ryan.stickster;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by Ryan on 10/4/2014.
 * this fragment should get inflated when a photo is selected or taken by replacing the CameraFragment in the framelayout
 */
public class PhotoFragment extends Fragment{
    ImageView imgPhoto;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_photo, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CanvasView canvasView = new CanvasView(this.getActivity());
        canvasView.invalidate();

//        imgPhoto = (ImageView)getView().findViewById(R.id.photoCanvas);
//        imgPhoto.setImageBitmap(((MainActivity)getActivity()).photo);



        //Implement button for test_sticker
        ImageButton launcher = (ImageButton) getView().findViewById(R.id.launcher);
        launcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("launcher", "launcher");

            }
        });

        //Implement button for lightsaber
        ImageButton lightsaber = (ImageButton) getView().findViewById(R.id.lightsaber);
        lightsaber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("lightsaber", "lightsaber");
            }
        });

        //Implement button for monocle
        ImageButton monocle = (ImageButton) getView().findViewById(R.id.monocle);
        monocle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("monocle", "monocle");
            }
        });

        //Implement button for sandwich
        ImageButton sandwich = (ImageButton) getView().findViewById(R.id.sandwich);
        sandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("sandwich", "sandwich");
            }
        });

        //Implement button for football
        ImageButton football = (ImageButton) getView().findViewById(R.id.football);
        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("football", "football");
            }
        });

        //Implement button for soccerball
        ImageButton soccerball = (ImageButton) getView().findViewById(R.id.soccerball);
        soccerball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("soccerball", "soccerball");
            }
        });
    }
}


