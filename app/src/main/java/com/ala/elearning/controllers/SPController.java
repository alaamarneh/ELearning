package com.ala.elearning.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.ala.elearning.Beans.HomeWork;
import com.ala.elearning.Beans.User;

/**
 * Created by alaam on 12/28/2017.
 */

public class SPController {
    public static final String ARG_USER_NAME = "username";
    public static final String ARG_USER_ID = "userid";

    public static final int STATUS_COMPLETED = 1;
    public static final int STATUS_UNCOMPLETED = 0;

    public static void saveLocalUser(Context context, User user){
        SharedPreferences sp = context.getSharedPreferences("db",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(ARG_USER_NAME,user.getName());
        editor.putString(ARG_USER_ID,user.getId());


        editor.commit();
    }
    public static void logout(Context context){
        SharedPreferences sp = context.getSharedPreferences("db",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }
    public static boolean checkLogin(Context context){
        SharedPreferences sp = context.getSharedPreferences("db",Context.MODE_PRIVATE);
        return sp.getString(ARG_USER_ID, null) != null;
    }
    public static User loadLocalUser(Context context){
        SharedPreferences sp = context.getSharedPreferences("db",Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sp.getString(ARG_USER_NAME,null));
        user.setId(sp.getString(ARG_USER_ID,null));
        return user;
    }

    public static void setHomeworkStatus(Context context, HomeWork hw, int status){
        SharedPreferences sp = context.getSharedPreferences("hw",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(hw.getId() + "-"+ hw.getCourse().getId()+ "-status-", status);
        editor.commit();
    }
    public static int getHomeworkStatus(Context context, HomeWork hw){
        SharedPreferences sp = context.getSharedPreferences("hw",Context.MODE_PRIVATE);
        return sp.getInt(hw.getId() + "-"+ hw.getCourse().getId() + "-status-",STATUS_UNCOMPLETED);
    }
}
