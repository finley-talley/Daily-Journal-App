package com.example.dailyjournalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TimePicker;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);

        TimePicker picker=(TimePicker)findViewById(R.id.timePicker1);
        picker.setIs24HourView(false);
    }
}