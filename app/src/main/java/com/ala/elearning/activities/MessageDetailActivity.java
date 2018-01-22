package com.ala.elearning.activities;

import android.content.Context;
import android.content.Intent;

import com.ala.elearning.Beans.MSG;
import com.ala.elearning.activities.abstractActivities.DetailsActivity;
import com.ala.elearning.fragments.DetailsFragment;
import com.ala.elearning.fragments.MessageDetailFragment;

/**
 * Created by alaam on 12/31/2017.
 */

public class MessageDetailActivity extends DetailsActivity<MSG> {
    public static Intent getIntent(Context ctx, MSG msg){
        Intent intent = new Intent(ctx,MessageDetailActivity.class);
        intent.putExtra(ARG_ITEM_ID,msg);
        return intent;
    }
    @Override
    protected void onFabClicked() {
        Intent i = addMessageActivity.getIntent(this,mItem.getCourse());
        startActivity(i);
    }

    @Override
    protected String getBarTitle() {
        return "Message";
    }

    @Override
    protected DetailsFragment getFragment() {
        return MessageDetailFragment.newInstance(mItem);
    }
}
