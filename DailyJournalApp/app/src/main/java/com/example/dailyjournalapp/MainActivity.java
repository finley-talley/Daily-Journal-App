package com.example.dailyjournalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("fromNotif")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new EditJournalFragment(), "edit_journal_fragment")
                    .addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayout, new ViewJournalFragment(), "view_journal_fragment")
                    .addToBackStack(null)
                    .commit();
        }

    }

    public void previous(View view){
        ViewJournalFragment.previous(this);
    }

    public void next(View view){
        ViewJournalFragment.next(this);
    }

    public void delete(View view){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, new ViewJournalFragment(), "view_journal_fragment")
                .addToBackStack(null)
                .commit();
    }

    public void save(View view){
        if(EditJournalFragment.save(this)){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new ViewJournalFragment(), "view_journal_fragment")
                    .addToBackStack(null)
                    .commit();
        }
    }

}