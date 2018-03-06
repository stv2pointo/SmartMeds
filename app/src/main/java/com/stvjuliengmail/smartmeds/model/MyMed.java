package com.stvjuliengmail.smartmeds.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Steven on 3/1/2018.
 */

public class MyMed implements Parcelable{
    private String name;
    private String rxcui;
    private String dosage;
    private String doctor;
    private String directions;
    private String pharmacy;
    private String imageUrl;

    public MyMed(String name, String rxcui, String dosage, String doctor, String directions, String pharmacy, String imageUrl) {
        this.name = name;
        this.rxcui = rxcui;
        this.dosage = dosage;
        this.doctor = doctor;
        this.directions = directions;
        this.pharmacy = pharmacy;
        this.imageUrl = imageUrl;
    }

    protected MyMed(Parcel in) {
        name = in.readString();
        rxcui = in.readString();
        dosage = in.readString();
        doctor = in.readString();
        directions = in.readString();
        pharmacy = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<MyMed> CREATOR = new Creator<MyMed>() {
        @Override
        public MyMed createFromParcel(Parcel in) {
            return new MyMed(in);
        }

        @Override
        public MyMed[] newArray(int size) {
            return new MyMed[size];
        }
    };

    public String getRxcui() {
        return rxcui;
    }

    public void setRxcui(String rxcui) {
        this.rxcui = rxcui;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(rxcui);
        dest.writeString(dosage);
        dest.writeString(doctor);
        dest.writeString(directions);
        dest.writeString(pharmacy);
        dest.writeString(imageUrl);
    }
}
