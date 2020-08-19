package com.example.mesibowhatsappclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboUI;

public class PeerMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer_message);

        Intent intent = getIntent();
        String s = intent.getStringExtra("s");
        long l = intent.getLongExtra("l", 0);
        long l1 = intent.getLongExtra("l1", 0);

        MesiboMessagingFragment mFragment = (MesiboMessagingFragment) new MesiboMessagingFragment();


        Bundle bl = new Bundle();
        bl.putString(MesiboUI.PEER, s);
        bl.putLong(MesiboUI.GROUP_ID, l);
        mFragment.setArguments(bl);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mFragment, "null").commit();
        MesiboUI.launchMessageView(PeerMessageActivity.this, s, l);


    }
}