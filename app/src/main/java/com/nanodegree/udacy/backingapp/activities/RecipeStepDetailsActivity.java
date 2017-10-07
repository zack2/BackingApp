package com.nanodegree.udacy.backingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nanodegree.udacy.backingapp.R;
import com.nanodegree.udacy.backingapp.fragments.StepDetailsFragment;
import com.nanodegree.udacy.backingapp.model.Recipe;

import butterknife.ButterKnife;

import static com.nanodegree.udacy.backingapp.activities.MainActivity.EXTRA_RECIPE_ID;
import static com.nanodegree.udacy.backingapp.activities.MainActivity.EXTRA_RECIPE_OBJECT;
import static com.nanodegree.udacy.backingapp.activities.MainActivity.EXTRA_STEP_ID;

public class RecipeStepDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_STEP = "com.nanodegree.udacy.backingapp.extra.recipe";

    private String mStepID;
    private String mRecipeID;
    Recipe recipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra(EXTRA_RECIPE_ID) != null &&
                intent.getStringExtra(EXTRA_STEP_ID) != null) {
            mRecipeID = intent.getStringExtra(EXTRA_RECIPE_ID);
            mStepID = intent.getStringExtra(EXTRA_STEP_ID);
            recipe = intent.getParcelableExtra(EXTRA_RECIPE_OBJECT);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                            StepDetailsFragment.newInstance(mRecipeID, mStepID))
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Data here

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //data here
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }
}
