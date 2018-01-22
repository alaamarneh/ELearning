package com.ala.elearning.activities.abstractActivities;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ala.elearning.R;
import com.ala.elearning.fragments.ListFragment;

import java.util.ArrayList;

public abstract class ListActivity<T> extends AppCompatActivity {
    protected T mBean;
    public static final String ARG_NUMBER_OF_COLS = "numberOfCols";
    public static final String ARG_BEAN = "bean";

    ListFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mBean = getIntent().getParcelableExtra(ARG_BEAN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fm = getSupportFragmentManager();
        mFragment = (ListFragment) fm.findFragmentByTag(getFragment().getTag());
        if (mFragment == null){
            mFragment =(ListFragment) getFragment();
            fm.beginTransaction().replace(R.id.content,mFragment).commit();
        }

        setTitle(getBarTitle());
    }

    protected abstract String getBarTitle();

    protected int getNumberOfCols(){
        int numberOfCols = getIntent().getIntExtra(ARG_NUMBER_OF_COLS,1);
        if (numberOfCols > 0){
            return numberOfCols;
        }else {
            return 1;
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
    public abstract Fragment getFragment();
}
