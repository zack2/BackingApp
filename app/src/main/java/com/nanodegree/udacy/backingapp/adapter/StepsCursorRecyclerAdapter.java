package com.nanodegree.udacy.backingapp.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nanodegree.udacy.backingapp.R;

import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_STEP_VIDEO_URL;


/**
 * Created by root on 02/09/2017.
 */

public class StepsCursorRecyclerAdapter extends RecyclerViewCursorAdapter<RecyclerView.ViewHolder> {

    static private RecipesAdapterInteractionInterface mCallback;
    static final String TAG =StepsCursorRecyclerAdapter.class.getSimpleName();

    public StepsCursorRecyclerAdapter() {
        super(null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_steps, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, Cursor cursor) {
        //int itemViewType = getItemViewType(cursor.getPosition());

        MyViewHolder myHolder = (MyViewHolder) holder;

        //id
        myHolder.stepID = cursor.getInt(0) + "";

        //short description
        myHolder.stepName = cursor.getString(1);
        myHolder.stepVideo = cursor.getString(COLUMN_STEP_VIDEO_URL);

        if( myHolder.stepVideo == null ||  myHolder.stepVideo.isEmpty()){
            myHolder.imageView.setVisibility(View.GONE);
        }else {
            myHolder.imageView.setVisibility(View.VISIBLE);
        }


        Log.e(TAG+"stepName",  myHolder.stepName);
        myHolder.tvName.setText(myHolder.stepName);

        //implementation of android accecibilities
        myHolder.tvName.setContentDescription(cursor.getString(1));

        //the display ID has to start by 0, that's why we reduce of "-1"
        myHolder.tvPosition.setText((Integer.valueOf(myHolder.stepID) - 1) + "");

    }

    public void setCallbackListener(RecipesAdapterInteractionInterface callbackInterface) {
        mCallback = callbackInterface;
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvPosition;
        ImageView imageView;

        String stepName, stepID, stepVideo, stepImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.shortDescription);
            tvPosition = itemView.findViewById(R.id.recipeStepPosition);
            imageView = itemView.findViewById(R.id.video);

            itemView.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (mCallback != null)
                mCallback.onStepItemClick(stepID, stepName);
        }
    }

    /*
    private Bitmap getVieoThumb(String url){
        final int FRAMES = 2000000;

        FFmpegMediaMetadataRetriever mmr = new FFmpegMediaMetadataRetriever();
        mmr.setDataSource( url);
        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
        mmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
        Bitmap b = mmr.getFrameAtTime(FRAMES, FFmpegMediaMetadataRetriever.OPTION_CLOSEST); // frame at 2 seconds
        //byte [] artwork = mmr.getEmbeddedPicture();
        //mmr.release();

        return b;
    } */

    public interface RecipesAdapterInteractionInterface {
        void onStepItemClick(String stepID, String stepName);
    }
}
