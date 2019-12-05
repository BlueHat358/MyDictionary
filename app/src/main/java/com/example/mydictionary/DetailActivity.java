package com.example.mydictionary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    public static final String ITEM_WORD = "item_word";
    public static final String ITEM_TRANSLATE = "item_translate";

    TextView tvWord, tvTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvWord = findViewById(R.id.tv_word_detail);
        tvTranslate = findViewById(R.id.tv_translate_detail);

        String WORD = getIntent().getStringExtra(ITEM_WORD);
        String TRANSLATE = getIntent().getStringExtra(ITEM_TRANSLATE);

        tvWord.setText(WORD);
        tvTranslate.setText(TRANSLATE);
    }
}
