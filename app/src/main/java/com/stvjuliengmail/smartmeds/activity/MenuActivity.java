package com.stvjuliengmail.smartmeds.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stvjuliengmail.smartmeds.R;

public class MenuActivity extends AppCompatActivity {

    Button btnSearchActivity;
    Button btnMyMedsActivity;
    Button btnDisclaimer;

    boolean agreed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        btnSearchActivity = (Button)findViewById(R.id.btnSearchActivity);
        btnMyMedsActivity = (Button)findViewById(R.id.btnMyMedsActivity);
        btnDisclaimer = (Button)findViewById(R.id.btnDisclaimer);

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

        if (!agreed){
            btnMyMedsActivity.setEnabled(false);
            btnSearchActivity.setEnabled(false);
            disclaimer();
        }
//        else if (!agreed) {
//            btnSearchActivity.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    disclaimer();
//                }
//            });
//        }
    }

    private void disclaimer() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Disclaimer");
        alertDialog.setMessage("This app could kill you.  Are you ok with that?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "AGREE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        agreed = true;
                        btnMyMedsActivity.setEnabled(true);
                        btnSearchActivity.setEnabled(true);
                    }
                });
        alertDialog.show();
    }

    public void startUp(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
