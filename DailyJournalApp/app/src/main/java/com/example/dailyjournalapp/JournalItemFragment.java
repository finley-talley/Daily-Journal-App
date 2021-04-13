package com.example.dailyjournalapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 */
public class JournalItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private Cursor mCursor;
    private List<Map<Integer, String>> mItems = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JournalItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static JournalItemFragment newInstance(int columnCount) {
        JournalItemFragment fragment = new JournalItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Enable menu
        setHasOptionsMenu(true);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            mCursor = getActivity().getContentResolver()
                                   .query(JournalContentProvider.CONTENT_URI, null, null, null, null);


            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    Map<Integer, String> hm = new HashMap<>();
                    hm.put(mCursor.getInt(0), mCursor.getString(1));
                    mItems.add(hm);
                    Log.i("MSG", "Adding " + mCursor.getString(1));
                } while (mCursor.moveToNext());
                recyclerView.setAdapter(new MyJournalItemRecyclerViewAdapter(mItems,
                        item -> Toast.makeText(getContext(), item.get(1), Toast.LENGTH_LONG).show()));
            }



        }

        Log.i("MSG", "JournalItemFragment view launched");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_set_notif:
                NotificationFragment notifFrag = new NotificationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, notifFrag, "notification_fragment")
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.opt_new_entry:
                EditJournalFragment editFrag = new EditJournalFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, editFrag, "edit_journal_fragment")
                        .addToBackStack(null)
                        .commit();
                break;

        }
        return true;
    }
}