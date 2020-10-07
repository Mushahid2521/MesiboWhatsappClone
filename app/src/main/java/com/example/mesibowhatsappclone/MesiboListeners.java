package com.example.mesibowhatsappclone;

import android.util.Log;

import com.mesibo.api.Mesibo;
import com.mesibo.calls.MesiboAudioCallFragment;
import com.mesibo.calls.MesiboCall;
import com.mesibo.calls.MesiboIncomingAudioCallFragment;
import com.mesibo.calls.MesiboVideoCallFragment;

public class MesiboListeners implements MesiboCall.MesiboCallListener, Mesibo.MessageListener {

    private static MesiboListeners _instance = null;

    public static MesiboListeners get_instance(){
        if(null == _instance){
            synchronized (MesiboListeners.class){
                if(null==_instance) {
                    _instance = new MesiboListeners();
                }
            }
        }
        return _instance;
    }

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

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] bytes) {
        if(Mesibo.isReading(messageParams)){
            Log.e("Reading message", "..........");
        } else {
            Log.e("Notify New message", "...........");
        }
        return false;
    }

    @Override
    public void Mesibo_onMessageStatus(Mesibo.MessageParams messageParams) {

    }

    @Override
    public void Mesibo_onActivity(Mesibo.MessageParams messageParams, int i) {

    }

    @Override
    public void Mesibo_onLocation(Mesibo.MessageParams messageParams, Mesibo.Location location) {

    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {

    }
}
