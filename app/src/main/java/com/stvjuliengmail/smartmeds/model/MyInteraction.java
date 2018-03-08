package com.stvjuliengmail.smartmeds.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steven on 3/7/2018.
 */

public class MyInteraction implements Parcelable{
    private String firstDrug, secondDrug, description, url;

    public MyInteraction(String firstDrug, String secondDrug, String description, String url) {
        this.firstDrug = firstDrug;
        this.secondDrug = secondDrug;
        this.description = description;
        this.url = url;
    }

    protected MyInteraction(Parcel in) {
        firstDrug = in.readString();
        secondDrug = in.readString();
        description = in.readString();
        url = in.readString();
    }

    public static final Creator<MyInteraction> CREATOR = new Creator<MyInteraction>() {
        @Override
        public MyInteraction createFromParcel(Parcel in) {
            return new MyInteraction(in);
        }

        @Override
        public MyInteraction[] newArray(int size) {
            return new MyInteraction[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstDrug);
        dest.writeString(secondDrug);
        dest.writeString(description);
        dest.writeString(url);
    }
}
