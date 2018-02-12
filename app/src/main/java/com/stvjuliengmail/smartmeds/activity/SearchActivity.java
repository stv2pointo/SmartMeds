package com.stvjuliengmail.smartmeds.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.adapter.ResultsAdapter;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

        btnLoadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getImageListData().execute("");
            }
        });

        btnRxInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMyAct();
            }
        });

    }

    public void loadMyAct(){
        Intent intent = new Intent(this, RxInfo.class);
        startActivity(intent);
    }

    public class getImageListData extends AsyncTask<String, Integer, String> {
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
                showData(rxImagesResult);
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

    public void showData(RxImagesResult rxImagesResult){

        if (rxImagesResult != null){
            imageList.clear();
            imageList.addAll(Arrays.asList(rxImagesResult.getNlmRxImages()));
            adapter.notifyDataSetChanged();
        }
    }
}

