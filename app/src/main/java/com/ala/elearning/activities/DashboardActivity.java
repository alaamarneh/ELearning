package com.ala.elearning.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ala.elearning.MainActivity;
import com.ala.elearning.R;
import com.ala.elearning.controllers.SPController;
import com.ala.elearning.fragments.CoursesListFragment;
import com.ala.elearning.fragments.DashboardFragment;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.content, CoursesListFragment.newInstance(1,CoursesListFragment.MODE_NORMAL)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_msg, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_open_msg:
                SPController.logout(this);
                Intent i = new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
