package com.ala.elearning;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.ala.elearning.API.WebApi;
import com.ala.elearning.activities.DashboardActivity;
import com.ala.elearning.controllers.SPController;

/**
 * Created by alaam on 1/7/2018.
 */

public class SplashActivity  extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SPController.checkLogin(this)){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}