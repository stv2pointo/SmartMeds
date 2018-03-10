package com.stvjuliengmail.smartmeds.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;
import com.stvjuliengmail.smartmeds.model.ClassNameResult;

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
            //ClassNameResult is the POJO class that is the result of the JSON object
            // that holds what we need
            classNameResult = gson.fromJson(rawJson, ClassNameResult.class);
            // resultString is a string from the base class.
            // This is what gets sent back to the calling activity
            resultString  = classNameResult.getRxclassDrugInfoList()
                    .getRxclassDrugInfo()[0]
                        .getRxclassMinConceptItem().getClassName();
        } catch (Exception e) {
            resultString = "Class name not found";
        }
    }

    // weakReference.get() gives us a reference to the calling activity
    // displayClassName() is the method in that class that sets the
    // text in the UI element of choice
    @Override
    void setResultsInUI() {
        weakReference.get().displayClassName(resultString);
    }
}