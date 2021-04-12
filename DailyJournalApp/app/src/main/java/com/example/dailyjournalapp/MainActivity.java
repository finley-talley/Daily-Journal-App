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

    public static Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up notif fragment
        // TODO : check shared prefs or content provider for if you've set notifs
        //  before to determine if you wanna open it

        ContentValues values = new ContentValues();

        String jrnlTxt = "This is my first journal entry! " +
                "I'm really excited to start using this app.";

        values.put(JournalContentProvider.COLUMN_TITLE, "My First Journal Entry");
        values.put(JournalContentProvider.COLUMN_TEXT, jrnlTxt);
        values.put(JournalContentProvider.COLUMN_MOOD, 10);

        getContentResolver().insert(JournalContentProvider.CONTENT_URI, values);

        ContentValues[] values2 = new ContentValues[5];
        for (int i=0; i<5; i++) {
            ContentValues cv = new ContentValues();
            cv.put(JournalContentProvider.COLUMN_TITLE, "Title " + (i+1));
            cv.put(JournalContentProvider.COLUMN_TEXT, "Test text " + (i+1));
            cv.put(JournalContentProvider.COLUMN_MOOD, (i+1));
            values2[i] = cv;
        }

        getContentResolver().bulkInsert(JournalContentProvider.CONTENT_URI, values2);

        NotificationFragment notifFrag = new NotificationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(R.id.frameLayout, notifFrag, "notif_fragment");
        trans.addToBackStack(null);
        trans.commit();


    }
}