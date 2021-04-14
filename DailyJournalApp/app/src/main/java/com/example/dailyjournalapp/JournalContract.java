package com.example.dailyjournalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class JournalContract {

    public static void addEntry(Context context, ContentValues values){
        context.getContentResolver().insert(JournalContentProvider.CONTENT_URI, values);
    }

    public static void editEntry(Context context, ContentValues values){
        context.getContentResolver().update(JournalContentProvider.CONTENT_URI, values, null, null);
    }


    public static Cursor queryEntry(Context context, final int entryNum){
        return context.getContentResolver().query(
                JournalContentProvider.CONTENT_URI, null, JournalContentProvider.COLUMN_ENTRYNUM + " = ?", new String[] {""+entryNum}, null);
    }

    public static boolean entryExists(Context context, int entryNum){
        Cursor cursor = queryEntry(context, entryNum);
        return cursor.getCount() != 0;
    }

}
