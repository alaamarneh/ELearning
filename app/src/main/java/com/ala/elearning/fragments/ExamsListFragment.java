package com.ala.elearning.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ala.elearning.API.API;
import com.ala.elearning.API.WebApi;
import com.ala.elearning.API.WebDummy;
import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.Exam;
import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.R;
import com.ala.elearning.activities.ExamActivity;
import com.ala.elearning.activities.ExamsListActivity;
import com.ala.elearning.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 12/31/2017.
 */

public class ExamsListFragment extends ListFragment  implements IResponseTriger<List<Exam>> {

    public static Fragment newInstance(int numberOfCols, Course course){
        Fragment fragment = new ExamsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUM_OF_COLS,numberOfCols);
        bundle.putParcelable(ExamsListActivity.ARG_COURSE,course);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void setupRecyclerViewAdapter() {
        API api = WebApi.getInstance(getActivity());
        Course course = getArguments().getParcelable(ExamsListActivity.ARG_COURSE);
        api.getAvaiableExams(course,this);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onResponse(List<Exam> value) {
        mSwipeRefreshLayout.setRefreshing(false);
        // exams available
        MyAdapter adapter = new MyAdapter(value);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onError(String err) {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(getView(),err,Snackbar.LENGTH_SHORT);
    }

    private class MyHolder extends ListFragment.Holder<Exam>{
        private TextView tvDate, tvExamTitle,tvEndDate,tvCourseName,tvExamStatus;
        public MyHolder(View view) {
            super(view);
            tvCourseName = view.findViewById(R.id.tvCourseName);
            tvDate = view.findViewById(R.id.tvDate);
            tvExamTitle = view.findViewById(R.id.tvExamTitle);
            tvEndDate = view.findViewById(R.id.tvEndDate);
            tvExamStatus =view.findViewById(R.id.tvExamStatus);
        }

        @Override
        public void bind(Exam exam, int pos) {
            tvCourseName.setText(exam.getCourse().getName());
            tvDate.setText(DateUtil.formatDate(exam.getDate().getTime()));
            tvExamTitle.setText(exam.getName());
            tvEndDate.setText(DateUtil.formatDate(exam.getEndDate().getTime()));
            tvExamStatus.setText(exam.getStatusName());

            if(exam.getStatus() == Exam.STATUS_COMPLETED)
                tvExamStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
            else
                tvExamStatus.setTextColor(getResources().getColor(R.color.colorAccent));

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClicked(View v) {
            if(mItem.getStatus() == Exam.STATUS_NEW){
                // startExaming
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Start Exam")
                        .setMessage("Start this Exam?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = ExamActivity.getIntent(getContext(),mItem);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


            }else if(mItem.getStatus() == Exam.STATUS_COMPLETED){
                Toast.makeText(getContext(), "This exam has completed", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private class MyAdapter extends ListFragment.Adapter<Exam>{

        public MyAdapter(List<Exam> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.row_exam;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new MyHolder(v);
        }
    }
}
