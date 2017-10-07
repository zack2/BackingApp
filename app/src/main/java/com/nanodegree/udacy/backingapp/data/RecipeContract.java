package com.nanodegree.udacy.backingapp.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pc on 07/02/2017.
 */

public class RecipeContract {


    public static final String CONTENT_AUTHORITY = "com.nanodegree.udacy.backingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_RECIPE = "recipe";
    public static final String PATH_INGREDIENT = "ingredient";
    public static final String PATH_STEP = "step";


    public static final class RecipeEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RECIPE;

        // Table name
        public static final String TABLE_NAME = "recipe";

        public static final String COLUMN_RECIPE_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVINGS = "serving";
        public static final String COLUMN_IMAGE = "image";


        public static Uri buildRecipeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildRecipeWithId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getIdRecipeFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class IngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;

        // Table name
        public static final String TABLE_NAME = "ingredient";

        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MEASURE = "measure";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_UNIQUE_FIELD = "unique_field";
        public static final String COLUMN_RECIPE_ID = "recipe_id";


        public static Uri buildIngredientUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildIngredientWithId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getIdIngredientFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }

    public static final class StepEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STEP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STEP;

        // Table name
        public static final String TABLE_NAME = "steps";

        public static final String COLUMN_STEP_ID = "id";
        public static final String COLUMN_SHORT_DESCRIPTION = "shortDescription";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_VIDEO_URL = "videoURL";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnailURL";
        public static final String COLUMN_UNIQUE_FIELD = "unique_field";
        public static final String COLUMN_RECIPE_ID = "recipe_id";


        public static Uri buildStepUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildStepWithId(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

        public static String getIdStepFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }


}
