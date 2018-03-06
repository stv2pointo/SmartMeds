package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.api.ImageDownloadTask;
import com.stvjuliengmail.smartmeds.api.REQUEST_BASE;
import com.stvjuliengmail.smartmeds.api.RxInfoMayTreatsTask;
import com.stvjuliengmail.smartmeds.model.BitmapUtility;
import com.stvjuliengmail.smartmeds.model.DBHelper;

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
    private Bitmap pillImage;
    private byte[] pillImageArray;
    private String mayTreat;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id==R.id.action_mainmenu)
        {
            Intent mainMenuIntent = new Intent(RxInfoActivity.this, MenuActivity.class);
            startActivity(mainMenuIntent);
        }
        return super.onOptionsItemSelected(item);
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
                BitmapUtility bu = new BitmapUtility();
                pillImageArray = bu.getBytes(pillImage);
                db.insertRX(rxcui, name, pillImageArray, mayTreat);

                Intent intent = new Intent(context, MyMedsActivity.class);
                startActivity(intent);
                Toast.makeText(context, "Medication Saved.", Toast.LENGTH_SHORT).show();
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
            pillImage = task.execute(imageUrl).get();
            if(pillImage == null){
                imageView.setImageResource(R.drawable.no_img_avail);
            }
            else{
                imageView.setImageBitmap(pillImage);
            }
        } catch (Exception e) {
            Log.d(TAG, "display image exception: " + e.getMessage());
        }
    }

    public String getMayTreatsRequest() {
        return REQUEST_BASE.CLASS_BY_RXCUI + Integer.toString(rxcui) + "&relaSource=NDFRT&relas=may_treat";
    }

    public void populateMayTreat(String diseases) {
        mayTreat = diseases; // for database insert
        tvMayTreat.setText("May Treat: \n" + diseases);
    }

}
