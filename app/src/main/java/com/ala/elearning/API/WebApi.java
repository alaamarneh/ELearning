package com.ala.elearning.API;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ala.elearning.Beans.Course;
import com.ala.elearning.Beans.Exam;
import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.Beans.MSG;
import com.ala.elearning.Beans.Ques;
import com.ala.elearning.Beans.RunningExam;
import com.ala.elearning.Beans.Submission;
import com.ala.elearning.Beans.User;
import com.ala.elearning.IResponseTriger;
import com.ala.elearning.controllers.SPController;
import com.ala.elearning.util.DateDeserializer;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alaam on 1/6/2018.
 */

public class WebApi implements API {
    private static WebApi instance;
    private Context mContext;
    private RequestQueue requestQueue;
    public static WebApi getInstance(Context context) {
        if(instance == null)
            instance = new WebApi(context);
        return instance;
    }

    public WebApi(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getAvailableCourses(final IResponseTriger<List<Course>> triger) {
        User user = SPController.loadLocalUser(mContext);
        String url = "http://alamamoon.000webhostapp.com/api_elearn/api_elearn/courses/read.php?uid="+ user.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.has("records")){
                    try {

                    Gson gson = new Gson();
                    List<Course> courses = gson.fromJson(response.getString("records"), new TypeToken<List<Course>>(){}.getType());
                    triger.onResponse(courses);
                    }catch (Exception e){
                        e.printStackTrace();
                        triger.onError(e.getMessage());
                    }
                }else
                    triger.onError("no records");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                triger.onError(error.getMessage());
            }
        });

        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    @Override
    public void getAvaiableExams(final Course course, final IResponseTriger<List<Exam>> triger) {
        String url = "http://alamamoon.000webhostapp.com/api_elearn/api_elearn/exams/read.php?cid="+ course.getId() + "&uid=" +SPController.loadLocalUser(mContext).getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.has("records")){
                    try {
                        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();

                        List<Exam> exams = gson.fromJson(response.getString("records"), new TypeToken<List<Exam>>(){}.getType());
                        for (Exam e :
                                exams) {
                            e.setCourse(course);
                        }
                        triger.onResponse(exams);
                    }catch (Exception e){
                        e.printStackTrace();
                        triger.onError(e.getMessage());
                    }
                }else
                    triger.onError("no records");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                triger.onError(error.getMessage());
            }
        });

        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    @Override
    public void getExamResults(Course course, IResponseTriger<List<Exam>> triger) {
        getAvaiableExams(course,triger);
    }

    @Override
    public void getMessages(final Course course, final IResponseTriger<List<MSG>> triger) {
        String url = "http://alamamoon.000webhostapp.com/api_elearn/api_elearn/messages/read.php?cid="+ course.getId() + "&from=" + SPController.loadLocalUser(mContext).getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.has("records")){
                    try {
                        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();

                        List<MSG> msgs = gson.fromJson(response.getString("records"), new TypeToken<List<MSG>>(){}.getType());
                        for (MSG m :
                                msgs) {
                            m.setCourse(course);
                        }
                        triger.onResponse(msgs);
                    }catch (Exception e){
                        e.printStackTrace();
                        triger.onError(e.getMessage());
                    }
                }else
                    triger.onError("no records");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                triger.onError(error.getMessage());
            }
        });

        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    @Override
    public void getHomeworks(final Course course, final IResponseTriger<List<HomeWork>> triger) {
        String url = "http://alamamoon.000webhostapp.com/api_elearn/api_elearn/homework/read.php?cid="+ course.getId();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.has("records")){
                    try {
                        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new DateDeserializer()).create();

                        List<HomeWork> homeWork = gson.fromJson(response.getString("records"), new TypeToken<List<HomeWork>>(){}.getType());
                        for (HomeWork hw :
                                homeWork) {
                            hw.setCourse(course);
                        }
                        triger.onResponse(homeWork);
                    }catch (Exception e){
                        e.printStackTrace();
                        triger.onError(e.getMessage());
                    }
                }else
                    triger.onError("no records");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                triger.onError(error.getMessage());
            }
        });

        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    @Override
    public void getExamQuestions(final Exam exam, final IResponseTriger<RunningExam> triger, Context context) {
        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);

        String quesUrl = "http://alamamoon.000webhostapp.com/api_elearn/api_elearn/action/getq.php?s=" + exam.getId();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, quesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("records")){

                        Gson gson = new Gson();
                        List<Ques> questions = gson.fromJson(response, new TypeToken<List<Ques>>(){}.getType());

                        RunningExam runningExam = new RunningExam();
                        runningExam.setParagraph(exam.getParagraph());
                        runningExam.setTitle(exam.getName());
                        runningExam.setExam(exam);
                        runningExam.setQuestions(questions);

                        triger.onResponse(runningExam);
                    }else{
                        triger.onError(response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    triger.onError(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                triger.onError(error.getMessage());

            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void sendMSG(MSG msg, IResponseTriger<Boolean> triger) {

    }

    @Override
    public void submitAnswers(List<Submission> submissions, IResponseTriger<Boolean> triger) {

    }
}
