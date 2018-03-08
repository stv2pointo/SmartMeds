package com.stvjuliengmail.smartmeds.api;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stvjuliengmail.smartmeds.activity.InteractionsActivity;
import com.stvjuliengmail.smartmeds.activity.MyInteractionsActivity;
import com.stvjuliengmail.smartmeds.model.Interaction;
import com.stvjuliengmail.smartmeds.model.InteractionsResult;
import com.stvjuliengmail.smartmeds.model.MyInteraction;
import com.stvjuliengmail.smartmeds.model.MyListInteractionResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Steven on 3/7/2018.
 */

public class MyInteractionsTask extends AsyncTask<String, Integer, String> {
    private final String TAG = getClass().getSimpleName();
    private String rawJson = "";
    private ArrayList<MyInteraction> myInteractions;
    private final WeakReference<MyInteractionsActivity> weakActivity;
    private String[] rxcuis;
    private String disclaimer;
    private ProgressDialog progressDialog;


    public MyInteractionsTask(MyInteractionsActivity myInteractionsActivity, String[] rxcuis) {
        this.weakActivity = new WeakReference<MyInteractionsActivity>(myInteractionsActivity);
        this.rxcuis = rxcuis;
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
        myInteractions = new ArrayList<>();

        try {
            MyListInteractionResult resultObject = jsonParse(result);
            myInteractions = resultToMyInteraction(resultObject);
            disclaimer = resultObject.getNlmDisclaimer();
            if (myInteractions != null && myInteractions.size() > 0) {
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

    private MyListInteractionResult jsonParse(String rawJson) {
        GsonBuilder gsonB = new GsonBuilder();
        Gson gson = gsonB.create();

        MyListInteractionResult myListInteractionResult = null;
        try {
            myListInteractionResult = gson.fromJson(rawJson, MyListInteractionResult.class);
        } catch (Exception e) {
            Log.d(TAG, "jsonParse() Exception !!!: " + e.getMessage());
        }
        return myListInteractionResult;
    }

    private String buildRequest() {
        String request = REQUEST_BASE.INTERACTIONS_FOR_LIST;
        if(rxcuis != null && rxcuis.length > 1){
            for(int i=0;i<rxcuis.length;i++){
                if(i==0){
                    request += rxcuis[0];
                }
                else {
                    request += "+" + rxcuis[i];
                }
            }
        }
        Log.d(TAG, request);
        return request;
    }

    public ArrayList<MyInteraction> resultToMyInteraction(MyListInteractionResult myListInteractionResult) {
        if(myListInteractionResult != null){
            disclaimer = myListInteractionResult.getNlmDisclaimer();
            MyListInteractionResult.FullInteractionTypeGroup onlyOneInTheUselessArray =
                    myListInteractionResult.getFullInteractionTypeGroup()[0];
            MyListInteractionResult.FullInteractionType[] types = onlyOneInTheUselessArray.getFullInteractionType();

            for(MyListInteractionResult.FullInteractionType type : types){
                MyListInteractionResult.InteractionPair pair = type.getInteractionPair()[0];
                String description = pair.getDescription();
                MyListInteractionResult.InteractionConcept[] concepts = pair.getInteractionConcept();
                MyListInteractionResult.SourceConceptItem sourceConceptItem1 = concepts[0].getSourceConceptItem();
                String drug1 = sourceConceptItem1.getName();
                String url = sourceConceptItem1.getUrl();
                MyListInteractionResult.SourceConceptItem sourceConceptItem2 = concepts[1].getSourceConceptItem();
                String drug2 = sourceConceptItem2.getName();
                myInteractions.add(new MyInteraction(drug1, drug2, description, url));
            }
        }
        return myInteractions;
    }

    public void setResultsInUI() {
        if (myInteractions != null) {
            MyInteractionsActivity activity = weakActivity.get();
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                activity.populateDisclaimer(disclaimer);
                activity.populateRecyclerView(myInteractions);
            }
        }
    }

}
