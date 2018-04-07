package com.stvjuliengmail.smartmeds.api;

/**
 * Created by Steven on 2/18/2018.
 */

public class REQUEST_BASE {
    public static final String IMAGE = "https://rximage.nlm.nih.gov/api/rximage/1/rxnav?&resolution=600";
    public static final String CLASS_BY_RXCUI = "https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=";
    public static final String INTERACTIONS_BY_RXCUI = "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui=";
    public static final String INTERACTIONS_FOR_LIST = "https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis=";
    public static final String RXCLASS_BY_CLASSID="https://rxnav.nlm.nih.gov/REST/rxclass/classTree.json?classId=N0000185505";
    public static final String CLASSNAME_BY_RXUI = "https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=";
    public static final String NAME_SUGGESTION =  "https://rxnav.nlm.nih.gov/REST/spellingsuggestions.json?name=ibefprofen";
}
