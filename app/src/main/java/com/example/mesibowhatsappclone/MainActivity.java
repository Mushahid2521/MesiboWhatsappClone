package com.example.mesibowhatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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