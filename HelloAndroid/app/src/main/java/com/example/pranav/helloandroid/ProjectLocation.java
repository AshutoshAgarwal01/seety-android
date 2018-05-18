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

public class ProjectLocation extends Activity{
    EditText et_street, et_city, et_state, et_pin;
    String street, city, state, pin;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.projectlocation);
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
        tv.setText(R.string.projectAddress);

        et_street = findViewById(R.id.address_street);
        et_city = findViewById(R.id.address_city);
        et_state = findViewById(R.id.address_state);
        et_pin = findViewById(R.id.address_pin);

        loadSavedData();
    }


    private void saveData() {
        CustomerResponse.Address = new ProjectLocationInformation(street, city, state, pin);
    }

    private void loadSavedData() {
        if(CustomerResponse.Address != null){
            et_city.setText(CustomerResponse.Address.getCity());
            et_pin.setText(CustomerResponse.Address.getPin());
            et_state.setText(CustomerResponse.Address.getState());
            et_street.setText(CustomerResponse.Address.getStreet());
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
        Intent i = new Intent(this, CustomerInfo.class);
        startActivity(i);
    }

    private boolean validate() {
        boolean result = true;
        if(street.isEmpty()){
            et_street.setError("Please enter valid street!");
            result = false;
        }
        if(city.isEmpty()){
            et_city.setError("Please enter valid city!");
            result = false;
        }
        if(state.isEmpty()){
            et_state.setError("Please enter valid state!");
            result = false;
        }
        if(pin.isEmpty() || pin.length() != 6){
            et_pin.setError("Please enter valid pin!");
            result = false;
        }
        return result;
    }

    private void getFieldValues() {
        street = et_street.getText().toString().trim();
        city = et_city.getText().toString().trim();
        state = et_state.getText().toString().trim();
        pin = et_pin.getText().toString().trim();
    }
}
