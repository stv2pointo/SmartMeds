package com.stvjuliengmail.smartmeds.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stvjuliengmail.smartmeds.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RxInfoActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private TextView tvTest, tvMayTreat;
    private int rxcui = -1; // the id of the selected pill
    private ArrayList<String> mayTreatDiseaseNames;
    private FloatingActionButton fabSaveMyMeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_info);
        final Context context = this;
        // instantiate ui elements
        tvTest = (TextView) findViewById(R.id.tvTest);
        tvMayTreat = (TextView) findViewById(R.id.tvMayTreat);

        fabSaveMyMeds = (FloatingActionButton) findViewById(R.id.fabSaveMyMeds);
        fabSaveMyMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This should open a form",Toast.LENGTH_SHORT).show();
            }
        });
        // Create a collection for stuff that was passed to this activity on start
        Bundle extras = getIntent().getExtras();
        // get the id of the selected pill
        rxcui = extras.getInt("rxcui");

        // TODO: Remove and replace with a desired field
        tvTest.setText(Integer.toString(rxcui));

        // instantiate an empty list to put disease names in
        mayTreatDiseaseNames = new ArrayList<String>();

        // Create a new asynchronous task that will go get the "May Treat" disease names from rxNav
        new getMayTreatsJSON().execute("");

    }

    public void populateMayTreat(ArrayList<String> diseases) {
        tvMayTreat.setText(concatenateMayTreatDiseaseNames(diseases));
    }

    public String concatenateMayTreatDiseaseNames(ArrayList<String> diseaseNames) {
        String result = "May Treat:\n";
        if (diseaseNames != null && diseaseNames.size() > 0) {
            for (String str : diseaseNames) {
                result += str + "\n";
            }
        }
        return result;
    }

    /**
     * Asynchronous task for getting a list of disease names that a pill
     * might treat. Perhaps this task could be in its own file to make
     * the RxInfoActivity class cleaner
     * TODO: Refactor by abstracting this from the RxInfoActivity
     * TODO: Handle bad results...
     */
    public class getMayTreatsJSON extends AsyncTask<String, Integer, String> {
        String rawJson = "";

        @Override
        protected String doInBackground(String... params) {
            try {
                String request = "https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui="
                        + Integer.toString(rxcui) + "&relaSource=NDFRT&relas=may_treat";
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
                mayTreatDiseaseNames = jsonParse(result);
                populateMayTreat(mayTreatDiseaseNames);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Parses the result by hand. The JSON result object is a nasty nested thing.
         * We could make a POJO and use :
         * GsonBuilder gsonB = new GsonBuilder();
         * Gson gson = gsonB.create();
         * MyResultObject myResult = null;
         * try {
         * myResult = gson.fromJson(rawJson, MyResultObject.class);
         * but I thought it unneccessary to create a whole object for a list of strings.
         * To avoid creating a stupid nested POJO for that list
         * I parsed the JSON tree until I got to the stuff I wanted
         * This required a nested parse because the JSON result I got back had two objects,
         * the second object contained an array of objects
         * Each of those objects contained two objects
         * The second of those objects contained the field I wanted: className
         * Freaking brutal...
         *
         * @param rawJson
         * @return
         */
        private ArrayList<String> jsonParse(String rawJson) {

            ArrayList<String> mayTreatsFromJson = new ArrayList<>();

            try {
                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(rawJson);
                if (element.isJsonObject()) {
                    JsonObject wholeThing = element.getAsJsonObject();
                    JsonObject rxclassDrugInfoList = wholeThing.getAsJsonObject("rxclassDrugInfoList");
                    JsonArray rxclassDrugInfos = rxclassDrugInfoList.getAsJsonArray("rxclassDrugInfo");
//                    Log.d("test", "This list should have six and the count is " + Integer.toString(rxclassDrugInfos.size()));
                    for (JsonElement listElement : rxclassDrugInfos) {
                        JsonParser nestedParser = new JsonParser();
                        JsonElement nestedElement = nestedParser.parse(listElement.toString());
                        JsonObject unNamedListItem = nestedElement.getAsJsonObject();
                        JsonObject thingThatIactuallyWant = unNamedListItem.getAsJsonObject("rxclassMinConceptItem");
                        JsonElement elementIwant = thingThatIactuallyWant.get("className");
                        mayTreatsFromJson.add(elementIwant.getAsString());
                    }
                }
            } catch (Exception e) {
                Log.d(TAG, "jsonParse(): " + e.getMessage());
            }
            return mayTreatsFromJson;
        } // end parse

    } // end getMayTreats task
}
