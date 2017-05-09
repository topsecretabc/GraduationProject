package com.jeff.footballmanager.receiver;

import com.jeff.footballmanager.service.UpdateNewsService;
import com.jeff.footballmanager.service.UpdateTimeService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent time = new Intent(context,UpdateTimeService.class);
		Intent news = new Intent(context,UpdateNewsService.class);
		context.startService(news);
		context.startService(time);
		Log.i("jeff","start service");
	}

}
