package com.example.pranav.helloandroid;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

public class CustomerInfo extends Activity {
    EditText et_fname, et_lname, et_email, et_phone;
    String fname, lname, email, phone;
    private android.support.design.widget.BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.customerinfo);
        initialize();
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

    private void initialize() {
        initializeBottomNav();

        TextView tv = findViewById(R.id.topCategories);
        tv.setText(R.string.customerInfo);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        MenuItem item = bottomNav.getMenu().findItem(R.id.nav_next);
        item.setIcon(R.drawable.send);
        item.setTitle("Submit");

        et_fname = findViewById(R.id.firstName);
        et_lname = findViewById(R.id.lastName);
        et_email = findViewById(R.id.email);
        et_phone = findViewById(R.id.phoneNumber);

        loadSavedData();
    }

    private void saveData() {
        CustomerResponse.CustomerPersonalInfo = new CustomerInformation(fname, lname, email, phone);
    }

    private void loadSavedData() {
        if(CustomerResponse.CustomerPersonalInfo != null){
            et_email.setText(CustomerResponse.CustomerPersonalInfo.getEmail());
            et_fname.setText(CustomerResponse.CustomerPersonalInfo.getFirstName());
            et_lname.setText(CustomerResponse.CustomerPersonalInfo.getLastName());
            et_phone.setText(CustomerResponse.CustomerPersonalInfo.getPhoneNumber());
        }
    }

    public void prevClick(View v){
        this.finish();
    }

    public void nextClick(View v){
        getFieldValues();
        if(!validate()){
            return;
        }

        saveData();

        //when, Little more, address, name-email-phone
        Intent i = new Intent(this, WorkSummaryActivity.class);
        startActivity(i);
    }

    private boolean validate() {
        boolean result = true;
        if(fname.isEmpty()){
            et_fname.setError("Please enter valid first name!");
            result = false;
        }

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please enter valid email!");
            result = false;
        }

        if(phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()){
            et_phone.setError("Please enter valid phone number!");
            result = false;
        }
        return result;
    }

    private void getFieldValues() {
        fname = et_fname.getText().toString().trim();
        lname = et_lname.getText().toString().trim();
        email = et_email.getText().toString().trim();
        phone = et_phone.getText().toString().trim();
    }
}
