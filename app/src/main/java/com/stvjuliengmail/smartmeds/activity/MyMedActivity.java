package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.api.ImageDownloadTask;
import com.stvjuliengmail.smartmeds.database.SmartMedsDbOpenHelper;
import com.stvjuliengmail.smartmeds.model.MyMed;

public class MyMedActivity extends AppCompatActivity {

    private TextView tvName, tvDosage, tvDoctor, tvDirections, tvPharmacy;
    private ImageView ivPillImage;
    private FloatingActionButton fabEdit, fabDelete;
    private MyMed myMed;
    private Context context;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_med);
        context = this;
        setMyMedFromIntent();
        initializeUiComponents();
        wireUpClicks();
        setImage();
        setText();
    }

    private void setMyMedFromIntent(){
        extras = getIntent().getExtras();
        myMed = extras.getParcelable("myMed");
    }

    private void initializeUiComponents(){
        tvName =  findViewById(R.id.tvName);
        tvDosage = findViewById(R.id.tvDosage);
        tvDoctor = findViewById(R.id.tvDoctor);
        tvDirections = findViewById(R.id.tvDirections);
        tvPharmacy = findViewById(R.id.tvPharmacy);
        ivPillImage = findViewById(R.id.ivPillImage);
        fabEdit = findViewById(R.id.fabEdit);
        fabDelete = findViewById(R.id.fabDelete);
    }

    private void wireUpClicks(){
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        ivPillImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImageView();
            }
        });
    }

    private void setText(){
        tvName.setText(myMed.getName());
        tvDosage.setText(myMed.getDosage());
        tvDoctor.setText(myMed.getDoctor());
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

    private void edit(){
        Intent intent = new Intent(this, AddOrEditMyMedActivity.class);
        intent.putExtra("myMed", myMed);
        startActivity(intent);
    }

    private void delete() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage("This will permanently delete this medication.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DELETE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SmartMedsDbOpenHelper dbOpenHelper = SmartMedsDbOpenHelper.getInstance(context);
                        dbOpenHelper.deleteMyMed(myMed);
                        Toast.makeText(context,"Medication Deleted Successfully.",Toast.LENGTH_SHORT).show();
                        routeToMyMeds();
                    }
                });
        alertDialog.show();
    }

    private void routeToMyMeds() {
        Intent intent = new Intent(this, MyMedsActivity.class);
        startActivity(intent);
        finish();
    }

    private void startImageView(){
        String imageUrl = myMed.getImageUrl();
        Intent intent = new Intent(context, ViewImageActivity.class);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
    }
}
