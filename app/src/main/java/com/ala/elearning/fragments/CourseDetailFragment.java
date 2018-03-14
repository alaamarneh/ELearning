package com.ala.elearning.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ala.elearning.Beans.Course;
import com.ala.elearning.R;
import com.ala.elearning.activities.ExamsListActivity;
import com.ala.elearning.activities.HomeworkListActivity;
import com.ala.elearning.activities.abstractActivities.DetailsActivity;
import com.ala.elearning.activities.ExamResultsListActivity;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by alaam on 12/30/2017.
 */

public class CourseDetailFragment extends DetailsFragment<Course> {
    private TextView tvCourseName,tvInstructorName,tvSummary;
    private Button btnExamsResult;
    private View layoutHomework, layoutExams;
    public CourseDetailFragment(){}

    public static DetailsFragment newInstance(Course course){
        CourseDetailFragment fragment = new CourseDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsActivity.ARG_ITEM_ID,course);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_course_detail, container, false);
        tvCourseName = rootView.findViewById(R.id.tvCourseName);
        tvInstructorName = rootView.findViewById(R.id.tvInstructorName);
        tvSummary = rootView.findViewById(R.id.tvSummary);
        btnExamsResult = rootView.findViewById(R.id.btnExamsResult);
        layoutHomework = rootView.findViewById(R.id.layoutHomework);
        layoutExams = rootView.findViewById(R.id.layoutExams);

        btnExamsResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExamsResult();
            }
        });
        layoutExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = ExamsListActivity.getIntent(getContext(),mItem);
                startActivity(i);
            }
        });
        layoutHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = HomeworkListActivity.getIntent(getContext(),mItem);
                startActivity(i);
//                try{
//                    File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                    File file = new File(sdCard, "image.jpg");
//                    FileOutputStream fos = new FileOutputStream(file);
//
//                rootView.setDrawingCacheEnabled(true);
//                rootView.buildDrawingCache();
//                Bitmap b = Bitmap.createBitmap(rootView.getDrawingCache());
//                b.compress(Bitmap.CompressFormat.JPEG, 95, fos
//);
//                }catch (Exception e){e.printStackTrace();}
            }
        });

        refreshView();
        return rootView;
    }

    private void openExamsResult() {
        Intent i = ExamResultsListActivity.getIntent(getContext(),1,mItem);
        startActivity(i);
    }

    @Override
    protected void refreshView() {
        if(mItem != null){
            tvCourseName.setText(mItem.getName());
            tvInstructorName.setText(mItem.getInstructor().getName());
            tvSummary.setText(mItem.getSummary());
        }
    }
}
