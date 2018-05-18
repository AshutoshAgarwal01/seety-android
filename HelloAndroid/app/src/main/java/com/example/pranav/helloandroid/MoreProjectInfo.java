package com.example.pranav.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MoreProjectInfo extends Activity {
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.moreprojectinfo);
        initializeBottomNav();
        initialize();
    }

    private void initialize() {
        TextView tv = findViewById(R.id.topCategories);
        tv.setText(R.string.moreProjectInfo);

        //Load previously entered information.
        if(CustomerResponse.MoreProjectInfo != null)
        {
            EditText et = findViewById(R.id.moreProjInfoId);
            et.setText(CustomerResponse.MoreProjectInfo);
        }
    }

    private void initializeBottomNav(){
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_back:
                        prevClick(bottomNav);
                        return true;
                    case R.id.nav_home:
                        return true;
                    case  R.id.nav_next:
                        nextClick(bottomNav);
                        return true;
                }
                return false;
            }
        });
    }

    public void prevClick(View v){
        this.finish();
    }

    public void nextClick(View v){
        EditText et = findViewById(R.id.moreProjInfoId);
        CustomerResponse.MoreProjectInfo = et.getText().toString();

        //when, Little more, address, name-email-phone
        Intent i = new Intent(this, ProjectLocation.class);
        startActivity(i);
    }
}
