package com.example.dailyjournalapp;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;

public class ViewJournalFragment extends Fragment {
    private static final int TITLE = 0, TEXT = 1;
    private static EditText[] data;
    private static SeekBar moodBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_journal, container, false);
    }

    private static void getData(Activity activity){
        data = new EditText[2];
        data[TITLE] = (EditText) activity.findViewById(R.id.editTitleText);
        data[TEXT] = (EditText) activity.findViewById(R.id.editJournalText);
        moodBar = (SeekBar) activity.findViewById(R.id.moodBar);
    }

    public static void display(Activity activity){

    }

}