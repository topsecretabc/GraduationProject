package com.licong.graduationproject.activity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.licong.graduationproject.db.MyDatabaseHelper;

public class TextAppProvider extends ContentProvider {

    public  static final int TEXT_CONTENT = 0;

    public static  final String AUTHORITY = "com.licong.graduationproject.provider";

    private static UriMatcher uriMatcher;

    private MyDatabaseHelper dbHelper;

    static  {
        uriMatcher =new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"Textapp",TEXT_CONTENT);
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {

        return  null;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper = new MyDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //查询表中内容
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        Cursor cursor =null;
        switch (uriMatcher.match(uri)){
            case TEXT_CONTENT:
                cursor = db.query("Textapp",projection,selection,selectionArgs,
                        null,null,sortOrder);

                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
