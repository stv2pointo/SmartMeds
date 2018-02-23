package com.stvjuliengmail.smartmeds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;

public class InteractionsActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private String name;
    private int rxcui;
    private TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactions);
        Log.d(TAG, "oncreate of interactions");
        unpackIntentExtras();
        initializeUiElements();
        displayUiElements();
    }

    private void initializeUiElements(){
        tvName = (TextView) findViewById(R.id.tvName);
    }
    private void displayUiElements() {
        tvName.setText(name);
    }

    private void unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        name = extras.getString("name");
    }

}
