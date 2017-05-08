package com.licong.graduationproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by licong on 5/3/17.
 *  用户信息帮助类
 */

public class UserDatabaseHelper extends SQLiteOpenHelper {
    //昵称nickname
    //UIDid;
    //性别gender;
    //个性签名signature
    public static final String CREATE_USERS="create table User("
            +"id integer primary key autoincrement,"
            +"nickname text,"
            +"gender text,"
            +"signature text)";
    private Context mContext;
    public UserDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
