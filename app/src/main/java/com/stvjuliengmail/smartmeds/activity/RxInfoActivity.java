package com.stvjuliengmail.smartmeds.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stvjuliengmail.smartmeds.R;
import com.stvjuliengmail.smartmeds.model.RxImagesResult;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RxInfoActivity extends AppCompatActivity {

    TextView tvTest, tvMayTreat;
    int rxcui = -1;
    ArrayList<String> mayTreats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_info);

        tvTest = (TextView) findViewById(R.id.tvTest);
        tvMayTreat = (TextView) findViewById(R.id.tvMayTreat);

        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        tvTest.setText(Integer.toString(rxcui));

        mayTreats = getMayTreats();
        populateMayTreat();

        new getMayTreatsJSON().execute("");

    }

    public ArrayList<String> getMayTreats() {
        ArrayList<String> symptoms = new ArrayList<>();
        symptoms.add("one");
        symptoms.add("two");
        symptoms.add("three");
        return symptoms;
    }

    public void populateMayTreat() {
        String result = "May Treat:\n";
        if (mayTreats != null && mayTreats.size() > 0) {
            for (String str : mayTreats) {
                result += str + "\n";
            }
        }
        tvMayTreat.setText(result);
    }

    public class getMayTreatsJSON extends AsyncTask<String, Integer, String> {
        String rawJson = "";

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=966200&relaSource=NDFRT&relas=may_treat");
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
                        //Log.d("test", "raw json string length = " + rawJson.length());
                        Log.d("test", "raw first 256 chars = " + rawJson.substring(0, 256));
                        //Log.d("test", "ra json last 256 = " + rawJson.substring(rawJson.length()-256,rawJson.length()));
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
//                RxImagesResult rxImagesResult = jsonParse(result);
//                populateRecyclerView(rxImagesResult);
                mayTreats = jsonParse(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private ArrayList<String> jsonParse(String rawJson) {
//            GsonBuilder gsonB = new GsonBuilder();
//            Gson gson = gsonB.create();
//
//            RxImagesResult rxImagesResult = null;
            ArrayList<String> mayTreatsFromJson = new ArrayList<>();

            try {
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(rawJson);
                if (element.isJsonObject()) {


                    JsonObject wholeThing = element.getAsJsonObject();

                    JsonObject rxclassDrugInfoList = wholeThing.getAsJsonObject("rxclassDrugInfoList");
                    JsonArray rxclassDrugInfos = rxclassDrugInfoList.getAsJsonArray("rxclassDrugInfo");
                    Log.d("test", "This list should have six and the count is " + Integer.toString(rxclassDrugInfos.size()));
//                    for(JsonElement listElement: rxclassDrugInfos){
//                        JsonObject disease = listElement.getAsJsonObject("rxclassMinConceptItem");
//
//                    }

                }
//                rxImagesResult = gson.fromJson(rawJson, RxImagesResult.class);
//                Log.d("test", "the replyStatus.img count is " + Integer.toString(rxImagesResult.getReplyStatus().getImageCount()));
//                Log.d("test", "the first imageUrl in the array is " + rxImagesResult.getNlmRxImages()[0].getImageUrl());
            } catch (Exception e) {
                Log.d("test", e.getMessage());
            }
//            return rxImagesResult;
            return new ArrayList<>();
        } // end parse

    } // end getImageList task
}
