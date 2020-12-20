package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

//    private ImageView mimageView;
//    private static final int REQUEST_IMAGE_CAPTURE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();

        //change actionbar title, if you dont change it will be accord to your system default settings
        ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        setContentView(R.layout.activity_dashboard);
        Button changeLang = findViewById(R.id.changeMyLang);   //Lang option button
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show alert dialog to show list of language options
                showChangeLanguageDialog();
            }
        });
//        mimageView = findViewById(R.id.imageView);

    }

    private void showChangeLanguageDialog() {
        //array of lang to display
        final String[] listItems = {"English","हिंदी","मराठी"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Dashboard.this);
        mBuilder.setTitle("Change Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0){
                    setLocale("en");
                    recreate();
                }
                else if (i == 1){
                    setLocale("hi");
                    recreate();
                }
                else if (i == 2){
                    setLocale("mr");
                    recreate();
                }

                //dismiss alert dialog when language selected
                dialog.dismiss();

            }
        });
        AlertDialog mDialog = mBuilder.create();
        //show alert dialog
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale= locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    //load language saved in shared preferences
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang","");
        setLocale(language);
    }


//    public void takePicture(View view) {
//        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if(imageTakeIntent.resolveActivity(getPackageManager())!= null){
//            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if ( requestCode== REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mimageView.setImageBitmap(imageBitmap);
//
//        }
//    }
}