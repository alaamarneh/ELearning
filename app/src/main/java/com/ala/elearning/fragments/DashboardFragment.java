package com.ala.elearning.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ala.elearning.Beans.MSG;
import com.ala.elearning.R;
import com.ala.elearning.activities.CoursesListActivity;
import com.ala.elearning.activities.ExamsListActivity;
import com.ala.elearning.activities.HomeworkListActivity;
import com.ala.elearning.activities.MessagesListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 1/1/2018.
 */

public class DashboardFragment extends ListFragment {
    private static final String TAG_COURSES = "tagCourses";
    private static final String TAG_HW = "tagHW";
    private static final String TAG_EXAMS = "tagExams";
    private static final String TAG_MESSAGES = "tagMsg";

    public static Fragment newInstance(int numberOfCols){
        Fragment fragment = new DashboardFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NUM_OF_COLS,numberOfCols);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void setupRecyclerViewAdapter() {
        setupListAndRecycler();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void setupListAndRecycler(){
        List<DashboardItem> list = getItems();
        if (mAdapter == null)
        {
            mAdapter = new MyAdapter(list);
            mRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setList(list);
            mAdapter.notifyDataSetChanged();
        }
    }
    private List<DashboardItem> getItems() {
        DashboardItem courses = new DashboardItem(TAG_COURSES,getResources().getDrawable(R.drawable.ic_person_black_24dp),"COURSES");
        DashboardItem exams = new DashboardItem(TAG_EXAMS,getResources().getDrawable(R.drawable.ic_person_black_24dp),"EXAMS");
        DashboardItem homeworks = new DashboardItem(TAG_HW,getResources().getDrawable(R.drawable.ic_person_black_24dp),"HOMEWORKS");
        DashboardItem messages = new DashboardItem(TAG_MESSAGES,getResources().getDrawable(R.drawable.ic_person_black_24dp),"MESSAGES");


        ArrayList<DashboardItem> items = new ArrayList<>();
        items.add(courses);
        items.add(exams);
        items.add(homeworks);
        items.add(messages);

        return items;
    }

    private class MyHolder extends ListFragment.Holder<DashboardItem>{
        private ImageView mImageView;
        private TextView mTextView;
        private TextView mSummeryTextView;
        private DashboardItem mDashboardItem;

        public MyHolder(View view) {
            super(view);

            mImageView= (ImageView)itemView.findViewById(R.id.imageView);
            mTextView=(TextView)itemView.findViewById(R.id.textView);
            mSummeryTextView=(TextView)itemView.findViewById(R.id.summeryTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void bind(DashboardItem item, int pos) {
            mDashboardItem = item;
            mTextView.setText(item.getTitle());
            mImageView.setImageDrawable(item.getDrawable());
            mSummeryTextView.setText(item.getSummery());
        }
        @Override
        public void onClicked(View v) {
            switch (mDashboardItem.getTag()){
                case TAG_COURSES:
                    Intent i = new Intent(getContext(),CoursesListActivity.class);
                    startActivity(i);
                    break;
                case TAG_EXAMS:
                    startActivity(new Intent(getContext(),ExamsListActivity.class));
                    break;
                case TAG_HW:
                    startActivity(new Intent(getContext(),HomeworkListActivity.class));
                    break;
                case TAG_MESSAGES:
                    startActivity(new Intent(getContext(),MessagesListActivity.class));
                    break;
            }
        }

    }
    private class MyAdapter extends ListFragment.Adapter<DashboardItem>{

        public MyAdapter(List<DashboardItem> items) {
            super(items);
        }

        @Override
        public int getLayoutId() {
            return R.layout.row_dashboard_item_layout;
        }

        @Override
        public Holder getNewHolder(View v) {
            return new MyHolder(v);
        }
    }


    private class DashboardItem{
        private Drawable mDrawable;
        private String title;
        private String tag;
        private String summery;
        private boolean tint;

        public int getLineColor() {
            return lineColor;
        }

        public void setLineColor(int lineColor) {
            this.lineColor = lineColor;
        }

        private int lineColor;

        public String getSummery() {
            return summery;
        }

        public void setSummery(String summery) {
            this.summery = summery;
        }

        public DashboardItem(String tag, Drawable drawable, String title) {
            this.tag = tag;
            mDrawable = drawable;
            this.title = title;
            summery = "";
//            lineColor = Color.TRANSPARENT;
        }

        public boolean isTint() {
            return tint;
        }

        public void setTint(boolean tint) {
            this.tint = tint;
        }

        public String getTag(){
            return tag;
        }
        public void setTag(String tag){
            this.tag = tag;
        }

        public Drawable getDrawable() {
            return mDrawable;
        }

        public void setDrawable(Drawable drawable) {
            mDrawable = drawable;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
