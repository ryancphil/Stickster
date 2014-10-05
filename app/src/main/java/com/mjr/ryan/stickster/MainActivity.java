package com.mjr.ryan.stickster;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CameraFragment cameraFragment = new CameraFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        fragmentTransaction.add(R.id.frag_content, cameraFragment);

        // Commit the transaction
        fragmentTransaction.commit();

    }


}
