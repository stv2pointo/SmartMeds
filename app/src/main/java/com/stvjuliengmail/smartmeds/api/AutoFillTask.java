package com.stvjuliengmail.smartmeds.api;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stvjuliengmail.smartmeds.activity.NameListActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by shaht_000 on 4/5/2018.
 */

public class AutoFillTask extends AsyncTask<String, Integer, String>
{

    private final String TAG = getClass().getSimpleName();
    private String rawJson = "";
    private ArrayList<String> pillNamesFromAPI;
    private final WeakReference<NameListActivity> weakActivity;
    private String parPillName;
    private ProgressDialog progressDialog;

    public AutoFillTask(NameListActivity nameListActivity, String parPillName)
    {
        this.weakActivity = new WeakReference<NameListActivity>(nameListActivity);
        this.parPillName = parPillName;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progressDialog = new ProgressDialog(weakActivity.get());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... strings)
    {
        rawJson = "blah";
        try {
            Log.d(TAG, "DoInBackground");
            URL url = new URL(REQUEST_BASE.NAME_SUGGESTION);
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
        GsonBuilder gsonB = new GsonBuilder();
        Gson gson = gsonB.create();
//        interactions = new ArrayList<>();
//
        try {
            NameSearchResult resultObject;
            resultObject = gson.fromJson(rawJson, NameSearchResult.class);
            pillNamesFromAPI = resultToPillNames(resultObject);
            if (pillNamesFromAPI != null && pillNamesFromAPI.size() > 0) {
                setResultsInUI();
            } else {
                Toast.makeText(weakActivity.get(), "No Results", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.d(TAG, "onPostExecute: Exception !!!" + e.getMessage());
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        setResultsInUI();
    }

    private ArrayList<String> resultToPillNames(NameSearchResult result)
    {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(result.getSuggestionGroup().getSuggestionList().getSuggestion()));
        return list;
    }

    public void setResultsInUI()
    {

        if (pillNamesFromAPI != null)
        {
            NameListActivity activity = weakActivity.get();
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed())
            {
                activity.populateListView(pillNamesFromAPI);
            }
        }
        Log.d(TAG, "NULL");
    }
}
