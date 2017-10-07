package com.nanodegree.udacy.backingapp.fragments;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.nanodegree.udacy.backingapp.R;
import com.nanodegree.udacy.backingapp.activities.MainActivity;
import com.nanodegree.udacy.backingapp.activities.StepActivity;
import com.nanodegree.udacy.backingapp.adapter.RecipesCursorRecyclerAdapter;
import com.nanodegree.udacy.backingapp.data.RecipeContract;

import static com.nanodegree.udacy.backingapp.utils.Constant.RECIPE_COLUMNS;


/**
 * Created by root on 26/09/2017.
 */

public class RecipesFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>, RecipesCursorRecyclerAdapter.RecipesAdapterInteractionInterface {

    public static final int LOADER_ID = 0;
    public static final String ACTION_MAIN_ACTIVITY = "com.nanodregree.udacy.action.mainactivity";

    StaggeredGridLayoutManager staggeredGridLayoutManager;
    RecipesCursorRecyclerAdapter mCursorAdapter;

    RecyclerView mRecyclerView;
    private final String LOG_TAG = RecipesFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_recipes, container, false);
        mRecyclerView =rootview.findViewById(R.id.myRecyclerView);
        final int SPAN_COUNT = getResources().getInteger(R.integer.recipes_span_count);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        mCursorAdapter = new RecipesCursorRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mCursorAdapter);

        mCursorAdapter.setCallbackListener(this);

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
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {


        Log.e("BALog_onCreLoad", "in on create loader, URI:");
        //build the query
        return new android.support.v4.content.CursorLoader(
                //use either getActivity() or getContext()
                getActivity(),
                RecipeContract.RecipeEntry.CONTENT_URI.normalizeScheme(),
                RECIPE_COLUMNS,
                null, null,
                null);
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
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        Log.i("dataSize", data.getCount() + "");

        //pass the data to the CursorAdapter instance
        mCursorAdapter.swapCursor(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(android.support.v4.content.Loader loader) {


        //swap a null to the CursorAdapter instance
        mCursorAdapter.swapCursor(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }



    @Override
    public void onItemClick(String recipeID, String itemName) {
        Intent intent = new Intent(getActivity(), StepActivity.class);
        intent.setAction(ACTION_MAIN_ACTIVITY);

        Bundle data = new Bundle();
        data.putString(MainActivity.EXTRA_RECIPE_ID, recipeID);
        data.putString(MainActivity.EXTRA_RECIPE_NAME, itemName);

        Log.e("BALog", itemName);
        intent.putExtras(data);
        getActivity().startActivity(intent);
    }

}
