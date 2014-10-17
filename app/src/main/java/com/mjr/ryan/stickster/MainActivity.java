package com.mjr.ryan.stickster;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends Activity {
    ImageView imgFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgFavorite = (ImageView)findViewById(R.id.imageView1);

        CameraFragment cameraFragment = new CameraFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        fragmentTransaction.add(R.id.frag_content, cameraFragment);

        // Commit the transaction
        fragmentTransaction.commit();

        open();

    }

    public void open(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bp = (Bitmap) data.getExtras().get("data");
        imgFavorite.setImageBitmap(bp);
    }
}
