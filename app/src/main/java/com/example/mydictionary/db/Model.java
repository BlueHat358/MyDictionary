package com.example.mydictionary.db;

import android.os.Parcel;
import android.os.Parcelable;

public class Model implements Parcelable {
    private String word, translate;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.word);
        dest.writeString(this.translate);
        dest.writeInt(this.id);
    }

    public Model() {

    }

    public Model(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    protected Model(Parcel in) {
        this.word = in.readString();
        this.translate = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Model> CREATOR = new Parcelable.Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel source) {
            return new Model(source);
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[size];
        }
    };
}
