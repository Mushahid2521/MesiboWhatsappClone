package com.example.mesibowhatsappclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.mesibo.mediapicker.ImageEditor;
import com.mesibo.mediapicker.MediaPicker;
import com.mesibo.messaging.MesiboUI;

public class MainActivity extends AppCompatActivity implements CustomImageEditor.OnImageEditListner{

    public ImageView imageView;

    @Override
    public void onImageEdit(int i, String s, String s1, Bitmap bitmap, int i1) {
        Log.v("Got Youuuu", ".........");
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        imageView  = findViewById(R.id.image);

        if(requestCode == MediaPicker.TYPE_FILEIMAGE && resultCode == RESULT_OK) {
            Log.v("Data URI", String.valueOf(data.getData()));
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
                MediaPicker.launchPicker(MainActivity.this, MediaPicker.TYPE_FILEIMAGE);
            }
        });
    }


    void startLogin() {
//        EditText nv = findViewById(R.id.name);
//        EditText pv = findViewById(R.id.phone);
//        final Button loginButton = findViewById(R.id.login);
//
//        String name = nv.getText().toString();
//        String phone = pv.getText().toString();
//
//        if(TextUtils.isEmpty(phone)) {
//            loginButton.setEnabled(true);
//            return;
//        }

        SampleAppWebAPi.login("name", "phone");
        startMessagingActivity();

    }

    private void startMessagingActivity() {
        Intent intent = new Intent(MainActivity.this, InboxActivity.class);
        startActivity(intent);
    }
}