package com.stvjuliengmail.smartmeds.api;

/**
 * Created by Steven on 2/18/2018.
 */

public class REQUEST_BASE {
    public static final String IMAGE = "https://rximage.nlm.nih.gov/api/rximage/1/rxnav?&resolution=600";
    public static final String CLASS_BY_RXCUI = "https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=";
    public static final String INTERACTIONS_BY_RXCUI = "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui=";
}
