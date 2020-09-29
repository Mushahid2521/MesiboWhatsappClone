package com.example.mesibowhatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboCall;
import com.mesibo.mediapicker.MediaPicker;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboUserListFragment;


import java.util.ArrayList;
import java.util.List;

public class InboxActivity extends AppCompatActivity implements MesiboUserListFragment.FragmentListener, MesiboMessagingFragment.FragmentListener, MediaPicker.ImageEditorListener, Mesibo.CallListener {


    MesiboUserListFragment mUserListFragment;
    //MesiboMyCallLogsFragment mesiboMyCallLogsFragment;
    ViewPagerAdapter mAdapter;
    RelativeLayout mReturnToCallFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_messaging);

        mReturnToCallFragment  = findViewById(R.id.returnToCallLayout);

        ViewPager viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mReturnToCallFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MesiboCall.getInstance().call(InboxActivity.this, 0,null, false);
            }
        });


        Mesibo.addListener(this);

    }


    private void setupViewPager(ViewPager viewPager) {
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Create a new Fragment to be placed in the activity layout
        mUserListFragment = new MesiboUserListFragment();
        mUserListFragment.setListener(this);

        Bundle bundle = new Bundle();
        bundle.putInt(MesiboUserListFragment.MESSAGE_LIST_MODE, MesiboUserListFragment.MODE_MESSAGELIST);
        mUserListFragment.setArguments(bundle);

        MesiboUserListFragment allUserFragment = new MesiboUserListFragment();
        allUserFragment.setListener(this);

        Bundle b2 = new Bundle();
        b2.putInt(MesiboUserListFragment.MESSAGE_LIST_MODE, MesiboUserListFragment.MODE_SELECTCONTACT);
        allUserFragment.setArguments(b2);

        //mesiboMyCallLogsFragment = new MesiboMyCallLogsFragment();
        mAdapter.addFragment(mUserListFragment, "Chats");
        mAdapter.addFragment(allUserFragment,"Contacts");

        viewPager.setAdapter(mAdapter);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filePath = MediaPicker.processOnActivityResult(this, requestCode, resultCode, data);


        if(null == filePath)
            return;

        MediaPicker.launchEditor((AppCompatActivity)this, MediaPicker.TYPE_FILEIMAGE, -1, null, filePath, false, false, true, true, 600, this);
    }

    @Override
    public void onImageEdit(int i, String s, String filePath, Bitmap bitmap, int status1) {

    }

    @Override
    public void Mesibo_onUpdateTitle(String s) {

    }

    @Override
    public void Mesibo_onUpdateSubTitle(String s) {

    }

    @Override
    public boolean Mesibo_onClickUser(String s, long l, long l1) {

        Intent i = new Intent(this, CustomMessagingActivityNew.class);
        i.putExtra("peer", s);
        i.putExtra("groupid", l);
        startActivity(i);

        //return false to load default
        return true;
    }

    @Override
    public boolean Mesibo_onUserListFilter(Mesibo.MessageParams messageParams) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void Mesibo_onUpdateUserPicture(Mesibo.UserProfile userProfile, Bitmap bitmap, String s) {

    }

    @Override
    public void Mesibo_onUpdateUserOnlineStatus(Mesibo.UserProfile userProfile, String s) {

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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean Mesibo_onCall(long l, long l1, Mesibo.UserProfile userProfile, int i) {
        return false;
    }

    @Override
    public boolean Mesibo_onCallStatus(long l, long l1, int i, int i1, String s) {

        if(MesiboCall.getInstance().isCallInProgress()){

            mReturnToCallFragment.setVisibility(View.VISIBLE);
        }else{
            mReturnToCallFragment.setVisibility(View.GONE);
        }

        return false;
    }

    @Override
    public void Mesibo_onCallServer(int i, String s, String s1, String s2) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
