/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nanodegree.udacy.backingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by pc on 25/09/2017.
 */

public class RecipeHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private final String LOG_TAG = RecipeHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "backingApp.db";

    public RecipeHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_RECIPE_TABLE = "CREATE TABLE IF NOT EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME + " (" +
                // the ID of the recipe entry as returned by the API
                RecipeContract.RecipeEntry._ID + " INTEGER," +
                RecipeContract.RecipeEntry.COLUMN_NAME+ " TEXT , " +
                RecipeContract.RecipeEntry.COLUMN_SERVINGS + " INTEGER  DEFAULT 8, " +
                RecipeContract.RecipeEntry.COLUMN_IMAGE + " TEXT, " +
                " UNIQUE (" + RecipeContract.RecipeEntry._ID + "));";


        final String SQL_CREATE_INGREDIENT_TABLE = "CREATE TABLE IF NOT EXISTS  " + RecipeContract.IngredientEntry.TABLE_NAME + " (" +

                RecipeContract.IngredientEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipeContract.IngredientEntry.COLUMN_QUANTITY + " DOUBLE , " +
                RecipeContract.IngredientEntry.COLUMN_MEASURE + " TEXT , " +
                RecipeContract.IngredientEntry.COLUMN_INGREDIENT + " TEXT , " +
                RecipeContract.IngredientEntry.COLUMN_UNIQUE_FIELD + " TEXT NOT NULL, " +
                RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + " INTEGER, " +

                // Set up the "RecipeStepEntry.COLUMN_RECIPE_ID" column
                // as a foreign key to recipes table.
                " FOREIGN KEY (" + RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + ") REFERENCES " +
                RecipeContract.RecipeEntry.TABLE_NAME + " (" + RecipeContract.RecipeEntry._ID + "), "

                // To ensure the application doesn't duplicate recipes,
                // we create a UNIQUE constraint
                + " UNIQUE (" + RecipeContract.IngredientEntry.COLUMN_UNIQUE_FIELD + ") );";


        final String SQL_CREATE_STEP_TABLE = "CREATE TABLE IF NOT EXISTS  " + RecipeContract.StepEntry.TABLE_NAME + " (" +
                // the ID of the recipe entry as returned by the API
                RecipeContract.StepEntry._ID + " INTEGER," +

                RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION + " TEXT, " +
                RecipeContract.StepEntry.COLUMN_DESCRIPTION + " TEXT, " +
                RecipeContract.StepEntry.COLUMN_VIDEO_URL + " TEXT, " +
                RecipeContract.StepEntry.COLUMN_THUMBNAIL_URL + " TEXT, " +

                RecipeContract.StepEntry.COLUMN_UNIQUE_FIELD + " TEXT NOT NULL, " +
                RecipeContract.StepEntry.COLUMN_RECIPE_ID + " INTEGER, " +

                // Set up the "IngredientEntry.COLUMN_RECIPE_ID" column
                // as a foreign key to recipes table.
                " FOREIGN KEY (" + RecipeContract.StepEntry.COLUMN_RECIPE_ID + ") REFERENCES " +
                RecipeContract.RecipeEntry.TABLE_NAME + " (" + RecipeContract.RecipeEntry._ID + "), " +

                // To ensure the application doesn't duplicate recipes,
                // we create a UNIQUE constraint
                " UNIQUE (" + RecipeContract.StepEntry.COLUMN_UNIQUE_FIELD + ") );";

        sqLiteDatabase.execSQL(SQL_CREATE_RECIPE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_INGREDIENT_TABLE);
       sqLiteDatabase.execSQL(SQL_CREATE_STEP_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  RecipeContract.RecipeEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  RecipeContract.IngredientEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  RecipeContract.StepEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
