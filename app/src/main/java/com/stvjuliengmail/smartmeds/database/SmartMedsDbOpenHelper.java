package com.stvjuliengmail.smartmeds.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.stvjuliengmail.smartmeds.model.MyMed;

import java.util.ArrayList;

/**
 * Created by Steven on 3/1/2018.
 */

public class SmartMedsDbOpenHelper extends SQLiteOpenHelper {
    private final String TAG = getClass().getSimpleName();
    // Database Info
    public static final String DATABASE_NAME = "meds.db";
    public static final int DATABASE_VERSION = 1;

    private static SmartMedsDbOpenHelper sInstance;

    public static synchronized SmartMedsDbOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new SmartMedsDbOpenHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private SmartMedsDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
//    @Override
//    public void onConfigure(SQLiteDatabase db) {
//        super.onConfigure(db);
//        db.setForeignKeyConstraintsEnabled(true);
//    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SmartMedsDbContract.MyMedEntry.SQL_CREATE_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + SmartMedsDbContract.MyMedEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    // Insert or update a myMed in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // item already exists) optionally followed by an INSERT (in case the item does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the item's primary key if we did an update.
    public long addOrUpdateMyMed(MyMed myMed) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long rowId = -1;

        db.beginTransaction();
        try {
            ContentValues values = buildMyMedContentValues(myMed);
            String[] params = new String[]{myMed.getRxcui()};
            // First try to update the myMed in case the myMed already exists in the database
            // Assumes Unique rxcui
            int rows = db.update(SmartMedsDbContract.MyMedEntry.TABLE_NAME, values,
                    SmartMedsDbContract.MyMedEntry.COLUMN_RXCUI + "= ?", params);

            // Check if update succeeded
            if (rows == 1) {

                // Get the myMed we just updated
                Cursor cursor = db.rawQuery(SmartMedsDbContract.MyMedEntry.SQL_MED_WHERE_RXCUI, params);
                try {
                    if (cursor.moveToFirst()) {
                        rowId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // myMed with this rxcui did not already exist, so insert new myMed
                rowId = db.insertOrThrow(SmartMedsDbContract.MyMedEntry.TABLE_NAME, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update myMed");
        } finally {
            db.endTransaction();
        }
        return rowId;
    }

//    // Insert a med into the database
//    public void addMyMed(MyMed myMed) {
//        // Create and/or open the database for writing
//        SQLiteDatabase db = getWritableDatabase();
//
//        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
//        // consistency of the database.
//        db.beginTransaction();
//        try {
//            ContentValues medValuesToAdd = buildContentValues(myMed);
//
//            db.insertOrThrow(SmartMedsDbContract.MyMedEntry.TABLE_NAME, null, medValuesToAdd);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to add myMed to database");
//        } finally {
//            db.endTransaction();
//        }
//    }

    // Get all myMeds from the database
    public ArrayList<MyMed> getAllMyMeds() {
        ArrayList<MyMed> myMeds = new ArrayList<>();

        // SELECT * FROM myMeds table
        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SmartMedsDbContract.MyMedEntry.SQL_SELECT_ALL_MEDS, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    myMeds.add( new MyMed(
                            cursor.getString(cursor.getColumnIndex(SmartMedsDbContract.MyMedEntry.COLUMN_NAME)),
                            cursor.getString(cursor.getColumnIndex(SmartMedsDbContract.MyMedEntry.COLUMN_RXCUI)),
                            cursor.getString(cursor.getColumnIndex(SmartMedsDbContract.MyMedEntry.COLUMN_DOSAGE)),
                            cursor.getString(cursor.getColumnIndex(SmartMedsDbContract.MyMedEntry.COLUMN_DOCTOR)),
                            cursor.getString(cursor.getColumnIndex(SmartMedsDbContract.MyMedEntry.COLUMN_DIRECTIONS)),
                            cursor.getString(cursor.getColumnIndex(SmartMedsDbContract.MyMedEntry.COLUMN_PHARMACY)),
                            cursor.getString(cursor.getColumnIndex(SmartMedsDbContract.MyMedEntry.COLUMN_IMAGE_URL))
                    ));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get meds from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return myMeds;
    }

    // Update
    public int updateMyMed(int id,MyMed myMed) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] params = new String[1];
        params[0] = Integer.toString(id);
        ContentValues values = buildMyMedContentValues(myMed);
        values.put(SmartMedsDbContract.MyMedEntry._ID, id);

        return db.update(SmartMedsDbContract.MyMedEntry.TABLE_NAME, values, SmartMedsDbContract.MyMedEntry._ID + " = ?", params);
    }

    // Delete one myMed
    public void deleteMyMed(MyMed myMed) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            String[] params = new String[]{myMed.getRxcui()};
            // Order of deletions is important when foreign key relationships exist.
            db.delete(SmartMedsDbContract.MyMedEntry.TABLE_NAME, SmartMedsDbContract.MyMedEntry.COLUMN_RXCUI + " = ?", params);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all myMeds");
        } finally {
            db.endTransaction();
        }
    }

    // Delete all
    public void deleteAllMyMeds() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(SmartMedsDbContract.MyMedEntry.TABLE_NAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all myMeds");
        } finally {
            db.endTransaction();
        }
    }


    private ContentValues buildMyMedContentValues(MyMed myMed) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_RXCUI, myMed.getRxcui());
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_NAME, myMed.getName());
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_DOSAGE, myMed.getDosage());
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_DOCTOR, myMed.getDoctor());
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_DIRECTIONS, myMed.getDirections());
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_PHARMACY, myMed.getPharmacy());
        contentValues.put(SmartMedsDbContract.MyMedEntry.COLUMN_IMAGE_URL, myMed.getImageUrl());
        return contentValues;
    }
}
