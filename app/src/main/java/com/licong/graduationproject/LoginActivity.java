package com.licong.graduationproject;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.licong.graduationproject.adapter.MainInterface;
import com.licong.graduationproject.adapter.MainInterfaceAdapter;

public class LoginActivity extends AppCompatActivity {
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        }
    //跳转到主界面
    public void toMainActivity(View v){
        Intent intent_main=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent_main);
    }

    }


