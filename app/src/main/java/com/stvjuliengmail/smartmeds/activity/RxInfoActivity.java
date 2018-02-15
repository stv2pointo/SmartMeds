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
    int line = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_info);

        tvTest = (TextView) findViewById(R.id.tvTest);
        tvMayTreat = (TextView) findViewById(R.id.tvMayTreat);

        Bundle extras = getIntent().getExtras();
        rxcui = extras.getInt("rxcui");
        tvTest.setText(Integer.toString(rxcui));

        mayTreats = new ArrayList<String>();

        new getMayTreatsJSON().execute("");

    }

    public void populateMayTreat(ArrayList<String> diseases) {
        String result = "May Treat:\n";
        if (diseases != null && diseases.size() > 0) {
            for (String str : diseases) {
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
                String request = "https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui="
                        + Integer.toString(rxcui)+ "&relaSource=NDFRT&relas=may_treat";
//                URL url = new URL("https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=966200&relaSource=NDFRT&relas=may_treat");
                URL url = new URL(request);
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
        } // end doInBackg...


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                mayTreats = jsonParse(result);
                populateMayTreat(mayTreats);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private ArrayList<String> jsonParse(String rawJson) {
//            GsonBuilder gsonB = new GsonBuilder();
//            Gson gson = gsonB.create();

            ArrayList<String> mayTreatsFromJson = new ArrayList<>();

            try {
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(rawJson);
                if (element.isJsonObject()) {


                    JsonObject wholeThing = element.getAsJsonObject();

                    JsonObject rxclassDrugInfoList = wholeThing.getAsJsonObject("rxclassDrugInfoList");
                    JsonArray rxclassDrugInfos = rxclassDrugInfoList.getAsJsonArray("rxclassDrugInfo");
//                    Log.d("test", "This list should have six and the count is " + Integer.toString(rxclassDrugInfos.size()));
                    for(JsonElement listElement: rxclassDrugInfos){
                        JsonParser nestedParser = new JsonParser();
                        JsonElement nestedElement = nestedParser.parse(listElement.toString());
                        JsonObject unNamedListItem = nestedElement.getAsJsonObject();
                        JsonObject thingThatIactuallyWant = unNamedListItem.getAsJsonObject("rxclassMinConceptItem");
                        JsonElement elementIwant = thingThatIactuallyWant.get("className");
                        mayTreatsFromJson.add(elementIwant.getAsString());
                    }
                }
            } catch (Exception e) {
                Log.d("test", "EXCEPTION OCCURRED: " + e.getMessage());
            }
            return mayTreatsFromJson;
        } // end parse

    } // end getMayTreats task
}
