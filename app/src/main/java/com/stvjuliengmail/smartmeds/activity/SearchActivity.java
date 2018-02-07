package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.stvjuliengmail.smartmeds.R;

public class SearchActivity extends AppCompatActivity {

    Button btnRxInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btnRxInfo = (Button) findViewById(R.id.btnRxInfo);
        btnRxInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMyAct();
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.listContainer,new RxInfo(),"RxInfo")
//                        .addToBackStack(null)
//                        .commit();
            }
        });
    }

    public void loadMyAct(){
        Intent intent = new Intent(this, RxInfo.class);
        startActivity(intent);
    }
//    public void loadFrag(){
//                        getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.listContainer,new RxInfo(),"RxInfo")
//                        .addToBackStack(null)
//                        .commit();
//    }
}
/** load a frag
 *
 *             getSupportFragmentManager().beginTransaction()
 .replace(R.id.container2,new FragmentA(),"FA2")
 .addToBackStack(null)
 .commit();
 */
