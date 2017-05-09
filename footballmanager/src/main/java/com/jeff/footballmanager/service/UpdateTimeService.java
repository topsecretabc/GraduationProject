package com.jeff.footballmanager.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.widget.MyAppWidgetProvider;

public class UpdateTimeService extends Service {

	private Timer timer;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	     flags = START_STICKY;  
	     timer = new Timer();
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					updateTime();
				}
		 }, 0, 1000);
	     return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	private void updateTime() {
		String time = dateFormat.format(new Date(System.currentTimeMillis()));
		RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.my_widget);
			remoteViews.setTextViewText(R.id.awTimeText,time);
		AppWidgetManager aw = AppWidgetManager.getInstance(getApplicationContext());
		aw.updateAppWidget(new ComponentName(getApplicationContext(), MyAppWidgetProvider.class), remoteViews);
	}

	@Override
	public void onDestroy() {
		Intent intent = new Intent("com.jeff.football.SERVICE_DESTORY");  
		sendOrderedBroadcast(intent, null);
		Log.i("jeff","stop service");
		super.onDestroy();
	}
	
	
}
