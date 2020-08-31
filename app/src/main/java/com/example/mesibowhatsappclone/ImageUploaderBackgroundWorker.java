package com.example.mesibowhatsappclone;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.mesibowhatsappclone.NetworkingLayer.ApiModels.ImageUploadReceive;
import com.example.mesibowhatsappclone.NetworkingLayer.ApiModels.ImageUploadRequest;
import com.example.mesibowhatsappclone.NetworkingLayer.RFClient;
import com.example.mesibowhatsappclone.NetworkingLayer.RFInterface;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageUploaderBackgroundWorker extends Worker {

    private Context context;

    public ImageUploaderBackgroundWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        return uploadImage() ? Result.success() : Result.retry();
    }

    private boolean uploadImage() {
        try {
            File file = new File(Objects.requireNonNull(getInputData().getString("image_path")));

            RequestBody requestBody = RequestBody.create(MediaType.parse("image"), file);

            ImageUploadRequest imageUploadRequest = new ImageUploadRequest();
            imageUploadRequest.setFileName(file.getName());

            ImageUploadReceive imageUploadReceive = RFClient.getClient()
                    .create(RFInterface.class).getImageUploadStatus(
                            RequestBody.create(MultipartBody.FORM, new Gson().toJson(imageUploadRequest)),
                            MultipartBody.Part.createFormData("my_image", file.getName(), requestBody)
                    ).execute().body();
            if (imageUploadReceive != null && imageUploadReceive.isStatus()) {
                Logger.e("Success To Upload Image");
                return true;
            } else
                return false;
        } catch (Exception e) {
            Logger.e("Failed " + e);
        }
        return false;
    }

    private void startTransfer(){

    }
}
