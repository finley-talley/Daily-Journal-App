package com.example.dailyjournalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up notif fragment
        // TODO : check shared prefs or content provider for if you've set notifs
        //  before to determine if you wanna open it
        if (getIntent().hasExtra("fromNotif")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new EditJournalFragment(), "edit_journal_fragment")
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayout, new JournalItemFragment(), "journal_item_fragment")
                .addToBackStack(null)
                .commit();
        }
    }
}