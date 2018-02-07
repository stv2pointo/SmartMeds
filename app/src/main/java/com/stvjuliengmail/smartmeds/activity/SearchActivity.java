package com.stvjuliengmail.smartmeds.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stvjuliengmail.smartmeds.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
}
/** load a frag
 *
 *             getSupportFragmentManager().beginTransaction()
 .replace(R.id.container2,new FragmentA(),"FA2")
 .addToBackStack(null)
 .commit();
 */
