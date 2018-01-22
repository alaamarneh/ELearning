package com.ala.elearning.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ala.elearning.activities.abstractActivities.DetailsActivity;

/**
 * Created by alaam on 12/30/2017.
 */

public abstract class DetailsFragment<T> extends Fragment {
        protected T mItem;
        public void refresh(T item){
            mItem = item;
            refreshView();
        }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBean();
    }

    private void setBean() {
        if (getArguments().containsKey(DetailsActivity.ARG_ITEM_ID)) {
            mItem = getArguments().getParcelable(DetailsActivity.ARG_ITEM_ID);
        }else {
            mItem=null;
        }
    }

    protected abstract void refreshView();
}