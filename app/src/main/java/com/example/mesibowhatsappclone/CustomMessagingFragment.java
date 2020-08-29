package com.example.mesibowhatsappclone;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MesiboMessagingFragment;
import com.mesibo.messaging.MesiboRecycleViewHolder;
import com.mesibo.messaging.i;

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
            if (messageParams.isIncoming()) {
                return MesiboRecycleViewHolder.TYPE_INCOMING;
            } else {
                return MesiboRecycleViewHolder.TYPE_OUTGOING;
            }
        }

//        return MesiboRecycleViewHolder.TYPE_NONE;
    }

    @Override
    public MesiboRecycleViewHolder Mesibo_onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == MesiboRecycleViewHolder.TYPE_HEADER) {
            Log.v("Headerrrrrr", "found.......");
        }
        if (MesiboRecycleViewHolder.TYPE_CUSTOM == viewType) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.special_message_layout, viewGroup, false);
            return new SpecialMessageViewHolder(v);

        }
        else {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_message_show, viewGroup, false);
            return new IncomingOutgoingMessageViewHolder(v);
        }

//        return null;
    }

    @Override
    public void Mesibo_onFile(Mesibo.MessageParams messageParams, Mesibo.FileInfo fileInfo) {
        super.Mesibo_onFile(messageParams, fileInfo);
    }

    @Override
    public void Mesibo_onBindViewHolder(MesiboRecycleViewHolder mesiboRecycleViewHolder, int viewType, boolean selected, Mesibo.MessageParams messageParams, Mesibo.MesiboMessage mesiboMessage) {

        if (MesiboRecycleViewHolder.TYPE_CUSTOM == viewType) {

            SpecialMessageViewHolder IncomingView = (SpecialMessageViewHolder) mesiboRecycleViewHolder;
            IncomingView.totoal_view.setVisibility(View.VISIBLE);
            Calendar var1;
            (var1 = Calendar.getInstance()).setTimeInMillis(mesiboMessage.ts);
            Date var2 = var1.getTime();
            SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm aa");
            String time = localDateFormat.format(var2);


            IncomingView.mIncomingMessageTV.setText(mesiboMessage.message);
            IncomingView.mTime.setText(time);

        }

        if (MesiboRecycleViewHolder.TYPE_INCOMING == viewType) {
            IncomingOutgoingMessageViewHolder messageViewHolder = (IncomingOutgoingMessageViewHolder) mesiboRecycleViewHolder;
            messageViewHolder.mOutgoing_total_view.setVisibility(View.GONE);
            messageViewHolder.mIncoming_total_view.setVisibility(View.VISIBLE);

            Calendar var1;
            (var1 = Calendar.getInstance()).setTimeInMillis(mesiboMessage.ts);
            Date var2 = var1.getTime();
            SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm aa");
            String time = localDateFormat.format(var2);

            messageViewHolder.mIncomingMessageTV.setText(mesiboMessage.message);
            messageViewHolder.mIncomingTime.setText(time);
        }

        if (MesiboRecycleViewHolder.TYPE_OUTGOING == viewType) {
            IncomingOutgoingMessageViewHolder messageViewHolder = (IncomingOutgoingMessageViewHolder) mesiboRecycleViewHolder;
            messageViewHolder.mOutgoing_total_view.setVisibility(View.VISIBLE);
            messageViewHolder.mIncoming_total_view.setVisibility(View.GONE);

            Calendar var1;
            (var1 = Calendar.getInstance()).setTimeInMillis(mesiboMessage.ts);
            Date var2 = var1.getTime();
            SimpleDateFormat localDateFormat = new SimpleDateFormat("hh:mm aa");
            String time = localDateFormat.format(var2);

            messageViewHolder.mOutgoingMessageTV.setText(mesiboMessage.message);
            messageViewHolder.mOutgoingTime.setText(time);
            messageViewHolder.mSendStatus.setVisibility(View.VISIBLE);

            switch (mesiboMessage.status) {
                case 0: messageViewHolder.mSendStatus.setText("Sending..");
                break;
                case 1: messageViewHolder.mSendStatus.setText("Sent");
                break;
                case 2: messageViewHolder.mSendStatus.setText("Delivered");
                break;
                case 3: messageViewHolder.mSendStatus.setText("Read");
                break;
                default: break;
            }
        }
    }

    @Override
    public void Mesibo_oUpdateViewHolder(MesiboRecycleViewHolder mesiboRecycleViewHolder, Mesibo.MesiboMessage mesiboMessage) {

    }


    @Override
    public void Mesibo_onViewRecycled(MesiboRecycleViewHolder mesiboRecycleViewHolder) {

    }

    @Override
    public boolean Mesibo_onMessage(Mesibo.MessageParams messageParams, byte[] bytes) {
        return super.Mesibo_onMessage(messageParams, bytes);
    }


    @Override
    public void onMediaButtonClicked(int i) {
        super.onMediaButtonClicked(i);
    }

    public class SpecialMessageViewHolder extends MesiboRecycleViewHolder {
        View mViewIncomingMessage;
        TextView mIncomingMessageTV;
        TextView mTime;
        CardView totoal_view;


        public SpecialMessageViewHolder(View v) {
            super(v);
            totoal_view = v.findViewById(R.id.cardview_recive_messsage);
            mIncomingMessageTV = v.findViewById(R.id.textview_msg_recive);
            mTime = v.findViewById(R.id.textview_recive_time);
            mViewIncomingMessage = v;
        }
    }

    public class IncomingOutgoingMessageViewHolder extends MesiboRecycleViewHolder {
        View mViewIncomingMessage;
        TextView mIncomingMessageTV, mOutgoingMessageTV;
        TextView mIncomingTime, mOutgoingTime, mSendStatus;
        CardView mIncoming_total_view, mOutgoing_total_view;


        public IncomingOutgoingMessageViewHolder(View v) {
            super(v);
            mIncoming_total_view = v.findViewById(R.id.cardview_recive_messsage);
            mOutgoing_total_view = v.findViewById(R.id.cardview_send_messsage);

            mIncomingMessageTV = v.findViewById(R.id.textview_msg_recive);
            mOutgoingMessageTV = v.findViewById(R.id.textview_msg_send);

            mIncomingTime = v.findViewById(R.id.textview_recive_time);
            mOutgoingTime = v.findViewById(R.id.textview_send_time);
            mSendStatus = v.findViewById(R.id.textview_msg_send_status);

            mViewIncomingMessage = v;
        }
    }

}