package com.nanodegree.udacy.backingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 26/09/2017.
 */

public class Recipe implements Parcelable{
    private String TAG = getClass().getName();

    private int id;
    private String name;
    private int servings;
    private String image;

    public Recipe() {
    }

    public Recipe(int id, String name, int servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }

    public Recipe(int id, String name, int servings) {
        this.id = id;
        this.name = name;
        this.servings = servings;
    }

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected Recipe(Parcel in) {
        TAG = in.readString();
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TAG);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
