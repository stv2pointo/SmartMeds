package com.stvjuliengmail.smartmeds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.stvjuliengmail.smartmeds.R;

public class SavePillActivity extends AppCompatActivity {

    private String rxcuiAsString, name, imageUrl;
    private EditText etName, etDoctor, etDirections, etPharmacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_pill);

        unpackIntentExtras();

        etName = (EditText) findViewById(R.id.etName);
        etDoctor = (EditText) findViewById(R.id.etDoctor);
        etDirections = (EditText) findViewById(R.id.etDirections);
        etPharmacy = (EditText) findViewById(R.id.etPharmacy);

        displayDataFromIntent();
    }

    private void unpackIntentExtras() {
        Bundle extras = getIntent().getExtras();
        rxcuiAsString = extras.getString("rxcui");
        name = extras.getString("name");
        imageUrl = extras.getString("imageUrl");
    }

    private void displayDataFromIntent() {
        etName.setText(name);
    }
}
