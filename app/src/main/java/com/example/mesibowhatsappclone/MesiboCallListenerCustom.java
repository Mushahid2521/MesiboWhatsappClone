package com.example.mesibowhatsappclone;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboAudioCallFragment;
import com.mesibo.calls.MesiboCall;
import com.mesibo.calls.MesiboIncomingAudioCallFragment;
import com.mesibo.calls.MesiboVideoCallFragment;

public class MesiboCallListenerCustom implements MesiboCall.MesiboCallListener {
    @Override
    public boolean MesiboCall_onNotify(int type, Mesibo.UserProfile profile, boolean video) {

        return true;
    }

    @Override
    public MesiboVideoCallFragment MesiboCall_getVideoCallFragment(Mesibo.UserProfile userProfile) {
        VideoCallFragment videoCallFragment = new VideoCallFragment();
        videoCallFragment.setProfile(userProfile);

        return videoCallFragment;

    }

    @Override
    public MesiboAudioCallFragment MesiboCall_getAudioCallFragment(Mesibo.UserProfile userProfile) {
        AudioCallInProgressFragment audioCallInProgressFragment = new AudioCallInProgressFragment();
        return audioCallInProgressFragment;
    }


    @Override
    public MesiboIncomingAudioCallFragment MesiboCall_getIncomingAudioCallFragment(Mesibo.UserProfile userProfile) {
        AudioIncomingFragment audioIncomingFragment = new AudioIncomingFragment();
        audioIncomingFragment.setProfile(userProfile);


        return audioIncomingFragment;
    }
}
