package com.stvjuliengmail.smartmeds.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.api.ImageDownloadTask;
import com.stvjuliengmail.smartmeds.model.MyMed;

public class MyMedActivity extends AppCompatActivity {

    private TextView tvName, tvDosage, tvDoctor, tvDirections, tvPharmacy;
    private ImageView ivPillImage;
    private MyMed myMed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_med);

        Bundle extras = getIntent().getExtras();
        myMed = extras.getParcelable("myMed");

        tvName = (TextView) findViewById(R.id.tvName);
        tvDosage = (TextView) findViewById(R.id.tvDosage);
        tvDirections = (TextView) findViewById(R.id.tvDirections);
        tvPharmacy = (TextView) findViewById(R.id.tvPharmacy);
        ivPillImage = (ImageView) findViewById(R.id.ivPillImage);

        setImage();
        setText();
    }

    private void setText(){
        tvName.setText(myMed.getName());
        tvDosage.setText(myMed.getDosage());
        tvDirections.setText(myMed.getDirections());
        tvPharmacy.setText(myMed.getPharmacy());
    }

    private void setImage(){
        ImageDownloadTask task = new ImageDownloadTask();
        try {
            Bitmap myBitmap = task.execute(myMed.getImageUrl()).get();
            if(myBitmap == null){
                ivPillImage.setImageResource(R.drawable.no_img_avail);
            }
            else {
                ivPillImage.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
