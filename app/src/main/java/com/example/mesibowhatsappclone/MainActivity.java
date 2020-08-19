package com.example.mesibowhatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        EditText nv = findViewById(R.id.name);
        EditText pv = findViewById(R.id.phone);
        final Button loginButton = findViewById(R.id.login);

        String name = nv.getText().toString();
        String phone = pv.getText().toString();

        if(TextUtils.isEmpty(phone)) {
            loginButton.setEnabled(true);
            return;
        }

        SampleAppWebAPi.login(name, phone);
        startMessagingActivity();

    }

    private void startMessagingActivity() {
        Log.v("Startting   ", "Meaasign     ");
        Intent intent = new Intent(MainActivity.this, MessagingActivity.class);
        startActivity(intent);
    }
}