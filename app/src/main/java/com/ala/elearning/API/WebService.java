package com.ala.elearning.API;

import android.content.Context;
import android.service.textservice.SpellCheckerService;
import android.util.Log;

import com.ala.elearning.Beans.Choice;
import com.ala.elearning.Beans.Ques;
import com.ala.elearning.Beans.Session;
import com.ala.elearning.Beans.Submission;
import com.ala.elearning.ITriger;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alaam on 11/30/2017.
 */

public class WebService {
    private RequestQueue requestQueue;
    private Context mContext;
    private static WebService instance;
    private WebService(Context context) {
        mContext = context;
    }
    public static WebService getInstance(Context context) {
        if (instance == null)
            instance = new WebService(context);
        return instance;
    }
    public void getQuestions(int sid, final ITriger<List<Ques>> listITriger){
        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);

        String quesUrl = "http://alamamoon.000webhostapp.com/api_elearn/api_elearn/action/getq.php?s=" + sid;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, quesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Ques> questions = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("records")){
//                        JSONArray jsonArray = jsonObject.getJSONArray("records");
                        questions = extractGson(jsonObject.getString("records"));
//                        if (jsonArray != null) {
//                            int len = jsonArray.length();
//                            for (int i=0;i<len;i++){ // loop for questions
//                                JSONObject mJsonObjectQuestion = jsonArray.getJSONObject(i);
//
//                                Ques q = new Ques();
//                                q.setId(mJsonObjectQuestion.getInt("id"));
////                                q.setImageurl(mJsonObjectQuestion.getString("imageurl"));
//                                q.setSid(mJsonObjectQuestion.getInt("s_id"));
//                                q.setText(mJsonObjectQuestion.getString("text"));
//                                q.setCreated(mJsonObjectQuestion.getString("created"));
//
//                                if(mJsonObjectQuestion.has("choices")){ // choices of question
////                                    extractGson(mJsonObjectQuestion.getString("choices"));
//
//                                    JSONArray mJsonArray = mJsonObjectQuestion.getJSONArray("choices");
//                                    ArrayList<Choice> choices = new ArrayList<>();
//                                    for (int j = 0;j<mJsonArray.length();j++){ // loop of choices
//                                        JSONObject mJsonObjectChoice = mJsonArray.getJSONObject(j);
//                                        Choice choice = new Choice();
//                                        choice.setId(mJsonObjectChoice.getInt("id"));
//                                        choice.setStatus(mJsonObjectChoice.getInt("status"));
//                                        choice.setText(mJsonObjectChoice.getString("text"));
//                                        choice.setQid(mJsonObjectChoice.getInt("qid"));
//
//                                        choices.add(choice);
//                                    }
//                                    q.setChoices(choices);
//                                }
//                                questions.add(q);

//                            }
//                        }
//                        Log.d("tag","records : "+jsonObject.get("records").toString());
                        listITriger.onResponse(questions);
                    }else{
                        Log.d("tag",response);
                        listITriger.onError(response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    listITriger.onError(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag","ERROR "+ error.toString());

            }
        });
        requestQueue.add(stringRequest);
    }

    private List<Ques> extractGson(String response) {
        try {

        Gson gson = new Gson();
        List<Ques> questions = gson.fromJson(response, new TypeToken<List<Ques>>(){}.getType());

        for (Ques ques :
                questions) {
            Log.d("tag",ques.getText());
            for (Choice c :
                    ques.getChoices()) {
                Log.d("tag", c.getText());
                Log.d("tag", c.getStatus() +"");
            }
        }
        return questions;
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    public void submit(final ArrayList<Submission> submissions, final ITriger<Boolean> triger){
        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);

        String quesUrl = "http://alamamoon.000webhostapp.com/api_elearn/api_elearn/submission/create.php";

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
    private JSONArray listToJson(ArrayList<Submission> list){
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
    public void getSession(final ITriger<ArrayList<Session>> listITriger){
        if (requestQueue==null)
            requestQueue = Volley.newRequestQueue(mContext);

        String quesUrl = "http://alamamoon.000webhostapp.com/api_elearn/api_elearn/session/read.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, quesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Session> sessions = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("records")){
                        JSONArray jsonArray = jsonObject.getJSONArray("records");
                        if (jsonArray != null) {
                            int len = jsonArray.length();
                            for (int i=0;i<len;i++){ // loop for questions
                                JSONObject mJsonObjectQuestion = jsonArray.getJSONObject(i);

                                Session s = new Session();
                                s.setId(mJsonObjectQuestion.getInt("id"));
//                                q.setImageurl(mJsonObjectQuestion.getString("imageurl"));
                                s.setName(mJsonObjectQuestion.getString("name"));
                                s.setDescription(mJsonObjectQuestion.getString("description"));
                                s.setCreated(mJsonObjectQuestion.getString("created"));


                                sessions.add(s);

                            }
                        }
                        Log.d("tag","records : "+jsonObject.get("records").toString());
                        listITriger.onResponse(sessions);
                    }else{
                        Log.d("tag",response);
                        listITriger.onError(response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    listITriger.onError(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("tag","ERROR "+ error.toString());

            }
        });
        requestQueue.add(stringRequest);
    }
}
