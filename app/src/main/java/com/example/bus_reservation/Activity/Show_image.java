package com.example.bus_reservation.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.bus_reservation.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.sql.DataSource;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Show_image extends AppCompatActivity {
    PhotoView imageView;
    TextView back;
    private static final  int WRITE_EXTERNAL_STORAGE_CODE=1;
String Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final SweetAlertDialog pDial = new SweetAlertDialog(Show_image.this, SweetAlertDialog.PROGRESS_TYPE);
        pDial.setContentText("Loading Image...");
        pDial.show();

        imageView=findViewById(R.id.show_img);

        String image = getIntent().getStringExtra("image");

        Picasso.with(this).load(image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        pDial.dismiss();
                    }
                    @Override
                    public void onError() {
                        Toast.makeText(Show_image.this,"Error in Connection",Toast.LENGTH_LONG).show();
                    }
                });

//        imageView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                final SweetAlertDialog pDialog = new SweetAlertDialog(Show_image.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
//                pDialog.setCustomImage(R.drawable.downloading);
//                pDialog.setContentText("You want to download");
//                pDialog.setConfirmText("Yes");
//                pDialog.show();
//                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                                requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
//                            } else {
//                                pDialog.dismiss();
//                                final SweetAlertDialog pDialog2 = new SweetAlertDialog(Show_image.this, SweetAlertDialog.PROGRESS_TYPE);
//                                pDialog2.setTitleText("Downloading...");
//                                pDialog2.show();
//
//                                Glide.with(Show_image.this)
//                                        .load(Image)
//                                        .asBitmap()
//                                        .into(new SimpleTarget<Bitmap>(350, 350)
//                                        {
//                                            @Override
//                                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                                                saveImage(resource);
//                                            }
//                                        });
//                                pDialog2.dismiss();
//                                final SweetAlertDialog pDialog3 = new SweetAlertDialog(Show_image.this, SweetAlertDialog.SUCCESS_TYPE);
//                                pDialog3.setTitleText("Downloading Complete");
//                                pDialog3.setContentText("Picture Saved");
//                                pDialog3.setConfirmText("OK");
//                                pDialog3.setCancelable(false);
//                                pDialog3.show();
//                                pDialog3.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        pDialog3.dismiss();
//                                    }
//                                });
//
//                            }
//                        }
//                    }
//                });
//
//                return false;
//            }
//        });


    }
//
//    private String saveImage(Bitmap image) {
//        String savedImagePath = null;
//
//        String imageFileName = image+".png";
//        File storageDir = new File(
//                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                        + "/GT Bus");
//        boolean success = true;
//        if (!storageDir.exists()) {
//            success = storageDir.mkdirs();
//        }
//        if (success) {
//            File imageFile = new File(storageDir, imageFileName);
//            savedImagePath = imageFile.getAbsolutePath();
//            try {
//                OutputStream fOut = new FileOutputStream(imageFile);
//                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//                fOut.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // Add the image to the system gallery
//            galleryAddPic(savedImagePath);
//        }
//        return savedImagePath;
//    }
//    private void galleryAddPic(String imagePath) {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(imagePath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//    }

}
