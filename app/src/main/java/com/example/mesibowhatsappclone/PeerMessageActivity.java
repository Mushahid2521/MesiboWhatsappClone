package com.example.mesibowhatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboCall;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.messaging.MessagingActivityNew;
import com.mesibo.messaging.a;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeerMessageActivity extends AppCompatActivity implements MesiboMessagingFragment.FragmentListener{

    private Mesibo.UserProfile userProfile;
    private ActionBar actionBar;
    private com.mesibo.messaging.a mLetterTileProvider;
    private Toolbar toolbar;

    TextView userNameView;
    TextView userStatusView;
    CircleImageView userAvatar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer_message);

        Log.v("Another user ", String.valueOf(Mesibo.getUserProfile("second_user")));
        Log.v("User Profiles", String.valueOf(Mesibo.getUserProfiles()));

        Intent intent = getIntent();
        String s = intent.getStringExtra("s");
        long l = intent.getLongExtra("l", 0);
        long l1 = intent.getLongExtra("l1", 0);

        userProfile = Mesibo.getUserProfile(s);
        //Log.v("User Status Start Time", userProfile.status);

        setToolbar();


        MesiboMessagingFragment mFragment = new CustomMessagingFragment();


        Bundle bl = new Bundle();
        bl.putString(MesiboUI.PEER, s);
        bl.putLong(MesiboUI.GROUP_ID, l);
        bl.putBoolean(MesiboMessagingFragment.SHOWMISSEDCALLS, true);
        mFragment.setArguments(bl);

        getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_container, mFragment, "MesiboMessagingFragment").commit();
//        MesiboUI.launchMessageView(PeerMessageActivity.this, s, l);

//        Intent var14;
//        (var14 = new Intent(PeerMessageActivity.this, MessagingActivityCustom.class)).putExtra("peer", s);
//        var14.putExtra("groupid", l);
//        var14.putExtra("mid", l1);
//        startActivity(var14);

    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userNameView = findViewById(R.id.username);
        userStatusView = findViewById(R.id.status);
        userAvatar = findViewById(R.id.profile_image);

        if(userProfile.picturePath==null) {
            userAvatar.setImageResource(R.drawable.ic_launcher);
        } else {

        }

        if (userProfile.name!=null) {
            userNameView.setText(userProfile.name);
        }

        if (userProfile.status!=null) {
            userStatusView.setText(userProfile.status);
        } else {
            userStatusView.setText("");
        }

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public void Mesibo_onUpdateUserPicture(Mesibo.UserProfile userProfile, Bitmap bitmap, String s) {

    }

    @Override
    public void Mesibo_onUpdateUserOnlineStatus(Mesibo.UserProfile userProfile, String s) {
        if (s != null) {
            Log.v("User Statussssss", s);
            userStatusView.setText(s);
        } else {
            userStatusView.setText("");
        }

    }

    @Override
    public void Mesibo_onShowInContextUserInterface() {

    }

    @Override
    public void Mesibo_onHideInContextUserInterface() {

    }

    @Override
    public void Mesibo_onContextUserInterfaceCount(int i) {

    }

    @Override
    public void Mesibo_onError(int i, String s, String s1) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mesiboui_messages_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_call:
                MesiboCall.getInstance().call(getApplicationContext(), Mesibo.random(), userProfile, false);
                return true;
            case R.id.action_videocall:
                MesiboCall.getInstance().call(getApplicationContext(), Mesibo.random(), userProfile, true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}