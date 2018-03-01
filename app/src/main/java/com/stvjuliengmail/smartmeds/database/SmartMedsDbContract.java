package com.stvjuliengmail.smartmeds.database;

import android.provider.BaseColumns;

/**
 * Created by Steven on 3/1/2018.
 */

public final class SmartMedsDbContract {
    private SmartMedsDbContract() {}

    public static final class MyMedEntry implements BaseColumns{
        public static final String TABLE_NAME = "my_med";
        // _ID is from base class
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_RXCUI = "rxcui";
        public static final String COLUMN_DOSAGE = "dosage";
        public static final String COLUMN_DOCTOR = "doctor";
        public static final String COLUMN_DIRECTIONS = "directions";
        public static final String COLUMN_PHARMACY = "pharmacy";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_RXCUI + " TEXT UNIQUE NOT NULL, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_DOSAGE + " TEXT, " +
                        COLUMN_DOCTOR + " TEXT, " +
                        COLUMN_DIRECTIONS + " TEXT, " +
                        COLUMN_PHARMACY + " TEXT, " +
                        COLUMN_IMAGE_URL + " TEXT)";
    }
}

