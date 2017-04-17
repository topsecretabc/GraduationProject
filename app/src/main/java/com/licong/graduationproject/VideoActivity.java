package com.licong.graduationproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class  VideoActivity extends AppCompatActivity {


    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);

        //初始化界面
        initViewPager();
    }

    //给视频界面创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video, menu);
        return true;
    }

    //定义视频界面菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //进入关于界面
            case R.id.video_about:
                Intent intent_video_about = new Intent(VideoActivity.this, AboutActivity.class);
                startActivity(intent_video_about);
                break;
            //返回主界面
            //homeAsUp按钮id永远是android.R.id.home
            case android.R.id.home:
                finish();
                break;
            case R.id.video_setup:
                Intent intent_video_setting = new Intent(VideoActivity.this, SettingActivity.class);
                startActivity(intent_video_setting);
                break;
        }
        return true;
    }

    public void initViewPager() {
        //得到Toolbar的实例传入setSupportActionBar()
        // 既使用了Toolbar又让它外观与功能与ActionBar一致
        toolbar = (Toolbar) findViewById(R.id.toolbar_video);
        setSupportActionBar(toolbar);
        //调用getSupportActionBar()得到ActionBar实例，虽然ActionBar是由Toolbar完成的
        ActionBar actionBar = getSupportActionBar();
        //加个非空，防止出现错误
        if (actionBar != null) {
            //setDisplayHomeAsUpEnabled()方法让导航按钮显示出来
            // setHomeAsUpIndicator()设置一个导航按钮，
            // 实际上Toolbar最左侧的按钮就叫HomeAsUp，
            // 默认是一个返回的箭头，但我还是换成了back_black
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        }
        //悬浮按钮的点击事件
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.play_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //等api申请好
            }
        });
    }
}