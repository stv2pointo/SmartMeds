package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ImageView;

import com.stvjuliengmail.smartmeds.R;

import static com.stvjuliengmail.smartmeds.R.anim.mytransition;

/**
 * Created by lisatokunaga on 3/25/18.
 */

public class Splash extends AppCompatActivity{
private TextView tv;
private ImageView iv;

    protected void onCreate (Bundle savedInstantState ){
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_splash);
        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);
        Animation myanim = AnimationUtils.loadAnimation(this, mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this, MenuActivity.class);
        Thread timer = new Thread(){
            public void run() {
                try {
                    sleep(5000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
            timer.start();
    }

}
