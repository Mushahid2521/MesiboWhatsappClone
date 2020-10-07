package com.example.mesibowhatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.mesibo.api.Mesibo;
import com.mesibo.mediapicker.ImageEditor;
import com.mesibo.mediapicker.MediaPicker;
import com.mesibo.messaging.MesiboUI;

public class MainActivity extends AppCompatActivity implements CustomImageEditor.OnImageEditListner{

    private static final int PICK_IMAGE = 100;
    public ImageView imageView;

    @Override
    public void onImageEdit(int i, String path, Bitmap bitmap, int i1) {
        Uri uri = null;
        if(bitmap!=null) {
            uri = ImageCropperHelper.getImageUri(this, bitmap);
        }
        if(uri!=null)
            imageView.setImageURI(uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MediaPicker.TYPE_FILEIMAGE && resultCode == RESULT_OK) {
            String filePath = MediaPicker.processOnActivityResult(this, requestCode, resultCode, data);


            ImageCropperHelper.launchEditor(this, MediaPicker.TYPE_FILEIMAGE, -1,
                    null, filePath, true, false,
                    false, false, 0, this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button loginButton = findViewById(R.id.login);

        imageView  = findViewById(R.id.image);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startLogin();
                loginButton.setEnabled(false);
            }
        });

        final Button picker = findViewById(R.id.pick);
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                PICK_IMAGE);
                    } else {

                        MediaPicker.launchPicker(MainActivity.this, MediaPicker.TYPE_FILEIMAGE);
                    }
                } catch (Exception e) {
                    Log.e("Older version", String.valueOf(e));
                }
            }
        });
    }


    void startLogin() {
        SampleAppWebAPi.login("name", "phone");
        startMessagingActivity();

    }

    private void startMessagingActivity() {
        Intent intent = new Intent(MainActivity.this, InboxActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_IMAGE:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    MediaPicker.launchPicker(MainActivity.this, MediaPicker.TYPE_FILEIMAGE);
                } else {
                    Toast.makeText(this, "Permission Required to Set Image", Toast.LENGTH_SHORT).show();
                }
        }
    }
}