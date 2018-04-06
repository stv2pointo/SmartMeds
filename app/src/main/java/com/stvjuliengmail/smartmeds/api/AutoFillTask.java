package com.stvjuliengmail.smartmeds.api;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.stvjuliengmail.smartmeds.activity.NameListActivity;

import java.lang.ref.WeakReference;
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
//        try {
//            URL url = new URL(buildRequest());
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.connect();
//            int status = connection.getResponseCode();
//            switch (status) {
//                case 200:
//                case 201:
//                    BufferedReader br =
//                            new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                    rawJson = br.readLine();
//                    break;
//                default:
//                    Toast.makeText(weakActivity.get(), "Server Error", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "doInBackground() Exception !!: " + e.getMessage());
//        }
        return rawJson;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
//        interactions = new ArrayList<>();
//
//        try {
//            InteractionsResult resultObject = jsonParse(result);
//            interactions = resultToInteractions(resultObject);
//            disclaimer = resultObject.getNlmDisclaimer();
//            if (interactions != null && interactions.size() > 0) {
//                setResultsInUI();
//            } else {
//                Toast.makeText(weakActivity.get(), "No Results", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "onPostExecute: Exception !!!" + e.getMessage());
//        }
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        setResultsInUI();
    }
    public void setResultsInUI()
    {
        pillNamesFromAPI = new ArrayList<String>(
                Arrays.asList("Android", "iPhone", "WindowsMobile",
                        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                        "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                        "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                        "Android", "iPhone", "WindowsMobile"));
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
