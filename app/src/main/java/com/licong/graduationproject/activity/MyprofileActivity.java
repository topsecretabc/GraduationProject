package com.licong.graduationproject.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.licong.graduationproject.R;
import com.licong.graduationproject.db.UserDatabaseHelper;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by licong on 3/31/17.
 * 用户资料
 */

public class MyprofileActivity extends AppCompatActivity {

    private UserDatabaseHelper mUserDatabaseHelper;
    private TextView mNickname;//昵称
    private TextView UID;//id
    private TextView mGender;//性别
    private TextView mSignature;//个性签名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_layout);
        mNickname=(TextView)findViewById(R.id.nickname_textview);
//        UID=(TextView)findViewById(R.id.UID_textview);
        mGender=(TextView)findViewById(R.id.gender_setting);
        mSignature=(TextView)findViewById(R.id.signature_setting);
        mUserDatabaseHelper = new UserDatabaseHelper(this,"Users.db",null,1);
        SQLiteDatabase db = mUserDatabaseHelper.getReadableDatabase();
        //
        //查询User表中的所有数据
        Cursor cursor = db.query("User",null,null,null,null,null,null);
        //遍历User表中的所有数据，并放入Textview中
        if (cursor.moveToFirst()){
            do {
                String nickName=cursor.getString(cursor.getColumnIndex("nickname"));
//                String mUID=cursor.getString(cursor.getColumnIndex("id"));
                String gender=cursor.getString(cursor.getColumnIndex("gender"));
                String signature=cursor.getString(cursor.getColumnIndex("signature"));
                mNickname.setText(nickName);
//                UID.setText(mUID);
                mGender.setText(gender);
                mSignature.setText(signature);
            }while (cursor.moveToNext());

        }
        cursor.close();
        Button button_main = (Button)findViewById(R.id.profileback_button);
        //返回主界面
        button_main.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myprofile_main = new Intent(MyprofileActivity.this,MainActivity.class);
                startActivity(myprofile_main);
                finish();
            }
        });
        Button button_data = (Button)findViewById(R.id.to_modifydata);
        //进入修改界面
        button_data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myprofile_data = new Intent(MyprofileActivity.this,ModifyDataActivity.class);
                startActivity(myprofile_data);
                finish();
            }
        });

    }

























}