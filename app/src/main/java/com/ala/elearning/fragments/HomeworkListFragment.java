package com.ala.elearning.fragments;

import android.content.Intent;
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
import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.Beans.MSG;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.R;
import com.ala.elearning.activities.HomeworkDetailActivity;
import com.ala.elearning.activities.HomeworkListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 12/31/2017.
 */

public class HomeworkListFragment extends ListFragment  implements IResponseTriger<List<HomeWork>> {

    public static Fragment newInstance(int numberOfCols, Course course){
        Fragment fragment = new HomeworkListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUM_OF_COLS,numberOfCols);
        bundle.putParcelable(HomeworkListActivity.ARG_COURSE,course);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void setupRecyclerViewAdapter() {
        API api = WebApi.getInstance();
        Course course = getArguments().getParcelable(HomeworkListActivity.ARG_COURSE);
        api.getHomeworks(course,this);
        mSwipeRefreshLayout.setRefreshing(true);
    }
    @Override
    public void onResponse(List<HomeWork> value) {
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

    private class MyHolder extends ListFragment.Holder<HomeWork>{
        private TextView tvDate, tvHomeworkTitle,tvEndDate,tvCourseName;
        public MyHolder(View view) {
            super(view);
            tvCourseName = view.findViewById(R.id.tvCourseName);
            tvDate = view.findViewById(R.id.tvDate);
            tvHomeworkTitle = view.findViewById(R.id.tvHomeworkTitle);
            tvEndDate = view.findViewById(R.id.tvEndDate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(HomeWork hw, int pos) {
            tvCourseName.setText(hw.getCourse().getName());
            tvDate.setText(DateUtils.formatDateTime(getContext(),hw.getDate().getTime(),DateUtils.FORMAT_SHOW_TIME));
            tvHomeworkTitle.setText(hw.getTitle());
            tvEndDate.setText(DateUtils.formatDateTime(getContext(),hw.getEndDate().getTime(),DateUtils.FORMAT_SHOW_TIME));
        }
        @Override
        public void onClicked(View v) {
            Intent i = HomeworkDetailActivity.getIntent(getContext(),mItem);
            startActivity(i);
        }

    }
    private class MyAdapter extends ListFragment.Adapter<HomeWork>{

        public MyAdapter(List<HomeWork> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.row_homework;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new MyHolder(v);
        }
    }
}
