package com.stvjuliengmail.smartmeds.api;

import android.app.ProgressDialog;
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

public class ImageListTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getSimpleName();
    private String rawJson = "";
    private RxImagesResult rxImagesResult;
    private SearchActivity searchActivity;
    private String baseRequest = "https://rximage.nlm.nih.gov/api/rximage/1/rxnav?&resolution=600";
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            Log.d(TAG, e.getMessage());
        }
        return rxImagesResult;
    }

    public String buildRequest() {
        String request = baseRequest;
        request += (imageFilter.imp != null && !imageFilter.imp.isEmpty()) ?
                "&imprint=" + imageFilter.imp : "";
        request += (imageFilter.nam != null && !imageFilter.nam.isEmpty()) ?
                "&name=" + imageFilter.nam : "";
        request += (imageFilter.col != null && !imageFilter.col.isEmpty()) ?
                "&color=" + imageFilter.col : "";
        request += (imageFilter.shap != null & !imageFilter.shap.isEmpty()) ?
                "&shape=" + imageFilter.shap : "";
        request += (imageFilter.limit != 0) ?
                ("&rLimit=" + Integer.toString(imageFilter.limit)) : "";
        return request;
    }

    public void setResultsInUI() {
        searchActivity.populateRecyclerView(rxImagesResult);
    }

    public static class ImageFilter {
        public String imp;
        public String nam;
        public String col;
        public String shap;
        public int limit;
    }

}
