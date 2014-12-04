package com.mjr.ryan.stickster;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends Activity {
    public Bitmap photo;
    //DisplayMetrics metrics = getResources().getDisplayMetrics();
    float scale; //= getResources().getDisplayMetrics().density;
    DisplayMetrics metrics = new DisplayMetrics();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraFragment cameraFragment = new CameraFragment();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        scale = metrics.density;

        Log.e("scale", Float.toString(scale));

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frag_content, cameraFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getFragmentManager();
        if(manager != null){
            if(manager.getBackStackEntryCount()>0){
                Fragment currentFragment = manager.findFragmentById(R.id.frag_content);
                //Log.i("Fragment","Fragment Name: " + (manager.getBackStackEntryAt(manager.getBackStackEntryCount()-1).getName()));
                if(currentFragment.getClass() == PhotoFragment.class){
                    new AlertDialog.Builder(this)
                            .setMessage("Are you sure you want to leave your work?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CameraFragment cameraFragment = new CameraFragment();
                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.frag_content, cameraFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create().show();
                }
                else finish();
            }
            else finish();
        }
    }

    public Bitmap getPhoto(){
        return this.photo;
    }

    private File getTempFile(Context context){
        //it will return /sdcard/image.tmp
        final File path = new File( Environment.getExternalStorageDirectory(), context.getPackageName() );
        if(!path.exists()){
            path.mkdir();
        }
        return new File(path, "image.tmp");
    }

    //not used yet, but would rotate image depending on orientation
    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

}
