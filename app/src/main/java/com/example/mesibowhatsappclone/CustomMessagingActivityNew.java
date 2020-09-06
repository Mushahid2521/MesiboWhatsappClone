package com.example.mesibowhatsappclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboCall;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.messaging.MessagingActivityNew;
import com.mesibo.messaging.i;

public class CustomMessagingActivityNew extends MessagingActivityNew implements MesiboMessagingFragment.FragmentListener {

//    private Toolbar c = null;
//    i a = null;
//    private Mesibo.UIHelperListner d = null;
    private MesiboUI.Config e = null;
    private Mesibo.UserProfile f = null;
    private i a;
    //    private ImageView g = null;
//    private TextView h = null;
//    private TextView i = null;
//    private String j = null;
//    private Bitmap k = null;
//    private ActionMode l = null;
//    private Mesibo.MessageParams n = null;
//    static int b = 1;

    public CustomMessagingActivityNew() {
        super();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        Bundle var2;

        if ((var2 = this.getIntent().getExtras()) != null) {
            if (!Mesibo.isReady()) {
                this.finish();
            } else {
                String var3 = var2.getString("peer");
                long var4;
                if ((var4 = var2.getLong("groupid")) > 0L) {
                    this.f = Mesibo.getUserProfile(var4);
                } else {
                    this.f = Mesibo.getUserProfile(var3);
                }
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mesiboui_messages_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_call:
                MesiboCall.getInstance().call(getApplicationContext(), Mesibo.random(), f, false);
                return true;
            case R.id.action_videocall:
                MesiboCall.getInstance().call(getApplicationContext(), Mesibo.random(), f, true);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void Mesibo_onUpdateUserPicture(Mesibo.UserProfile userProfile, Bitmap bitmap, String s) {
        super.Mesibo_onUpdateUserPicture(userProfile, bitmap, s);
    }

    @Override
    public void Mesibo_onUpdateUserOnlineStatus(Mesibo.UserProfile userProfile, String s) {
        super.Mesibo_onUpdateUserOnlineStatus(userProfile, s);
    }

    @Override
    public void Mesibo_onShowInContextUserInterface() {
        super.Mesibo_onShowInContextUserInterface();
    }

    @Override
    public void Mesibo_onHideInContextUserInterface() {
        super.Mesibo_onHideInContextUserInterface();
    }

    @Override
    public void Mesibo_onContextUserInterfaceCount(int i) {
        super.Mesibo_onContextUserInterfaceCount(i);
    }

    @Override
    public void Mesibo_onError(int i, String s, String s1) {
        super.Mesibo_onError(i, s, s1);
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strings, int[] ints) {
        super.onRequestPermissionsResult(i, strings, ints);
    }

    @Override
    protected void onActivityResult(int i, int i1, @Nullable Intent intent) {
        super.onActivityResult(i, i1, intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
