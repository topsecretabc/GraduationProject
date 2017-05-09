package com.jeff.footballmanager.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.activity.SpalashActivity;
import com.jeff.footballmanager.domain.news;
import com.jeff.footballmanager.service.UpdateNewsService;
import com.jeff.footballmanager.service.UpdateTimeService;

public class MyAppWidgetProvider extends AppWidgetProvider {

	private Bitmap map;
	private news firstnNews = null;
	private RemoteViews remoteViews = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		
		//不能用timer定时器，
		//1.耗电 2.屏幕挂起后定时器休眠，停止计时
		//更新时间
//		Intent intent = new Intent(context,UpdateTimeService.class);
//		context.startService(intent);
		//更新新闻，每8分钟请求一次
//		Intent newsIntent = new Intent(context,UpdateNewsService.class);
//		context.startService(newsIntent);
		
		//更新时间,每3s请求一次
		Intent intent = new Intent(context,UpdateTimeService.class);
		context.startService(intent);
		Long time = System.currentTimeMillis();
//		Intent intent = new Intent();
//		intent.setClass(context, UpdateTimeService.class);
//		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
//		AlarmManager alarm = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
//		alarm.setRepeating(AlarmManager.RTC, time, 1000, pendingIntent);
		
		//更新新闻，每5分钟请求一次
		Intent intentn = new Intent();
		intentn.setClass(context, UpdateNewsService.class);
		PendingIntent npendingIntent = PendingIntent.getService(context, 0, intentn, 0);
		AlarmManager alarmn = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
		alarmn.setRepeating(AlarmManager.RTC, time, 1000*60*5, npendingIntent);

		//启动app
		Intent i = new Intent();
		i.setClass(context,SpalashActivity.class);
		PendingIntent startIntent = PendingIntent.getActivity(context, 0, i, 0);
		remoteViews = new RemoteViews(context.getPackageName(),R.layout.my_widget);
		remoteViews.setOnClickPendingIntent(R.id.widget, startIntent);
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Intent intent = new Intent(context,UpdateTimeService.class);
		context.stopService(intent);
	}

}
