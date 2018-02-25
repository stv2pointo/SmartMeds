package com.stvjuliengmail.smartmeds.api;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Steven on 2/21/2018.
 */

public abstract class RxInfoStringTask extends AsyncTask<String, Integer, String> {
    protected final String TAG = getClass().getSimpleName();
    protected String rawJson = "";
    protected final WeakReference<RxInfoActivity> weakReference;
    protected String requestPath;
    protected String resultString;

    public RxInfoStringTask(RxInfoActivity activity, String requestPath) {
        this.weakReference = new WeakReference<RxInfoActivity>(activity);
        this.requestPath = requestPath;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(requestPath);
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
                    break;
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
            jsonParse(result);
            if(resultString != null && !resultString.isEmpty()){
                setResultsInUI();
            }
        } catch (Exception e) {
            Log.d(TAG, "onPostExecute: Exception !!!" + e.getMessage());
        }

    }

    abstract void jsonParse(String rawJson);

    abstract void setResultsInUI();
}
