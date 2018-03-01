package com.stvjuliengmail.smartmeds.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Steven on 3/1/2018.
 */

public class SmartMedsDbOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SmartMedsDb.db";
    public static final int DATABASE_VERSION = 1;

    public SmartMedsDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SmartMedsDbContract.MyMedEntry.SQL_CREATE_TABLE);

        DatabaseDataWorker worker = new DatabaseDataWorker(db);
        worker.insertDummyPill();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
