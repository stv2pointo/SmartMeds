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

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.api.ImageDownloadTask;
import com.stvjuliengmail.smartmeds.api.REQUEST_BASE;
import com.stvjuliengmail.smartmeds.api.RxInfoClassNameTask;
import com.stvjuliengmail.smartmeds.api.RxInfoMayTreatsTask;
import com.stvjuliengmail.smartmeds.api.SimpleNameTask;

import java.util.ArrayList;

public class RxInfoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private TextView tvName, tvFullName, tvMayTreat, tvClassName;
    private ImageView imageView;
    private int rxcui; // the id of the selected pill
    private String longName;
    private String shortName;
    private String imageUrl;
    private ArrayList<String> mayTreatDiseaseNames;
    private FloatingActionButton fabSaveMyMeds;
    private Button btnInteractions;
    private Context context;

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
        startApiTasks();
    }

    public void unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        longName = extras.getString("name");
        imageUrl = extras.getString("imageUrl");
    }

    public void instantiateUiElements() {
        tvName = (TextView) findViewById(R.id.tvName);
        tvFullName = (TextView) findViewById(R.id.tvFullName);
        tvMayTreat = (TextView) findViewById(R.id.tvMayTreat);
        tvClassName = (TextView) findViewById(R.id.tvClassName);
        fabSaveMyMeds = (FloatingActionButton) findViewById(R.id.fabSaveMyMeds);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnInteractions = (Button) findViewById(R.id.btnInteractions);
    }

    public void wireUpSaveToMyMedsButton() {
        fabSaveMyMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dieAndStartAddMed();
            }
        });
    }

    public void wireUpInteractionsButton(){
        btnInteractions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startInteractions();
            }
        });
    }

    public void displayName(){
        tvFullName.setText(longName);
    }

    public void displayClassName(String className){
        tvClassName.setText(className);
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

    private void startApiTasks() {
        new RxInfoMayTreatsTask(this, getMayTreatsRequest()).execute("");
        new RxInfoClassNameTask(this, getClassNameRequest()).execute("");
        new SimpleNameTask(this, getSimplePillNameRequest()).execute("");
    }

    private String getMayTreatsRequest() {
        return REQUEST_BASE.CLASS_BY_RXCUI + Integer.toString(rxcui) +
                REQUEST_BASE.MAY_TREAT_PARMS;
    }

    private String getClassNameRequest(){
        return REQUEST_BASE.CLASS_BY_RXCUI + Integer.toString(rxcui) +
                REQUEST_BASE.CLASS_BY_RXCUI_PARMS;
    }

    private String getSimplePillNameRequest(){
        return REQUEST_BASE.SIMPLE_NAME_BY_RXCUI + Integer.toString(rxcui) +
                REQUEST_BASE.SIMPLE_NAME_PARMS;
    }

    public void populateMayTreat(String diseases) {
        tvMayTreat.setText(diseases);
    }

    public void populateSimplePillName(String simpleNameFromApi){
        shortName = simpleNameFromApi;
        tvName.setText(simpleNameFromApi);
    }

    private void startInteractions(){
        Intent intent = new Intent(context, InteractionsActivity.class);
        intent.putExtra("name", longName);
        intent.putExtra("rxcui", rxcui);
        startActivity(intent);
    }

    private void dieAndStartAddMed(){
        Intent intent = new Intent(context, AddOrEditMyMedActivity.class);
        intent.putExtra("rxcui", Integer.toString(rxcui));
        intent.putExtra("name", shortName);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
        finish();
    }
}
