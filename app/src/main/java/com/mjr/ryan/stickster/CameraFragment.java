package com.mjr.ryan.stickster;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

    float scale;

    // Native camera.
    private Camera mCamera;
    private static boolean facingFront = false;
    private static int flashMode = 1;

    // View to display the camera output.
    private CameraPreview mPreview;

    // Reference to the containing view.
    private View mCameraView;

    FrameLayout preview;

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
        scale = ((MainActivity)getActivity()).scale;

        // Trap the capture button.
        final Button captureButton = (Button) view.findViewById(R.id.button_capture);;
        captureButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) captureButton.getLayoutParams();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int pixels = (int) (110 * scale + 0.5f);
                    lp.width=pixels;
                    lp.height=pixels;
                    pixels = (int) (0 * scale + 0.5f);
                    lp.bottomMargin=pixels;
                    captureButton.setLayoutParams(lp);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    int pixels = (int) (100 * scale + 0.5f);
                    lp.width=pixels;
                    lp.height=pixels;
                    pixels = (int) (5 * scale + 0.5f);
                    lp.bottomMargin=pixels;
                    captureButton.setLayoutParams(lp);

                    if(facingFront && flashMode == 2){
                        FrontFlashFragment flashFragment = new FrontFlashFragment();

                        getFragmentManager().beginTransaction()
                                .add(R.id.frag_content,flashFragment)
                                .commit();
                    }

                    mCamera.takePicture(null, null, mPicture);

                    return true;
                }
                return false;
            }
        });

        final Button galleryButton = (Button) view.findViewById(R.id.button_gallery);
        galleryButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) galleryButton.getLayoutParams();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int pixels = (int) (62 * scale + 0.5f);
                    lp.width=pixels;
                    lp.height=pixels;
                    pixels = (int) (19 * scale + 0.5f);
                    lp.topMargin=pixels;
                    lp.rightMargin=pixels;
                    galleryButton.setLayoutParams(lp);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    int pixels = (int) (50 * scale + 0.5f);
                    lp.width=pixels;
                    lp.height=pixels;
                    pixels = (int) (25 * scale + 0.5f);
                    lp.topMargin=pixels;
                    lp.rightMargin=pixels;
                    galleryButton.setLayoutParams(lp);

                    Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,0);
                    return true;
                }
                return false;
            }
        });

        final Button cameraToggle = (Button) view.findViewById(R.id.camera_toggle);
        cameraToggle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) cameraToggle.getLayoutParams();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int pixels = (int) (62 * scale + 0.5f);
                    lp.width=pixels;
                    lp.height=pixels;
                    pixels = (int) (19 * scale + 0.5f);
                    lp.rightMargin=pixels;
                    lp.bottomMargin=pixels;
                    cameraToggle.setLayoutParams(lp);
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    int pixels = (int) (50 * scale + 0.5f);
                    lp.width=pixels;
                    lp.height=pixels;
                    pixels = (int) (25 * scale + 0.5f);
                    lp.rightMargin=pixels;
                    lp.bottomMargin=pixels;
                    cameraToggle.setLayoutParams(lp);
                    if(Camera.getNumberOfCameras() > 1){
                        facingFront = !facingFront;
                        safeCameraOpenInView(null);
                    }
                    return true;
                }
                return false;
            }
        });

        final Button flashButton = (Button) view.findViewById(R.id.button_flash);
        flashButton.setTag(flashMode);
        if(flashMode == 0){
            flashButton.setBackgroundResource(R.drawable.flash_auto);
        }
        else if(flashMode == 1){
            flashButton.setBackgroundResource(R.drawable.flash_off);
        }
        else {
            flashButton.setBackgroundResource(R.drawable.flash_on);
        }
        flashButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int status =(Integer) v.getTag();
                        if(status == 0) {
                            flashButton.setBackgroundResource(R.drawable.flash_off);
                            mPreview.setFlash(1);
                            flashMode = 1;
                            v.setTag(1);
                        }
                        else if (status == 1){
                            flashButton.setBackgroundResource(R.drawable.flash_on);
                            mPreview.setFlash(2);
                            flashMode = 2;
                            v.setTag(2);
                        }
                        else if (status == 2){
                            flashButton.setBackgroundResource(R.drawable.flash_auto);
                            mPreview.setFlash(0);
                            flashMode = 0;
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

    private boolean safeCameraOpenInView(View view) {
        boolean qOpened = false;
        releaseCameraAndPreview();
        mCamera = getCameraInstance();
        mCameraView = view;
        qOpened = (mCamera != null);
        if(qOpened == true){
            mPreview = new CameraPreview(getActivity().getBaseContext(), mCamera, getView(), flashMode);
            preview = (FrameLayout) getView().findViewById(R.id.camera_preview);
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

        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

        for(int cameraID = 0; cameraID < Camera.getNumberOfCameras(); cameraID++){
            Camera.getCameraInfo(cameraID,cameraInfo);
            if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK && !facingFront){
                try{
                    c = Camera.open(cameraID);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            else if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT && facingFront){
                try{
                    c = Camera.open(cameraID);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
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
            preview.removeView(mPreview);
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
            Log.i("Reached","On Picture Taken");
            Bitmap bp = BitmapFactory.decodeByteArray(data, 0, data.length);

            Log.e("PictureDimensions", "Picture Height: " + bp.getHeight() + "\tWidth: " + bp.getWidth());

            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            bp = Bitmap.createScaledBitmap(bp, display.getHeight(), display.getWidth(), false);

            Matrix matrix = new Matrix();
            if (bp.getHeight() < bp.getWidth()) {
                matrix.postRotate(90);
            }
            if(facingFront){
                matrix.postScale(1,-1);
                System.out.println("rotated");
            }
            else {
                matrix.postRotate(0);
            }

            ((MainActivity)getActivity()).photo = Bitmap.createBitmap(bp, 0, 0, bp.getWidth(), bp.getHeight(), matrix, true);

            if(bp==null){
                Toast.makeText((getActivity()).getApplicationContext(), "Not Captured", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText((getActivity()).getApplicationContext(), "Captured", Toast.LENGTH_SHORT).show();
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