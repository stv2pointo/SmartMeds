package com.stvjuliengmail.smartmeds.database;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;

    public static final String DATABASE_NAME = "SmartMeds2.db";
    public static final String TABLE_MYMEDS = "Mymeds";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RXCUI = "rxcui";
    public static final String COLUMN_DOSAGE = "dosage";
    public static final String COLUMN_DOCTOR = "doctor";
    public static final String COLUMN_DIRECTIONS = "directions";
    public static final String COLUMN_PHARMACY = "pharmacy";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public SQLiteDatabase open(){
        database = getWritableDatabase();
        return database;
    }

    public void close(){
        if(database != null){
            database.close();
        }
    }

    public long insertPill(String rxcui, String name, String dosage, String doctor, String directions, String pharmacy) {
        long rowId = -1;
        // TODO: CHECK TO MAKE SURE THIS RXCUI DOESN'T ALREADY EXIST
        ContentValues newPillValuesToAdd = buildContentValues(rxcui,name,dosage,doctor,directions,pharmacy);
        if(database != null){
            rowId = database.insert(TABLE_MYMEDS, null, newPillValuesToAdd);
            close();
        }
        return rowId;
    }

    public long updatePill(long id, String rxcui, String name, String dosage, String doctor, String directions, String pharmacy) {
        long rowId = -1;
        ContentValues pillValuesToUpdate = buildContentValues(rxcui, name, dosage, doctor, directions, pharmacy);
        pillValuesToUpdate.put(COLUMN_ID, id);
        if(open() != null){
            rowId = database.update(TABLE_MYMEDS, pillValuesToUpdate, COLUMN_ID + "=" + id, null);
            close();
        }
        return rowId;
    }

    public void deletePill(long id){
        if(open() != null){
            database.delete(TABLE_MYMEDS," "+COLUMN_ID + "=" + id, null);
            Log.d("test", "pill at index " + id + " deleted");
            close();
        }
    }

    public void deleteAllPills(){
        Log.d("test", "database helper delete ALL called");
        if(open() != null){
            database.delete(TABLE_MYMEDS," "+COLUMN_ID+">0 ", null);
            close();
        }

    }

    public Cursor getAllPillsCursor(){
        Cursor cursor = null;
        if(open() != null){
            cursor = database.rawQuery("SELECT * FROM " + TABLE_MYMEDS, null);
        }
        return cursor;
    }

    public Cursor getOnePillCursor(long id){
        String[] params = new String[1];
        params[0] = "" + id;
        Cursor cursor = null;
        if (open() != null) {
            cursor = database.rawQuery("SELECT * FROM " + TABLE_MYMEDS + " WHERE " + COLUMN_ID + " = ?", params);
        }
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

Log.d("test", "oncreate was called");
        String createQuery =
                "create table " + TABLE_MYMEDS +
                " (" + COLUMN_ID + " integer primary key autoincrement, " +
                        COLUMN_RXCUI + " TEXT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DOSAGE + " TEXT , " +
                        COLUMN_DOCTOR + " TEXT, " +
                        COLUMN_DIRECTIONS + " TEXT, " +
                        COLUMN_PHARMACY + " TEXT)";
        db.execSQL(createQuery);
        // TODO: REMOVE AFTER TESTING IS COMPLETE

            seedDatabaseWithDummyData();

    }
//    public void temporaryFunctionToDropDatabaseTable(){
//        database.execSQL("DROP TABLE IF EXISTS " + TABLE_MYMEDS);
//        String createQuery =
//                "create table " + TABLE_MYMEDS +
//                        " (" + COLUMN_ID + " integer primary key autoincrement, " +
//                        COLUMN_RXCUI + " TEXT, " +
//                        COLUMN_NAME + " TEXT, " +
//                        COLUMN_DOSAGE + " TEXT , " +
//                        COLUMN_DOCTOR + " TEXT, " +
//                        COLUMN_DIRECTIONS + " TEXT, " +
//                        COLUMN_PHARMACY + " TEXT)";
//        database.execSQL(createQuery);
//        // TODO: REMOVE AFTER TESTING IS COMPLETE
//        if(numberOfRows() < 1) {
//            seedDatabaseWithDummyData();
//        }
//    }




    private ContentValues buildContentValues(String rxcui, String name, String dosage, String doctor, String directions, String pharmacy) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RXCUI, rxcui);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_DOSAGE, dosage);
        contentValues.put(COLUMN_DOCTOR, doctor);
        contentValues.put(COLUMN_DIRECTIONS, directions);
        contentValues.put(COLUMN_PHARMACY, pharmacy);
        return contentValues;
    }

    public void seedDatabaseWithDummyData(){
        Long rowId = insertPill("33333", "Somethinginol","150 mg", "Dr Smith", "Take 2 and call me in the morning", "ACME Pharmacy");
        Log.d("test", "dummy data added at row " + Long.toString(rowId));
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYMEDS);
        onCreate(db);
    }

    public int numberOfRows(){
        int numRows = (int) DatabaseUtils.queryNumEntries(database, TABLE_MYMEDS);
        return numRows;
    }

    public ArrayList<String> getAllMeds() {
        ArrayList<String> array_list = new ArrayList<String>();

        Cursor cursor =  getAllPillsCursor();
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
//            String testSTring = "blah";
//            ifcursor.getString(1);
//            Log.d("test", testSTring);
            array_list.add(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            cursor.moveToNext();
        }
        return array_list;
    }
}