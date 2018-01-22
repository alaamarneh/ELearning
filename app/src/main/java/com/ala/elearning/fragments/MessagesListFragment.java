package com.ala.elearning.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ala.elearning.API.API;
import com.ala.elearning.API.WebApi;
import com.ala.elearning.API.WebDummy;
import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.MSG;
import com.ala.elearning.Beans.User;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.R;
import com.ala.elearning.activities.MessageDetailActivity;
import com.ala.elearning.activities.MessagesListActivity;
import com.firebase.ui.database.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 12/30/2017.
 */

public class MessagesListFragment extends ListFragment implements IResponseTriger<List<MSG>> {


    public static Fragment newInstance(int numberOfCols, Course course){
        Fragment fragment = new MessagesListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUM_OF_COLS,numberOfCols);
        bundle.putParcelable(MessagesListActivity.ARG_COURSE,course);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void setupRecyclerViewAdapter() {

        API api = WebApi.getInstance(getActivity());
        Course course = getArguments().getParcelable(MessagesListActivity.ARG_COURSE);
        api.getMessages(course,this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onResponse(List<MSG> value) {
        mSwipeRefreshLayout.setRefreshing(false);
        // msg available
        MyAdapter adapter = new MyAdapter(value);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String err) {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(getView(),err,Snackbar.LENGTH_SHORT);
    }

    private class MyHolder extends ListFragment.Holder<MSG>{
        private TextView tvDate, tvFrom,tvTo,tvCourseName;
        public MyHolder(View view) {
            super(view);
            tvCourseName = view.findViewById(R.id.tvCourseName);
            tvDate = view.findViewById(R.id.tvDate);
            tvFrom = view.findViewById(R.id.tvFrom);
            tvTo = view.findViewById(R.id.tvTo);

            itemView.setOnClickListener(this);

        }

        @Override
        public void bind(MSG msg, int pos) {
            tvCourseName.setText(msg.getCourse().getName());
            tvDate.setText(DateUtils.getRelativeDateTimeString(getContext(),msg.getDate().getTime()
            ,DateUtils.MINUTE_IN_MILLIS,DateUtils.WEEK_IN_MILLIS,0));
            tvFrom.setText(msg.getFrom().getName());
            tvTo.setText(msg.getTo().getName());
        }
        @Override
        public void onClicked(View v) {
            startActivity(MessageDetailActivity.getIntent(getContext(),mItem));
        }

    }
    private class MyAdapter extends ListFragment.Adapter<MSG>{

        public MyAdapter(List<MSG> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.row_message;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new MyHolder(v);
        }
    }
}
