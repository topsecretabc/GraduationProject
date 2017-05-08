package com.licong.graduationproject.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import com.dl7.player.media.IjkPlayerView;
import com.google.gson.Gson;
import com.licong.graduationproject.API.API;
import com.licong.graduationproject.R;
import com.licong.graduationproject.bean.VideoMessage;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class  VideoActivity extends AppCompatActivity {
    private IjkPlayerView mPlayerView;
    private String contid;
    private VideoMessage mVideos ;
    String urls;
    //主线程收到消息初始化播放器
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    Uri uri=Uri.parse(urls);
                    mPlayerView.init()
                            .setVideoPath(uri)
                            .enableDanmaku()        // 加载弹幕
                            .setDanmakuSource(getResources().openRawResource(R.raw.bili))
                            .setMediaQuality(IjkPlayerView.MEDIA_QUALITY_HIGH);
            }

        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.video_layout);
            Bundle bundle = getIntent().getExtras();
            //取得contid，标识符为“video”
            contid = bundle.getString("video");
            mPlayerView=(IjkPlayerView)findViewById(R.id.play_view);
            sendRequestWithOkHttp();
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
    //发送网络请求因为是耗时操作所以开启子线程操作
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(API.VIDEO_API+contid)
                            //设置请求头
                            // X-Channel-Code 	固定值 official，可为空
//                    X-Client-Agent 	手机品牌，例如 Xiaomi，可为空
//                    X-Client-Hash 	长度为32的小写字母和数字混合字符串，例2f3d6ffkda95dlz2fhju8d3s6dfges3t，可为空
//                    X-Client-ID 	    长度为的15数字字符串，例123456789123456，可为空
//                    X-Client-Version 	为空即可
//                    X-Long-Token 	    为空即可
//                    X-Platform-Type 	为空即可
//                    X-Platform-Version 	为空即可
//                    X-Serial-Num 	Unix    时间戳
//                    X-User-ID 	    为空即可
                            //示例
//                    X-Channel-Code:official
//                    X-Client-Agent:Xiaomi
//                    X-Client-Hash:2f3d6ffkda95dlz2fhju8d3s6dfges3t
//                    X-Client-ID:123456789123456
//                    X-Client-Version:2.3.2
//                    X-Long-Token:
//                    X-Platform-Type:0
//                    X-Platform-Version:5.0
//                    X-Serial-Num:1492140134
//                    X-User-ID:
                            .addHeader("X-Channel-Code","official")
                            .addHeader("X-Client-Agent","Xiaomi")
                            .addHeader("X-Client-Hash","2f3d6ffkda95dlz2fhju8d3s6dfges3t")
                            .addHeader("X-Client-ID","123456789123456")
                            .addHeader("X-Client-Version","2.3.2")
                            .addHeader("X-Long-Token","")
                            .addHeader("X-Platform-Type","0")
                            .addHeader("X-Platform-Version","5.0")
                            .addHeader("X-Serial-Num",""+System.currentTimeMillis())
                            .addHeader("X-User-ID","")
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //解析json
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        //解析这种非常复杂的嵌套的json需要先创建一个基类的对象然后再用其拿到数据
        mVideos = gson.fromJson(jsonData,VideoMessage.class);
        urls =  mVideos.getContent().getVideos().get(0).getUrl();

        //发送一个消息告诉主线程json字符串已经解析完
        handler.sendEmptyMessage(1);
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