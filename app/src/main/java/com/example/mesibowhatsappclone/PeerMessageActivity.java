package com.example.mesibowhatsappclone;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.mesibo.api.Mesibo;
import com.mesibo.api.MesiboFileProvider;
import com.mesibo.calls.MesiboAudioCallFragment;
import com.mesibo.calls.MesiboCall;
import com.mesibo.calls.MesiboCallConfig;
import com.mesibo.calls.MesiboVideoCallFragment;
import com.mesibo.mediapicker.MediaPicker;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.messaging.n;
import com.orhanobut.logger.AndroidLogAdapter;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mesibo.api.Mesibo.FileInfo.STATUS_INPROGRESS;
import static com.mesibo.api.Mesibo.FileInfo.TYPE_IMAGE;
import static com.mesibo.api.Mesibo.FileInfo.TYPE_VIDEO;

public class PeerMessageActivity extends AppCompatActivity implements MesiboMessagingFragment.FragmentListener,
        View.OnClickListener, MesiboCall.MesiboCallListener{

    //private MesiboCall mesiboCall;

    private Mesibo.UserProfile userProfile;
    private ActionBar actionBar;
    private com.mesibo.messaging.a mLetterTileProvider;
    private Toolbar toolbar;
    private ImageButton send_btn, image_btn, video_btn, media_btn, camera_btn;
    private TextView send_text_view;

    private int MediaButtonClicked;
    private boolean mediaViewOpen = false;

    TextView userNameView;
    TextView userStatusView;
    CircleImageView userAvatar;

    MesiboMessagingFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peer_message);

        Intent intent = getIntent();
        String s = intent.getStringExtra("s");
        long l = intent.getLongExtra("l", 0);
        long l1 = intent.getLongExtra("l1", 0);

        userProfile = Mesibo.getUserProfile(s);

        setToolbar();


//        mesiboCall = MesiboCall.getInstance();
//        //mesiboCall.setListener(this);
//        MesiboCallConfig mesiboCallConfig = mesiboCall.getConfig();
//        mesiboCallConfig.backgroundColor = R.color.myPrimaryColor;
//        mesiboCallConfig.enableDialer = false;

        mFragment = new CustomMessagingFragment();


        Bundle bl = new Bundle();
        bl.putString(MesiboUI.PEER, s);
        bl.putLong(MesiboUI.GROUP_ID, l);
        bl.putBoolean(MesiboMessagingFragment.SHOWMISSEDCALLS, true);
        bl.putBoolean(MesiboMessagingFragment.HIDE_REPLY, true);
        //mFragment.addHeaderMessage();
        mFragment.setArguments(bl);

        getSupportFragmentManager().beginTransaction().
                add(R.id.fragment_container, mFragment, "MesiboMessagingFragment").commit();


//        MesiboUI.launchMessageView(PeerMessageActivity.this, s, l);

//        Intent var14;
//        (var14 = new Intent(PeerMessageActivity.this, MessagingActivityCustom.class)).putExtra("peer", s);
//        var14.putExtra("groupid", l);
//        var14.putExtra("mid", l1);
//        startActivity(var14);
        media_btn = (ImageButton) findViewById(R.id.btn_media);

        image_btn = (ImageButton) findViewById(R.id.btn_image);
        image_btn.setOnClickListener(this);

        video_btn = (ImageButton) findViewById(R.id.btn_video);
        video_btn.setOnClickListener(this);

        camera_btn = (ImageButton) findViewById(R.id.btn_camera);
        camera_btn.setOnClickListener(this);

        send_btn = (ImageButton) findViewById(R.id.btn_send);
        send_text_view = (TextView) findViewById(R.id.send_txt);
        send_text_view.setOnClickListener(view -> {
            if (mediaViewOpen) {
                image_btn.setVisibility(View.GONE);
                video_btn.setVisibility(View.GONE);
                camera_btn.setVisibility(View.GONE);
            }
        });

        send_btn.setOnClickListener(view -> sendMsg());

        media_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mediaViewOpen) {
                    image_btn.setVisibility(View.VISIBLE);
                    video_btn.setVisibility(View.VISIBLE);
                    camera_btn.setVisibility(View.VISIBLE);

                    mediaViewOpen = true;
                } else {
                    image_btn.setVisibility(View.GONE);
                    video_btn.setVisibility(View.GONE);
                    camera_btn.setVisibility(View.GONE);
                    mediaViewOpen = false;
                }
            }
        });

