package com.example.mesibowhatsappclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.messaging.MesiboUserListFragment;

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity implements MesiboUserListFragment.FragmentListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        final ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        /////////
        MesiboUserListFragment userListFragment = new MesiboUserListFragment();
        userListFragment.setListener(this);

        Bundle bl = new Bundle();
        bl.putInt(MesiboUserListFragment.MESSAGE_LIST_MODE, MesiboUserListFragment.MODE_MESSAGELIST);
        userListFragment.setArguments(bl);

        //////////
        MesiboUserListFragment allUserFragment = new MesiboUserListFragment();
        allUserFragment.setListener(this);

        Bundle b2 = new Bundle();
        allUserFragment.mMemberProfiles = getUserProfiles();
        b2.putInt(MesiboUserListFragment.MESSAGE_LIST_MODE, MesiboUserListFragment.MODE_SELECTCONTACT);
        allUserFragment.setArguments(b2);

//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.userlist_fragment, userListFragment, "null");
//        ft.commit();

//        viewPagerAdapter.addFragment(new ChatFragment(), "Chats");
//        viewPagerAdapter.addFragment(new UsersFragment(), "Users");
//        viewPagerAdapter.addFragment(new ProfileFragment(), "Profile");
        viewPagerAdapter.addFragment(userListFragment, "Chats");
        viewPagerAdapter.addFragment(allUserFragment, "Contacts");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private ArrayList<Mesibo.UserProfile> getUserProfiles() {
        ArrayList<Mesibo.UserProfile> userProfiles = new ArrayList<>();
        Mesibo.UserProfile new_user = new Mesibo.UserProfile();
        new_user.name = "seond_user";
        userProfiles.add(new_user);
        return userProfiles;
    }

    @Override
    public void Mesibo_onUpdateTitle(String s) {

    }

    @Override
    public void Mesibo_onUpdateSubTitle(String s) {

    }

    @Override
    public boolean Mesibo_onClickUser(String s, long l, long l1) {


        Intent peerMessageIntent = new Intent(MessagingActivity.this, PeerMessageActivity.class);
        peerMessageIntent.putExtra("s", s);
        peerMessageIntent.putExtra("l", l);
        peerMessageIntent.putExtra("l1", l1);
        startActivity(peerMessageIntent);

//        Intent intent = new Intent(MessagingActivity.this, com.mesibo.messaging.MessagingActivity.class);
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


    public class ViewPagerAdapter extends FragmentPagerAdapter {

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