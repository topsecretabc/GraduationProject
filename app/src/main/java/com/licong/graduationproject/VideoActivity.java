package com.licong.graduationproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
;


import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;



public class  VideoActivity extends AppCompatActivity {
    private IjkPlayerView mPlayerView;
    private static final String VIDEO_URL = "http://flv2.bn.netease.com/videolib3/1611/28/GbgsL3639/SD/movie_index.m3u8";

        @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);
//        mPlayerView = new IjkPlayerView(this);
//            RelativeLayout videoContainer = (RelativeLayout)
//                    findViewById(R.id.play_view);
//            videoContainer.addView((View) mPlayerView);
            mPlayerView=(IjkPlayerView)findViewById(R.id.play_view);
            Log.e("wanghao","VIDEO_URL:"+VIDEO_URL);
            Uri uri=Uri.parse(VIDEO_URL);
        mPlayerView.init()
                .setVideoPath(uri)
                .enableDanmaku()
                .setDanmakuSource(getResources().openRawResource(R.raw.bili))
                .setTitle("这是个跑马灯TextView，标题要足够长才会跑。-(゜ -゜)つロ 乾杯~")
                .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayerView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPlayerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (mPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
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
}