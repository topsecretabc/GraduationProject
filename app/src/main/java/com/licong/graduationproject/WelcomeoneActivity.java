package com.licong.graduationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class WelcomeoneActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomehead_layout);
        //读取SharedPreFerences中需要的数据,使用SharedPreFerences来记录程序启动的使用次数
        SharedPreferences preferences = getSharedPreferences("isFirstIn", MODE_PRIVATE);
        Log.e("wanghao","WelcomeoneActivity");
        //取得相应的值,如果没有该值,说明还未写入,用true作为默认值
        boolean isFirstIn;
        isFirstIn = preferences.getBoolean("isFirstIn", false);
        //判断程序第几次启动
        if (isFirstIn) {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Log.e("wanghao","postDelayed");
                    Intent mainIntent = new Intent(WelcomeoneActivity.this,MainActivity.class);
                    WelcomeoneActivity.this.startActivity(mainIntent);
                    WelcomeoneActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGHT);
        } else {
            Log.e("wanghao","else");
            Intent intent = new Intent(WelcomeoneActivity.this, WelcomePageActivity.class);
            WelcomeoneActivity.this.startActivity(intent);
            WelcomeoneActivity.this.finish();
        }
    }
}