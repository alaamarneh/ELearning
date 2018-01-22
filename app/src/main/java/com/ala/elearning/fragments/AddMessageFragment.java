package com.ala.elearning.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ala.elearning.API.API;
import com.ala.elearning.API.WebDummy;
import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.MSG;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.R;
import com.ala.elearning.activities.CoursesListActivity;
import com.ala.elearning.activities.addMessageActivity;
import com.ala.elearning.controllers.SPController;

import java.util.Date;

public class AddMessageFragment extends Fragment {
    API api = WebDummy.getInstance(getContext());
    private static final String ARG_COURSE = "argCourse";
    private Button btnSelect, btnSend;
    private EditText txtMsgText;
    private Course mCourse;
    private IResponseTriger<Boolean> msgResponse;

    public AddMessageFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag","onCreate");
    }

    public static AddMessageFragment newInstance(Course course) {
        AddMessageFragment fragment = new AddMessageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COURSE,course);
        fragment.setArguments(args);
        return fragment;
    }

    public MSG generateMsg(){
        if(!validate())
            return null;
        MSG msg = new MSG();
        msg.setText(txtMsgText.getText().toString().trim());
        msg.setTo(mCourse.getInstructor());
        msg.setFrom(SPController.loadLocalUser(getContext()));
        msg.setCourse(mCourse);
        msg.setDate(new Date());
        return msg;
    }

    private boolean validate() {
        if(TextUtils.isEmpty(txtMsgText.getText())){
            txtMsgText.setError("enter a message");
            txtMsgText.requestFocus();
            return false;
        }
        if(mCourse == null){
            Toast.makeText(getContext(), "invalid instructor", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void setFreeze(boolean freeze){
        if (freeze){
            btnSelect.setEnabled(false);
            txtMsgText.setEnabled(false);
            btnSend.setEnabled(false);
        }else {
            btnSelect.setEnabled(true);
            txtMsgText.setEnabled(true);
            btnSend.setEnabled(true);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("tag","onStart");
        msgResponse = new IResponseTriger<Boolean>() {
            @Override
            public void onResponse(Boolean value) {
                if(value) {
                    setFreeze(false);
                    getActivity().finish();
                    Toast.makeText(getContext(), "MSG Sent", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String err) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                setFreeze(false);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("tag","onCreateView");
        View view = inflater.inflate(R.layout.fragment_add_message, container, false);
        txtMsgText = view.findViewById(R.id.txtMsgText);
        btnSelect = view.findViewById(R.id.btnSelect);
        btnSend = view.findViewById(R.id.btnSend);

        mCourse = getArguments().getParcelable(ARG_COURSE);
        if(mCourse != null)
            putCourse(mCourse);



        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = CoursesListActivity.getIntent(getContext(),CoursesListActivity.MODE_SELECT);
                getActivity().startActivityForResult(i, addMessageActivity.CODE_COURSE);

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                    sendMsg();
            }
        });
        return view;
    }

    private void sendMsg() {
        setFreeze(true);
        api.sendMSG(generateMsg(),msgResponse);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("tag","onStop");
        msgResponse = null;
    }

    public void putCourse(Course course) {
        if(course == null)
            return;
        mCourse = course;
        btnSelect.setText(course.getInstructor().getName());
        Log.d("tag","put course"+course.getInstructor().getName());
    }

}
