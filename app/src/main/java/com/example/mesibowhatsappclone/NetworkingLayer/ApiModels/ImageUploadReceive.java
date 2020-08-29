package com.example.mesibowhatsappclone.NetworkingLayer.ApiModels;

import com.google.gson.annotations.SerializedName;

public class ImageUploadReceive {

    @SerializedName("status")
    boolean status;

    public boolean isStatus() {
        return status;
    }
}
