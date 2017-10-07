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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import static com.nanodegree.udacy.backingapp.utils.Constant.INGREDIENT_COLUMNS;
import static com.nanodegree.udacy.backingapp.utils.Constant.RECIPE_COLUMNS;

/**
 * Created by pc on 07/02/2017.
 */

public class RecipeProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private RecipeHelper mOpenHelper;
    private final String LOG_TAG = RecipeProvider.class.getSimpleName();

    static final int RECIPES = 100;
    static final int RECIPE_WITH_ID = 101;
    static final int INGREDIENTS = 200;
    static final int INGREDIENT_WITH_ID = 201;
    static final int STEPS = 300;
    static final int STEP_WITH_ID = 301;


    // the selections variables
    private static final String sRecipeIDSelection =
            RecipeContract.RecipeEntry.TABLE_NAME +
                    "." + RecipeContract.RecipeEntry.COLUMN_RECIPE_ID + " = ? ";

    private static final String sIngredientIDSelection =
            RecipeContract.IngredientEntry.TABLE_NAME +
                    "." + RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + " = ? ";

    private static final String sStepIDSelection =
            RecipeContract.StepEntry.TABLE_NAME +
                    "." + RecipeContract.StepEntry.COLUMN_STEP_ID + " = ? ";


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RecipeContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, RecipeContract.PATH_RECIPE, RECIPES);
        matcher.addURI(authority, RecipeContract.PATH_INGREDIENT, INGREDIENTS);
        matcher.addURI(authority, RecipeContract.PATH_STEP, STEPS);

        matcher.addURI(authority, RecipeContract.PATH_RECIPE + "/#", RECIPE_WITH_ID);
        matcher.addURI(authority, RecipeContract.PATH_INGREDIENT + "/#", INGREDIENTS);
        matcher.addURI(authority, RecipeContract.PATH_STEP + "/#", STEP_WITH_ID);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new RecipeHelper(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {

        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case RECIPES:
                return RecipeContract.RecipeEntry.CONTENT_TYPE;
            case RECIPE_WITH_ID:
                return RecipeContract.RecipeEntry.CONTENT_TYPE;
            case INGREDIENTS:
                return RecipeContract.IngredientEntry.CONTENT_TYPE;
            case INGREDIENT_WITH_ID:
                return RecipeContract.IngredientEntry.CONTENT_TYPE;
            case STEPS:
                return RecipeContract.StepEntry.CONTENT_TYPE;
            case STEP_WITH_ID:
                return RecipeContract.StepEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        Log.d(LOG_TAG, "URI is " + uri);
        final int match = sUriMatcher.match(uri);

        Log.d(LOG_TAG, " match is " + match);

        switch (match) {
            // "recipes"
            case RECIPES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        RecipeContract.RecipeEntry.TABLE_NAME,
                        RECIPE_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        RecipeContract.RecipeEntry.TABLE_NAME + "." + RecipeContract.RecipeEntry._ID + " DESC"
                );

            }
            break;

            case INGREDIENTS: {
                String recipeID = uri.getLastPathSegment();
                if (selection == null) {
                    selection = RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + " = ?";
                }
                if (selectionArgs == null) {
                    selectionArgs = new String[]{recipeID};
                }

                retCursor = mOpenHelper.getReadableDatabase().query(
                        RecipeContract.IngredientEntry.TABLE_NAME,
                        INGREDIENT_COLUMNS,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        RecipeContract.IngredientEntry.TABLE_NAME
                                + "." + RecipeContract.IngredientEntry._ID
                );

            }
            break;


            case STEPS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        RecipeContract.StepEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        RecipeContract.StepEntry.TABLE_NAME + "." + RecipeContract.StepEntry._ID
                );
            }
            break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        Log.d(LOG_TAG, String.valueOf(match));

        switch (match) {
            case RECIPES: {
                long _id = 0;

                try {
                    _id = db.insertOrThrow(RecipeContract.RecipeEntry.TABLE_NAME, null, values);
                } catch (SQLiteConstraintException ex) {
                    ex.printStackTrace();
                }

                if (_id > 0) {
                    returnUri = RecipeContract.RecipeEntry.buildRecipeUri(values.getAsLong(RecipeContract.RecipeEntry._ID));
                    getContext().getContentResolver().notifyChange(uri, null);
                }
            }
            break;
            case INGREDIENTS: {
                long _id = 0;

                try {
                    _id = db.insertOrThrow(RecipeContract.IngredientEntry.TABLE_NAME, null, values);
                } catch (SQLiteConstraintException ex) {
                    ex.printStackTrace();
                }

                if (_id > 0) {
                    returnUri = RecipeContract.IngredientEntry.buildIngredientUri(values.getAsLong(RecipeContract.IngredientEntry._ID));
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            }

            case STEPS: {
                long _id = 0;

                try {
                    _id = db.insertOrThrow(RecipeContract.StepEntry.TABLE_NAME, null, values);
                } catch (SQLiteConstraintException ex) {
                    ex.printStackTrace();
                }

                if (_id > 0) {
                    returnUri = RecipeContract.StepEntry.buildStepUri(values.getAsLong(RecipeContract.StepEntry._ID));
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                break;
            }
        }

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";
        switch (match) {
            case RECIPES:
                rowsDeleted = db.delete(
                        RecipeContract.RecipeEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case RECIPE_WITH_ID:
                String idRecipe = uri.getPathSegments().get(1);
                rowsDeleted = db.delete(
                        RecipeContract.RecipeEntry.TABLE_NAME,
                        RecipeContract.RecipeEntry.COLUMN_RECIPE_ID + "=?",
                        new String[]{idRecipe});
                break;

            case INGREDIENTS:
                rowsDeleted = db.delete(
                        RecipeContract.IngredientEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case INGREDIENT_WITH_ID:
                String idIngred = uri.getPathSegments().get(1);
                rowsDeleted = db.delete(
                        RecipeContract.IngredientEntry.TABLE_NAME,
                        RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + "=?",
                        new String[]{idIngred});
                break;

            case STEPS:
                rowsDeleted = db.delete(
                        RecipeContract.StepEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case STEP_WITH_ID:
                String idStep = uri.getPathSegments().get(1);
                rowsDeleted = db.delete(
                        RecipeContract.StepEntry.TABLE_NAME,
                        RecipeContract.StepEntry.COLUMN_STEP_ID + "=?",
                        new String[]{idStep});
                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        Log.d(LOG_TAG, "delete is " + rowsDeleted);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        if (selection == null) {
            selection = RecipeContract.RecipeEntry.COLUMN_RECIPE_ID + "=?";
        }

        if (selectionArgs == null) {
            String backingID = uri.getLastPathSegment();
            selectionArgs = new String[]{backingID};
        }

        switch (match) {
            case RECIPES:
                rowsUpdated = db.update(RecipeContract.RecipeEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        Log.e(LOG_TAG, "Matched int is " + match);
        switch (match) {
            case RECIPES:
                Log.e(LOG_TAG, "RECIPES Bulk insert");
                Log.e(LOG_TAG, "Matched int is " + match);
                db.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        try {
                            long _id = db.insertOrThrow(RecipeContract.RecipeEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnCount++;
                                Log.e("bulkInsert RECIPES", returnCount + "");
                            }
                        } catch (SQLiteConstraintException ex) {
                            ex.printStackTrace();
                        }
                    }

                    db.setTransactionSuccessful();
                } catch (SQLiteException ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }

                if (returnCount > 0) {
                    getContext().getContentResolver().notifyChange(uri.normalizeScheme(), null);
                }
                break;
            case INGREDIENTS:
                Log.e(LOG_TAG, "INGREDIENTS Bulk insert");
                Log.e(LOG_TAG, "Matched int is " + match);
                db.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        try {
                            long _id = db.insertOrThrow(RecipeContract.IngredientEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnCount++;
                                Log.e("bulkInsert INGREDIENTS", returnCount + "");
                            }
                        } catch (SQLiteConstraintException ex) {
                            ex.printStackTrace();
                        }
                    }

                    db.setTransactionSuccessful();

                } catch (SQLiteException ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
                break;
            case STEPS:
                Log.e(LOG_TAG, "STEPS Bulk insert");
                Log.e(LOG_TAG, "Matched int is " + match);
                db.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        try {
                            long _id = db.insertOrThrow(RecipeContract.StepEntry.TABLE_NAME, null, value);
                            if (_id != -1) {
                                returnCount++;
                                Log.e("bulkInsert STEPS", returnCount + "");
                            }
                        } catch (SQLiteConstraintException ex) {
                            ex.printStackTrace();
                        }
                    }

                    db.setTransactionSuccessful();
                } catch (SQLiteException ex) {
                    ex.printStackTrace();
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                return super.bulkInsert(uri, values);
        }

        if (returnCount > 0) {
            Log.e(LOG_TAG +"bulkInsert", "restCount=" + returnCount + "");

            try {
                getContext().getContentResolver().notifyChange(uri.normalizeScheme(), null);
            } catch (Exception ex) {
                Log.e(LOG_TAG+"bulkInsert", " getContentResolver failed");
            }
        }
        return returnCount; }

    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }


}