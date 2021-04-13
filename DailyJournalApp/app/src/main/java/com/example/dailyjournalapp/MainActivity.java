package com.example.dailyjournalapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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