package com.jeff.footballmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.utils.ActivityController;

public class BaseActivity extends FragmentActivity {

	public  final int showToastTime = 1000;
	public  final int showToastTimeShort = 500;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除actionBar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base);
		ActivityController.addActivity(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	public void startMyActivity(Context ct,Context ct2,Bundle extras,int i){
		Intent intent = new Intent(ct,ct2.getClass());
		if(null!=extras){
			intent.putExtras(extras);
		}
		intent.putExtra("num",i);
		startActivity(intent);
	}
	
	/**
	 * 完成请求后回调
	 * @param data
	 */
	public void doAfterGetData(String data){
	}
	
	/**
	 * i为0成功，为-1访问网络失败,为1服务器返回出错
	 * @param i
	 */
	public void doMessage(int i,String msg){
	}
	
	public void showToastSuccess(Context ct,String msg,int time){
		Toast.makeText(ct, msg, time).show();
	}
	
	public void showToastFault(Context ct,String msg,int time){
		Toast.makeText(ct, msg, time).show();
	}
}
