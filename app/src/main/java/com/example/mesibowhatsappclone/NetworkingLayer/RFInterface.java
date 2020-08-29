/*
 *  Copyright (C) Kenakata, Inc - All Rights Reserved
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Kenakata Engineering Team <engineering.team@kenakata.app>, 2020
 */

package com.example.mesibowhatsappclone.NetworkingLayer;


import com.example.mesibowhatsappclone.NetworkingLayer.ApiModels.ImageUploadReceive;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RFInterface {


    @Multipart
    @POST("api/upload")
    Call<ImageUploadReceive> getImageUploadStatus(@Part("payload") RequestBody payload, @Part MultipartBody.Part image_file);

}