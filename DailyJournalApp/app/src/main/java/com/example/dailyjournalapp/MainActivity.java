package com.example.dailyjournalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public Cursor mCursor;

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
                .add(R.id.frameLayout, new JournalListFragment(), "journal_list_fragment")
                .addToBackStack(null)
                .commit();
        }
    }
}
