package com.stvjuliengmail.smartmeds.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.stvjuliengmail.smartmeds.database.SmartMedsDbContract.MyMedEntry;
import com.stvjuliengmail.smartmeds.database.SmartMedsDbOpenHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 3/1/2018.
 */

public class DataManager {
    private static DataManager ourInstance = null;

    private List<MyMed> myMeds = new ArrayList<>();

    public static DataManager getInstance(){
        if(ourInstance == null) {
            ourInstance = new DataManager();
        }
        return ourInstance;
    }

    public static void loadFromDatabase(SmartMedsDbOpenHelper dbOpenHelper) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        final String[] myMedColumns = {
                MyMedEntry.COLUMN_NAME,
                MyMedEntry.COLUMN_RXCUI,
                MyMedEntry.COLUMN_DOSAGE,
                MyMedEntry.COLUMN_DOCTOR,
                MyMedEntry.COLUMN_DIRECTIONS,
                MyMedEntry.COLUMN_PHARMACY,
                MyMedEntry.COLUMN_IMAGE_URL
        };
        final Cursor myMedsCursor = db.query(MyMedEntry.TABLE_NAME, myMedColumns,
                null, null, null, null, null);
    }



}
