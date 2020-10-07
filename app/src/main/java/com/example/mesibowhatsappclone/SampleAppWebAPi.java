package com.example.mesibowhatsappclone;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.mesibo.api.Mesibo;

/**
 * Web API to communicate with your own backend server(s).
 * Note - in this example, we are not authenticating. In real app, you should authenticate user first
 * using email or phone OTP.
 *
 * When user is successfully authenticated, your server should create a mesibo auth token using
 * mesibo server side api and send it back here.
 *
 * Refer to PHP server api for code sample.
 */

public class SampleAppWebAPi {
    public static final String TAG="SampleAppWebApi";
    private static SharedPreferences mSharedPref = null;
    public static final String mSharedPrefKey = "com.example.mesibowhatsappclone";
    private static Gson mGson = new Gson();
    private static String mToken = null;

    public interface DemoWebApiResponseHandler {
        void onApiResponse(boolean result);
    }

    public static class Contacts {
        public String name = "";
        public String phone = "";
        public long   gid = 0;
    }

    public static class Response {
        public String result;
        public String op;
        public String error;
        public String token;
        public Contacts[] contacts;

        Response() {
            result = null;
            op = null;
            error = null;
            token = null;
            contacts = null;
        }
    }

    public static synchronized  void init() {
        if(null != mSharedPref) return;

        mToken = "48eb6784e3eb51f0b98490ae5697f116b2feea974e4afd32e2214f647";

        if(isLoggedin())
            MainApplication.startMesibo();
    }

    public static boolean isLoggedin() {
        return !TextUtils.isEmpty(mToken);
    }

    public static String getToken() {
        return mToken;
    }

    public static void login(String name, String phone) {

        setStringValue("token", "ce3a32a03240bd86e0f49e6faa19db97add620e5ff2e3314f37e");
        mToken = "ce3a32a03240bd86e0f49e6faa19db97add620e5ff2e3314f37e";
        Mesibo.reset();
        /* start mesibo before saving contacts */
        MainApplication.startMesibo();
    }


    public static boolean setStringValue(String key, String value) {
        try {
            synchronized (mSharedPref) {
                SharedPreferences.Editor poEditor = mSharedPref.edit();
                poEditor.putString(key, value);
                poEditor.commit();
                //backup();
                return true;
            }
        } catch (Exception e) {
            Log.d(TAG, "Unable to set long value in RMS:" + e.getMessage());
            return false;
        }
    }

    public static String getStringValue(String key, String defaultVal) {
        try {
            synchronized (mSharedPref) {
                if (mSharedPref.contains(key))
                    return mSharedPref.getString(key, defaultVal);
                return defaultVal;
            }
        } catch (Exception e) {
            Log.d(TAG, "Unable to fet long value in RMS:" + e.getMessage());
            return defaultVal;
        }
    }
}

