package com.stvjuliengmail.smartmeds.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.stvjuliengmail.smartmeds.R;

public class MenuActivity extends AppCompatActivity {

    private Button btnSearchActivity;
    private Button btnMyMedsActivity;
    private Button btnDisclaimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeUiComponents();
        if (!getIsAgreed()){
//            btnMyMedsActivity.setEnabled(false);
//            btnSearchActivity.setEnabled(false);
            disclaimer();
        }
    }

    private void initializeUiComponents(){
        btnSearchActivity = findViewById(R.id.btnSearchActivity);
        btnMyMedsActivity = findViewById(R.id.btnMyMedsActivity);
        btnDisclaimer = findViewById(R.id.btnDisclaimer);

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

        btnDisclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disclaimer();
            }
        });
    }

    private void disclaimer() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Disclaimer");
        alertDialog.setMessage("The information in this app is not intended or implied to be a substitute for " +
                "professional medical advice, diagnosis or treatment. All content, including text, graphics, " +
                "images and information, contained on or available through this app is for general information purposes only. " +
                "SmartMeds makes no representation and assumes no responsibility for the accuracy of information contained on " +
                "or available through this app, and such information is subject to change without notice. You are encouraged to " +
                "confirm any information obtained from or through this app with other sources, and review all information regarding " +
                "any medical condition or treatment with your physician. NEVER DISREGARD PROFESSIONAL MEDICAL ADVICE OR DELAY SEEKING " +
                "MEDICAL TREATMENT BECAUSE OF SOMETHING YOU HAVE READ ON OR ACCESSED THROUGH THIS APP.");
        if(getIsAgreed()){
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Close",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }
        else{
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            killApp();
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "AGREE",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setIsAgreed(true);
//                        btnMyMedsActivity.setEnabled(true);
//                        btnSearchActivity.setEnabled(true);
                        }
                    });
        }
        alertDialog.show();
    }

    private void setIsAgreed(boolean agreed){
        SharedPreferences mSharedPreferences = getSharedPreferences("agreed", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean("agreed", agreed);
        mEditor.apply();
    }

    private boolean getIsAgreed(){
        SharedPreferences mSharedPreferences = getSharedPreferences("agreed", MODE_PRIVATE);
        boolean haveAgreed = mSharedPreferences.getBoolean("agreed", false);
        return haveAgreed;
    }

    public void startUp(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void killApp(){
        this.finish();
    }
}
