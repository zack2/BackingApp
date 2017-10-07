
package com.nanodegree.udacy.backingapp.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nanodegree.udacy.backingapp.R;
import com.nanodegree.udacy.backingapp.api.CustomJSONArrayRequest;
import com.nanodegree.udacy.backingapp.fragments.RecipesFragment;
import com.nanodegree.udacy.backingapp.server.FetchJSONData;
import com.nanodegree.udacy.backingapp.utils.BackingAppApplication;

import org.json.JSONArray;
import org.json.JSONException;

import static com.nanodegree.udacy.backingapp.utils.Constant.JSON_LINK;


public class MainActivity extends AppCompatActivity {

    private static final String TAG_MAIN_FRAGMENT = "main";
    private static final String TAG_RECIPE_DETAILS_FRAGMENT = "steps";
    private static final String TAG_INGREDIENTS_FRAGMENT = "ingredients";
    private static final String TAG_STEP_DETAILS = "step_details";

    public static final String EXTRA_RV_SCHROLL_POSITION = "com.nanodegree.udacy.backingapp.mainactivity.schroll";
    public static final String EXTRA_RECIPE_NAME = "extra_recipe_name";
    public static final String EXTRA_STEP_NAME = "extra_step_name";
    public static final String EXTRA_STEP_ID = "extra_step_id";
    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";
    public static final String EXTRA_RECIPE_OBJECT = "com.recipe.object";
    public static final String EXTRA_CURRENT_FRAGMENT = "fragment_tag";

    private static final long SWIPE_REFRESHING_TIMEOUT = 12000;
    private boolean isReadyForExit = false;

    Toolbar toolbar;
    CustomJSONArrayRequest jsonRequest;
    SwipeRefreshLayout swipeRefresh;
    final static String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        setSupportActionBar(toolbar);
        downloadRecipes();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RecipesFragment())
                    .commit();
        }



    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            downloadRecipes();
                            swipeRefresh.setRefreshing(false);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        }
                }, SWIPE_REFRESHING_TIMEOUT);

            }
        });
    }

    @SuppressWarnings("deprecation")
   private void initUI(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.icons));
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
    }

    //download all recipe
   private void downloadRecipes(){
         jsonRequest = new CustomJSONArrayRequest(
                JSON_LINK,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                            FetchJSONData.parseJSON(jsonArray, getApplication());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG+"VolleyError", volleyError.toString());
                    }
                }
        );

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        BackingAppApplication.getInstance(getApplication()).addToRequestQueue(jsonRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    void setReadyForExit() {
        isReadyForExit = true;
        Toast.makeText(getApplicationContext(), R.string.alert_exit_doubleclick, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {

        if (isReadyForExit) {
            super.onBackPressed();
        } else {
            setReadyForExit();
        }
    }
}
