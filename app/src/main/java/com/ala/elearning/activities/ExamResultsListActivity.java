package com.ala.elearning.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ala.elearning.Beans.Course;
import com.ala.elearning.activities.abstractActivities.ListActivity;
import com.ala.elearning.fragments.ExamResultsListFragment;

/**
 * Created by alaam on 12/30/2017.
 */

public class ExamResultsListActivity extends ListActivity<Course> {
    public static Intent getIntent(Context ctx, int numberOfCos, Course course){
        Intent intent = new Intent(ctx,ExamResultsListActivity.class);
        intent.putExtra(ARG_NUMBER_OF_COLS,numberOfCos);
        intent.putExtra(ARG_BEAN,course);
        return intent;
    }

    @Override
    protected String getBarTitle() {
        return mBean.getName() + " Results";
    }

    @Override
    public Fragment getFragment() {
        return ExamResultsListFragment.newInstance(getNumberOfCols(),mBean);
    }
}
