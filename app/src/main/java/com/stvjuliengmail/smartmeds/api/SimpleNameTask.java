package com.stvjuliengmail.smartmeds.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;
import com.stvjuliengmail.smartmeds.model.SimplePillNameResult;

/**
 * Created by Steven on 4/8/2018.
 */

public class SimpleNameTask extends RxInfoStringTask{
    public SimpleNameTask(RxInfoActivity activity, String requestPath) {
        super(activity, requestPath);
    }

    @Override
    void jsonParse(String rawJson) {
        try {
            GsonBuilder gsonB = new GsonBuilder();
            Gson gson = gsonB.create();
            try {
                SimplePillNameResult result = gson.fromJson(rawJson, SimplePillNameResult.class);
                // resultString is a string from the base class.
                // This is what gets sent back to the calling activity
                resultString  = result.getDisplayGroup().getDisplayName();
            } catch (Exception e) {
                resultString = "";
            }
        } catch (Exception e) {
            Log.d(TAG, "jsonParse(): " + e.getMessage());
        }
    }

    @Override
    void setResultsInUI() {
        weakReference.get().populateSimplePillName(resultString);
    }

}
