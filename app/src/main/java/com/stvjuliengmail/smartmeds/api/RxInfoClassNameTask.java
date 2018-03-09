package com.stvjuliengmail.smartmeds.api;

import com.stvjuliengmail.smartmeds.activity.RxInfoActivity;

/**
 * Created by danm1 on 3/9/2018.
 * https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=7052&relaSource=ATC
 */

public class RxInfoClassNameTask extends RxInfoStringTask {
    public RxInfoClassNameTask(RxInfoActivity activity, String requestPath) {
        super(activity, requestPath);
    }

    @Override
    void jsonParse(String rawJson) {

    }

    @Override
    void setResultsInUI() {
        //This is where we call displayClassName

    }
}
