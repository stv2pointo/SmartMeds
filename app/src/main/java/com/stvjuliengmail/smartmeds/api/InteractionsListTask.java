package com.stvjuliengmail.smartmeds.api;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.activity.InteractionsActivity;
import com.stvjuliengmail.smartmeds.model.Interaction;
import com.stvjuliengmail.smartmeds.model.InteractionsResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.provider.Settings.Global.getString;

/**
 * Created by Steven on 2/23/2018.
 */

public class InteractionsListTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getSimpleName();
    private String rawJson = "";
    private ArrayList<Interaction> interactions;
    private final WeakReference<InteractionsActivity> weakActivity;
    private String strRxcui;
    private String disclaimer;
    private ProgressDialog progressDialog;


    public InteractionsListTask(InteractionsActivity interactionsActivity, String strRxcui) {
        this.weakActivity = new WeakReference<InteractionsActivity>(interactionsActivity);
        this.strRxcui = strRxcui;
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
        interactions = new ArrayList<>();

        try {
            InteractionsResult resultObject = jsonParse(result);
            interactions = resultToInteractions(resultObject);
            disclaimer = resultObject.getNlmDisclaimer();
            if (interactions != null && interactions.size() > 0) {
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
    }

    private InteractionsResult jsonParse(String rawJson) {
        GsonBuilder gsonB = new GsonBuilder();
        Gson gson = gsonB.create();

        InteractionsResult interactionsResult = null;
        try {
            interactionsResult = gson.fromJson(rawJson, InteractionsResult.class);
        } catch (Exception e) {
            Log.d(TAG, "jsonParse() Exception !!!: " + e.getMessage());
        }
        return interactionsResult;
    }

    private String buildRequest() {
        String request = REQUEST_BASE.INTERACTIONS_BY_RXCUI;
        request += strRxcui;
        request += "&sources=ONCHigh";
        //Log.d(TAG, request);
        return request;
    }

    public ArrayList<Interaction> resultToInteractions(InteractionsResult interactionsResult) {
        try{
            disclaimer = interactionsResult.getNlmDisclaimer();
            InteractionsResult.InteractionPair[] pairs = interactionsResult.getInteractionTypeGroup()[0].getInteractionType()[0].getInteractionPair();
            for (InteractionsResult.InteractionPair pair : pairs) {
                String _name = pair.getInteractionConcept()[1].getSourceConceptItem().getName();
                String _desc = pair.getDescription();
                String _severity = pair.getSeverity();
                interactions.add(new Interaction(_name, _desc, "Severity: " + _severity) );
            }
        }
        catch (Exception e){
            Log.d(TAG, "BAD RESULT OBJECT: " + e.getMessage());
            interactions = new ArrayList<>();
            interactions.add(new Interaction(
                    "No interactions with high severity found.",
                    "Seek further information from a health professional",
                    ""));
        }
        return interactions;
    }

    public void setResultsInUI() {
        if (interactions != null) {
            InteractionsActivity activity = weakActivity.get();
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                activity.populateDisclaimer(disclaimer);
                activity.populateRecyclerView(interactions);
            }
        }
        Log.d(TAG, "NULL");
    }

}
