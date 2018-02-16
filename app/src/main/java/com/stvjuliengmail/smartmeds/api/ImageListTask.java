package com.stvjuliengmail.smartmeds.api;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stvjuliengmail.smartmeds.activity.SearchActivity;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Steven on 2/16/2018.
 */

public class ImageListTask extends AsyncTask<String, Integer, String> {
    String rawJson = "";
    RxImagesResult rxImagesResult;
    SearchActivity searchActivity;

    public ImageListTask(SearchActivity searchActivity) {
        this.searchActivity = searchActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
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
                    Log.d("test", "raw first 256 chars = " + rawJson.substring(0, 256));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rawJson;
    } // end doInBackg...


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            rxImagesResult = jsonParse(result);
            setResultsInUI();
        } catch (Exception e) {
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
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }
        return rxImagesResult;
    } // end parse


    public void setResultsInUI(){
        searchActivity.populateRecyclerView(rxImagesResult);
    }
} // end getImageList task
