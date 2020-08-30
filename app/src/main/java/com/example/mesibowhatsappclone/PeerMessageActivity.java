package com.example.mesibowhatsappclone;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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

import com.google.gson.Gson;
import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboCall;
import com.mesibo.mediapicker.MediaPicker;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboUI;
import com.mesibo.messaging.n;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.mesibo.api.Mesibo.FileInfo.MODE_UPLOAD;
import static com.mesibo.api.Mesibo.FileInfo.TYPE_IMAGE;

public class PeerMessageActivity extends AppCompatActivity implements MesiboMessagingFragment.FragmentListener,
        View.OnClickListener, Mesibo.FileTransferHandler{

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
        onBackPressed();
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
        if (requestCode == MediaPicker.TYPE_FILEIMAGE && resultCode == Activity.RESULT_OK) {
            assert data != null;

            Uri i = data.getData();
            File imageFile = new File(getRealPathFromURI(i));

            Mesibo.FileInfo file = new Mesibo.FileInfo();
            file.setPath(getRealPathFromURI(i));
            file.setUrl("https://27a19f3e5874.ngrok.io/api/upload");
            file.mode = MODE_UPLOAD;
            file.mid = Mesibo.random();
            file.type = TYPE_IMAGE;

            Mesibo.MessageParams p = new Mesibo.MessageParams();
            p.profile = userProfile;
            Mesibo.startFileTranser(file);

//            Mesibo.FileInfo file = new Mesibo.FileInfo();
//            file.setPath(getRealPathFromURI(i));
//            file.setUrl("https://27a19f3e5874.ngrok.io/api/upload");
//            file.mode = MODE_UPLOAD;
//            file.mid = Mesibo.random();
//            file.type = TYPE_IMAGE;
//            Log.e("PATH is ", imageFile.getPath());
//
//            Mesibo.startFileTranser(file);
//            Mesibo.MessageParams p = new Mesibo.MessageParams();
//            p.profile=userProfile;
//            Mesibo.sendFile(p, Mesibo.random(), file);

//            WorkManager.getInstance(PeerMessageActivity.this).enqueue(new OneTimeWorkRequest
//                    .Builder(ImageUploaderBackgroundWorker.class)
//                    .setInputData(new Data.Builder()
//                            .putString("image_path", imageFile.getAbsolutePath())
//                            .build())
//                    .setConstraints(new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
//                    .build());
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
    public boolean Mesibo_onStartFileTransfer(Mesibo.FileInfo file) {
        Logger.e("we are here...");
        if(Mesibo.FileInfo.MODE_DOWNLOAD == file.mode)
            return downloadFile(file.getParams(), file);

        return uploadFile(file.getParams(), file);
    }

    @Override
    public boolean Mesibo_onStopFileTransfer(Mesibo.FileInfo fileInfo) {
        return false;
    }


    private static Gson mGson = new Gson();
    private static Mesibo.HttpQueue mQueue = new Mesibo.HttpQueue(4, 0);


    public static class UploadResponse {
        public String op;
        public String file;
        public String result;

        UploadResponse() {
            result = null;
            op = null;
            file = null;
        }
    }

   //PeerMessageActivity() {
   //    Mesibo.addListener(this);
   //}


    public boolean uploadFile(Mesibo.MessageParams params, final Mesibo.FileInfo file) {


        /* [OPTIONAL] check the required network connectivity for automatic or manual file download */
        if(Mesibo.getNetworkConnectivity() != Mesibo.CONNECTIVITY_WIFI && !file.userInteraction)
            return false;

        final long mid = file.mid;

        /* [OPTIONAL] any POST data to send with the file */
        Bundle b = new Bundle();
        b.putString("op", "upload");
        b.putString("token", SampleAppWebAPi.getToken());
        b.putLong("mid", mid);
        /* end of post data */

        Mesibo.Http http = new Mesibo.Http();

        http.url = SampleAppConfiguration.apiUrl;
        http.postBundle = b;
        http.uploadFile = file.getPath();
        http.uploadFileField = "photo";
        http.other = file;
        file.setFileTransferContext(http);

        http.listener = new Mesibo.HttpListener() {
            @Override
            public boolean Mesibo_onHttpProgress(Mesibo.Http config, int state, int percent) {
                Mesibo.FileInfo f = (Mesibo.FileInfo)config.other;

                if(100 == percent && Mesibo.Http.STATE_DOWNLOAD == state) {

                    //parse response
                    String response = config.getDataString();
                    MesiboFileTransferHelper.UploadResponse uploadResponse = null;
                    try {
                        uploadResponse = mGson.fromJson(response, MesiboFileTransferHelper.UploadResponse.class);
                    } catch (Exception ignored) {}

                    if(null == uploadResponse || null == uploadResponse.file) {
                        Mesibo.updateFileTransferProgress(f, -1, Mesibo.FileInfo.STATUS_FAILED);
                        return false;
                    }

                    f.setUrl(uploadResponse.file);
                }

                int status = f.getStatus();
                if(100 == percent || status != Mesibo.FileInfo.STATUS_RETRYLATER) {
                    status = Mesibo.FileInfo.STATUS_INPROGRESS;
                    if(percent < 0)
                        status = Mesibo.FileInfo.STATUS_RETRYLATER;
                }

                if(percent < 100 || (100 == percent && Mesibo.Http.STATE_DOWNLOAD == state))
                    Mesibo.updateFileTransferProgress(f, percent, status);

                return ((100 == percent && Mesibo.Http.STATE_DOWNLOAD == state) || status != Mesibo.FileInfo.STATUS_RETRYLATER);
            }
        };

        if(null != mQueue)
            mQueue.queue(http);
        else if(http.execute()) {

        }

        return true;
    }

    public boolean downloadFile(final Mesibo.MessageParams params, final Mesibo.FileInfo file) {

        String url = file.getUrl();
        if(!url.toLowerCase().startsWith("http://") && !url.toLowerCase().startsWith("https://")) {
            url = SampleAppConfiguration.downloadUrl + url;
        }

        Mesibo.Http http = new Mesibo.Http();

        http.url = url;
        http.downloadFile = file.getPath();
        http.resume = true;
        http.maxRetries = 10;
        http.other = file;
        file.setFileTransferContext(http);

        http.listener = new Mesibo.HttpListener() {
            @Override
            public boolean Mesibo_onHttpProgress(Mesibo.Http http, int state, int percent) {
                Mesibo.FileInfo f = (Mesibo.FileInfo)http.other;

                int status = f.getStatus();
                if(100 == percent || status != Mesibo.FileInfo.STATUS_RETRYLATER) {
                    status = Mesibo.FileInfo.STATUS_INPROGRESS;
                    if(percent < 0)
                        status = Mesibo.FileInfo.STATUS_RETRYLATER;
                }

                Mesibo.updateFileTransferProgress(f, percent, status);

                return (100 == percent  || status != Mesibo.FileInfo.STATUS_RETRYLATER);
            }
        };

        if(null != mQueue)
            mQueue.queue(http);
        else if(http.execute()) {

        }

        return true;
    }
}