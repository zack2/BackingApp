package com.nanodegree.udacy.backingapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacy.backingapp.R;
import com.nanodegree.udacy.backingapp.utils.utils;

import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_RECIPE_ID;
import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_RECIPE_IMAGE;
import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_RECIPE_NAME;
import static com.nanodegree.udacy.backingapp.utils.Constant.sBrowniesImage;
import static com.nanodegree.udacy.backingapp.utils.Constant.sCheeseCakeImage;
import static com.nanodegree.udacy.backingapp.utils.Constant.sNutellaImage;
import static com.nanodegree.udacy.backingapp.utils.Constant.sYelloCakeImage;


/**
 * Created by root on 02/09/2017.
 */

public class RecipesCursorRecyclerAdapter extends RecyclerViewCursorAdapter<RecyclerView.ViewHolder> {

    static private RecipesAdapterInteractionInterface mCallback;
    private static final String TAG = RecipesCursorRecyclerAdapter.class.getSimpleName();
    Context mContext;
    String imageRecipe, recipeName;

    public RecipesCursorRecyclerAdapter(Context context) {
        super(null);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_recipe, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        //int itemViewType = getItemViewType(cursor.getPosition());

        MyViewHolder myHolder = (MyViewHolder) holder;
        int idRecipe = cursor.getInt(COLUMN_RECIPE_ID);

        Log.e(TAG, cursor.getString(COLUMN_RECIPE_NAME));
        recipeName = cursor.getString(COLUMN_RECIPE_NAME);
        imageRecipe = cursor.getString(COLUMN_RECIPE_IMAGE);

        myHolder.recipeId = cursor.getInt(COLUMN_RECIPE_ID) + "";
        myHolder.recipeName = cursor.getString(COLUMN_RECIPE_NAME);

        //if there is no image download my personal images
        if(imageRecipe.isEmpty() || imageRecipe.contains("")){
            if(recipeName.contains("Nutella Pie")){
                utils.downloadImage(mContext, sNutellaImage, myHolder.image);
            }
            if(recipeName.contains("Brownies")){
                utils.downloadImage(mContext, sBrowniesImage, myHolder.image);
            }
            if(recipeName.contains("Yellow Cake")){
                utils.downloadImage(mContext, sYelloCakeImage, myHolder.image);
            }
            if(recipeName.contains("Cheesecake")){
                utils.downloadImage(mContext, sCheeseCakeImage, myHolder.image);
            }
        }else {
            if(recipeName.contains("Nutella Pie")){
                utils.downloadImage(mContext, imageRecipe, myHolder.image);
            }
            if(recipeName.contains("Brownies")){
                utils.downloadImage(mContext, imageRecipe, myHolder.image);
            }
            if(recipeName.contains("Yellow Cake")){
                utils.downloadImage(mContext, imageRecipe, myHolder.image);
            }
            if(recipeName.contains("Cheesecake")){
                utils.downloadImage(mContext, imageRecipe, myHolder.image);
            }
        }


        myHolder.tvName.setText(cursor.getString(COLUMN_RECIPE_NAME));
    }

    public void setCallbackListener(RecipesAdapterInteractionInterface callbackInterface) {
        mCallback = callbackInterface;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tvName;
        ImageView image;
        String recipeName, recipeId;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_recipe);
            tvName = itemView.findViewById(R.id.txt_name_recipe);

            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (mCallback != null) mCallback.onItemClick(recipeId, recipeName);
        }
    }



    public interface RecipesAdapterInteractionInterface {
        void onItemClick(String itemId, String itemName);
    }
}
