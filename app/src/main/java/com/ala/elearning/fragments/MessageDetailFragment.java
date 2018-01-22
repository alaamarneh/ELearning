package com.ala.elearning.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ala.elearning.Beans.MSG;
import com.ala.elearning.R;
import com.ala.elearning.activities.abstractActivities.DetailsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageDetailFragment extends DetailsFragment<MSG> {
    private TextView tvDate, tvFrom,tvTo,tvCourseName,tvText;
    public static DetailsFragment newInstance(MSG msg){
        DetailsFragment fragment = new MessageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsActivity.ARG_ITEM_ID,msg);
        fragment.setArguments(bundle);
        return fragment;
    }
    public MessageDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_detail, container, false);
        tvCourseName = view.findViewById(R.id.tvCourseName);
        tvDate = view.findViewById(R.id.tvDate);
        tvFrom = view.findViewById(R.id.tvFrom);
        tvTo = view.findViewById(R.id.tvTo);
        tvText = view.findViewById(R.id.tvText);

        refreshView();
        return view;
    }

    @Override
    protected void refreshView() {
        tvCourseName.setText(mItem.getCourse().getName());
        tvDate.setText(DateUtils.getRelativeDateTimeString(getContext(),mItem.getDate().getTime()
                ,DateUtils.MINUTE_IN_MILLIS,DateUtils.WEEK_IN_MILLIS,0));
        tvFrom.setText(mItem.getFrom().getName());
        tvTo.setText(mItem.getTo().getName());
        tvText.setText(mItem.getText());
    }
}
