package com.nanodegree.udacy.backingapp.fragments;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.nanodegree.udacy.backingapp.R;
import com.nanodegree.udacy.backingapp.adapter.IngredientsCursorRecyclerAdapter;
import com.nanodegree.udacy.backingapp.data.RecipeContract;

import static com.nanodegree.udacy.backingapp.activities.MainActivity.EXTRA_RECIPE_ID;
import static com.nanodegree.udacy.backingapp.activities.MainActivity.EXTRA_STEP_ID;
import static com.nanodegree.udacy.backingapp.utils.Constant.INGREDIENT_COLUMNS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must
 * Use the {@link RecipeIngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeIngredientsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOADER_ID = 3;

    private String mStepID;

    LinearLayoutManager mLayoutManager;
    IngredientsCursorRecyclerAdapter mCursorAdapter;
    RecyclerView mRecyclerView;

    public RecipeIngredientsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipeId   Parameter 1.
     * @param recipeName Parameter 2.
     * @return A new instance of fragment RecipeDetailsFragment.
     */
    public static RecipeIngredientsFragment newInstance(String recipeId, String recipeName) {
        RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_STEP_ID, recipeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStepID = getArguments().getString(EXTRA_STEP_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_ingredients, container, false);
        mRecyclerView = rootview.findViewById(R.id.ingredientsRecyclerView);

        if (savedInstanceState != null) {
            String tmpRecipeID = savedInstanceState.getString(EXTRA_RECIPE_ID);
            if (tmpRecipeID != null) {
                mStepID = tmpRecipeID;
            }
        }


        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCursorAdapter = new IngredientsCursorRecyclerAdapter(null);
        mRecyclerView.setAdapter(mCursorAdapter);

        return rootview;
    }

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        //build the query

        String selection = RecipeContract.IngredientEntry.TABLE_NAME
                + "." + RecipeContract.IngredientEntry.COLUMN_RECIPE_ID + " = ?";
        String[] selectionArgs = new String[]{mStepID};

        return new android.support.v4.content.CursorLoader(
                getActivity(),
                RecipeContract.IngredientEntry.CONTENT_URI,
                INGREDIENT_COLUMNS,
                selection,
                selectionArgs,
                RecipeContract.IngredientEntry.TABLE_NAME
                        + "." + RecipeContract.IngredientEntry._ID);

    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link android.support.v4.app.FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     * <p>
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     * <p>
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);

        Log.i("data_size", data.getCount() + "");
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(EXTRA_RECIPE_ID, mStepID);
        super.onSaveInstanceState(outState);
    }
}
