package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.api.ImageDownloadTask;
import com.stvjuliengmail.smartmeds.api.REQUEST_BASE;
import com.stvjuliengmail.smartmeds.api.RxInfoMayTreatsTask;

import java.util.ArrayList;

public class RxInfoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private TextView tvName, tvMayTreat;
    private ImageView imageView;
    private int rxcui; // the id of the selected pill
    private String name;
    private String imageUrl;
    private ArrayList<String> mayTreatDiseaseNames;
    private FloatingActionButton fabSaveMyMeds;
    private Button btnInteractions;
    private Context context;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_info);
        context = this;

        unpackIntentExtras();

        instantiateUiElements();

        wireUpSaveToMyMedsButton();

        wireUpInteractionsButton();

        displayName();

        displayImage();

        new RxInfoMayTreatsTask(this, getMayTreatsRequest()).execute("");
    }

    public void unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        name = extras.getString("name");
        imageUrl = extras.getString("imageUrl");
    }

    public void instantiateUiElements() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvMayTreat = (TextView) findViewById(R.id.tvMayTreat);
        fabSaveMyMeds = (FloatingActionButton) findViewById(R.id.fabSaveMyMeds);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnInteractions = (Button) findViewById(R.id.btnInteractions);
    }

    public void wireUpSaveToMyMedsButton() {
        db = new DBHelper(this);
        fabSaveMyMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Insert into db. boolean insertRX (Integer RXid, String dosage, String rxDoc)
                Log.d("test","RxInfoActivity: Button Clicked before Insert");

                db.insertRX(rxcui,"Take two","Me");
                Log.d("test","RxInfoActivity: Button Clicked after Insert");
                //Add an Intent. Put rxcui.
                //Open a new activity (editMed)?
                Intent intent = new Intent(context, MyMedsActivity.class);
                startActivity(intent);
                Toast.makeText(context, "Added to your pills", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void wireUpInteractionsButton(){
        btnInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InteractionsActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("rxcui", rxcui);
                startActivity(intent);
            }
        });
    }

    public void displayName(){
        tvName.setText(name);
    }

    public void displayImage(){
        ImageDownloadTask task = new ImageDownloadTask();
        try {
            Bitmap myBitmap = task.execute(imageUrl).get();
            if(myBitmap == null){
                imageView.setImageResource(R.drawable.no_img_avail);
            }
            else{
                imageView.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            Log.d(TAG, "display iMage exdeption: " + e.getMessage());
        }
    }

    public String getMayTreatsRequest() {
        return REQUEST_BASE.CLASS_BY_RXCUI + Integer.toString(rxcui) + "&relaSource=NDFRT&relas=may_treat";
    }

    public void populateMayTreat(String diseases) {
        tvMayTreat.setText(diseases);
    }

}
