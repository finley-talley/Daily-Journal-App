package com.example.dailyjournalapp;

import android.content.Context;
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

import com.example.dailyjournalapp.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 */
public class JournalItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JournalItemFragment() {
    }

    // TODO: Customize parameter initialization
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
            recyclerView.setAdapter(new MyJournalItemRecyclerViewAdapter(DummyContent.ITEMS));
        }

        Log.i("MSG", "JournalItemFragment view launched");
        return view;
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