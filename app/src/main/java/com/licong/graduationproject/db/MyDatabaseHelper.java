package com.licong.graduationproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by licong on 4/27/17.
 *  另一个app的帮助类
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    //创建TextApp的数据库
    //autoincrement表示id自增的
        public static final String CREATE_TEXTAPP = "create table Textapp("
                + "id integer primary key autoincrement,"
                + "textapptext text)";



   /* public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }*/
    public MyDatabaseHelper(Context context){
        super(context,"Textapp",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TEXTAPP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
