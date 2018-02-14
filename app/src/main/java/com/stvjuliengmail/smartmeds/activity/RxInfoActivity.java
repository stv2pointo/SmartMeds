package com.stvjuliengmail.smartmeds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;

public class RxInfoActivity extends AppCompatActivity {

    TextView tvTest;
    int rxcui = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_info);

        tvTest = (TextView)findViewById(R.id.tvTest);


        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        tvTest.setText(Integer.toString(rxcui));
    }
}
