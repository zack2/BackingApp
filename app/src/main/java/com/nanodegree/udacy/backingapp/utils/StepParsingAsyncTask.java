package com.nanodegree.udacy.backingapp.utils;

import android.database.Cursor;
import android.os.AsyncTask;

import com.nanodegree.udacy.backingapp.data.RecipeContract;
import com.nanodegree.udacy.backingapp.model.RecipeStep;

import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_ID_STEPS;
import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_STEP_DESCRIPTION;
import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_STEP_SHORT_DESCRIPTION;
import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_STEP_THUMBNAIL_URL;
import static com.nanodegree.udacy.backingapp.utils.Constant.COLUMN_STEP_VIDEO_URL;


/**
 * Created by root on 26/09/2017.
 */

public class StepParsingAsyncTask extends AsyncTask<Cursor, Object, RecipeStep> {
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected RecipeStep doInBackground(Cursor... params) {
        //The cursor coresponding to a unique entry of RecipeStep
        Cursor cursor = params[0];
        RecipeStep step = null;
        try {
            if (cursor.moveToFirst()) {
                step = new RecipeStep();
                step.setId(cursor.getInt(COLUMN_ID_STEPS));
                step.setShortDesc(cursor.getString(COLUMN_STEP_SHORT_DESCRIPTION));
                step.setDesc(cursor.getString(COLUMN_STEP_DESCRIPTION));
                step.setVideoUrl(cursor.getString(COLUMN_STEP_VIDEO_URL));
                step.setThumbnailUrl(cursor.getString(COLUMN_STEP_THUMBNAIL_URL));
            }
        } catch (IllegalStateException | NullPointerException ex) {
            ex.printStackTrace();
        }

        return step;
    }
}
