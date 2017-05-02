package com.licong.graduationproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.licong.graduationproject.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by licong on 3/31/17.
 * 用户资料
 */

public class MyprofileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_layout);
        Button button = (Button)findViewById(R.id.profileback_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myprofile_main = new Intent(MyprofileActivity.this,MainActivity.class);
                startActivity(myprofile_main);
                finish();
            }
        });

    }

























}