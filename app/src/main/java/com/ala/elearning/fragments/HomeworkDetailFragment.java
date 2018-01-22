package com.ala.elearning.fragments;


import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.R;
import com.ala.elearning.activities.abstractActivities.DetailsActivity;

public class HomeworkDetailFragment extends DetailsFragment<HomeWork> {
    private TextView tvDate, tvHomeworkTitle,tvEndDate,tvCourseName, tvHomeworkText;

    public HomeworkDetailFragment() {
    }
    public static DetailsFragment newInstance(HomeWork homeWork){
        DetailsFragment fragment = new HomeworkDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsActivity.ARG_ITEM_ID,homeWork);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homework_detail, container, false);
        tvCourseName = view.findViewById(R.id.tvCourseName);
        tvDate = view.findViewById(R.id.tvDate);
        tvHomeworkTitle = view.findViewById(R.id.tvHomeworkTitle);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        tvHomeworkText = view.findViewById(R.id.tvHomeworkText);

        refreshView();
        return view;
    }

    @Override
    protected void refreshView() {
        HomeWork hw = mItem;
        tvCourseName.setText(hw.getCourse().getName());
        tvDate.setText(DateUtils.formatDateTime(getContext(),hw.getDate().getTime(),DateUtils.FORMAT_SHOW_TIME));
        tvHomeworkTitle.setText(hw.getTitle());
        tvEndDate.setText(DateUtils.formatDateTime(getContext(),hw.getEndDate().getTime(),DateUtils.FORMAT_ABBREV_TIME));
        tvHomeworkText.setText(hw.getText());
    }
}
