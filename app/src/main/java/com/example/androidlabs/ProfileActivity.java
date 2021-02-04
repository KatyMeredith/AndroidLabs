package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton imageButton = (ImageButton)findViewById(R.id.imgBtn);

        imageButton.setOnClickListener(v -> {
            dispatchTakePictureIntent();
        });

        Log.e(ACTIVITY_NAME, "onCreate");
    }

    private void dispatchTakePictureIntent() {
        Log.e(ACTIVITY_NAME, "dispatch camera intent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == resultCode) {
            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap)extras.get("data");

            ((ImageButton)findViewById(R.id.imgBtn)).setImageBitmap(imageBitmap);
        }

        Log.e(ACTIVITY_NAME, "onActivityResult");
    }

    @Override
    protected void onStart() {
        super.onStart();


        Log.e(ACTIVITY_NAME, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();


        Log.e(ACTIVITY_NAME, "onResume");
    }


    @Override
    protected void onPause() {
        super.onPause();


        Log.e(ACTIVITY_NAME, "onPause");
    }


    @Override
    protected void onStop() {
        super.onStop();


        Log.e(ACTIVITY_NAME, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        Log.e(ACTIVITY_NAME, "onDestroy");
    }


}