//        image_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                MediaPicker.launchPicker(PeerMessageActivity.this, MediaPicker.TYPE_CAMERAIMAGE, Mesibo.getTempFilesPath());
//            }
//        });


    }

    private void sendMsg() {
        String message = send_text_view.getText().toString();
        if (message.length() != 0) {
            mFragment.sendTextMessage(message);
            send_text_view.setText("");
        }
    }


    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userNameView = findViewById(R.id.username);
        userStatusView = findViewById(R.id.status);
        userAvatar = findViewById(R.id.profile_image);

        if (userProfile.picturePath == null) {
            userAvatar.setImageResource(R.drawable.ic_launcher);
        } else {
            // TODO: Load the profile picture from profile picture URL

        }

        if (userProfile.name != null) {
            userNameView.setText(userProfile.name);
        }

        if (userProfile.status != null) {
            userStatusView.setText(userProfile.status);
        } else {
            userStatusView.setText("");
        }

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public void Mesibo_onUpdateUserPicture(Mesibo.UserProfile userProfile, Bitmap bitmap, String s) {

    }

    @Override
    public void Mesibo_onUpdateUserOnlineStatus(Mesibo.UserProfile userProfile, String s) {
        if (s != null) {
            Log.v("User Status", s);
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
        Log.v("back Pressed", "............");
        mFragment.Mesibo_onBackPressed();
        super.onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {

        this.MediaButtonClicked = view.getId();
        ArrayList var2 = new ArrayList();
        if (this.MediaButtonClicked != R.id.btn_camera) {
            var2.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }

        if (this.MediaButtonClicked == R.id.btn_camera || this.MediaButtonClicked == R.id.btn_video || this.MediaButtonClicked == R.id.btn_image) {
            var2.add("android.permission.CAMERA");
        }

//        if (this.mMediaButtonClicked == id.location) {
//            var2.add("android.permission.ACCESS_FINE_LOCATION");
//        }

        if (n.a(PeerMessageActivity.this, var2)) {
            this.onMediaButtonClicked(this.MediaButtonClicked);
            this.MediaButtonClicked = -1;
        }

    }

    public void onMediaButtonClicked(int var1) {
        if (var1 == R.id.btn_camera) {
            MediaPicker.launchPicker(this, MediaPicker.TYPE_CAMERAIMAGE, Mesibo.getTempFilesPath());
        }
//        else if (var1 == id.audio) {
//            MediaPicker.launchPicker(this.myActivity(), MediaPicker.TYPE_AUDIO);
//        } else if (var1 == id.document_btn) {
//            MediaPicker.launchPicker(this.myActivity(), MediaPicker.TYPE_FILE);
//        } else if (var1 == id.location) {
//            try {
//                this.displayPlacePicker();
//            } catch (GooglePlayServicesNotAvailableException var4) {
//                var4.printStackTrace();
//            } catch (GooglePlayServicesRepairableException var5) {
//                var5.printStackTrace();
//            }
//        }
        else if (var1 == R.id.btn_video) {
            CharSequence[] var2 = new CharSequence[]{"Video Recorder", "From Gallery"};
            AlertDialog.Builder var3;
            (var3 = new AlertDialog.Builder(this)).setTitle("Select your video from?");
            var3.setItems(var2, new android.content.DialogInterface.OnClickListener() {
                public final void onClick(DialogInterface var1, int var2) {
                    if (var2 == 0) {
                        MediaPicker.launchPicker(PeerMessageActivity.this, MediaPicker.TYPE_CAMERAVIDEO, Mesibo.getTempFilesPath());
                    } else {
                        if (var2 == 1) {
                            MediaPicker.launchPicker(PeerMessageActivity.this, MediaPicker.TYPE_FILEVIDEO);
                        }
                    }
                }
            });
            var3.show();
        } else {
            if (var1 == R.id.btn_image) {
                MediaPicker.launchPicker(this, MediaPicker.TYPE_FILEIMAGE);
            }

        }
    }

    // java.lang.RuntimeException: Failure delivering result ResultInfo{who=null,
// request=10000, result=-1, data=Intent { dat=content://media/external/images/media/12122 flg=0x1 (has extras) }}
// to activity {com.example.mesibowhatsappclone/
// com.example.mesibowhatsappclone.PeerMessageActivity}: java.lang.NullPointerException: println needs a message
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            assert data != null;

            Uri i = data.getData();
            String actual_path = getRealPathFromURI(i);
            File imageFile = new File(actual_path);

            long randomId = Mesibo.random();

            Mesibo.MessageParams mParameter = new Mesibo.MessageParams();
            mParameter.mid = randomId;
            mParameter.peer = userProfile.name;
            mParameter.groupid = 0;
            mParameter.profile = userProfile;
            mParameter.type = TYPE_IMAGE;
            mParameter.setStatus(STATUS_INPROGRESS);

            Mesibo.FileInfo fileInfo = null;

            if (requestCode == MediaPicker.TYPE_FILEIMAGE || requestCode == MediaPicker.TYPE_CAMERAIMAGE) {
                fileInfo = Mesibo.getFileInstance(mParameter,
                        randomId,
                        Mesibo.FileInfo.MODE_UPLOAD,
                        TYPE_IMAGE,
                        Mesibo.FileInfo.SOURCE_MESSAGE,
                        actual_path,
                        SampleAppConfiguration.apiUrl,
                        fileInfo1 -> {
                            return false;
                        });
            } else if (requestCode == MediaPicker.TYPE_FILEVIDEO) {
                fileInfo = Mesibo.getFileInstance(mParameter,
                        randomId,
                        Mesibo.FileInfo.MODE_UPLOAD,
                        TYPE_VIDEO,
                        Mesibo.FileInfo.SOURCE_MESSAGE,
                        actual_path,
                        SampleAppConfiguration.apiUrl,
                        fileInfo1 -> {

                            return false;
                        });
            }

            assert fileInfo != null;

            fileInfo.other = imageFile.getName();
            fileInfo.image = BitmapFactory.decodeFile(actual_path);
            fileInfo.message = String.valueOf(actual_path);
            Log.v("File Inffooo", String.valueOf(fileInfo.image));
            fileInfo.userInteraction = true;

            Log.v("From Peer activity", String.valueOf(fileInfo.image));
            mFragment.Mesibo_onFile(mParameter, fileInfo);

            boolean result = Mesibo.startFileTranser(fileInfo);
        }

    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    @Override
    public boolean MesiboCall_onNotify(int i, Mesibo.UserProfile userProfile, boolean b) {
        return false;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }

    @Override
    public MesiboAudioCallFragment MesiboCall_getAudioCallFragment(Mesibo.UserProfile userProfile) {
        return null;
    }

    @Override
    public Fragment MesiboCall_getIncomingAudioCallFragment(Mesibo.UserProfile userProfile) {
        AudioIncomingFragment audioIncomingFragment = new AudioIncomingFragment();
        audioIncomingFragment.setUserProfile(userProfile);

        return audioIncomingFragment;
    }


//    @Override
//    public boolean Mesibo_onFileTransferProgress(Mesibo.FileInfo fileInfo) {
//        Logger.e("File " + fileInfo.getPath() + "  is Uploading");
//        return false;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mFragment.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFragment.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFragment.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFragment.onStop();
    }
}