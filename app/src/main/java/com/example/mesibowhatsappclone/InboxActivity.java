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
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MesiboUserListFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class InboxActivity extends AppCompatActivity implements MesiboUserListFragment.FragmentListener {

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);


        //    setUserProfiles();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        if (savedInstanceState == null) {

            /////////
            MesiboUserListFragment userListFragment = new MesiboUserListFragment();
            userListFragment.setListener(this);

            Bundle bl = new Bundle();
            bl.putInt(MesiboUserListFragment.MESSAGE_LIST_MODE, MesiboUserListFragment.MODE_MESSAGELIST);
            userListFragment.setArguments(bl);


            MesiboUserListFragment allUserFragment = new MesiboUserListFragment();
            allUserFragment.setListener(this);

            Bundle b2 = new Bundle();
            b2.putInt(MesiboUserListFragment.MESSAGE_LIST_MODE, MesiboUserListFragment.MODE_SELECTCONTACT);
            allUserFragment.setArguments(b2);

            viewPagerAdapter.addFragment(userListFragment, "Chats");
            viewPagerAdapter.addFragment(allUserFragment, "Contacts");

            viewPager.setAdapter(viewPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);

        }
    }


    @Override
    public void Mesibo_onUpdateTitle(String s) {

    }

    @Override
    public void Mesibo_onUpdateSubTitle(String s) {

    }

    @SuppressLint("LongLogTag")
    @Override
    public boolean Mesibo_onClickUser(String s, long l, long l1) {

        Intent peerMessageIntent = new Intent(InboxActivity.this, PeerMessageActivity.class);
        peerMessageIntent.putExtra("s", s);
        peerMessageIntent.putExtra("l", l);
        peerMessageIntent.putExtra("l1", l1);
        startActivity(peerMessageIntent);

//        Intent intent = new Intent(InboxActivity.this, com.mesibo.messaging.InboxActivity.class);
//        intent.putExtra("peer", s);
//        intent.putExtra("groupid", l);
//        intent.putExtra("mid", l1);
//        startActivity(intent);

        return true;
    }

    @Override
    public boolean Mesibo_onUserListFilter(Mesibo.MessageParams messageParams) {
        return false;
    }


    public static class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
}