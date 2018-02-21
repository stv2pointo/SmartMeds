package com.stvjuliengmail.smartmeds.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Steven on 2/21/2018.
 */

public class RxInfoMayTreatsTask extends RxInfoStringTask {
    private final String TAG = getClass().getSimpleName();

    public RxInfoMayTreatsTask(RxInfoActivity activity, String requestPath) {
        super(activity, requestPath);
    }

    @Override
    public void jsonParse(String rawJson){
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
        StringBuilder sb = new StringBuilder();
        for (String s : mayTreatsFromJson)
        {
            sb.append(s);
            sb.append("\n");
        }

        resultString = sb.toString();
    }

    @Override
    void setResultsInUI() {
        weakReference.get().populateMayTreat(resultString);
    }
}
