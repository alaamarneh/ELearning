package com.ala.elearning.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.R;
import com.ala.elearning.activities.abstractActivities.DetailsActivity;
import com.ala.elearning.controllers.SPController;
import com.ala.elearning.fragments.DetailsFragment;
import com.ala.elearning.fragments.HomeworkDetailFragment;

/**
 * Created by alaam on 12/31/2017.
 */

public class HomeworkDetailActivity extends DetailsActivity<HomeWork> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupFab();
    }

    private void setupFab() {
        int status = SPController.getHomeworkStatus(this,mItem);
        if(status == SPController.STATUS_COMPLETED){
            fab.setImageResource(R.drawable.ic_done_all_white_24dp);
        }else
            fab.setImageResource(R.drawable.ic_done_white_24dp);
    }

    public static Intent getIntent(Context ctx, HomeWork homeWork){
        Intent intent = new Intent(ctx,HomeworkDetailActivity.class);
        intent.putExtra(ARG_ITEM_ID,homeWork);
        return intent;
    }
    @Override
    protected void onFabClicked() {
        int status = SPController.getHomeworkStatus(this,mItem);
        if(status == SPController.STATUS_COMPLETED){
            fab.setImageResource(R.drawable.ic_done_white_24dp);
            SPController.setHomeworkStatus(this,mItem,SPController.STATUS_UNCOMPLETED);

            Toast.makeText(this, "Marked as UnComplete", Toast.LENGTH_SHORT).show();
        }else {
            fab.setImageResource(R.drawable.ic_done_all_white_24dp);
            SPController.setHomeworkStatus(this,mItem,SPController.STATUS_COMPLETED);

            Toast.makeText(this, "Marked as Complete", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected String getBarTitle() {
        return "Homework";
    }

    @Override
    protected DetailsFragment getFragment() {
        return HomeworkDetailFragment.newInstance(mItem);
    }
}
