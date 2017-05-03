package com.licong.graduationproject.service;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.app.Service;
import android.os.IBinder;

import com.licong.graduationproject.R;
import com.licong.graduationproject.widget.AppWidget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class TimerService extends Service {

    private Timer mTimer;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    //初始化时间，因为是耗时操作，所以开启一个线程
    //间隔为1000毫秒即一秒
    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                updata();
            }
        }, 0, 1000);
    }
    //更新操作
    private void updata() {
        //加载布局将时间放入
        String time = sdf.format(new Date());
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.appwidget_layout);
        rv.setTextViewText(R.id.widget_time, time);
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cn =new ComponentName(getApplicationContext(),AppWidget.class);
        manager.updateAppWidget(cn, rv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mTimer = null;
    }
}