package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.RecyclerViewItemClickListener;
import com.stvjuliengmail.smartmeds.adapter.ResultsAdapter;
import com.stvjuliengmail.smartmeds.api.ImageListTask;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    // TODO: replace hard coded imprint with search parms later
    String imprint;
    Button btnLoadList;
    RecyclerView recyclerView;
    ResultsAdapter adapter;
    List<RxImagesResult.NlmRxImage> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //final Context context = this;
        imprint = "dp";

        recyclerView = (RecyclerView) findViewById(R.id.recVwResultList);
        btnLoadList = (Button) findViewById(R.id.btnLoadList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ResultsAdapter(imageList, R.layout.list_search_result,
                getApplicationContext());

        //Create custom interface object and send it to adapter
        //Adapter triggers it when any item view is clicked
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(context, "item clicked " + Integer.toString(imageList.get(position).getRxcui()), Toast.LENGTH_SHORT).show();
                startRxInfoActivity(imageList.get(position).getRxcui());
            }

            // TODO: Use long click to open option to save to myMeds
            @Override
            public void onItemLongClick(View view, int position) {
                //Toast.makeText(MainActivity.this, getResources().getString(R.string.long_clicked_item, albumList.get(position).getAlbumName()), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        btnLoadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        Log.d(TAG, "ONCREATE()");
    }

    public void search(){
        new ImageListTask(this, imprint).execute("");
    }

    public void populateRecyclerView(RxImagesResult rxImagesResult) {
        if (rxImagesResult != null) {
            imageList.clear();
            imageList.addAll(Arrays.asList(rxImagesResult.getNlmRxImages()));
            adapter.notifyDataSetChanged();
        }
    }

    public void startRxInfoActivity(int rxcui) {
        Intent intent = new Intent(this, RxInfoActivity.class);
        intent.putExtra("rxcui", rxcui);
        startActivity(intent);
    }
}

