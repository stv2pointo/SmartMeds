package com.stvjuliengmail.smartmeds.api;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stvjuliengmail.smartmeds.activity.SearchActivity;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageListTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getSimpleName();
    private String rawJson = "";
    private RxImagesResult rxImagesResult;
    private SearchActivity searchActivity;
    private ImageFilter imageFilter;
    private ProgressDialog progressDialog;

    public ImageListTask(SearchActivity searchActivity, ImageFilter imageFilter) {
        this.searchActivity = searchActivity;
        this.imageFilter = imageFilter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(searchActivity);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(buildRequest());
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
                    Log.d(TAG, "raw first 256 chars = " + rawJson.substring(0, 256));
            }
        } catch (Exception e) {
            Toast.makeText(searchActivity, "Problems retrieving data",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "doInBackground() : " + e.getMessage());
        }
        return rawJson;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            rxImagesResult = jsonParse(result);
            setResultsInUI();
        } catch (Exception e) {
            Toast.makeText(searchActivity, "Problems retrieving data",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onPostExecute: " + e.getMessage());
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private RxImagesResult jsonParse(String rawJson) {
        GsonBuilder gsonB = new GsonBuilder();
        Gson gson = gsonB.create();

        RxImagesResult rxImagesResult = null;

        try {
            rxImagesResult = gson.fromJson(rawJson, RxImagesResult.class);
            Log.d(TAG, "the replyStatus.img count is " + Integer.toString(rxImagesResult.getReplyStatus().getImageCount()));
            Log.d(TAG, "the first imageUrl in the array is " + rxImagesResult.getNlmRxImages()[0].getImageUrl());
        } catch (Exception e) {
            Log.d(TAG, "jsonParse() : " + e.getMessage());
        }
        return rxImagesResult;
    }

    public String buildRequest() {
        String request = REQUEST_BASE.IMAGE;
        request += (imageFilter.imprint != null && !imageFilter.imprint.isEmpty()) ?
                "&imprint=" + imageFilter.imprint : "";
        request += (imageFilter.name != null && !imageFilter.name.isEmpty()) ?
                "&name=" + imageFilter.name : "";
        request += (imageFilter.color != null && !imageFilter.color.isEmpty()) ?
                "&color=" + imageFilter.color : "";
        request += (imageFilter.shape != null & !imageFilter.shape.isEmpty()) ?
                "&shape=" + imageFilter.shape : "";
        request += (imageFilter.limit != 0) ?
                ("&rLimit=" + Integer.toString(imageFilter.limit)) : "";
        return request;
    }

    public void setResultsInUI() {
        searchActivity.populateRecyclerView(rxImagesResult);
    }

    public static class ImageFilter {
        public String imprint;
        public String name;
        public String color;
        public String shape;
        public int limit;
    }

}
