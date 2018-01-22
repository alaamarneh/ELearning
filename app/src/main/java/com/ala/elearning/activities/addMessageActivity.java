package com.ala.elearning.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ala.elearning.API.API;
import com.ala.elearning.API.WebDummy;
import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.MSG;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.R;
import com.ala.elearning.activities.abstractActivities.EmptyActivity;
import com.ala.elearning.fragments.AddMessageFragment;

public class addMessageActivity extends EmptyActivity<Course> {

    public static final int CODE_COURSE = 1;
    private IResponseTriger<Boolean> msgResponse;
    public static Intent getIntent(Context context, Course course){
        Intent i = new Intent(context,addMessageActivity.class);
        i.putExtra(ARG_BEAN,course);
        return i;
    }


    @Override
    protected String getBarTitle() {
        return "Send Message";
    }
    @Override
    public Fragment getFragment() {
        return AddMessageFragment.newInstance(mBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Log.d("tag","on");
        if(requestCode == CODE_COURSE && resultCode == RESULT_OK){
                    ((AddMessageFragment)mFragment).putCourse((Course)data.getParcelableExtra("data"));

        }
    }
}
