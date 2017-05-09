package com.licong.graduationproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.licong.graduationproject.R;

public class LoginActivity extends AppCompatActivity {
    private ImageView mImageView;

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    private ImageView mLeftLogo;

    private ImageView mRightLogo;

    private EditText accountEdit;

    private EditText passwordEdit;

    private Button registered;

    private Button login;

    private CheckBox remeberpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        //通过PreferenceManager的静态方法getDefaultSharedPreferences()
        //获取SharedPreferences对象
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        Button button=(Button)findViewById(R.id.login_back) ;
        mLeftLogo=(ImageView)findViewById(R.id.iv_icon_left);
        mRightLogo=(ImageView)findViewById(R.id.iv_icon_right);
        accountEdit =(EditText) findViewById(R.id.et_username);
        passwordEdit =(EditText) findViewById(R.id.et_password);
        remeberpassword =(CheckBox)findViewById(R.id.login_remember);
//        registered =(Button)findViewById(R.id.button_registered);
        login=(Button)findViewById(R.id.button_login);
        //记住密码的操作
        boolean isRemember =preferences.getBoolean("记住密码",false);
        if(isRemember){
            String account = preferences.getString("您的用户名","");
            String password =preferences.getString("请输入密码","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            remeberpassword.setChecked(true);
        }
        //返回主界面操作
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_main=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent_main);
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String accountt = accountEdit.getText().toString();
                String passwordd = passwordEdit.getText().toString();
                //判断密码是否正确
                Log.e("lciong","aaaa"+accountt.equals(R.string.admin)+""+passwordd.equals(R.string.password));
                if (accountt.equals(getString(R.string.admin)) && passwordd.equals(getString(R.string.password))) {
                    editor = preferences.edit();
                    if (remeberpassword.isChecked()) {
                        //判断是否记住密码
                        editor.putBoolean("记住密码", true);
                        editor.putString("您的用户名", accountt);
                        editor.putString("请输入密码", passwordd);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    //按了登录通过就回到主界面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //密码或用户名不是admin和123456就报错
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        accountEdit.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    mLeftLogo.setImageResource(R.drawable.ic_22);
                    mRightLogo.setImageResource(R.drawable.ic_33);
                } else {
                    mLeftLogo.setImageResource(R.drawable.ic_22_hide);
                    mRightLogo.setImageResource(R.drawable.ic_33_hide);
                }
            }

        });
    }
}



























