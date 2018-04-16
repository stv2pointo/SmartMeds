package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.stvjuliengmail.smartmeds.fragment.MyMedFragment;
import com.stvjuliengmail.smartmeds.fragment.RxInfoButtonsFragment;
import com.stvjuliengmail.smartmeds.model.MyMed;

import java.util.ArrayList;

public class RxInfoActivity extends AppCompatActivity implements MyMedFragment.OnFragmentInteractionListener,
        RxInfoButtonsFragment.OnFragmentInteractionListener {
    private final String TAG = getClass().getSimpleName();
    private TextView tvName, tvFullName, tvMayTreat, tvClassName;
    private ImageView imageView;
    private int rxcui; // the id of the selected pill
    private String longName;
    private String shortName;
    private String imageUrl;
    private ArrayList<String> mayTreatDiseaseNames;
    private MyMed myMed;
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
        wireUpImageClick();
        displayName();
        displayImage();
        if (myMed != null) {
            displayMyMedInfoFragment();
        } else {
            displayRxInfoButtonFragment();
        }
        startApiTasks();
    }

    public void unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        myMed = extras.getParcelable("myMed");
        if(myMed == null){
            rxcui = extras.getInt("rxcui");
            longName = extras.getString("name");
            imageUrl = extras.getString("imageUrl");
        }
        else{
            rxcui = Integer.valueOf(myMed.getRxcui());
            longName = "";
            imageUrl = myMed.getImageUrl();
        }
    }

    public void instantiateUiElements() {
        tvName = findViewById(R.id.tvName);
        tvFullName = findViewById(R.id.tvFullName);
        tvMayTreat = findViewById(R.id.tvMayTreat);
        tvClassName = findViewById(R.id.tvClassName);
        imageView = findViewById(R.id.imageView);
    }

    private void displayMyMedInfoFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("myMed", myMed);
        bundle.putString("longName", longName);
        MyMedFragment fragment = new MyMedFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_my_med, fragment, "MyMedFrag").commit();
    }

    private void displayRxInfoButtonFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_my_med, new RxInfoButtonsFragment(), "RxInfoBtnsFrag").commit();
    }

    private void wireUpSaveToMyMedsButton() {
//        fabSaveMyMeds.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               dieAndStartAddMed();
//            }
//        });
    }

    private void wireUpInteractionsButton() {
//        btnInteractions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startInteractions();
//            }
//        });
    }

    private void wireUpImageClick() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImageView();
            }
        });
    }

    private void displayName() {
        tvFullName.setText(longName);
    }

    public void displayClassName(String className) {
        tvClassName.setText(className);
    }

    public void displayImage() {
        ImageDownloadTask task = new ImageDownloadTask();
        try {
            Bitmap myBitmap = task.execute(imageUrl).get();
            if (myBitmap == null) {
                imageView.setImageResource(R.drawable.no_img_avail);
            } else {
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

    private String getClassNameRequest() {
        return REQUEST_BASE.CLASS_BY_RXCUI + Integer.toString(rxcui) +
                REQUEST_BASE.CLASS_BY_RXCUI_PARMS;
    }

    private String getSimplePillNameRequest() {
        return REQUEST_BASE.SIMPLE_NAME_BY_RXCUI + Integer.toString(rxcui) +
                REQUEST_BASE.SIMPLE_NAME_PARMS;
    }

    public void populateMayTreat(String diseases) {
        tvMayTreat.setText(diseases);
    }

    public void populateSimplePillName(String simpleNameFromApi) {
        shortName = simpleNameFromApi;
        tvName.setText(simpleNameFromApi);
    }

    public void startInteractions() {
        Intent intent = new Intent(context, InteractionsActivity.class);
        intent.putExtra("name", longName);
        intent.putExtra("rxcui", rxcui);
        startActivity(intent);
    }

    public void dieAndStartAddMed() {
        Intent intent = new Intent(context, AddOrEditMyMedActivity.class);
        intent.putExtra("rxcui", Integer.toString(rxcui));
        intent.putExtra("name", shortName);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
        finish();
    }

    private void startImageView() {
        Intent intent = new Intent(context, ViewImageActivity.class);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
