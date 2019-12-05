package com.example.mydictionary.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract(){}

    public static final String TABLE_ENGLISH = "TABLE_ENGLISH";
    public static final String TABLE_INDONESIA = "TABLE_INDONESIA";

    public static final class DictionaryColumns implements BaseColumns{
        public static final String WORD = "word";
        public static final String TRANSLATE = "translate";
    }
}
