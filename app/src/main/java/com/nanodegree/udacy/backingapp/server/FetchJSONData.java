package com.nanodegree.udacy.backingapp.server;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.nanodegree.udacy.backingapp.data.RecipeContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

import static com.nanodegree.udacy.backingapp.utils.Constant.ARRAY_INGREDIENTS;
import static com.nanodegree.udacy.backingapp.utils.Constant.ARRAY_STEPS;
import static com.nanodegree.udacy.backingapp.utils.Constant.IMAGE;
import static com.nanodegree.udacy.backingapp.utils.Constant.INDEX_OF_INGREDIENTS;
import static com.nanodegree.udacy.backingapp.utils.Constant.INDEX_OF_RECIPES;
import static com.nanodegree.udacy.backingapp.utils.Constant.INDEX_OF_STEPS;
import static com.nanodegree.udacy.backingapp.utils.Constant.INGREDIENT_ID;
import static com.nanodegree.udacy.backingapp.utils.Constant.INGREDIENT_MEASURE;
import static com.nanodegree.udacy.backingapp.utils.Constant.INGREDIENT_NAME;
import static com.nanodegree.udacy.backingapp.utils.Constant.INGREDIENT_QUANTITY;
import static com.nanodegree.udacy.backingapp.utils.Constant.RECIPE_ID;
import static com.nanodegree.udacy.backingapp.utils.Constant.RECIPE_NAME;
import static com.nanodegree.udacy.backingapp.utils.Constant.SERVINGS;
import static com.nanodegree.udacy.backingapp.utils.Constant.STEP_DESCRIPTION;
import static com.nanodegree.udacy.backingapp.utils.Constant.STEP_ID;
import static com.nanodegree.udacy.backingapp.utils.Constant.STEP_IMAGE_URL;
import static com.nanodegree.udacy.backingapp.utils.Constant.STEP_SHORT_DESCRIPTION;
import static com.nanodegree.udacy.backingapp.utils.Constant.STEP_VIDEO_URL;


/**
 * Created by pc on 25/09/2017.
 */

public class FetchJSONData {

    final static String LOG_TAG = FetchJSONData.class.getSimpleName();


    public static void parseJSON(JSONArray jsonArray, Context mContext) throws JSONException {
        Log.e(LOG_TAG, jsonArray.toString());
        int recipesInsertCount;
        int ingredientsInsertCount;
        int stepsInsertCount;

        try {
            Object[] apiObjects = getBackingDataFromJson(jsonArray);

            recipesInsertCount = mContext.getContentResolver().bulkInsert(
                    RecipeContract.RecipeEntry.CONTENT_URI,
                            (ContentValues[]) apiObjects[INDEX_OF_RECIPES]
            );

            Log.e(LOG_TAG +"recipesInsertCount valu", String.valueOf(recipesInsertCount));

           ingredientsInsertCount = mContext.getContentResolver().bulkInsert(
                    RecipeContract.IngredientEntry.CONTENT_URI,
                            (ContentValues[]) apiObjects[INDEX_OF_INGREDIENTS]
                    );

            Log.e(LOG_TAG +"ingredientsInsert value", String.valueOf(ingredientsInsertCount));

            stepsInsertCount = mContext.getContentResolver().bulkInsert(
                    RecipeContract.StepEntry.CONTENT_URI,
                            (ContentValues[]) apiObjects[INDEX_OF_STEPS]
            );

            Log.e(LOG_TAG +"stepsInsertCount value", String.valueOf(stepsInsertCount));
         if (recipesInsertCount > 0) {
                Log.e(LOG_TAG +"recipesInsertCount", String.valueOf(recipesInsertCount));
                mContext.getContentResolver().notifyChange(RecipeContract.RecipeEntry.CONTENT_URI, null, false);
            }
           if (ingredientsInsertCount > 0) {
                Log.e(LOG_TAG +"recipesInsertCount", String.valueOf(ingredientsInsertCount));
                mContext.getContentResolver().notifyChange(RecipeContract.IngredientEntry.CONTENT_URI, null, false);
            }
          if (stepsInsertCount > 0) {
                Log.e(LOG_TAG +"recipesInsertCount", String.valueOf(stepsInsertCount));
                mContext.getContentResolver().notifyChange(RecipeContract.StepEntry.CONTENT_URI, null, false);
            }

            Log.e(LOG_TAG, "inserted recipes " + recipesInsertCount);
            Log.e(LOG_TAG, "inserted ingredients for all " + ingredientsInsertCount);
            Log.e(LOG_TAG, "inserted steps for all " + stepsInsertCount);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e(LOG_TAG+" Exception", ex.toString());
        }
    }

