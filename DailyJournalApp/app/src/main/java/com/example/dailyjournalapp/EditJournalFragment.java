package com.example.dailyjournalapp;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditJournalFragment extends Fragment {
    private static final int TITLE = 0, TEXT = 1;
    private static EditText[] data;
    private static SeekBar moodBar;
    private static DatePicker datePicker;
    private static TimePicker timePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_journal, container, false);
    }

    private static void getData(Activity activity){
        data = new EditText[2];
        data[TITLE] = activity.findViewById(R.id.editTitleText);
        data[TEXT] = activity.findViewById(R.id.editJournalText);
        moodBar = activity.findViewById(R.id.moodBar);
        datePicker = activity.findViewById(R.id.datePicker);
        timePicker = activity.findViewById(R.id.timePicker);
    }

    private static ContentValues getContentValues(Activity activity) {
        ContentValues values = new ContentValues();
        values.put(JournalContentProvider.COLUMN_ENTRYNUM, JournalContentProvider.getNumEntries());
        values.put(JournalContentProvider.COLUMN_TITLE, data[TITLE].getText().toString());
        values.put(JournalContentProvider.COLUMN_TEXT, data[TEXT].getText().toString());
        values.put(JournalContentProvider.COLUMN_MOOD, moodBar.getProgress());
        values.put(JournalContentProvider.COLUMN_DELETED, 0);
        values.put(JournalContentProvider.COLUMN_DATE, getTheDate(datePicker));
        values.put(JournalContentProvider.COLUMN_TIME, getTheTime(timePicker));
        return values;
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

    public static boolean save(Activity activity){
        getData(activity);

        boolean error = false;

        //Check empty
        for(EditText e : data){
            e.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material);
            if(e.getText().toString().trim().isEmpty()){
                e.setBackgroundColor(Color.argb(50,255,0,0));
                Toast.makeText(activity, activity.getString(R.string.emptyToast), Toast.LENGTH_SHORT).show();
                error = true;
            }
        }

        //Success Toast
        if(!error){
            Toast.makeText(activity, activity.getString(R.string.saveSuccess), Toast.LENGTH_LONG).show();
            UserContract.addEntry(activity.getApplicationContext(), getContentValues(activity));
            return true;
        }

        return false;
    }

}