package com.ala.elearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.ala.elearning.Beans.User;
import com.ala.elearning.activities.CoursesListActivity;
import com.ala.elearning.activities.DashboardActivity;
import com.ala.elearning.controllers.SPController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);
    }

    public void onClick(View view) {


        EditText txtId = findViewById(R.id.txtId);
        EditText txtPassword = findViewById(R.id.txtPassword);

        if(TextUtils.isEmpty(txtId.getText())){
            txtId.setError("Enter your id");
            txtId.requestFocus();
        }
        if(TextUtils.isEmpty(txtPassword.getText())){
            txtPassword.setError("Enter your password");
            txtPassword.requestFocus();
        }

        if(TextUtils.isEmpty(txtPassword.getText()) || TextUtils.isEmpty(txtId.getText()))
            return;
        String id = txtId.getText().toString();
        String password = txtPassword.getText().toString();

        // save user
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        SPController.saveLocalUser(this,user);

        Intent i = new Intent(this,DashboardActivity.class);
        startActivity(i);
        finish();

    }
}
