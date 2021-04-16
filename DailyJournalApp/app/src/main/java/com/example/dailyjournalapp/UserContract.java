package com.example.dailyjournalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class UserContract {

    public static void addEntry(Context context, ContentValues values){
        context.getContentResolver().insert(JournalContentProvider.CONTENT_URI, values);
    }

    public static Cursor queryEntry(Context context, final int entryNum){
        return context.getContentResolver().query(
                JournalContentProvider.CONTENT_URI, null, JournalContentProvider.COLUMN_ENTRYNUM + " = ?", new String[] {""+entryNum}, null);
    }

    public static void lazyDeleteEntry(Context context, int entryNum) {
        ContentValues cv = new ContentValues();
        cv.put(JournalContentProvider.COLUMN_DELETED, 1);
        context.getContentResolver().update(JournalContentProvider.CONTENT_URI, cv, JournalContentProvider.COLUMN_ENTRYNUM + " = ?", new String[]{""+entryNum});
    }

    public static void removeEntry(Context context, int entryNum) {
        Cursor cursor = queryEntry(context, entryNum);
        if (cursor.getCount() != 0) {
            context.getContentResolver().delete(JournalContentProvider.CONTENT_URI, JournalContentProvider.COLUMN_ENTRYNUM + " = ?", new String[]{""+entryNum});
        }
        cursor.close();
    }

    public static boolean entryExists(Context context, int entryNum){
        Cursor cursor = queryEntry(context, entryNum);
        return cursor.getCount() != 0;
    }

}
