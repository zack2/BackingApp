package com.nanodegree.udacy.backingapp.utils;

import com.nanodegree.udacy.backingapp.data.RecipeContract;

/**
 * Created by root on 25/09/17.
 */

public class Constant {

    //the lien which contain all things
    public static final String JSON_LINK= "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static final String INTENT_KEY_RECIPE = "com.udacy.detailRecipe";
    public static final String INTENT_KEY_INGREDIENT = "com.udacy.ingredient";

    // the image of cake
    public static final String sNutellaImage= "http://del.h-cdn.co/assets/16/32/1600x800/landscape-1470773544-delish-nutella-cool-whip-pie-1.jpg";
    public static final String sBrowniesImage= "http://d2gk7xgygi98cy.cloudfront.net/204-3-large.jpg";
    public static final String sYelloCakeImage= "https://i.pinimg.com/736x/ae/18/64/ae1864d06f37b5c16de653a5a734a8b3--moist-yellow-cakes-yellow-cake-recipes.jpg";
    public static final String sCheeseCakeImage= "http://floridastrawberry.org/wp-content/uploads/2012/12/Strawberry-Cheesecake-with-Strawberry-Syrup.jpg";

    //the variable from JSON
    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String ARRAY_INGREDIENTS = "ingredients";
    public static final String INGREDIENT_ID = "id";
    public static final String INGREDIENT_QUANTITY = "quantity";
    public static final String INGREDIENT_MEASURE = "measure";
    public static final String INGREDIENT_NAME = "ingredient";
    public static final String ARRAY_STEPS = "steps";
    public static final String STEP_ID = "id";
    public static final String STEP_SHORT_DESCRIPTION = "shortDescription";
    public static final String STEP_DESCRIPTION = "description";
    public static final String STEP_VIDEO_URL = "videoURL";
    public static final String STEP_IMAGE_URL = "thumbnailURL";
    public static final String SERVINGS = "servings";
    public static final String IMAGE = "image";

    //the variable from Column
    public static final int COLUMN_RECIPE_ID = 0;
    public static final int COLUMN_RECIPE_NAME = 1;
    public static final int COLUMN_RECIPE_SERVINGS = 2;
    public static final int COLUMN_RECIPE_IMAGE = 3;


    public static final int COLUMN_INGREDIENT_QUANTITY = 1;
    public static final int COLUMN_INGREDIENT_MEASURE = 2;
    public static final int COLUMN_INGREDIENT = 3;
    public static final int COLUMN_RECIPE_INGREDIENT_ID = 4;

    public static final int COLUMN_ID_STEPS = 0;
    public static final int COLUMN_STEP_SHORT_DESCRIPTION = 1;
    public static final int COLUMN_STEP_DESCRIPTION = 2;
    public static final int COLUMN_STEP_VIDEO_URL = 3;
    public static final int COLUMN_STEP_THUMBNAIL_URL = 4;

    //duration of animation
    public static final int DURATION = 250;
    //0:recipes, 1: ingredients, 2:steps
    public static final int INDEX_OF_RECIPES = 0,
            INDEX_OF_INGREDIENTS = 1,
            INDEX_OF_STEPS = 2;

    public static final int RECIPE_LOADER = 0;
    public static final String[] RECIPE_COLUMNS = {
            RecipeContract.RecipeEntry._ID ,
            RecipeContract.RecipeEntry.COLUMN_NAME,
            RecipeContract.RecipeEntry.COLUMN_SERVINGS,
            RecipeContract.RecipeEntry.COLUMN_IMAGE

    };

    public static final int RINGREDIENT_LOADER = 1;
    public static final String[] INGREDIENT_COLUMNS = {
            RecipeContract.IngredientEntry._ID ,
            RecipeContract.IngredientEntry.COLUMN_QUANTITY,
            RecipeContract.IngredientEntry.COLUMN_MEASURE,
            RecipeContract.IngredientEntry.COLUMN_INGREDIENT,
            RecipeContract.IngredientEntry.COLUMN_RECIPE_ID

    };

    public static final int STEP_LOADER = 2;
    public static final String[] STEP_COLUMNS = {
            RecipeContract.StepEntry._ID,
            RecipeContract.StepEntry.COLUMN_SHORT_DESCRIPTION,
            RecipeContract.StepEntry.COLUMN_DESCRIPTION,
            RecipeContract.StepEntry.COLUMN_VIDEO_URL,
            RecipeContract.StepEntry.COLUMN_THUMBNAIL_URL

    };
}
