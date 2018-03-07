package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stvjuliengmail.smartmeds.R;

public class MenuActivity extends AppCompatActivity {

    Button btnSearchActivity;
    Button btnMyMedsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.deleteDatabase("SmartMeds.db");
        btnSearchActivity = (Button)findViewById(R.id.btnSearchActivity);
        btnMyMedsActivity = (Button)findViewById(R.id.btnMyMedsActivity);

        btnSearchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUp(SearchActivity.class);
            }
        });

        btnMyMedsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUp(MyMedsActivity.class);
            }
        });
    }

    public void startUp(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
