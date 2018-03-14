package com.ala.elearning.API;

import android.content.Context;

import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.Exam;
import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.Beans.MSG;
import com.ala.elearning.Beans.Ques;
import com.ala.elearning.Beans.RunningExam;
import com.ala.elearning.Beans.Session;
import com.ala.elearning.Beans.Submission;
import com.ala.elearning.IResponseTriger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alaam on 12/28/2017.
 */

public interface API {
    String ApiUrl = "http://ec2-18-219-107-164.us-east-2.compute.amazonaws.com/api_elearn/api_elearn/";
    void getAvailableCourses(IResponseTriger<List<Course>> triger);
    void getAvaiableExams(Course course, IResponseTriger<List<Exam>> triger);
    void getExamResults(Course course, IResponseTriger<List<Exam>> triger);
    void getMessages(Course course, IResponseTriger<List<MSG>> triger);
    void getHomeworks(Course course,IResponseTriger<List<HomeWork>> triger);
    void getExamQuestions(Exam exam, IResponseTriger<RunningExam> triger, Context context);
    void sendMSG(MSG msg, IResponseTriger<Boolean> triger);
    void submitAnswers(List<Submission> submissions, IResponseTriger<Boolean> triger);
}
