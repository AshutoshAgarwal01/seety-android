package com.example.pranav.helloandroid;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProjectTime extends AppCompatActivity {
    int selectedYear, selectedMonth, selectedDay;
    int selectedHour, selectedMinute, amPm;
    private BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.projecttime);
        initializeBottomNav();
        initialize();
    }

    private void initialize() {
        TextView tv = findViewById(R.id.topCategories);
        tv.setText(R.string.projectTime);

        if(CustomerResponse.ProjectDate == null)
            showAndSaveSelectedDate(Calendar.getInstance());
        else
            showAndSaveSelectedDate(CustomerResponse.ProjectDate);

        if(CustomerResponse.ProjectTime == null)
            showAndSaveSelectedTime(Calendar.getInstance());
        else
            showAndSaveSelectedTime(CustomerResponse.ProjectTime);
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
    private void showAndSaveSelectedTime(Calendar calendar) {
        CustomerResponse.ProjectTime = calendar;

        selectedHour = calendar.get(Calendar.HOUR);
        selectedMinute = calendar.get(Calendar.MINUTE);
        amPm = calendar.get(Calendar.AM_PM);
        SimpleDateFormat postFormatter = new SimpleDateFormat("h:mm a");
        String timeStr = postFormatter.format(calendar.getTime());

        TextView timeText = findViewById(R.id.displayTime);
        timeText.setText(timeStr);
    }

    private void showAndSaveSelectedDate(Calendar calendar) {
        CustomerResponse.ProjectDate = calendar;

        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat postFormatter = new SimpleDateFormat("EEE, MMM d, yyyy");
        String newDateStr = postFormatter.format(calendar.getTime());

        TextView dateText = findViewById(R.id.displayDate);
        dateText.setText(newDateStr);
    }

    public void prevClick(View v){
        this.finish();
    }

    public void nextClick(View v){
        //when, Little more, address, name-email-phone
        Intent i = new Intent(this, MoreProjectInfo.class);
        startActivity(i);
    }

    public void showDatePickerDialog(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(ProjectTime.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth, 0, 0);
                showAndSaveSelectedDate(c);
            }
        },selectedYear, selectedMonth, selectedDay);
        datePickerDialog.show();
    }

    public void showTimePickerDialog(View v){
        int tempHour = amPm == Calendar.AM ? selectedHour : selectedHour + 12;
        TimePickerDialog timePickerDialog = new TimePickerDialog(ProjectTime.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c = Calendar.getInstance();
                c.set(0, 0, 0, hourOfDay, minute);
                showAndSaveSelectedTime(c);
            }
        }, tempHour, selectedMinute, false);
        timePickerDialog.show();
    }
}
