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
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ViewJournalFragment extends Fragment {
    private static int currentEntry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_view_journal, container, false);

        // Enable menu
        setHasOptionsMenu(true);

        currentEntry = (JournalContentProvider.getNumEntries() == 0) ? -1 : JournalContentProvider.getNumEntries()-1;
        startData(getActivity(), layout);

        return layout;
    }

    private static int findPrevEntry(Activity activity){
        if(currentEntry > 0){
            int i = currentEntry-1;
            while(i >= 0) {
                Cursor cursor = UserContract.queryEntry(activity, i);
                while (cursor.moveToNext()) {
                    int deleted = cursor.getInt(cursor.getColumnIndex(JournalContentProvider.COLUMN_DELETED));
                    if (deleted == 0) {
                        return i;
                    } else {
                        i--;
                    }
                }
            }
        }
        return -1;
    }

    private static int findNextEntry(Activity activity){
        if(currentEntry < JournalContentProvider.getNumEntries()-1){
            int i = currentEntry+1;
            while(i <= JournalContentProvider.getNumEntries()-1) {
                Cursor cursor = UserContract.queryEntry(activity, i);
                while (cursor.moveToNext()) {
                    int deleted = cursor.getInt(cursor.getColumnIndex(JournalContentProvider.COLUMN_DELETED));
                    if (deleted == 0) {
                        return i;
                    } else {
                        i++;
                    }
                }
            }
        }
        return -1;
    }

    @SuppressLint("ClickableViewAccessibility")
    private static void startData(Activity activity, View view){
        TextView title = view.findViewById(R.id.viewTitleText);
        TextView text = view.findViewById(R.id.viewJournalText);
        SeekBar moodBar = view.findViewById(R.id.moodBar);
        TextView date = view.findViewById(R.id.dateText);
        TextView time = view.findViewById(R.id.timeText);

        if(currentEntry == -1){
            title.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
            moodBar.setVisibility(View.GONE);
            date.setVisibility(View.GONE);
            time.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.titleLabel)).setText(R.string.journalEmpty);
            (view.findViewById(R.id.journalLabel)).setVisibility(View.GONE);
            (view.findViewById(R.id.moodLabel)).setVisibility(View.GONE);
            (view.findViewById(R.id.viewJournalText)).setVisibility(View.GONE);
            (view.findViewById(R.id.badLabel)).setVisibility(View.GONE);
            (view.findViewById(R.id.okayLabel)).setVisibility(View.GONE);
            (view.findViewById(R.id.greatLabel)).setVisibility(View.GONE);
            (view.findViewById(R.id.dateLabel)).setVisibility(View.GONE);
            (view.findViewById(R.id.timeLabel)).setVisibility(View.GONE);
            (view.findViewById(R.id.prevBtn)).setVisibility(View.GONE);
            (view.findViewById(R.id.nextBtn)).setVisibility(View.GONE);
        }else {
            if(findPrevEntry(activity) == -1){
                (view.findViewById(R.id.prevBtn)).setVisibility(View.INVISIBLE);
            }else{
                (view.findViewById(R.id.prevBtn)).setVisibility(View.VISIBLE);
            }
            if(findNextEntry(activity) == -1){
                (view.findViewById(R.id.nextBtn)).setVisibility(View.INVISIBLE);
            }else{
                (view.findViewById(R.id.nextBtn)).setVisibility(View.VISIBLE);
            }
            Cursor cursor = UserContract.queryEntry(activity, currentEntry);
            while (cursor.moveToNext()) {
                title.setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TITLE)));
                text.setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TEXT)));
                moodBar.setProgress(cursor.getInt(cursor.getColumnIndex(JournalContentProvider.COLUMN_MOOD)));
                moodBar.setOnTouchListener((view1, motionEvent) -> true);
                date.setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_DATE)));
                time.setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TIME)));
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private static void setData(Activity activity) {
        TextView title = activity.findViewById(R.id.viewTitleText);
        TextView text = activity.findViewById(R.id.viewJournalText);
        SeekBar moodBar = activity.findViewById(R.id.moodBar);
        TextView date = activity.findViewById(R.id.dateText);
        TextView time = activity.findViewById(R.id.timeText);

        if (currentEntry == -1) {
            title.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
            moodBar.setVisibility(View.GONE);
            date.setVisibility(View.GONE);
            time.setVisibility(View.GONE);
            ((TextView) activity.findViewById(R.id.titleLabel)).setText(R.string.journalEmpty);
            (activity.findViewById(R.id.journalLabel)).setVisibility(View.GONE);
            (activity.findViewById(R.id.moodLabel)).setVisibility(View.GONE);
            (activity.findViewById(R.id.viewJournalText)).setVisibility(View.GONE);
            (activity.findViewById(R.id.badLabel)).setVisibility(View.GONE);
            (activity.findViewById(R.id.okayLabel)).setVisibility(View.GONE);
            (activity.findViewById(R.id.greatLabel)).setVisibility(View.GONE);
            (activity.findViewById(R.id.dateLabel)).setVisibility(View.GONE);
            (activity.findViewById(R.id.timeLabel)).setVisibility(View.GONE);
            (activity.findViewById(R.id.prevBtn)).setVisibility(View.GONE);
            (activity.findViewById(R.id.nextBtn)).setVisibility(View.GONE);
        } else {
            if(findPrevEntry(activity) == -1){
                (activity.findViewById(R.id.prevBtn)).setVisibility(View.INVISIBLE);
            }else{
                (activity.findViewById(R.id.prevBtn)).setVisibility(View.VISIBLE);
            }
            if(findNextEntry(activity) == -1){
                (activity.findViewById(R.id.nextBtn)).setVisibility(View.INVISIBLE);
            }else{
                (activity.findViewById(R.id.nextBtn)).setVisibility(View.VISIBLE);
            }
            Cursor cursor = UserContract.queryEntry(activity, currentEntry);
            while (cursor.moveToNext()) {
                title.setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TITLE)));
                text.setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TEXT)));
                moodBar.setProgress(cursor.getInt(cursor.getColumnIndex(JournalContentProvider.COLUMN_MOOD)));
                moodBar.setOnTouchListener((view, motionEvent) -> true);
                date.setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_DATE)));
                time.setText(cursor.getString(cursor.getColumnIndex(JournalContentProvider.COLUMN_TIME)));
            }
        }
    }

    private static void delete(Activity activity){
        if(JournalContentProvider.getNumEntries() == 0){
            Toast.makeText(activity, activity.getString(R.string.dbEmptyToast), Toast.LENGTH_LONG).show();
            return;
        }

        UserContract.lazyDeleteEntry(activity, currentEntry);
        if(currentEntry > 0){
            int prev = findPrevEntry(activity);
            if(prev == -1){
                currentEntry = findNextEntry(activity);
            }else {
                currentEntry = prev;
            }
            setData(activity);
        }else if(currentEntry == 0){
            if(UserContract.entryExists(activity, currentEntry+1)){
                currentEntry = findNextEntry(activity);
            }else{
                currentEntry = -1;
            }
            setData(activity);
        }
    }

    public static void previous(Activity activity){
        if(currentEntry > 0){
            currentEntry = findPrevEntry(activity);
            setData(activity);
        }
    }

    public static void next(Activity activity){
        if(currentEntry < JournalContentProvider.getNumEntries()-1){
            currentEntry = findNextEntry(activity);
            setData(activity);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_new_entry:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new EditJournalFragment(), "edit_journal_fragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.opt_delete_entry:
                delete(getActivity());
                break;
            case R.id.opt_set_notif:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, new NotificationFragment(), "notification_fragment")
                        .addToBackStack(null)
                        .commit();
                break;
        }
        return true;
    }

}