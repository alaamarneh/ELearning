package com.ala.elearning.API;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

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
import com.android.volley.AuthFailureError;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alaam on 1/6/2018.
 */

public class WebApi implements API {
    private static WebApi instance;
    private Context mContext;
    private RequestQueue requestQueue;

    public static void init(Context context){
        if(instance == null)
            instance = new WebApi(context);
    }
    public static WebApi getInstance() {
        return instance;
    }

    public WebApi(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getAvailableCourses(final IResponseTriger<List<Course>> triger) {
        User user = SPController.loadLocalUser(mContext);
        String url = ApiUrl +"courses/read.php?uid="+ user.getId();
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
                error.printStackTrace();
                triger.onError(error.getMessage());
            }
        });

        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    @Override
    public void getAvaiableExams(final Course course, final IResponseTriger<List<Exam>> triger) {
        String url = ApiUrl + "exams/read.php?cid="+ course.getId() + "&uid=" +SPController.loadLocalUser(mContext).getId();
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
                error.printStackTrace();
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
        String url = ApiUrl + "messages/read.php?cid="+ course.getId() + "&from=" + SPController.loadLocalUser(mContext).getId();
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
                error.printStackTrace();
                triger.onError(error.getMessage());
            }
        });

        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    @Override
    public void getHomeworks(final Course course, final IResponseTriger<List<HomeWork>> triger) {
        String url = ApiUrl + "homework/read.php?cid="+ course.getId();
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
                error.printStackTrace();
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
        // default category
        String cat = "0";
        String quesUrl = ApiUrl + "action/getexamq.php?course_id=" + 1 + "&cat=" + cat;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, quesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("records")){

                        Gson gson = new Gson();
                        List<Ques> questions = gson.fromJson(jsonObject.getString("records"), new TypeToken<List<Ques>>(){}.getType());

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
                error.printStackTrace();
                triger.onError(error.getMessage());

            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public void sendMSG(MSG msg, IResponseTriger<Boolean> triger) {

    }

    @Override
    public void submitAnswers(final List<Submission> submissions,final IResponseTriger<Boolean> triger) {
        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);

        String quesUrl = ApiUrl + "submission/create.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, quesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("tag",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("result")){
                        triger.onResponse(true);
                    }else
                        triger.onResponse(true);


                } catch (Exception e) {
                    e.printStackTrace();
                    triger.onError(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag","ERROR "+ error.toString());
                triger.onError(error.toString());
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {

                try {

                    return listToJson(submissions).toString().getBytes("utf-8");
                }catch (Exception e){triger.onError(e.getMessage());}
                return super.getBody();
            }
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Content-Type","application/json; charset=UTF-8");
                headers.put("Access-Control-Max-Age","3600");
                headers.put("Access-Control-Allow-Origin","*");
                headers.put("Access-Control-Allow-Headers","Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
    private JSONArray listToJson(List<Submission> list){
        JSONArray mJsonArray = new JSONArray();
        try {

            for (Submission submission:
                    list) {
                JSONObject mJsonObject = new JSONObject(submission.toString());
                mJsonArray.put(mJsonObject);
            }
        }catch (Exception e){e.printStackTrace();return null;}
        return mJsonArray;
    }
}
