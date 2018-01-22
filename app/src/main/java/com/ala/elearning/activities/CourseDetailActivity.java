package com.ala.elearning.activities;

import android.content.Context;
import android.content.Intent;

import com.ala.elearning.Beans.Course;
import com.ala.elearning.activities.abstractActivities.DetailsActivity;
import com.ala.elearning.fragments.CourseDetailFragment;
import com.ala.elearning.fragments.DetailsFragment;

/**
 * Created by alaam on 12/30/2017.
 */

public class CourseDetailActivity extends DetailsActivity<Course> {
    public static Intent getIntent(Context ctx,Course course){
        Intent intent = new Intent(ctx,CourseDetailActivity.class);
        intent.putExtra(ARG_ITEM_ID,course);
        return intent;
    }
    @Override
    protected void onFabClicked() {
        Intent i = MessagesListActivity.getIntent(this,mItem);
        startActivity(i);
    }

    @Override
    protected String getBarTitle() {
        return mItem.getName();
    }

    @Override
    protected DetailsFragment getFragment() {
        return CourseDetailFragment.newInstance(mItem);
    }
}
