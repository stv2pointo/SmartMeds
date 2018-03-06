package com.stvjuliengmail.smartmeds.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SmartMeds.db";
    public static final String TABLE_NAME = "Mymeds";
    public static final String COLUMN_RXid = "RXid";
    public static final String COLUMN_Name = "rxname";
    public static final String COLUMN_DOSAGE = "dosage";
    public static final String COLUMN_RXDOC = "rxDoc";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table Mymeds " +
                        "(id integer primary key, RXid, rxname, dosage, rxDoc)"
        );

//        ContentValues contentValues = new ContentValues();
//        contentValues.put("RXid", 1234);
//        contentValues.put("dosage", "take three times a day");
//        contentValues.put("rxDoc", "dr smith");
//        db.insert("Mymeds", null, contentValues);
//
//        contentValues = new ContentValues();
//        contentValues.put("RXid", 5678);
//        contentValues.put("dosage", "take 4 times a day");
//        contentValues.put("rxDoc", "dr jones");
//        db.insert("Mymeds", null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Mymeds");
        onCreate(db);
    }


    public boolean insertRX (Integer RXid, String rxName, String dosage, String rxDoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RXid", RXid);
        contentValues.put("rxName", rxName);
        contentValues.put("dosage", dosage);
        contentValues.put("rxDoc", rxDoc);
        db.insert("Mymeds", null, contentValues);
        return true;
    }


    public boolean insertRX (Integer RXid, String rxName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RXid", RXid);
        contentValues.put("rxName", rxName);
        db.insert("Mymeds", null, contentValues);
        return true;
    }


    public Cursor getData(Integer rxid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Mymeds where id="+rxid+"", null );
        return res;
    }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }


    public boolean updateMed (Integer id, String RXid, String rxName, String dosage, String rxDoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RXid", RXid);
        contentValues.put("rxName", rxName);
        contentValues.put("dosage", dosage);
        contentValues.put("rxDoc", rxDoc);
        db.update("Mymeds", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }


    public boolean updateMed (String RXid, String rxName, String dosage, String rxDoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("RXid", RXid);
        contentValues.put("rxName", rxName);
        contentValues.put("dosage", dosage);
        contentValues.put("rxDoc", rxDoc);

        db.update(TABLE_NAME, contentValues, "RXid="+RXid,null);

        return true;
    }


    public Integer deleteMed (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Mymeds",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }


    public ArrayList<String> getAllMeds() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Mymeds", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
//            array_list.add(res.getString(res.getColumnIndex(COLUMN_RXid)));
            array_list.add(res.getString(res.getColumnIndex(COLUMN_Name)));
            res.moveToNext();
        }
        return array_list;
    }
}