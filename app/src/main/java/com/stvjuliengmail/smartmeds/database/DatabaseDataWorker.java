package com.stvjuliengmail.smartmeds.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Steven on 3/1/2018.
 */

public class DatabaseDataWorker {
    private SQLiteDatabase mDb;

    public DatabaseDataWorker(SQLiteDatabase mDb) {
        this.mDb = mDb;
    }

    public void insertDummyPill() {
        insertPill("Levothyroxine Sodium 0.15 MG Oral Tablet [Levoxyl]", "966200", "150 mg", "Dr. FeelGood", "Take 2 and call me in the morning", "Walgreens");
    }

    private void insertPill(String name, String rxcui, String dosage, String doctor, String directions, String pharmacy) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_NAME, name);
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_RXCUI, rxcui);
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_DOSAGE, dosage);
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_DOCTOR, doctor);
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_DIRECTIONS, directions);
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_PHARMACY, pharmacy);
        long newRowId = mDb.insert(SmartMedsDbContract.MyMedEntry.TABLE_NAME, null, contentValues);
    }

}
