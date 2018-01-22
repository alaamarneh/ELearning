package com.ala.elearning.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.MSG;
import com.ala.elearning.R;
import com.ala.elearning.activities.abstractActivities.ListActivity;
import com.ala.elearning.fragments.MessagesListFragment;

/**
 * Created by alaam on 12/31/2017.
 */

public class MessagesListActivity extends ListActivity<MSG> {
    public static final String ARG_COURSE = "course";

    public static Intent getIntent(Context context, Course course){
        Intent i = new Intent(context,MessagesListActivity.class);
        i.putExtra(ARG_COURSE,course);
        return i;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_newMsg:
                startActivity(addMessageActivity.getIntent(this,null));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected String getBarTitle() {
        return "Messages";
    }

    @Override
    public Fragment getFragment() {
        return MessagesListFragment.newInstance(getNumberOfCols(),getCourse());
    }
    private Course getCourse() {
        if(getIntent().getParcelableExtra(ARG_COURSE) == null)
            return null;
        return  (Course) getIntent().getParcelableExtra(ARG_COURSE);
    }

}
