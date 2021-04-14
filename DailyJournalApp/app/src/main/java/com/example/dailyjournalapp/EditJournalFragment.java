package com.example.dailyjournalapp;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import static java.text.DateFormat.getDateTimeInstance;

public class EditJournalFragment extends Fragment {

    private static final int TITLE = 0, TEXT = 1;
    private static EditText[] data;
    private static SeekBar moodBar;
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

        // loads in journal entry if we clicked on one
        String mSelectionClause = "_ID = ?";
        String[] mSelectionArgs = new String[] {Integer.toString(rowID)};
        Cursor mCursor = getActivity().getContentResolver().query(JournalContentProvider.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
        if (rowID != -1) {
            if (mCursor != null && mCursor.moveToFirst()) {

                String editTitleText = mCursor.getString(2);
                editTitle.setText(editTitleText);
                String editJournalText = mCursor.getString(3);
                editJournal.setText(editJournalText);
                int moodProgress = mCursor.getInt(4);
                moodBar.setProgress(moodProgress);

            }
        }

        // save or create new journal entry
        Button saveBtn = (Button) rootView.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getData(getActivity());

                if (rowID == -1) { // this is a new entry

                    ContentValues cv = getContentValues(getActivity());

                    JournalContract.addEntry(getContext(), cv);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, new JournalListFragment(), "journal_list_fragment")
                            .addToBackStack(null)
                            .commit();
                }
                else { // we are updating an entry
                    boolean success = save(getActivity());
                    if (success) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, new JournalListFragment(), "journal_list_fragment")
                                .addToBackStack(null)
                                .commit();
                    }
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
    }

    private static ContentValues getContentValues(Activity activity) {
        ContentValues values = new ContentValues();
        values.put(JournalContentProvider.COLUMN_ENTRYNUM, JournalContentProvider.getNumEntries()+1);
        values.put(JournalContentProvider.COLUMN_TITLE, data[TITLE].getText().toString());
        values.put(JournalContentProvider.COLUMN_TEXT, data[TEXT].getText().toString());
        values.put(JournalContentProvider.COLUMN_MOOD, moodBar.getProgress());
        return values;
    }

    public static boolean save(Activity activity) {
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
        if (JournalContract.entryExists(activity.getApplicationContext(), JournalContentProvider.getNumEntries() + 1)) {
            Toast.makeText(activity, activity.getString(R.string.entryAlreadyExistsToast), Toast.LENGTH_SHORT).show();
            error = true;
        }

        //Success Toast
        if (!error) {
            Toast.makeText(activity, activity.getString(R.string.saveSuccess), Toast.LENGTH_LONG).show();
            JournalContract.editEntry(activity.getApplicationContext(), getContentValues(activity));
            return true;
        }

        return false;
    }





}
