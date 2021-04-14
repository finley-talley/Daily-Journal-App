package com.example.dailyjournalapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class ViewJournalFragment extends Fragment {
    private static int currentEntry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_view_journal, container, false);

        // Enable menu
        setHasOptionsMenu(true);

        currentEntry = JournalContentProvider.getNumEntries();
        startData(getActivity(), layout, currentEntry);

        return layout;
    }

    @SuppressLint("ClickableViewAccessibility")
    private static void startData(Activity activity, View view, int entryNum){
        Cursor cursor = UserContract.queryEntry(activity, entryNum);
        while(cursor.moveToNext()) {
            ((TextView) view.findViewById(R.id.viewTitleText)).setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TITLE)));
            ((TextView) view.findViewById(R.id.viewJournalText)).setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TEXT)));
            ((SeekBar) view.findViewById(R.id.moodBar)).setProgress(cursor.getInt(cursor.getColumnIndex(JournalContentProvider.COLUMN_MOOD)));
            ((SeekBar) view.findViewById(R.id.moodBar)).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private static void setData(Activity activity, int entryNum){
        Cursor cursor = UserContract.queryEntry(activity, entryNum);
        while(cursor.moveToNext()) {
            ((TextView) activity.findViewById(R.id.viewTitleText)).setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TITLE)));
            ((TextView) activity.findViewById(R.id.viewJournalText)).setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TEXT)));
            ((SeekBar) activity.findViewById(R.id.moodBar)).setProgress(cursor.getInt(cursor.getColumnIndex(JournalContentProvider.COLUMN_MOOD)));
            ((SeekBar) activity.findViewById(R.id.moodBar)).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
    }

    public static void previous(Activity activity){
        if(currentEntry > 1){
            setData(activity, --currentEntry);
        }
        if(currentEntry == 1){
            activity.findViewById(R.id.prevBtn).setVisibility(View.INVISIBLE);
        }else if(currentEntry == JournalContentProvider.getNumEntries()-1){
            activity.findViewById(R.id.nextBtn).setVisibility(View.VISIBLE);
        }
    }

    public static void next(Activity activity){
        if(currentEntry < JournalContentProvider.getNumEntries()+2){
            setData(activity, ++currentEntry);
        }
        if(currentEntry == JournalContentProvider.getNumEntries()){
            activity.findViewById(R.id.nextBtn).setVisibility(View.INVISIBLE);
        }else if(currentEntry == 2){
            activity.findViewById(R.id.prevBtn).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_set_notif:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new NotificationFragment(), "notification_fragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.opt_new_entry:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new EditJournalFragment(), "edit_journal_fragment")
                        .addToBackStack(null)
                        .commit();
                break;

        }
        return true;
    }

}