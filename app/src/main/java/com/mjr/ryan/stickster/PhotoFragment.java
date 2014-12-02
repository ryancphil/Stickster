package com.mjr.ryan.stickster;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

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
        ImageButton lightsaber_blue = (ImageButton) getView().findViewById(R.id.lightsaber_blue);
        lightsaber_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("lightsaber-blue", "lightsaber-blue");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lightsaber_blue);
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

        //Implement button for acoutic guitar
        ImageButton acoustic_guitar = (ImageButton) getView().findViewById(R.id.acoustic_guitar);
        acoustic_guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("acoustic_guitar", "acoustic_guitar");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.acoustic_guitar);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for electric guitar
        ImageButton electric_guitar = (ImageButton) getView().findViewById(R.id.electric_guitar);
        electric_guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("electric_guitar", "electric_guitar");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.electric_guitar);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for aviator_glasses
        ImageButton aviator_glasses = (ImageButton) getView().findViewById(R.id.aviator_glasses);
        aviator_glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("aviator_glasses", "aviator_glasses");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.aviator_glasses);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for beard
        ImageButton beard = (ImageButton) getView().findViewById(R.id.beard);
        beard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("beard", "beard");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beard);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for beer
        ImageButton beer = (ImageButton) getView().findViewById(R.id.beer);
        beer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("beer", "beer");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.beer);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for biohazard
        ImageButton biohazard = (ImageButton) getView().findViewById(R.id.biohazard);
        biohazard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("biohazard", "biohazard");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.biohazard);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for cut
        ImageButton cut = (ImageButton) getView().findViewById(R.id.cut);
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("cut", "cut");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cut);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for door_blood
        ImageButton door_blood = (ImageButton) getView().findViewById(R.id.door_blood);
        door_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("door_blood", "door_blood");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.door_blood);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for explosion
        ImageButton explosion = (ImageButton) getView().findViewById(R.id.explosion);
        explosion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("explosion", "explosion");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for glass_bullethole
        ImageButton glass_bullethole = (ImageButton) getView().findViewById(R.id.glass_bullethole);
        glass_bullethole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("glass_bullethole", "glass_bullethole");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.glass_bullethole);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for hair_beiber
        ImageButton hair_beiber = (ImageButton) getView().findViewById(R.id.hair_beiber);
        hair_beiber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("hair_beiber", "hair_beiber");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hair_beiber);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for hair_red
        ImageButton hair_red = (ImageButton) getView().findViewById(R.id.hair_red);
        hair_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("hair_red", "hair_red");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hair_red);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for heart
        ImageButton heart = (ImageButton) getView().findViewById(R.id.heart);
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("heart", "heart");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for hipster_glasses
        ImageButton hipster_glasses = (ImageButton) getView().findViewById(R.id.hipster_glasses);
        hipster_glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("hipster_glasses", "hipster_glasses");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.hipster_glasses);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for handlebar
        ImageButton handlebar = (ImageButton) getView().findViewById(R.id.handlebar);
        handlebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("handlebar", "handlebar");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.handlebar);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for irish_hat
        ImageButton irish_hat = (ImageButton) getView().findViewById(R.id.irish_hat);
        irish_hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("irish_hat", "irish_hat");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.irish_hat);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for kanye
        ImageButton kanye = (ImageButton) getView().findViewById(R.id.kanye);
        kanye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("kanye", "kanye");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.kanye);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for metal_bullethole
        ImageButton metal_bullethole = (ImageButton) getView().findViewById(R.id.metal_bullethole);
        metal_bullethole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("metal_bullethole", "metal_bullethole");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.metal_bullethole);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for mustache
        ImageButton mustache = (ImageButton) getView().findViewById(R.id.mustache);
        mustache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("mustache", "mustache");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mustache);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for lightsaber_red
        ImageButton lightsaber_red = (ImageButton) getView().findViewById(R.id.lightsaber_red);
        lightsaber_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("lightsaber_red", "lightsaber_red");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.lightsaber_red);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });


        //Implement button for playboy
        ImageButton playboy = (ImageButton) getView().findViewById(R.id.playboy);
        playboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("playboy", "playboy");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.playboy);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for santa_hat
        ImageButton santa_hat = (ImageButton) getView().findViewById(R.id.santa_hat);
        santa_hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("santa_hat", "santa_hat");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.santa_hat);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for sparta
        ImageButton sparta = (ImageButton) getView().findViewById(R.id.sparta);
        sparta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("sparta", "sparta");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sparta);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for sword
        ImageButton sword = (ImageButton) getView().findViewById(R.id.sword);
        sword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("sword", "sword");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sword);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for sword
        ImageButton tattoo = (ImageButton) getView().findViewById(R.id.tattoo);
        tattoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("tattoo", "tattoo");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tattoo);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for umbrella
        ImageButton umbrella = (ImageButton) getView().findViewById(R.id.umbrella);
        umbrella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("umbrella", "umbrella");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.umbrella);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for vodka
        ImageButton vodka = (ImageButton) getView().findViewById(R.id.vodka);
        vodka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("vodka", "vodka");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.vodka);
                BitmapTriple bitmapTriple = new BitmapTriple(bitmap);
                canvasView.mItemsCollection.add(bitmapTriple);
                canvasView.invalidate();
            }
        });

        //Implement button for vodka
        ImageButton wreath = (ImageButton) getView().findViewById(R.id.wreath);
        wreath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add imageview to fragment dynamically
                Log.e("wreath", "wreath");
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.wreath);
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
                int selectedIndex = canvasView.mItemsCollection.indexOf(canvasView.selectedBitmap);
                if(canvasView.mItemsCollection.size() > 0 && selectedIndex != -1) {
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
                canvasView.selectionRect.set(0,0,0,0);
                canvasView.invalidate();
                //Execute save as an asynctask to stop delay.
                new SaveTask().execute();
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


    public class SaveTask extends AsyncTask<Void,Void,Void> {

        private File getOutputMediaFile() {

            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "Stickster");

            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.e("Camera Guide", "Required media storage does not exist");
                    return null;
                }
            }

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");

            //mediaFile.mkdir();
            new SingleMediaScanner(getActivity(), mediaFile);

            //DialogHelper.showDialog( "Success!","Your picture has been saved!",getActivity());
            Log.e("path location", String.valueOf(mediaFile));

            return mediaFile;
        }

        @Override
        protected Void doInBackground(Void... params) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(getOutputMediaFile());
                //Bitmap temp = ((MainActivity) context).photo;
                Bitmap temp = canvasView.getDrawingCache();
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
            return null;
        }
    }
}