package com.jeff.footballmanager.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.activity.LoginActivity;
import com.jeff.footballmanager.domain.Result;
import com.jeff.footballmanager.domain.news;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.utils.HttpUtils;
import com.jeff.footballmanager.utils.ImageCacheUtils;
import com.jeff.footballmanager.widget.MyAppWidgetProvider;

public class UpdateNewsService extends Service {
	
	private static news oldNews = null;
	private static int count = 0,nums=0;
	private boolean firstInit = true;
	news firstnNews = null;
	Context context = null;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	     flags = START_STICKY;  
	     updateNew(this);
	     return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Intent intent = new Intent("com.jeff.football.SERVICE_DESTORY");  
	    sendOrderedBroadcast(intent, null);
		Log.i("jeff","stop service");
		super.onDestroy();
	}

	private void updateNew(Context ctx) {
		context = ctx;
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpUtils httpUtils = new HttpUtils();
				Message msg = new Message();
				Result result = httpUtils.doGet(StaticParam.NEWS_URL+"&tableNum=1&pagesize=1"+"&page="+1);
				if(result!=null && result.getData().size()>0){
					count = 0;
					firstnNews = result.getData().get(0);
					if(oldNews!=null && oldNews.getNews_id().equals(firstnNews.getNews_id())){
						//新闻未更新
						return;
					}else{
						ImageCacheUtils cacheUtils = new ImageCacheUtils(context);
						String http = firstnNews.getTop_image();
						Bitmap map = cacheUtils.disPlayImage(http);
						if(map == null){
							map = httpUtils.getBitMap(http);
							cacheUtils.saveBitmap(map,http);
						}
						if(firstnNews!=null){
							firstnNews.setTopmap(map);
							//新闻已更新
							oldNews = firstnNews;
							RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.my_widget);
							remoteViews.setTextViewText(R.id.awTitleText,firstnNews.getTitle());
							remoteViews.setImageViewBitmap(R.id.aWImageView, firstnNews.getTopmap());
							//更新小部件
							AppWidgetManager apm = AppWidgetManager.getInstance(context);
							apm.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class),remoteViews);
							
							//发送通知
							if(!firstInit)
								sendNotification();
							else{
								firstInit = false;
							}
							firstnNews = null;
						}
					}
				}else{
					//访问失败，未获取到数据
					if(count<3)
					{
						try {
							Thread.sleep(2000*(count+1));
							count++;
							updateNew(context);
						} catch (InterruptedException e) {
						}
					}else{
						
					}
				}						
			}

		}).start();		
	}
	
	@SuppressLint("NewApi")
	private void sendNotification() {
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification.Builder builder = new Notification.Builder(context);
		
		builder.setAutoCancel(true);//点击后消失
		builder.setDefaults(Notification.DEFAULT_SOUND);
		builder.setSmallIcon(R.drawable.icon);
		builder.setTicker(oldNews.getTitle());
		builder.setContentTitle(oldNews.getTitle());
		if(!oldNews.getContent().equals(""))
			builder.setContentText(oldNews.getContent());
		else
			builder.setContentText(oldNews.getDigest());
		
		//点击跳转打开app
		Intent intent = new Intent(context,LoginActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		builder.setContentIntent(pendingIntent);
		
		manager.notify(0, builder.build());				
	}
}
