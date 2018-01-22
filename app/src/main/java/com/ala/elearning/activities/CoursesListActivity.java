package com.ala.elearning.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.activities.abstractActivities.ListActivity;
import com.ala.elearning.fragments.CoursesListFragment;

/**
 * Created by alaam on 12/30/2017.
 */

public class CoursesListActivity extends ListActivity {
    public static final String ARG_MODE = "argMode";
    public static final int MODE_NORMAL = 1;
    public static final int MODE_SELECT = 2;
    public static Intent getIntent(Context ctx, int mode){
        Intent intent = new Intent(ctx,CoursesListActivity.class);
        intent.putExtra(ARG_MODE,mode);
        return intent;
    }
    @Override
    protected String getBarTitle() {
        return "Courses";
    }

    @Override
    public Fragment getFragment() {
        return CoursesListFragment.newInstance(getNumberOfCols(),getMode());
    }

    private int getMode() {
        return getIntent().getIntExtra(ARG_MODE,MODE_NORMAL);
    }
}
