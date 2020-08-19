package com.example.mesibowhatsappclone;

import android.view.ViewGroup;

import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboRecycleViewHolder;

public class MessagingUIFragment extends MesiboMessagingFragment implements MesiboRecycleViewHolder.Listener {

    @Override
    public int Mesibo_onGetItemViewType(Mesibo.MessageParams messageParams, String message) {
        if (messageParams.isIncoming()) {
            return MesiboRecycleViewHolder.TYPE_INCOMING;
        }

        return MesiboRecycleViewHolder.TYPE_OUTGOING;

    }

    @Override
    public MesiboRecycleViewHolder Mesibo_onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void Mesibo_onBindViewHolder(MesiboRecycleViewHolder mesiboRecycleViewHolder, int viewType, boolean selected, Mesibo.MessageParams messageParams, Mesibo.MesiboMessage mesiboMessage) {

    }

    @Override
    public void Mesibo_oUpdateViewHolder(MesiboRecycleViewHolder mesiboRecycleViewHolder, Mesibo.MesiboMessage mesiboMessage) {

    }


    @Override
    public void Mesibo_onViewRecycled(MesiboRecycleViewHolder mesiboRecycleViewHolder) {

    }
}