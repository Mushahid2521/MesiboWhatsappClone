package com.example.mesibowhatsappclone;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboRecycleViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomMessagingFragment extends MesiboMessagingFragment implements MesiboRecycleViewHolder.Listener, Mesibo.MessageListener {


    @Override
    public int Mesibo_onGetItemViewType(Mesibo.MessageParams messageParams, String message) {
        String tag = message.split(" ")[0];
        if (tag.equals("<>")) {
            return MesiboRecycleViewHolder.TYPE_CUSTOM;
        }
        else {
            return MesiboRecycleViewHolder.TYPE_NONE;
        }
    }

    @Override
    public MesiboRecycleViewHolder Mesibo_onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (MesiboRecycleViewHolder.TYPE_CUSTOM == viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incoming_chat_layout, viewGroup, false);
            return new IncomingMessgaeViewHolder(v);

        }

        return null;
    }

    @Override
    public void Mesibo_onBindViewHolder(MesiboRecycleViewHolder mesiboRecycleViewHolder, int viewType, boolean selected, Mesibo.MessageParams messageParams, Mesibo.MesiboMessage mesiboMessage) {
        if (MesiboRecycleViewHolder.TYPE_CUSTOM == viewType) {

            IncomingMessgaeViewHolder IncomingView = (IncomingMessgaeViewHolder) mesiboRecycleViewHolder;
            IncomingView.totoal_view.setVisibility(View.VISIBLE);
            Calendar var1;
            (var1 = Calendar.getInstance()).setTimeInMillis(mesiboMessage.ts);
            Date var2 = var1.getTime();
            SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm aa");
            String time = localDateFormat.format(var2);

            Log.v("Sepcial Message", String.valueOf(time));
            IncomingView.mIncomingMessageTV.setText(mesiboMessage.message);
            IncomingView.mTime.setText(time);

        }
    }

    @Override
    public void Mesibo_oUpdateViewHolder(MesiboRecycleViewHolder mesiboRecycleViewHolder, Mesibo.MesiboMessage mesiboMessage) {

    }


    @Override
    public void Mesibo_onViewRecycled(MesiboRecycleViewHolder mesiboRecycleViewHolder) {

    }

    public class IncomingMessgaeViewHolder extends MesiboRecycleViewHolder {
        View mViewIncomingMessage;
        TextView mIncomingMessageTV;
        TextView mTime;
        CardView totoal_view;


        public IncomingMessgaeViewHolder(View v) {
            super(v);
            totoal_view = v.findViewById(R.id.cardview_recive_messsage);
            mIncomingMessageTV = v.findViewById(R.id.textview_msg_recive);
            mTime = v.findViewById(R.id.textview_recive_time);
            mViewIncomingMessage = v;
        }
    }

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] bytes) {
        return super.Mesibo_onMessage(messageParams, bytes);
    }


    @Override
    public void onMediaButtonClicked(int i) {
        super.onMediaButtonClicked(i);
    }
}