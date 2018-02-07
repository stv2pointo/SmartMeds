package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.ResultsAdapter;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    // TODO: replace hard coded imprint with search parms later
    String imprint;
    Button btnRxInfo;
    Button btnLoadList;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<RxImagesResult.NlmRxImage> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imprint = "dp";

        recyclerView = (RecyclerView)findViewById(R.id.recVwResultList);
        btnLoadList = (Button)findViewById(R.id.btnLoadList);
        btnRxInfo = (Button) findViewById(R.id.btnRxInfo);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ResultsAdapter(imageList, R.layout.list_search_result,
                getApplicationContext());

        recyclerView.setAdapter(adapter);

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
