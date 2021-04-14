package com.example.dailyjournalapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditJournalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditJournalFragment extends Fragment {


    private static final String ARG_ROWID = "rowID";

    // TODO: Rename and change types of parameters
    private int rowID = -1;

    public EditJournalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditJournalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditJournalFragment newInstance(String param1, String param2) {
        EditJournalFragment fragment = new EditJournalFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            rowID = getArguments().getInt(ARG_ROWID);
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

        String mSelectionClause = "_ID = ?";
        String[] mSelectionArgs = new String[] {Integer.toString(rowID)};

        Cursor mCursor = getActivity().getContentResolver().query(JournalContentProvider.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);

        if (rowID != -1) {
            if (mCursor != null && mCursor.moveToFirst()) {

                String editTitleText = mCursor.getString(1);
                editTitle.setText(editTitleText);
                String editJournalText = mCursor.getString(2);
                editJournal.setText(editJournalText);
                int moodProgress = mCursor.getInt(3);
                moodBar.setProgress(moodProgress);

            }

        }

        return rootView;
    }
}