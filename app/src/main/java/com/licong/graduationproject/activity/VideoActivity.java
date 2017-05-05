package com.licong.graduationproject.activity;

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


import com.dl7.player.media.IjkPlayerView;
import com.licong.graduationproject.API.API;
import com.licong.graduationproject.R;
import com.licong.graduationproject.bean.LocalVideo;


public class  VideoActivity extends AppCompatActivity {
    private IjkPlayerView mPlayerView;
    private String contid;private String VIDEO_URL="http://baobab.kaiyanapp.com/api/v1/playUrl?vid=9528&editionType=default&source=ucloud";

        @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.video_layout);
            Bundle bundle = getIntent().getExtras();
            //取得contid，标识符为“video”
            contid = bundle.getString("video");
            mPlayerView=(IjkPlayerView)findViewById(R.id.play_view);
            Uri uri=Uri.parse(VIDEO_URL);
            mPlayerView.init()
                    .setVideoPath(uri)
                    .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
//            private IjkPlayerView mPlayerView;
//            LocalVideo playVideoFile;
//            @Override
//            protected void onCreate(Bundle savedInstanceState) {
//
//                Bundle bundle = getIntent().getExtras();
//                //取得视频信息，标识符为“video”
//                playVideoFile = (LocalVideo)bundle.getSerializable("video");
//                //取得视频地址
//                String mVideoUri = playVideoFile.getPath();
//                String mVideoTitle = playVideoFile.getTitle();
//                mPlayerView=(IjkPlayerView)findViewById(R.id.local_player_view);
//                Uri uri=Uri.parse(mVideoUri);
//                mPlayerView.init()
//                        .setVideoPath(uri)
//                        .setTitle(mVideoTitle)
//                        .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
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