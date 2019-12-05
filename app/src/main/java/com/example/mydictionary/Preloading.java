package com.example.mydictionary;

import android.content.Intent;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.mydictionary.db.Helper.DictionaryHelper;
import com.example.mydictionary.db.Model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Preloading extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloading);

        progressBar = findViewById(R.id.progressBar);
        new loadData().execute();
    }

    private class loadData extends AsyncTask<Void, Integer, Void>{
        DictionaryHelper dictionaryHelper;
        SharedPreferedDictionary prefs;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(Preloading.this);
            prefs = new SharedPreferedDictionary(Preloading.this);
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = prefs.getFirstRun();

            if (firstRun){
                ArrayList<Model> English = preLoadRaw(R.raw.indonesia_english);
                ArrayList<Model> Indonesia = preLoadRaw(R.raw.english_indonesia);

                publishProgress((int) progress);
                try {
                    dictionaryHelper.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Double progressMaxInsert = 100.0;
                Double progressDiff = (progressMaxInsert - progress) / (English.size() + Indonesia.size());

                dictionaryHelper.insertTransaction(English, true);
                progress += progressDiff;
                publishProgress((int)progress);

                dictionaryHelper.insertTransaction(Indonesia, false);
                progress += progressDiff;
                publishProgress((int)progress);

                dictionaryHelper.close();
                prefs.setFirstRun(false);
                publishProgress((int)maxprogress);
            }else{
                try {
                    synchronized (this) {
                        this.wait(1000);

                        publishProgress(50);

                        this.wait(300);
                        publishProgress((int) maxprogress);
                    }
                } catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(Preloading.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<Model> preLoadRaw(int data) {
        ArrayList<Model> list = new ArrayList<>();
        BufferedReader reader;

        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            String line = null;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");
                Model model;
                model = new Model(splitstr[0], splitstr[1]);
                list.add(model);
            } while (line != null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
