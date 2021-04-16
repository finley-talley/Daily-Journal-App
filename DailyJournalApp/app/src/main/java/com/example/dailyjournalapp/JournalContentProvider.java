package com.example.dailyjournalapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class JournalContentProvider extends ContentProvider {
    public final static String DBNAME = "JournalDB";
    public final static String TABLE_NAME = "journaltable";
    public final static String COLUMN_ENTRYNUM = "entryNum";
    public final static String COLUMN_TITLE = "title";
    public final static String COLUMN_TEXT = "text";
    public final static String COLUMN_MOOD = "mood";
    public final static String COLUMN_DATE = "date";
    public final static String COLUMN_TIME = "time";
    public final static String COLUMN_DELETED = "isDeleted";

    private static int numEntries;


    public static final String AUTHORITY = "com.example.dailyjournalapp.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    private static final String SQL_CREATE_MAIN = "CREATE TABLE " +
            TABLE_NAME +  // Table's name
            "( " +               // The columns in the table
            "_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_ENTRYNUM + " INTEGER, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_TEXT + " TEXT, " +
            COLUMN_MOOD + " INTEGER, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_TIME + " TEXT, " +
            COLUMN_DELETED + " INTEGER )";


    protected static final class MainDatabaseHelper extends SQLiteOpenHelper {
        MainDatabaseHelper(Context context) {
            super(context, DBNAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_MAIN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
//            db.execSQL("drop table " + TABLE_NAME);   //ONLY USED FOR CLEARING DATABASE
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    private MainDatabaseHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        //getContext().deleteDatabase(DBNAME);  //ONLY USED FOR CLEARING DATABASE
        mOpenHelper = new MainDatabaseHelper(getContext());
        numEntries = (int) DatabaseUtils.queryNumEntries(mOpenHelper.getReadableDatabase(), TABLE_NAME, null);
        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    public static int getNumEntries(){
        return numEntries;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String title = values.getAsString(COLUMN_TITLE).trim();
        String text = values.getAsString(COLUMN_TEXT).trim();

        if (title.isEmpty() || text.isEmpty()) {
            return null;
        }

        long id = mOpenHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
        numEntries = (int) DatabaseUtils.queryNumEntries(mOpenHelper.getReadableDatabase(), TABLE_NAME, null);
        Log.i("MSG", "Number of Entries: " + numEntries + " [JournalContentProvider]");
        return Uri.withAppendedPath(CONTENT_URI, "" + id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(TABLE_NAME, projection,
                selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return mOpenHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);
    }
}
