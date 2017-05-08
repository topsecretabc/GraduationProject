package com.licong.graduationproject.activity;
/*
 *修改资料
 */
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.licong.graduationproject.R;
import com.licong.graduationproject.db.UserDatabaseHelper;

public class ModifyDataActivity extends AppCompatActivity  implements View.OnClickListener {

    private UserDatabaseHelper mUserDatabaseHelper;
    private EditText mNickName;
    private EditText mGender;
    private EditText mSignature;
    //返回
    private Button mProfilebackdata_button;
    //确认
    private Button mProfile_data_yes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_data);
        //创建数据库的流程
        mUserDatabaseHelper = new UserDatabaseHelper(this,"Users.db",null,1);
        mNickName =(EditText)findViewById(R.id.nickname_edittext);
        mGender =(EditText)findViewById(R.id.gender_edittext);
        mSignature =(EditText)findViewById(R.id.signature_edittext);
        //点击事件按钮
        mProfile_data_yes =(Button)findViewById(R.id.profile_data_yes);
        mProfile_data_yes.setOnClickListener(this);
        mProfilebackdata_button =(Button)findViewById(R.id.profilebackdata_button);
        mProfilebackdata_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
            //返回资料界面
            case R.id.profilebackdata_button:
                Intent intent_backprofile = new Intent(ModifyDataActivity.this,MyprofileActivity.class);
                startActivity(intent_backprofile);
                finish();
             //确定然后返回资料界面
            case R.id.profile_data_yes:
                SQLiteDatabase db=mUserDatabaseHelper.getReadableDatabase();
                String inputText_mNickName=mNickName.getText().toString();
                String inputText_mGender =mGender.getText().toString();
                String inputText_mSignature =mSignature.getText().toString();
                //放入数据到数据库
                    ContentValues values = new ContentValues();
                    values.put("nickname", inputText_mNickName);
                    values.put("gender", inputText_mGender);
                    values.put("signature", inputText_mSignature);
                    db.insert("User", null, values);
                    Intent intent_yes = new Intent(ModifyDataActivity.this, MyprofileActivity.class);
                    startActivity(intent_yes);
                    finish();
        }
    }
}
