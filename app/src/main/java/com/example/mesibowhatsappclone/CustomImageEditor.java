package com.example.mesibowhatsappclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mesibo.mediapicker.MediaPicker;
import com.mesibo.mediapicker.cropper.CropImageView;
import com.mesibo.mediapicker.j;

public class CustomImageEditor extends AppCompatActivity {
    CropImageView cropImageView = null;
    Bitmap bitmap = null;
    EditText c = null;
    ImageButton sendButton = null;
    String e = null;
    boolean f = false;
    boolean g = false;
    boolean h = false;
    boolean i = true;
    boolean j = false;
    boolean k = false;
    RelativeLayout l = null;
    int m = -1;
    int n = 0;
    int o = 1280;
    int p = 0;
    private CustomImageEditor.OnImageEditListner editListner = null;
    public static final int q = 0;
    public static final int r = -1;
    private Rect w = new Rect();
    int s = 0;
    int t = 0;
    com.mesibo.mediapicker.j.a u = new j.a();

    public CustomImageEditor() {
    }

    public interface OnImageEditListner {
        void onImageEdit(int var1, String var2, String var3, Bitmap var4, int var5);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(com.mesibo.messaging.R.layout.activity_image_caption);
        ActionBar var2;
        (var2 = this.getSupportActionBar()).setHomeButtonEnabled(true);
        var2.setDisplayHomeAsUpEnabled(true);
        var2.setBackgroundDrawable(new ColorDrawable(MediaPicker.getToolbarColor()));
        Intent var3 = this.getIntent();
        this.e = var3.getStringExtra("filepath");
        this.o = var3.getIntExtra("maxDimension", 1280);
        this.f = var3.getBooleanExtra("showEditControls", false);
        this.h = var3.getBooleanExtra("showTitle", true);
        this.j = var3.getBooleanExtra("showCrop", false);
        this.k = var3.getBooleanExtra("squareCrop", false);
        String var4;
        if (!TextUtils.isEmpty(var4 = var3.getStringExtra("title"))) {
            var2.setTitle(var4);
        }

        if (!this.f) {
            var2.hide();
        }

        this.m = var3.getIntExtra("type", -1);
        this.editListner = ImageCropperHelper.getOnImageEditListner();
        this.sendButton = (ImageButton)this.findViewById(com.mesibo.messaging.R.id.caption_send);
        this.c = (EditText)this.findViewById(com.mesibo.messaging.R.id.caption_edit);
        this.l = (RelativeLayout)this.findViewById(com.mesibo.messaging.R.id.caption_view);
        if (!this.h) {
            this.c.setVisibility(View.INVISIBLE);
        }

