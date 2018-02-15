package com.stvjuliengmail.smartmeds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.stvjuliengmail.smartmeds.R;

import java.util.ArrayList;
import java.util.List;

public class RxInfoActivity extends AppCompatActivity {

    TextView tvTest, tvMayTreat;
    int rxcui = -1;
    ArrayList<String> mayTreats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_info);

        tvTest = (TextView)findViewById(R.id.tvTest);
        tvMayTreat = (TextView)findViewById(R.id.tvMayTreat);

        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        tvTest.setText(Integer.toString(rxcui));

        mayTreats = getMayTreats();
        populateMayTreat();

    }

    public ArrayList<String> getMayTreats(){
        ArrayList<String> symptoms = new ArrayList<>();
        symptoms.add("one");
        symptoms.add("two");
        symptoms.add("three");
        return symptoms;
    }

    public void populateMayTreat(){
        String result = "May Treat:\n";
        if (mayTreats != null && mayTreats.size() > 0){
            for(String str : mayTreats){
                result += str + "\n";
            }
        }
        tvMayTreat.setText(result);
    }
}
