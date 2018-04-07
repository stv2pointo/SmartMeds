package com.stvjuliengmail.smartmeds.api;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;

import java.util.ArrayList;

/**
 * Created by Steven on 2/21/2018.
 */

public class RxInfoMayTreatsTask extends RxInfoStringTask {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<String> mayTreatsFromJson;
    public RxInfoMayTreatsTask(RxInfoActivity activity, String requestPath) {
        super(activity, requestPath);
    }

    @Override
    public void jsonParse(String rawJson){
        mayTreatsFromJson = new ArrayList<>();

        try {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(rawJson);
            if (element.isJsonObject()) {
                JsonObject wholeThing = element.getAsJsonObject();
                JsonObject rxclassDrugInfoList = wholeThing.getAsJsonObject("rxclassDrugInfoList");
                JsonArray rxclassDrugInfos = rxclassDrugInfoList.getAsJsonArray("rxclassDrugInfo");
                for (JsonElement listElement : rxclassDrugInfos) {
                    JsonParser nestedParser = new JsonParser();
                    JsonElement nestedElement = nestedParser.parse(listElement.toString());
                    JsonObject unNamedListItem = nestedElement.getAsJsonObject();
                    JsonObject diseaseClass = unNamedListItem.getAsJsonObject("rxclassMinConceptItem");
                    JsonElement diseaseName = diseaseClass.get("className");
                    mayTreatsFromJson.add(diseaseName.getAsString());
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "jsonParse(): " + e.getMessage());
        }
        convertArrayListToStringThatLooksLikeVerticalList();
    }

    public void convertArrayListToStringThatLooksLikeVerticalList(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String diseaseName : mayTreatsFromJson)
        {
            stringBuilder.append(diseaseName);
            stringBuilder.append("\n");
        }
        resultString = stringBuilder.toString();
    }

    @Override
    void setResultsInUI() {
        weakReference.get().populateMayTreat(resultString);
    }
}
