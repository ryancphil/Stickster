package com.mjr.ryan.stickster;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.mjr.ryan.stickster.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraFragment extends Fragment {

    // Native camera.
    private Camera mCamera;

    // View to display the camera output.
    private CameraPreview mPreview;

    // Reference to the containing view.
    private View mCameraView;

    /**
     * Default empty constructor.
     */
    public CameraFragment(){
        super();
    }

    /**
     * Static factory method
     * @param sectionNumber
     * @return
     */
    public static CameraFragment newInstance(int sectionNumber) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        args.putInt("ARG_SECTION_NUMBER", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * OnCreateView fragment override
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        // Trap the capture button.
        Button captureButton = (Button) view.findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );

        Button galleryButton = (Button) view.findViewById(R.id.button_gallery);
        galleryButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,0);
                    }
                }
        );

        final Button flashButton = (Button) view.findViewById(R.id.button_flash);
        flashButton.setTag(0);
        flashButton.setText("Auto");
        flashButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int status =(Integer) v.getTag();
                        if(status == 0) {
                            flashButton.setText("Off");
                            mPreview.setFlash(1);
                            v.setTag(1);
                        }
                        else if (status == 1){
                            flashButton.setText("On");
                            mPreview.setFlash(2);
                            v.setTag(2);
                        }
                        else if (status == 2){
                            flashButton.setText("Auto");
                            mPreview.setFlash(0);
                            v.setTag(0);
                        }

                    }
                }
        );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean opened = safeCameraOpenInView(null);
        if(opened == false){
            Log.d("CameraGuide","Error, Camera failed to open");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCameraAndPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseCameraAndPreview();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String tempPath = getPath(selectedImageUri, getActivity());
            BitmapFactory.Options btmapOptions = new BitmapFactory.Options();

            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Bitmap bp = BitmapFactory.decodeFile(tempPath, btmapOptions);
            Matrix matrix = new Matrix();
            if (bp.getHeight() < bp.getWidth()) {
                matrix.postRotate(90);
                bp = Bitmap.createScaledBitmap(bp, display.getHeight(), display.getWidth(), false);
            }
            else {
                matrix.postRotate(0);
                bp = Bitmap.createScaledBitmap(bp, display.getWidth(), display.getHeight(), false);
            }




            ((MainActivity)getActivity()).photo = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);

            PhotoFragment photoFragment = new PhotoFragment();

            getFragmentManager().beginTransaction()
                    .replace(R.id.frag_content, photoFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * Recommended "safe" way to open the camera.
     * @param view
     * @return
     */
    private boolean safeCameraOpenInView(View view) {
        boolean qOpened = false;
        releaseCameraAndPreview();
        mCamera = getCameraInstance();
        mCameraView = view;
        qOpened = (mCamera != null);
        if(qOpened == true){
            mPreview = new CameraPreview(getActivity().getBaseContext(), mCamera, getView());
            FrameLayout preview = (FrameLayout) getView().findViewById(R.id.camera_preview);
            preview.addView(mPreview);
            mPreview.startCameraPreview();
        }
        return qOpened;
    }

    /**
     * Safe method for getting a camera instance.
     * @return
     */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * Clear any existing preview / camera.
     */
    private void releaseCameraAndPreview() {

        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();
            mCamera = null;
        }
        if(mPreview != null){
            mPreview.destroyDrawingCache();
            mPreview.mCamera = null;
        }
    }

    /**
     * Picture Callback for handling a picture capture and saving it out to a file.
     */
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Bitmap bp = BitmapFactory.decodeByteArray(data, 0, data.length);

            Log.e("PictureDimensions", "Picture Height: " + bp.getHeight() + "\tWidth: " + bp.getWidth());

            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            bp = Bitmap.createScaledBitmap(bp, display.getHeight(), display.getWidth(), false);

            Matrix matrix = new Matrix();
            if (bp.getHeight() < bp.getWidth()) {
                matrix.postRotate(90);
            }
            else
                matrix.postRotate(0);

            ((MainActivity)getActivity()).photo = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);

            if(bp==null){
                Toast.makeText((getActivity()).getApplicationContext(), "not taken", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText((getActivity()).getApplicationContext(), "taken", Toast.LENGTH_SHORT).show();
            }
            //cameraObject.release();
            PhotoFragment photoFragment = new PhotoFragment();

            getFragmentManager().beginTransaction()
                    .replace(R.id.frag_content, photoFragment)
                    .addToBackStack(null)
                    .commit();
        }
    };

}