        label62: {
            this.cropImageView = (CropImageView)this.findViewById(com.mesibo.messaging.R.id.caption_image);
            this.p = com.mesibo.mediapicker.k.c(this.e);
            int var9 = ((WindowManager)this.getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            boolean var10000;
            if (var9 != 0) {
                if (1 == var9) {
                    var10000 = true;
                    break label62;
                }

                if (2 == var9) {
                    var10000 = true;
                    break label62;
                }

                if (3 == var9) {
                    var10000 = true;
                    break label62;
                }
            }

            var10000 = false;
        }

        DisplayMetrics var5 = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(var5);
        int var12 = var5.heightPixels;
        var12 = var5.widthPixels;
        new Matrix();
        if (MediaPicker.TYPE_CAMERAIMAGE != this.m && MediaPicker.TYPE_FILEIMAGE != this.m) {
            if (MediaPicker.TYPE_CAMERAVIDEO != this.m && MediaPicker.TYPE_FILEVIDEO != this.m) {
                int var6;
                if ((var6 = var3.getIntExtra("drawableid", -1)) >= 0) {
                    this.bitmap = BitmapFactory.decodeResource(this.getApplicationContext().getResources(), var6);
                }
            } else {
                this.bitmap = ThumbnailUtils.createVideoThumbnail(this.e, 1);
            }

            this.f = false;
            this.j = false;
        } else {
            this.a();
        }

        if (this.bitmap == null) {
            Toast var10;
            (var10 = Toast.makeText(this.getApplicationContext(), "Not an image or corrupt image", Toast.LENGTH_SHORT)).setMargin(50.0F, 50.0F);
            var10.show();
            this.finish();
        } else {
            this.a(this.bitmap);
            CropImageView var7;
            if (this.k) {
                CropImageView var13 = this.cropImageView;
                boolean var11 = true;
                boolean var8 = true;
                var7 = var13;
                var13.a.setAspectRatioX(1);
                var7.a.setAspectRatioY(1);
                var7.setFixedAspectRatio(true);
            } else {
                (var7 = this.cropImageView).a.setAspectRatioX(1);
                var7.a.setAspectRatioY(1);
                var7.setFixedAspectRatio(false);
            }

            this.cropImageView.setShowCropOverlay(this.j);
            if (this.sendButton != null) {
                this.sendButton.setOnClickListener(new View.OnClickListener() {
                    public final void onClick(View v) {
                        if (CustomImageEditor.this.cropImageView.d) {
                            CustomImageEditor.this.a(CustomImageEditor.this.cropImageView.getCropRect());
                            CustomImageEditor.this.a();
                        }

                        String var2 = CustomImageEditor.this.c.getText().toString();
                        if (null != CustomImageEditor.this.editListner) {
                            CustomImageEditor.this.editListner.onImageEdit(CustomImageEditor.this.m, var2, CustomImageEditor.this.e, CustomImageEditor.this.bitmap, 0);
                            CustomImageEditor.this.finish();
                        } else {
                            if (null != CustomImageEditor.this.bitmap) {
                                CustomImageEditor.this.bitmap.recycle();
                            }

                            Intent var3;
                            (var3 = new Intent()).putExtra("filepath", CustomImageEditor.this.e);
                            var3.putExtra("message", var2);
                            CustomImageEditor.this.setResult(-1, var3);
                            CustomImageEditor.this.finish();
                        }
                    }
                });
            }

            this.b();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater var2 = this.getMenuInflater();
        if (this.f) {
            var2.inflate(R.menu.crop_image_menu, menu);
        }

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_crop) {
            this.a(true);
        } else {
            if (item.getItemId() ==  R.id.action_rotate) {
                this.bitmap = a(this.bitmap, 90);
                this.a(this.bitmap);
                this.n += 90;
                if (this.n == 360) {
                    this.n = 0;
                }

                return true;
            }

            if (item.getItemId() == R.id.action_ok) {

            }

            if (item.getItemId() == 16908332) {
                this.onBackPressed();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private static int a(int var0) {
        while(var0 < 0) {
            var0 += 360;
        }

        while(var0 >= 360) {
            var0 -= 360;
        }

        if (var0 != 0 && var0 != 90 && var0 != 270) {
            var0 -= var0 % 90;
        }

        return var0;
    }

    private static Bitmap a(Bitmap var0, int var1) {
        Matrix var2;
        (var2 = new Matrix()).postRotate((float)var1);
        return Bitmap.createBitmap(var0, 0, 0, var0.getWidth(), var0.getHeight(), var2, true);
    }

    public final void a() {
        this.u = new j.a();
        this.bitmap = com.mesibo.mediapicker.j.a(this.e, this.w, this.o, com.mesibo.mediapicker.j.b.b, this.u);
        if (this.bitmap != null) {
            if (this.w.isEmpty()) {
                this.w.left = 0;
                this.w.top = 0;
                this.w.bottom = this.u.d;
                this.w.right = this.u.c;
            }

            int var1;
            if ((var1 = a(this.p + this.n)) > 0) {
                this.bitmap = a(this.bitmap, var1);
            }

            this.s = this.bitmap.getWidth();
            this.t = this.bitmap.getHeight();
        }
    }

    public final void a(Rect var1) {
        int var2;
        if ((var2 = a(this.p + this.n)) > 0) {
            int var7 = 0 - var2;
            int var5 = this.t;
            int var4 = this.s;
            var7 = a(var7);
            if (var7 != 0) {
                int var8 = var1.right - var1.left;
                int var9 = var1.bottom - var1.top;
                int var10;
                int var11;
                int var12;
                int var13;
                if (90 == var7) {
                    var12 = var1.left;
                    var11 = (var10 = var5 - var1.top) - var9;
                    var13 = var12 + var8;
                } else if (180 == var7) {
                    var12 = var5 - var1.bottom;
                    var11 = (var10 = var4 - var1.left) - var8;
                    var13 = var12 + var9;
                } else {
                    var12 = var4 - var1.right;
                    var10 = (var11 = var1.top) + var9;
                    var13 = var12 + var8;
                }

                var1.right = var10;
                var1.left = var11;
                var1.top = var12;
                var1.bottom = var13;
            }
        }

        Rect var10000 = this.w;
        var10000.left = (int)((float)var10000.left + (float)var1.left * this.u.f);
        var10000 = this.w;
        var10000.top = (int)((float)var10000.top + (float)var1.top * this.u.f);
        this.w.right = this.w.left + (int)((float)(var1.right - var1.left) * this.u.f);
        this.w.bottom = this.w.top + (int)((float)(var1.bottom - var1.top) * this.u.f);
    }

    private void a(int var1, int var2, Rect var3, int var4) {
        var4 = a(var4);
        if (var4 != 0) {
            int var5 = var3.right - var3.left;
            int var6 = var3.bottom - var3.top;
            int var7;
            int var8;
            int var9;
            int var10;
            if (90 == var4) {
                var9 = var3.left;
                var8 = (var7 = var2 - var3.top) - var6;
                var10 = var9 + var5;
            } else if (180 == var4) {
                var9 = var2 - var3.bottom;
                var8 = (var7 = var1 - var3.left) - var5;
                var10 = var9 + var6;
            } else {
                var9 = var1 - var3.right;
                var7 = (var8 = var3.top) + var6;
                var10 = var9 + var5;
            }

            var3.right = var7;
            var3.left = var8;
            var3.top = var9;
            var3.bottom = var10;
        }
    }

    private void a(Bitmap var1) {
        this.bitmap = var1;
        this.cropImageView.setImageBitmap(var1);
        this.s = this.bitmap.getWidth();
        this.t = this.bitmap.getHeight();
    }

    private void b() {
        ((InputMethodManager)this.getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.c.getWindowToken(), 0);
    }

    private void a(boolean var1) {
        this.g = false;
        if (this.cropImageView.d) {
            this.g = true;
        }

        if (this.h) {
            this.c.setVisibility(!this.g ? View.GONE : View.VISIBLE);
        }

        this.l.setVisibility(!this.g ? View.GONE : View.VISIBLE);
        if (this.g) {
            if (var1) {
                this.a(this.cropImageView.getCropRect());
                this.a();
                this.a(this.bitmap);
            }

            this.g = false;
        } else {
            this.g = true;
            this.b();
        }

        this.cropImageView.setShowCropOverlay(this.g);
    }

    public void onBackPressed() {
        if (!this.j && this.cropImageView.d) {
            this.a(false);
        } else {
            if (this.bitmap != null) {
                this.bitmap.recycle();
            }

            if (this.editListner != null) {
                this.editListner.onImageEdit(this.m, (String)null, this.e, (Bitmap)null, -1);
                this.finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    protected void onResume() {
        super.onResume();
    }
}
