package com.ala.elearning.activities.abstractActivities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.ala.elearning.R;
import com.ala.elearning.fragments.DetailsFragment;

public abstract class DetailsActivity<T> extends AppCompatActivity {
    public static final String ARG_ITEM_ID = "item_id";
    protected CollapsingToolbarLayout mAppBarLayout;
    protected DetailsFragment mFragment;
    protected T mItem;
    protected FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mItem = getIntent().getParcelableExtra(ARG_ITEM_ID);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAppBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (mAppBarLayout != null) {
            mAppBarLayout.setTitle(getBarTitle());
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClicked();
            }
        });

        if (savedInstanceState == null) {
            // Create the detail fragme nt and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(ARG_ITEM_ID,
                    getIntent().getParcelableExtra(ARG_ITEM_ID));
            DetailsFragment fragment = getFragment();
            mFragment = fragment;

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment)
                    .commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected abstract void onFabClicked();
    protected abstract String getBarTitle();
    protected abstract DetailsFragment getFragment();

}