    public static Object[]getBackingDataFromJson(JSONArray jsonArray) throws JSONException {


        ContentValues[]
                recipesValuesArray = null,
                ingredientsValuesArray = null,
                stepsValuesArray = null;


        Vector<ContentValues> recipesValuesVector =
                new Vector<ContentValues>(jsonArray.length());

        ArrayList<ContentValues> ingredientsValuesList = new ArrayList<>();
        ArrayList<ContentValues> stepsValuesList = new ArrayList<>();

        //Temporary objects
        ContentValues values = null;
        String uniqueValue;
        JSONArray ingredientsJsonArray, stepsJsonAray;

        //looper
        JSONObject recipeJsonObj;
        for (int i = 0; i < jsonArray.length(); i++) {
            recipeJsonObj = jsonArray.getJSONObject(i);
            values = new ContentValues();

            //Parse the Recipe first -> then the recipe's ingredients & steps
            values.put(RecipeContract.RecipeEntry._ID, recipeJsonObj.getInt(RECIPE_ID));
            values.put(RecipeContract.RecipeEntry.COLUMN_NAME, recipeJsonObj.getString(RECIPE_NAME));
            values.put(RecipeContract.RecipeEntry.COLUMN_SERVINGS, recipeJsonObj.getInt(SERVINGS));
            values.put(RecipeContract.RecipeEntry.COLUMN_IMAGE, recipeJsonObj.getString(IMAGE));
            recipesValuesVector.add(values);

            // for each recipe, we parse & append its ingredients & steps
            // respectively into ingredientsValuesList & stepsValuesList
            ingredientsJsonArray = recipeJsonObj.getJSONArray(ARRAY_INGREDIENTS);
            stepsJsonAray = recipeJsonObj.getJSONArray(ARRAY_STEPS);

            //*******************************************************
            //parsing the ingredients ****************************
            JSONObject ingredient;
            for (int j = 0; j < ingredientsJsonArray.length(); j++) {

                ingredient = ingredientsJsonArray.getJSONObject(j);
                values = new ContentValues();

                // this is the ID of the RECIPE,
                // the foreign key referencing RECIPES database table entry
                values.put(RecipeContract.IngredientEntry.COLUMN_RECIPE_ID, recipeJsonObj.getInt(INGREDIENT_ID));

                // A Unique field -- protection against duplications
                uniqueValue = recipeJsonObj.getInt(RECIPE_ID) + "_" + ingredient.getString(INGREDIENT_NAME);
                values.put(RecipeContract.IngredientEntry.COLUMN_UNIQUE_FIELD, uniqueValue);
                values.put(RecipeContract.IngredientEntry.COLUMN_QUANTITY, ingredient.getDouble(INGREDIENT_QUANTITY));
                values.put(RecipeContract.IngredientEntry.COLUMN_MEASURE, ingredient.getString(INGREDIENT_MEASURE));
                values.put(RecipeContract.IngredientEntry.COLUMN_INGREDIENT, ingredient.getString(INGREDIENT_NAME));

                //this vector will contain the ingredients of all recipes
                ingredientsValuesList.add(values);
            }

            //******************************************************
            //parsing the recipes steps ***************************
            JSONObject step;
            for (int k = 0; k < stepsJsonAray.length(); k++) {

                step = stepsJsonAray.getJSONObject(k);
                values = new ContentValues();

                // this is the ID of the RECIPE,
                // the foreign key referencing RECIPES database table entry
                values.put(RecipeContract.StepEntry.COLUMN_RECIPE_ID, jsonArray.getJSONObject(i).getInt(RECIPE_ID));

                //ID from API, usefull for positioning
                // +1 beacause the ID starts at 0, & the CursorAdapter
                // will throw an exception
                values.put(RecipeContract.StepEntry._ID, step.getInt(STEP_ID) + 1);

                // A Unique field -- protection against duplications
                uniqueValue = recipeJsonObj.getInt(RECIPE_ID) + "_" + step.getInt(STEP_ID);
                values.put(RecipeContract.StepEntry.COLUMN_UNIQUE_FIELD, uniqueValue);
                values.put(RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION, step.getString(STEP_SHORT_DESCRIPTION));
                values.put(RecipeContract.StepEntry.COLUMN_DESCRIPTION, step.getString(STEP_DESCRIPTION));
                values.put(RecipeContract.StepEntry.COLUMN_VIDEO_URL, step.getString(STEP_VIDEO_URL));
                values.put(RecipeContract.StepEntry.COLUMN_THUMBNAIL_URL, step.getString(STEP_IMAGE_URL));

                //this vector will contain the steps of all recipes
                stepsValuesList.add(values);
            }
        }

        //Let's convert the Vector<ContentValues> to a ContentValues Array.
        if (recipesValuesVector.size() > 0) {
            Log.e(LOG_TAG, "recipesValuesVector" + recipesValuesVector);
            recipesValuesArray = new ContentValues[recipesValuesVector.size()];
            recipesValuesVector.toArray(recipesValuesArray);
        }
        if (ingredientsValuesList.size() > 0) {
            Log.e(LOG_TAG, "ingredientsValuesList" + recipesValuesVector);
            ingredientsValuesArray = ingredientsValuesList
                    .toArray(new ContentValues[ingredientsValuesList.size()]);
        }
        if (stepsValuesList.size() > 0) {
            Log.e(LOG_TAG, "stepsValuesList" + recipesValuesVector);
            stepsValuesArray = stepsValuesList
                    .toArray(new ContentValues[stepsValuesList.size()]);
        }

        return new Object[]{
                recipesValuesArray,
                ingredientsValuesArray,
                stepsValuesArray
        };
    }

}
