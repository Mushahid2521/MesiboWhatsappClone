package com.example.mesibowhatsappclone;

import android.app.Activity;
import android.content.Intent;

import com.mesibo.mediapicker.ImageEditor;
import com.mesibo.mediapicker.MediaPicker;

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

}
