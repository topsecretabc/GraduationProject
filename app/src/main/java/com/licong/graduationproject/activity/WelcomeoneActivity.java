package com.licong.graduationproject.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.licong.graduationproject.R;

public class WelcomeoneActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 3000; //延迟三秒

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomehead_layout);
        //判断是否有权限，运行时权限的处理
        if (ContextCompat.checkSelfPermission(WelcomeoneActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WelcomeoneActivity.this, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else {
            judgment();
        }
        }
    public  void  judgment(){
        //读取SharedPreFerences中需要的数据,使用SharedPreFerences来记录程序启动的使用次数
        SharedPreferences preferences = getSharedPreferences("isFirstIn", MODE_PRIVATE);
        //取得相应的值,如果没有该值,说明还未写入,用true作为默认值
        boolean isFirstIn;
        isFirstIn = preferences.getBoolean("isFirstIn", false);
        //判断程序第几次启动，和主界面的activity的判断做联系
        if (isFirstIn) {
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    //不是第一次就直接到主界面
                    Intent mainIntent = new Intent(WelcomeoneActivity.this,MainActivity.class);
                    WelcomeoneActivity.this.startActivity(mainIntent);
                    WelcomeoneActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGHT);
        } else {
            //是第一次就进入欢迎导航
            Intent intent = new Intent(WelcomeoneActivity.this, WelcomePageActivity.class);
            WelcomeoneActivity.this.startActivity(intent);
            WelcomeoneActivity.this.finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                //有权限就进入，没权限就报"没有权限你要我怎么办"。
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    judgment();
                } else {
                    Toast.makeText(this, "没有权限你要我怎么办", Toast.LENGTH_LONG).show();
                }
                break;
            default:
        }
    }
}

