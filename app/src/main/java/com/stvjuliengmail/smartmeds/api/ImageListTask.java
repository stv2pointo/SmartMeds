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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageListTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getSimpleName();
    private String rawJson = "";
    private RxImagesResult rxImagesResult;
    private final WeakReference<SearchActivity> weakActivity;
    private ImageFilter imageFilter;
    private ProgressDialog progressDialog;

    public ImageListTask(SearchActivity searchActivity, ImageFilter imageFilter) {
        this.weakActivity = new WeakReference<SearchActivity>(searchActivity);
        this.imageFilter = imageFilter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(weakActivity.get());
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
                    Log.d(TAG, "raw first 10 chars = " + rawJson.substring(0, 10));
                    break;
                default:
                    Toast.makeText(weakActivity.get(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d(TAG, "doInBackground() Exception !!: " + e.getMessage());
        }
        return rawJson;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            rxImagesResult = jsonParse(result);
            if(rxImagesResult != null && rxImagesResult.getNlmRxImages() != null){
                setResultsInUI();
            }
            else {
                Toast.makeText(weakActivity.get(), "No Results", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d(TAG, "onPostExecute: Exception !!!" + e.getMessage());
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
        } catch (Exception e) {
            Log.d(TAG, "jsonParse() Exception !!!: " + e.getMessage());
        }
        return rxImagesResult;
    }

    private String buildRequest() {
        String request = REQUEST_BASE.IMAGE;
        request += (imageFilter.imprint != null && !imageFilter.imprint.isEmpty()) ?
                "&imprint=" + imageFilter.imprint : "";
        request += (imageFilter.color != null && !imageFilter.color.isEmpty()) ?
                "&color=" + imageFilter.color : "";
        request += (imageFilter.shape != null && !imageFilter.shape.isEmpty()) ?
                "&shape=" + imageFilter.shape : "";
        request += (imageFilter.limit != 0) ?
                "&rLimit=" + Integer.toString(imageFilter.limit) : "";
        request += (imageFilter.name != null && imageFilter.name.length() >= 3) ?
                "&name=" + imageFilter.name : "";
        Log.d(TAG, request);
        return request;
    }


    public void setResultsInUI() {
        if(rxImagesResult != null){
            SearchActivity activity = weakActivity.get();
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                activity.populateRecyclerView(rxImagesResult);
            }
        }
    }

    public static class ImageFilter {
        public String imprint;
        public String name;
        public String color;
        public String shape;
        public int limit;
    }

}
