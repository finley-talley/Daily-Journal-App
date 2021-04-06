package com.example.dailyjournalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up notif fragment
        // TODO : check shared prefs or content provider for if you've set notifs
        //  before to determine if you wanna open it

        NotificationFragment notifFrag = new NotificationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(R.id.frameLayout, notifFrag, "notif_fragment");
        trans.addToBackStack(null);
        trans.commit();

        //for time picker eventually
        //TimePicker picker=(TimePicker)findViewById(R.id.timePicker1);
        //picker.setIs24HourView(false);

    }
}