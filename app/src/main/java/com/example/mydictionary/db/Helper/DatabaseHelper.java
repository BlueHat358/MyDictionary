package com.example.mydictionary.db.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mydictionary.db.DatabaseContract;
import com.example.mydictionary.db.DatabaseContract.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dictionary.db";
    public static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ENGLISH = "create table " + DatabaseContract.TABLE_ENGLISH + " (" +
            DictionaryColumns._ID + " integer primary key autoincrement, " +
            DictionaryColumns.WORD + " text not null, " +
            DictionaryColumns.TRANSLATE + " text not null);";

    public static String CREATE_TABLE_INDONESIA = "create table " + DatabaseContract.TABLE_INDONESIA + " (" +
            DictionaryColumns._ID + " integer primary key autoincrement, " +
            DictionaryColumns.WORD + " text not null, " +
            DictionaryColumns.TRANSLATE + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISH);
        db.execSQL(CREATE_TABLE_INDONESIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_ENGLISH);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_INDONESIA);
        onCreate(db);
    }
}
