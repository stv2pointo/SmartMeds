package com.stvjuliengmail.smartmeds.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.Log;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.TouchImageView;
import com.stvjuliengmail.smartmeds.api.ImageDownloadTask;

public class ViewImageActivity extends AppCompatActivity {
    private TouchImageView touchImageView;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        touchImageView = findViewById(R.id.touch_iv);
        unpackIntentExtras();
        displayImage();
    }

    private void unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        imageUrl = extras.getString("imageUrl");
    }

    public void displayImage(){
        ImageDownloadTask task = new ImageDownloadTask();
        try {
            Bitmap myBitmap = task.execute(imageUrl).get();
            if(myBitmap == null){
                touchImageView.setImageResource(R.drawable.no_img_avail);
            }
            else{
                touchImageView.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            touchImageView.setImageResource(R.drawable.no_img_avail);
            Log.d("ViewImageActivity", "display iMage exdeption: " + e.getMessage());
        }
    }
}
