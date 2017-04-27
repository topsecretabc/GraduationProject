package com.licong.graduationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.licong.graduationproject.adapter.MainInterface;
import com.licong.graduationproject.adapter.MainInterfaceAdapter;

import java.io.BufferedWriter;

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
        mLeftLogo=(ImageView)findViewById(R.id.iv_icon_left);
        mRightLogo=(ImageView)findViewById(R.id.iv_icon_right);
        accountEdit =(EditText) findViewById(R.id.et_username);
        passwordEdit =(EditText) findViewById(R.id.et_password);
        remeberpassword =(CheckBox)findViewById(R.id.login_remember);
        registered =(Button)findViewById(R.id.button_registered);
        login=(Button)findViewById(R.id.button_login);
        boolean isRemember =preferences.getBoolean("记住密码",false);
        if(isRemember){
            String account = preferences.getString("您的用户名","");
            String password =preferences.getString("请输入密码","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            remeberpassword.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (account.equals("admin") && password.equals("123456")) {
                    editor = preferences.edit();
                    if (remeberpassword.isChecked()) {
                        editor.putBoolean("记住密码", true);
                        editor.putString("您的用户名", account);
                        editor.putString("请输入密码", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        passwordEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mLeftLogo.setImageResource(R.drawable.ic_22_hide);
                mRightLogo.setImageResource(R.drawable.ic_33_hide);
            }
        });
        accountEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mLeftLogo.setImageResource(R.drawable.ic_22);
                mRightLogo.setImageResource(R.drawable.ic_33);
            }
        });

    }
}



























