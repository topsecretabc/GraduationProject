package com.licong.graduationproject.activity;

import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.dl7.player.media.IjkPlayerView;
import com.licong.graduationproject.R;
import com.licong.graduationproject.bean.LocalVideo;

import okhttp3.OkHttpClient;

public class PlayLocalVideoActivity extends AppCompatActivity {
    private IjkPlayerView mPlayerView;
    LocalVideo playVideoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_local_video_layout);
        Bundle bundle = getIntent().getExtras();
        //取得视频信息，标识符为“video”
        playVideoFile = (LocalVideo)bundle.getSerializable("video");
        //取得视频地址
        String mVideoUri = playVideoFile.getPath();
        String mVideoTitle = playVideoFile.getTitle();
        mPlayerView=(IjkPlayerView)findViewById(R.id.local_player_view);
        Uri uri=Uri.parse(mVideoUri);
        mPlayerView.init()
                .setVideoPath(uri)
                .setTitle(mVideoTitle)
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


}