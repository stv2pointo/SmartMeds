package com.stvjuliengmail.smartmeds.model;

/**
 * Created by Steven on 3/7/2018.
 */

public class MyInteraction {
    private String firstDrug, secondDrug, description, url;

    public MyInteraction(String firstDrug, String secondDrug, String description, String url) {
        this.firstDrug = firstDrug;
        this.secondDrug = secondDrug;
        this.description = description;
        this.url = url;
    }

    public String getFirstDrug() {
        return firstDrug;
    }

    public void setFirstDrug(String firstDrug) {
        this.firstDrug = firstDrug;
    }

    public String getSecondDrug() {
        return secondDrug;
    }

    public void setSecondDrug(String secondDrug) {
        this.secondDrug = secondDrug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
