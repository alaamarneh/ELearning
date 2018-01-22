package com.ala.elearning.controllers;

import android.content.Context;

import com.ala.elearning.Beans.Choice;
import com.ala.elearning.Beans.Ques;
import com.ala.elearning.Beans.Submission;
import com.ala.elearning.Beans.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alaam on 12/31/2017.
 */

public class ExamController {
    public static List<Submission> generateSubmissions(Context context, List<Ques> questions, Map<Integer,Choice> map){
        ArrayList<Submission> submissions = new ArrayList<>();
        User user = SPController.loadLocalUser(context);
        for (Ques q :
                questions) {

            if(map.containsKey(q.getId())){
                Submission s = new Submission();

                Choice choice = map.get(q.getId());
                s.setCid(choice.getId());
                s.setQ_id(q.getId());
                submissions.add(s);

                if(user!=null){
                    s.setUid(user.getId());
                    s.setUname(user.getName());
                }
            }
        }
        return submissions;
    }
}
