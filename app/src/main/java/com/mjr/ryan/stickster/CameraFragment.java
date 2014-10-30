package com.mjr.ryan.stickster;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Ryan on 10/4/2014.
 * This fragment should start the camera. This fragment is inflated in the onCreate method in the main activity
 */
public class CameraFragment extends Fragment
{
    private Camera cameraObject;
    private ShowCamera showCamera;

    public static Camera isCameraAvailable(){
        Camera object = null;
        try {
            object = Camera.open();
        }
        catch (Exception e){
        }
        return object;
    }

    private Camera.PictureCallback capturedIt = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            Bitmap bp = BitmapFactory.decodeByteArray(data, 0, data.length);
            //photo = Bitmap.createScaledBitmap(photo, 1920, 1080, false);
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
            cameraObject.release();
            PhotoFragment photoFragment = new PhotoFragment();

            getFragmentManager().beginTransaction()
                                .replace(R.id.frag_content, photoFragment)
                                .addToBackStack(null)
                                .commit();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_camera);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_camera, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        cameraObject = isCameraAvailable();
        showCamera = new ShowCamera(getActivity(), cameraObject);
        FrameLayout preview = (FrameLayout) getView().findViewById(R.id.camera_preview);
        preview.addView(showCamera);


        Button captureButton = (Button) getView().findViewById(R.id.button_capture);
        captureButton.bringToFront();
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        cameraObject.takePicture(null, null, capturedIt);
                    }
                }
        );
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
