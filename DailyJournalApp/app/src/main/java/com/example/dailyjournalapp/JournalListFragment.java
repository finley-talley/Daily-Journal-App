package com.example.dailyjournalapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JournalListFragment extends Fragment {

    public JournalListFragment () {}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        Cursor mCursor = getActivity().getContentResolver()
                .query(JournalContentProvider.CONTENT_URI, null, null, null, null);

        List<Map<String, String>> list = new ArrayList<>();

        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                Map<String, String> hm = new HashMap<>();
                String title = mCursor.getString(mCursor.getColumnIndex(JournalContentProvider.COLUMN_TITLE));
                hm.put(JournalContentProvider.COLUMN_TITLE, title);
                list.add(hm);
            }
        }

        String[] from = new String[] {JournalContentProvider.COLUMN_TITLE};
        int[] to = new int[] {android.R.id.text1};

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list, android.R.layout.simple_list_item_1, from, to);

        ListView lv = (ListView) rootView.findViewById(R.id.list);
        lv.setAdapter(adapter);

        return rootView;
    }

}
