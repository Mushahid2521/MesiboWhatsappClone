package com.example.mesibowhatsappclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.mesibo.mediapicker.ImageEditor;
import com.mesibo.mediapicker.MediaPicker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageCropperHelper extends MediaPicker {

    private static CustomImageEditor.OnImageEditListner onImageEditListner = null;

    public ImageCropperHelper() {

    }

    public static void launchEditor(Activity context, int type, int drawableid, String title, String filePath, boolean showEditControls, boolean showTitle, boolean showCropOverlay, boolean squareCrop, int maxDimension, CustomImageEditor.OnImageEditListner listener) {
        Intent var11;
        (var11 = new Intent(context, CustomImageEditor.class)).putExtra("title", title);
        var11.putExtra("filepath", filePath);
        var11.putExtra("showEditControls", showEditControls);
        var11.putExtra("showTitle", showTitle);
        var11.putExtra("showCrop", showCropOverlay);
        var11.putExtra("squareCrop", squareCrop);
        var11.putExtra("type", type);
        var11.putExtra("drawableid", drawableid);

        Log.v("Data URI", filePath);

        onImageEditListner = listener;
        if (maxDimension > 0) {
            var11.putExtra("maxDimension", maxDimension);
        }

        if (listener == null) {
            context.startActivityForResult(var11, TYPE_CAPTION);
        } else {
            context.startActivity(var11);
        }
    }



    public static CustomImageEditor.OnImageEditListner getOnImageEditListner() {
        return onImageEditListner;
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage){
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            File outFile = new File(inContext.getFilesDir(), System.currentTimeMillis() + ".jpeg");
            FileOutputStream outputStream = new FileOutputStream(outFile);
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            return Uri.fromFile(outFile);

        } catch (FileNotFoundException e) {
            Log.e("Image Corpper Helper", "Saving Error");
        } catch (IOException e){
            Log.e("Image Corpper Helper", "IO Exception");
        }
        return null;
    }

}
