package com.mjr.ryan.stickster;

import android.content.Context;
import android.hardware.Camera;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    // SurfaceHolder
    private SurfaceHolder mHolder;

    // Our Camera.
    public Camera mCamera;

    // Parent Context.
    private Context mContext;

    // Camera Sizing (For rotation, orientation changes)
    private Camera.Size mPreviewSize;

    // List of supported preview sizes
    private List<Camera.Size> mSupportedPreviewSizes;

    // Flash modes supported by this camera
    private List<String> mSupportedFlashModes;
    private static int flashMode = 1;

    // View holding this camera.
    private View mCameraView;

    public CameraPreview(Context context, Camera camera, View cameraView, int mode) {
        super(context);

        // Capture the context
        mCameraView = cameraView;
        mContext = context;
        setCamera(camera);
        flashMode = mode;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setKeepScreenOn(true);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    /**
     * Begin the preview of the camera input.
     */
    public void startCameraPreview()
    {
        try{
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Extract supported preview and flash modes from the camera.
     * @param camera
     */
    private void setCamera(Camera camera)
    {
        // Source: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
        mCamera = camera;
        mSupportedPreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        mSupportedFlashModes = mCamera.getParameters().getSupportedFlashModes();

        // Set the camera to Flash off mode initially, or whichever mode was used last
        Camera.Parameters parameters = mCamera.getParameters();
        if(mSupportedFlashModes != null){
            if (flashMode == 1 && mSupportedFlashModes.contains(Camera.Parameters.FLASH_MODE_OFF)){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            }
            else if(flashMode == 2 && mSupportedFlashModes.contains(Camera.Parameters.FLASH_MODE_ON)){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            }
            else if(flashMode == 0 && mSupportedFlashModes.contains(Camera.Parameters.FLASH_MODE_AUTO)){
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            }
        }
        mCamera.setParameters(parameters);

        requestLayout();
    }

    public void setFlash(int mode) {
        Camera.Parameters parameters = mCamera.getParameters();
        if (mode == 0) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            flashMode = 0;
        }
        else if (mode == 1) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            flashMode = 1;
        }
        else if (mode == 2) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            flashMode = 2;
        }
        mCamera.setParameters(parameters);
    }

    public int getFlashMode(){
        return flashMode;
    }

    /**
     * The Surface has been created, now tell the camera where to draw the preview.
     * @param holder
     */
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dispose of the camera preview.
     * @param holder
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera != null){
            mCamera.stopPreview();
        }
    }

    /**
     * React to surface changed events
     * @param holder
     * @param format
     * @param w
     * @param h
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            Camera.Parameters parameters = mCamera.getParameters();

            // Set the auto-focus mode to "continuous"
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

            // Preview size must exist.
            if(mPreviewSize != null) {
                Camera.Size previewSize = mPreviewSize;
                parameters.setPreviewSize(previewSize.width, previewSize.height);
            }

            mCamera.setParameters(parameters);
            mCamera.startPreview();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Calculate the measurements of the layout
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        // Source: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
        final int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);

        if (mSupportedPreviewSizes != null){
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes, width, height);
        }


    }

    /**
     * Update the layout based on rotation and orientation changes.
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        // Source: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
        if (changed) {
            final int width = right - left;
            final int height = bottom - top;

            int previewWidth = width;
            int previewHeight = height;

            if (mPreviewSize != null && mCamera != null){
                Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

                switch (display.getRotation())
                {
                    case Surface.ROTATION_0:
                        previewWidth = mPreviewSize.height;
                        previewHeight = mPreviewSize.width;
                        mCamera.setDisplayOrientation(90);
                        break;
                    case Surface.ROTATION_90:
                        previewWidth = mPreviewSize.width;
                        previewHeight = mPreviewSize.height;
                        break;
                    case Surface.ROTATION_180:
                        previewWidth = mPreviewSize.height;
                        previewHeight = mPreviewSize.width;
                        break;
                    case Surface.ROTATION_270:
                        previewWidth = mPreviewSize.width;
                        previewHeight = mPreviewSize.height;
                        mCamera.setDisplayOrientation(180);
                        break;
                }
            }

            final int scaledChildHeight = previewHeight * width / previewWidth;
            mCameraView.layout(0, height - scaledChildHeight, width, height);
        }
    }

    /**
     *
     * @param sizes
     * @param width
     * @param height
     * @return
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height)
    {
        // Source: http://stackoverflow.com/questions/7942378/android-camera-will-not-work-startpreview-fails
        Camera.Size optimalSize = null;

        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) height / width;

        // Try to find a size match which suits the whole screen minus the menu on the left.
        for (Camera.Size size : sizes){

            if (size.height != width) continue;
            double ratio = (double) size.width / size.height;
            if (ratio <= targetRatio + ASPECT_TOLERANCE && ratio >= targetRatio - ASPECT_TOLERANCE){
                optimalSize = size;
            }
        }

        // If we cannot find the one that matches the aspect ratio, ignore the requirement.
        if (optimalSize == null) {
            // TODO : Backup in case we don't get a size.
        }

        return optimalSize;
    }
}
