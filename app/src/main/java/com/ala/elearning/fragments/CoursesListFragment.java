package com.ala.elearning.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ala.elearning.API.API;
import com.ala.elearning.API.WebApi;
import com.ala.elearning.API.WebDummy;
import com.ala.elearning.Beans.Course;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.R;
import com.ala.elearning.activities.CourseDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 12/30/2017.
 */

public class CoursesListFragment extends ListFragment implements IResponseTriger<List<Course>>{

    public static final String ARG_MODE = "argMode";
    public static final int MODE_NORMAL = 1;
    public static final int MODE_SELECT = 2;

    public static Fragment newInstance(int numberOfCols, int mode){
        Fragment fragment = new CoursesListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUM_OF_COLS,numberOfCols);
        bundle.putInt(ARG_MODE,mode);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void setupRecyclerViewAdapter() {
        API api = WebApi.getInstance();
        api.getAvailableCourses(this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onResponse(List<Course> value) {
        mSwipeRefreshLayout.setRefreshing(false);
        // courses available
        MyAdapter adapter = new MyAdapter(value);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String err) {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(getView(),err,Snackbar.LENGTH_SHORT);
    }


    private class MyHolder extends ListFragment.Holder<Course>{
        private TextView tvCourseName, tvSummary;
        public MyHolder(View view) {
            super(view);
            tvCourseName = view.findViewById(R.id.tvCourseName);
            tvSummary = view.findViewById(R.id.tvSummary);

        }

        @Override
        public void bind(Course course, int pos) {
            tvCourseName.setText(course.getName());
            tvSummary.setText(course.getSummary());
        }

        @Override
        public void onClicked(View v) {
            int mode = getArguments().getInt(ARG_MODE);
            if(mode == MODE_NORMAL) {
                Intent i = CourseDetailActivity.getIntent(getContext(), mItem);
                startActivity(i);
            }else if(mode == MODE_SELECT){
                Intent data = new Intent();
                data.putExtra("data",mItem);
                getActivity().setResult(Activity.RESULT_OK,data);
                getActivity().finish();
            }
        }

    }
    private class MyAdapter extends ListFragment.Adapter<Course>{

        public MyAdapter(List<Course> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.row_course;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new MyHolder(v);
        }
    }
}
