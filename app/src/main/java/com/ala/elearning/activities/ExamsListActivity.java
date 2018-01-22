package com.ala.elearning.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.Exam;
import com.ala.elearning.activities.abstractActivities.ListActivity;
import com.ala.elearning.fragments.ExamsListFragment;

/**
 * Created by alaam on 12/31/2017.
 */

public class ExamsListActivity extends ListActivity<Exam> {
    public static final String ARG_COURSE = "course";
    public static Intent getIntent(Context context, Course course){
        Intent i = new Intent(context,ExamsListActivity.class);
        i.putExtra(ARG_COURSE,course);
        return i;
    }
    @Override
    protected String getBarTitle() {
        return "Exams";
    }

    @Override
    public Fragment getFragment() {
        return ExamsListFragment.newInstance(getNumberOfCols(),getCourse());
    }

    private Course getCourse() {
        if(getIntent().getParcelableExtra(ARG_COURSE) == null)
            return null;
       return  (Course) getIntent().getParcelableExtra(ARG_COURSE);
    }
}
