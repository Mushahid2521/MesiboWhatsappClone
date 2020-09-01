package com.example.mesibowhatsappclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboIncomingAudioCallFragment;

public class AudioIncomingFragment  extends MesiboIncomingAudioCallFragment implements Mesibo.CallListener {

    private static final int CALLSTATUS_COMPLETE = 10;

    private TextView profile_name_view;
    private Button accept_btn, decline_btn;
    private ImageView profile_img_view;

    private Mesibo.UserProfile mUserProfile;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_custom, container, false);

        // initialize your fragment views, click handlers etc
        profile_name_view = view.findViewById(R.id.profile_name);
        profile_img_view = view.findViewById(R.id.profile_image);
        accept_btn = view.findViewById(R.id.accept_btn);
        decline_btn = view.findViewById(R.id.decline_btn);

        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAnswer();
            }
        });

        decline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callHangup();
            }
        });

        return view;
    }

    public void setUserProfile(Mesibo.UserProfile mUserProfile) {
        this.mUserProfile = mUserProfile;
        populateViews();
    }

    private void populateViews() {
        //profile_img_view.setImageURI(mUserProfile.picturePath);
        if (mUserProfile!=null) {
            profile_name_view.setText(mUserProfile.name);
        }
    }


    // Hangup handler
    public void callHangup() {
        hangup(); // call hangup from the super class
        getActivity().finish();
    }

    public void callAnswer() {
        answer(); // call answer from the super class
    }

    @Override
    public void onResume() {
        super.onResume();
        Mesibo.addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Mesibo.removeListener(this);
    }


    @Override
    public boolean Mesibo_onCall(long peerid, long callid, Mesibo.UserProfile profile, int flags) {
        return true;
    }

    @Override
    public boolean Mesibo_onCallStatus(long peerid, long callid, int status, int flags, String desc) {
        if ((status & CALLSTATUS_COMPLETE) > 0) {
            getActivity().finish();
        }
        return true;
    }

    @Override
    public void Mesibo_onCallServer(int type, String url, String username, String credential) {

    }
}
