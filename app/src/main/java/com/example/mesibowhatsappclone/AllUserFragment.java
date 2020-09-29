package com.example.mesibowhatsappclone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mesibo.api.Mesibo;
import com.mesibo.messaging.MesiboUserListFragment;

public class AllUserFragment extends MesiboUserListFragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public AllUserFragment() {

    }



    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG","onPauseCalled**********************************************************************************");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG","onResumeCalled*************************************************************************************************************************************");
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            Log.d("TAG","FragmentVisible*************************************************************************************************************************************");

        }
        else {
            Log.d("TAG","FragmentNotVisible*************************************************************************************************************************************");
        }
    }
}
