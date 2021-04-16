package com.example.dailyjournalapp;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;

public class EditJournalFragment extends Fragment {

    private static final int TITLE = 0, TEXT = 1;
    private static EditText[] data;
    private static SeekBar moodBar;
    private static DatePicker datePicker;
    private static TimePicker timePicker;
    private static final String ARG_ROWID = "rowID";
    private static int rowID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rowID = getArguments().getInt(ARG_ROWID);
            Log.i("MSG", "Got row ID: " + rowID);
        }
        else {
            rowID = -1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_journal, container, false);

        EditText editTitle = (EditText) rootView.findViewById(R.id.editTitleText);
        EditText editJournal = (EditText) rootView.findViewById(R.id.editJournalText);
        SeekBar moodBar = (SeekBar) rootView.findViewById(R.id.moodBar);
        TimePicker tp = (TimePicker) rootView.findViewById(R.id.timePicker);
        DatePicker dp = (DatePicker) rootView.findViewById(R.id.datePicker);

        // loads in journal entry if we clicked on one
        String mSelectionClause = "_ID = ?";
        String[] mSelectionArgs = new String[] {Integer.toString(rowID)};
        Cursor mCursor = getActivity().getContentResolver().query(JournalContentProvider.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
        if (rowID != -1) {
            if (mCursor != null && mCursor.moveToFirst()) {

                String editTitleText = mCursor.getString(mCursor.getColumnIndex(JournalContentProvider.COLUMN_TITLE));
                editTitle.setText(editTitleText);
                String editJournalText = mCursor.getString(mCursor.getColumnIndex(JournalContentProvider.COLUMN_TEXT));
                editJournal.setText(editJournalText);
                int moodProgress = mCursor.getInt(mCursor.getColumnIndex(JournalContentProvider.COLUMN_MOOD));
                moodBar.setProgress(moodProgress);
                setTheDate(dp, mCursor.getString(mCursor.getColumnIndex(JournalContentProvider.COLUMN_DATE)));
                setTheTime(tp, mCursor.getString(mCursor.getColumnIndex(JournalContentProvider.COLUMN_TIME)));
                Log.i("MSG", "PRIMARY KEY " + mCursor.getInt(0) + " / ENTRYNUM " + mCursor.getInt(1));


            }
        }

        // save or create new journal entry
        Button saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rowID == -1) { // this is a new entry

                    boolean success = save(getActivity(), 1);
                    if (success) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, new JournalListFragment(), "journal_list_fragment")
                                .addToBackStack(null)
                                .commit();
                    }
                }
                else { // we are updating an entry
                    boolean success = save(getActivity(), 2);
                    if (success) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, new JournalListFragment(), "journal_list_fragment")
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }
        });

        Button deleteBtn = (Button) rootView.findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = delete(getActivity());
                if (success) {
                    Toast.makeText(getActivity(), "Journal entry deleted", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new JournalListFragment(), "journal_list_fragment")
                            .addToBackStack(null)
                            .commit();
                }
                else {
                    Toast.makeText(getActivity(), "New journal entry was not created", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new JournalListFragment(), "journal_list_fragment")
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return rootView;

    }


    private static void getData(Activity activity){
        data = new EditText[2];
        data[TITLE] = (EditText) activity.findViewById(R.id.editTitleText);
        data[TEXT] = (EditText) activity.findViewById(R.id.editJournalText);
        moodBar = (SeekBar) activity.findViewById(R.id.moodBar);
        datePicker = activity.findViewById(R.id.datePicker);
        timePicker = activity.findViewById(R.id.timePicker);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static ContentValues getContentValues(Activity activity, int delete) {
        ContentValues values = new ContentValues();
        values.put(JournalContentProvider.COLUMN_ENTRYNUM, JournalContentProvider.getNumEntries()+1);
        values.put(JournalContentProvider.COLUMN_TITLE, data[TITLE].getText().toString());
        values.put(JournalContentProvider.COLUMN_TEXT, data[TEXT].getText().toString());
        values.put(JournalContentProvider.COLUMN_MOOD, moodBar.getProgress());
        values.put(JournalContentProvider.COLUMN_DELETED, delete);
        values.put(JournalContentProvider.COLUMN_DATE, getTheDate(datePicker));
        values.put(JournalContentProvider.COLUMN_TIME, getTheTime(timePicker));
        return values;
    }

    public static void setTheDate(DatePicker dp, String date) {
        String month = Character.toString(date.charAt(0)) + Character.toString(date.charAt(1));
        String day = Character.toString(date.charAt(3)) + Character.toString(date.charAt(4));
        String year = Character.toString(date.charAt(6)) + Character.toString(date.charAt(7))
                + Character.toString(date.charAt(8)) + Character.toString(date.charAt(9));

        dp.updateDate(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));

    }

    public static void setTheTime(TimePicker tp, String time) {
        String hour, minute;
        boolean eve = false;
        if (time.charAt(6) == 'P') {
            eve = true;
        }

        hour = Character.toString(time.charAt(0)) + Character.toString(time.charAt(1));
        minute = Character.toString(time.charAt(3)) + Character.toString(time.charAt(4));
        if (eve) {
            tp.setHour(Integer.parseInt(hour)+12);
        }
        else {
            tp.setHour(Integer.parseInt(hour));
        }

        tp.setMinute(Integer.parseInt(minute));

    }

    private static String getTheDate(DatePicker datePicker){
        String date = "";
        int month = datePicker.getMonth()+1;
        int day = datePicker.getDayOfMonth();

        if(month < 10){
            date += "0";
        }
        date += month + "/";

        if(day < 10){
            date += "0";
        }
        date += day + "/" + datePicker.getYear();

        return date;
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    private static String getTheTime(TimePicker timePicker){
        String time = "";
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String AM_PM;

        if(hour > 12){
            hour -= 12;
            AM_PM = "PM";
        }else{
            AM_PM = "AM";
        }

        if(hour == 0){
            hour = 12;
        }

        if(hour < 10){
            time += "0";
        }
        time += hour + ":";

        if(minute < 10){
            time += "0";
        }
        time += minute + " " + AM_PM;

        return time;
    }

    public static boolean save(Activity activity, int type) {
        getData(activity);

        boolean error = false;

        //Check empty
        for (EditText e : data) {
            e.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material);
            if (e.getText().toString().trim().isEmpty()) {
                e.setBackgroundColor(Color.argb(50, 255, 0, 0));
                Toast.makeText(activity, activity.getString(R.string.emptyToast), Toast.LENGTH_SHORT).show();
                error = true;
            }
        }

        //Check ID against database
        if (type == 1 && JournalContract.entryExists(activity.getApplicationContext(), JournalContentProvider.getNumEntries() + 1)) {
            Toast.makeText(activity, activity.getString(R.string.entryAlreadyExistsToast), Toast.LENGTH_SHORT).show();
            error = true;
        }

        //Success Toast
        if (!error) {
            if (type == 1) { // new entry
                getData(activity);
                ContentValues cv = getContentValues(activity, 0);
                JournalContract.addEntry(activity.getApplicationContext(), cv);
                Toast.makeText(activity, activity.getString(R.string.saveSuccess), Toast.LENGTH_LONG).show();
            }
            else { // update entry
                JournalContract.editEntry(activity.getApplicationContext(), getContentValues(activity, 0), rowID);
                Toast.makeText(activity, activity.getString(R.string.saveSuccess), Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return false;
    }

    public static boolean delete(Activity activity) {
    if (rowID != -1) {
        getData(activity);
        JournalContract.deleteEntry(activity.getApplicationContext(), getContentValues(activity, 1), rowID);
        return true;
    }
    else {
        return false;
    }

    }



}
