package com.ala.elearning.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.ala.elearning.API.API;
import com.ala.elearning.API.WebApi;
import com.ala.elearning.API.WebDummy;
import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.Exam;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alaam on 12/30/2017.
 */

public class ExamResultsListFragment extends ListFragment implements IResponseTriger<List<Exam>> {

    public static Fragment newInstance(int numberOfCols, Course course){
        Fragment fragment = new ExamResultsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUM_OF_COLS,numberOfCols);
        bundle.putParcelable(ARG_ITEM,course);

        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void setupRecyclerViewAdapter() {
        API api = WebApi.getInstance();
        mSwipeRefreshLayout.setRefreshing(true);
        api.getExamResults(getCourse(),this);
    }

    private Course getCourse() {
        return getArguments().getParcelable(ARG_ITEM);
    }

    @Override
    public void onResponse(List<Exam> value) {
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

    private class MyHolder extends ListFragment.Holder<Exam>{
        private TextView tvExamDate, tvExamName,tvExamResult;
        public MyHolder(View view) {
            super(view);
            tvExamDate = view.findViewById(R.id.tvExamDate);
            tvExamName = view.findViewById(R.id.tvExamName);
            tvExamResult = view.findViewById(R.id.tvExamResult);
        }

        @Override
        public void bind(Exam exam, int pos) {
            tvExamDate.setText(DateUtils.getRelativeDateTimeString(getContext(),exam.getDate().getTime()
            ,DateUtils.MINUTE_IN_MILLIS,DateUtils.WEEK_IN_MILLIS,0));

            tvExamName.setText(exam.getName());
            tvExamResult.setText(exam.getMark() + " / " + exam.getMaxMark());
        }

        @Override
        public void onClicked(View v) {

        }

    }
    private class MyAdapter extends ListFragment.Adapter<Exam>{

        public MyAdapter(List<Exam> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.row_exam_result;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new MyHolder(v);
        }
    }
}
