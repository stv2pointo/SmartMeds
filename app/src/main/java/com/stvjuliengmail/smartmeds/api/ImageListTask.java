package com.stvjuliengmail.smartmeds.api;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stvjuliengmail.smartmeds.R;
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
    private String rawJson = "";
    private RxImagesResult rxImagesResult;
    private SearchActivity searchActivity;
    private String baseRequest = "https://rximage.nlm.nih.gov/api/rximage/1/rxnav?&resolution=600";
//    private String imprint;
    private ImageFilter imageFilter;

    public ImageListTask(SearchActivity searchActivity, ImageFilter imageFilter) {
        this.searchActivity = searchActivity;
        //this.imprint = imprint;
        this.imageFilter = imageFilter;
    }

    ProgressDialog pd;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(searchActivity);
        pd.setMessage("Loading...");
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            //&imprint=" + imprint + "&rLimit=12";
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
                    Log.d("test", "raw first 256 chars = " + rawJson.substring(0, 256));
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
        if (pd != null)
        {
            pd.dismiss();
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
    }

    public String buildRequest(){
        //&imprint=" + imprint + "&rLimit=12";
        String request = baseRequest;
        request += (imageFilter.imp != null && !imageFilter.imp.isEmpty()) ?
                "&imprint=" + imageFilter.imp : "";
        request += (imageFilter.nam != null && !imageFilter.nam.isEmpty()) ?
                "&name=" + imageFilter.nam : "";
        request += (imageFilter.col != null && !imageFilter.col.isEmpty()) ?
                "&color=" + imageFilter.col : "";
        request += (imageFilter.shap != null & !imageFilter.shap.isEmpty()) ? //R.string.empty_shape_field)) ?
                "&shape=" + imageFilter.shap : "";
        request += (imageFilter.limit != 0) ?
                ("&rLimit=" + Integer.toString(imageFilter.limit)) : "";
        return request;
    }

    public void setResultsInUI(){
        searchActivity.populateRecyclerView(rxImagesResult);
    }

    public static class ImageFilter{
        public String imp;
        public String nam;
        public String col;
        public String shap;
        public int limit;
    }

}
