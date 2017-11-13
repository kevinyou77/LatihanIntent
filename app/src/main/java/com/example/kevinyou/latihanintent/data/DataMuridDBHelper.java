package com.example.kevinyou.latihanintent.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.kevinyou.latihanintent.data.DataMuridContract.DataMuridEntry;

/**
 * Created by Kevin You on 11/12/2017.
 */

public class DataMuridDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "MainActivity";
    private static final String DATABASE_NAME = "DataSiswa.db";
    private static final int DATABASE_VERSION = 1;

    public DataMuridDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        String SQL_CREATE_TABLES = "CREATE TABLE " + DataMuridEntry.TABLE_NAME + "("
                                        + DataMuridEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                        + DataMuridEntry.COLUMN_NAME + " TEXT NOT NULL,"
                                        + DataMuridEntry.COLUMN_PHONE + " TEXT);";

        db.execSQL(SQL_CREATE_TABLES);
        Log.v(TAG, SQL_CREATE_TABLES);
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
