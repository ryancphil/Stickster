package com.mjr.ryan.stickster;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        //super.onViewCreated(view, savedInstanceState);

        imgPhoto = (ImageView)getView().findViewById(R.id.photoCanvas);

        imgPhoto.setImageBitmap(((MainActivity)getActivity()).photo);
    }
}
