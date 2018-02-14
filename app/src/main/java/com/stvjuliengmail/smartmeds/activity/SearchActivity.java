package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.RecyclerViewItemClickListener;
import com.stvjuliengmail.smartmeds.adapter.ResultsAdapter;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

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

        imprint = "dp";

        recyclerView = (RecyclerView)findViewById(R.id.recVwResultList);
        btnLoadList = (Button)findViewById(R.id.btnLoadList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ResultsAdapter(imageList, R.layout.list_search_result,
                getApplicationContext());

        //Create custom interface object and send it to adapter
        //Adapter trigger it when any item view is clicked
        final Context context = this;
        adapter.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context, "item clicked " + Integer.toString(imageList.get(position).getRxcui()), Toast.LENGTH_SHORT).show();
                startRxInfoActivity(imageList.get(position).getRxcui());
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //Toast.makeText(MainActivity.this, getResources().getString(R.string.long_clicked_item, albumList.get(position).getAlbumName()), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        btnLoadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getImageListJSON().execute("");
            }
        });

    }

    public void startRxInfoActivity(int rxcui){
        Intent intent = new Intent(this, RxInfoActivity.class);
        intent.putExtra("rxcui",rxcui);
        startActivity(intent);
    }

    public class getImageListJSON extends AsyncTask<String, Integer, String> {
        String rawJson = "";

        @Override
        protected String doInBackground(String... params){
            try{
                URL url = new URL("https://rximage.nlm.nih.gov/api/rximage/1/rxnav?&resolution=600&imprint=dp&rLimit=12");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int status = connection.getResponseCode();
                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br =
                                new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        rawJson = br.readLine();
                        //Log.d("test", "raw json string length = " + rawJson.length());
                        Log.d("test", "raw first 256 chars = " + rawJson.substring(0,256));
                        //Log.d("test", "ra json last 256 = " + rawJson.substring(rawJson.length()-256,rawJson.length()));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return rawJson;
        } // end doInBackg...


        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try{
                RxImagesResult rxImagesResult = jsonParse(result);
                populateRecyclerView(rxImagesResult);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        private RxImagesResult jsonParse(String rawJson) {
            GsonBuilder gsonB = new GsonBuilder();
            Gson gson = gsonB.create();

            RxImagesResult rxImagesResult = null;

            try {
                rxImagesResult = gson.fromJson(rawJson, RxImagesResult.class);
                Log.d("test", "the replyStatus.img count is " + Integer.toString(rxImagesResult.getReplyStatus().getImageCount()));
                Log.d("test", "the first imageUrl in the array is " + rxImagesResult.getNlmRxImages()[0].getImageUrl());
            }
            catch (Exception e) {
                Log.d("test", e.getMessage());
            }
            return rxImagesResult;
        } // end parse

    } // end getImageList task

    public void populateRecyclerView(RxImagesResult rxImagesResult){

        if (rxImagesResult != null){
            imageList.clear();
            imageList.addAll(Arrays.asList(rxImagesResult.getNlmRxImages()));
            adapter.notifyDataSetChanged();
        }
    }
}

