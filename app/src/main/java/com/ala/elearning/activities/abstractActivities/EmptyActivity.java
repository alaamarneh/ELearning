package com.ala.elearning.activities.abstractActivities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ala.elearning.R;
import com.ala.elearning.fragments.ListFragment;

public abstract class EmptyActivity<T> extends AppCompatActivity {
    protected T mBean;
    public static final String ARG_NUMBER_OF_COLS = "numberOfCols";
    public static final String ARG_BEAN = "bean";
    protected Fragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        mBean = getIntent().getParcelableExtra(ARG_BEAN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        mFragment =  fm.findFragmentByTag(getFragment().getTag());
        if (mFragment == null){
            mFragment = getFragment();
            fm.beginTransaction().replace(R.id.content,mFragment).commit();
        }
        setTitle(getBarTitle());
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
    protected abstract String getBarTitle();
    public abstract Fragment getFragment();
}
