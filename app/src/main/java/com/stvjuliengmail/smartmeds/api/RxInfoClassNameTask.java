package com.stvjuliengmail.smartmeds.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;
import com.stvjuliengmail.smartmeds.model.ClassNameResult;
import com.stvjuliengmail.smartmeds.model.MyListInteractionResult;

/**
 * Created by danm1 on 3/9/2018.
 * https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=7052&relaSource=ATC
 */

public class RxInfoClassNameTask extends RxInfoStringTask {

    private ClassNameResult classNameResult;

    public RxInfoClassNameTask(RxInfoActivity activity, String requestPath) {
        super(activity, requestPath);

    }

    @Override
    void jsonParse(String rawJson) {
        GsonBuilder gsonB = new GsonBuilder();
        Gson gson = gsonB.create();

        try {
            classNameResult = gson.fromJson(rawJson, ClassNameResult.class);
            resultString  = classNameResult.getRxclassDrugInfoList().getRxclassDrugInfo()[0].getRxclassMinConceptItem().getClassName();
        } catch (Exception e) {
            resultString = "Class name not found";
        }


    }


    @Override
    void setResultsInUI() {
        weakReference.get().displayClassName(resultString);
    }
}