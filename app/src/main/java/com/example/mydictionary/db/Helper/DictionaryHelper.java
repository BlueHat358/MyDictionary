package com.example.mydictionary.db.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.mydictionary.db.DatabaseContract;
import com.example.mydictionary.db.DatabaseContract.*;
import com.example.mydictionary.db.Model;

import java.util.ArrayList;

public class DictionaryHelper {

    private static String ENGLISH = DatabaseContract.TABLE_ENGLISH;
    private static String INDONESIA = DatabaseContract.TABLE_INDONESIA;

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper databaseHelper;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public Cursor searchDictionary(String query, boolean IndOrEng) {
        String DATABASE_TABLE = IndOrEng ? ENGLISH : INDONESIA;
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + DictionaryColumns.WORD + " LIKE '%" + query.trim() + "%'", null);
    }

    public ArrayList<Model> getSearch(String search, boolean IndOrEng){
        Model model;
        ArrayList<Model> list = new ArrayList<>();
        Cursor cursor = searchDictionary(search, IndOrEng);

        cursor.moveToFirst();
        if(cursor.getCount()>0){
            do {
                model = new Model();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DictionaryColumns._ID)));
                model.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DictionaryColumns.WORD)));
                model.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(DictionaryColumns.TRANSLATE)));
                list.add(model);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public Cursor DataDictionary(boolean IndOrEng) {
        String DATABASE_TABLE = IndOrEng ? ENGLISH : INDONESIA;
        return database.rawQuery("SELECT * FROM " + DATABASE_TABLE + " ORDER BY " + DictionaryColumns._ID + " ASC", null);
    }

    public ArrayList<Model> getDataDictionary(boolean IndOrEng) {
        Model model;

        ArrayList<Model> list = new ArrayList<>();
        Cursor cursor = DataDictionary(IndOrEng);

        cursor.moveToFirst();
        if(cursor.getCount()>0){
            do {
                model = new Model();
                model.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DictionaryColumns._ID)));
                model.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DictionaryColumns.WORD)));
                model.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(DictionaryColumns.TRANSLATE)));
                list.add(model);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public long insert(Model model, boolean IndOrEng) {
        String DATABASE_TABLE = IndOrEng ? ENGLISH : INDONESIA;
        ContentValues initialValues = new ContentValues();
        initialValues.put(DictionaryColumns.WORD, model.getWord());
        initialValues.put(DictionaryColumns.TRANSLATE, model.getTranslate());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Model model, boolean IndOrEng){
        String DATABASE_TABLE = IndOrEng ? ENGLISH : INDONESIA;
        ContentValues args = new ContentValues();
        args.put(DictionaryColumns.WORD, model.getWord());
        args.put(DictionaryColumns.TRANSLATE, model.getTranslate());
        return database.update(DATABASE_TABLE, args, DictionaryColumns._ID + "= '" + model.getId() + "'", null);
    }

    public void delete(int id, boolean IndOrEng) {
        String DATABASE_TABLE = IndOrEng ? ENGLISH : INDONESIA;
        database.delete(DATABASE_TABLE, DictionaryColumns._ID + "= '" + id + "'", null);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(ArrayList<Model> model, boolean IndOrEng) {
        String DATABASE_TABLE = IndOrEng ? ENGLISH : INDONESIA;

        String sql = "INSERT INTO " + DATABASE_TABLE + " (" +
                DictionaryColumns.WORD + ", " +
                DictionaryColumns.TRANSLATE + ") VALUES (?, ?)";

        beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);
        for (int i = 0; i < model.size(); i++) {
            stmt.bindString(1, model.get(i).getWord());
            stmt.bindString(2, model.get(i).getTranslate());
            stmt.execute();
            stmt.clearBindings();
        }

        setTransactionSuccess();
        endTransaction();
    }
}
