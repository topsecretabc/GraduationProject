package com.jeff.footballmanager.utils;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeff.footballmanager.R;
import com.jeff.footballmanager.activity.LoginActivity;
import com.jeff.footballmanager.activity.MainActivity;
import com.jeff.footballmanager.activity.SpalashActivity;
import com.jeff.footballmanager.param.APIRetParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.UserParam;

public class LoginAsyncTask<T> extends AsyncTask<UserParam, Integer, APIRetParam<T>> {

	private APIRetParam<T> user;
	private LoginActivity loginActivity;
	private SpalashActivity splashActivity;
	private Class<MainActivity> class1;
	
	public LoginAsyncTask(APIRetParam<T> user,LoginActivity loginActivity, Class<MainActivity> class1) {
		this.user = user;
		this.loginActivity = loginActivity;
		this.class1 = class1;
	}
	
	public LoginAsyncTask(APIRetParam<T> user,SpalashActivity splashActivity, Class<MainActivity> class1) {
		this.user = user;
		this.splashActivity = splashActivity;
		this.class1 = class1;
	}
	
	@Override
	protected APIRetParam<T> doInBackground(UserParam... params) {
		HttpUtils httpUtils = new HttpUtils();
		String json = httpUtils.getFootBallInfo(StaticParam.API_LOGIN+StaticParam.API_TOKEN,params[0].toString());
		Gson gson = new Gson();
		APIRetParam<T> user = new APIRetParam<T>();
		user = gson.fromJson(json, user.getClass());
		return user;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(loginActivity!=null)
			Toast.makeText(loginActivity,
				loginActivity.getApplicationContext().getString(R.string.logining),loginActivity.showToastTimeShort).show();
		if(splashActivity!=null)
			Toast.makeText(splashActivity,
				splashActivity.getApplicationContext().getString(R.string.logining),splashActivity.showToastTimeShort).show();
	}


	@Override
	protected void onPostExecute(APIRetParam<T> result) {
		super.onPostExecute(result);
		this.user = result;
		doInfo();
	}

	private void doInfo() {
		if(user==null){
			if(loginActivity!=null)
			{
				loginActivity.backLogin();
				Toast.makeText(loginActivity,
					loginActivity.getApplicationContext().getString(R.string.errorconnect),loginActivity.showToastTimeShort).show();
			}
			if(splashActivity!=null)
			{
				Toast.makeText(splashActivity,
					splashActivity.getApplicationContext().getString(R.string.errorconnect),splashActivity.showToastTimeShort).show();
				Intent i = new Intent(splashActivity, LoginActivity.class);
				splashActivity.startActivity(i);
				splashActivity.finish();
				splashActivity.overridePendingTransition(R.anim.slide_top_to_bottom,
						R.anim.my_alpha_action);
			}
			return;
		}
		if("200".equals(user.getStatusCode())){
			if(StaticParam.SUCCESS.equals(user.getMsg())){
				
				//保存用户信息
				StaticParam.USER_NAME = user.getData().get(0).getName();
				StaticParam.USER_NO = user.getData().get(0).getUserNo();
				if(user.getData().get(0).getHeadPath()!=null)
					StaticParam.HEAD_PATH = user.getData().get(0).getHeadPath();
				if(user.getData().get(0).getBgPath()!=null)
					StaticParam.BG_PATH = user.getData().get(0).getBgPath();

				if(loginActivity!=null)
				{
					Intent i = new Intent(loginActivity, MainActivity.class);
					loginActivity.startActivity(i);
					loginActivity.overridePendingTransition(R.anim.slide_top_to_bottom,
							R.anim.my_alpha_action);
				}
				if(splashActivity!=null)
				{
					Intent i = new Intent(splashActivity, MainActivity.class);
					splashActivity.startActivity(i);
					splashActivity.finish();
					splashActivity.overridePendingTransition(R.anim.slide_top_to_bottom,
							R.anim.my_alpha_action);
				}
			}else{
				if(loginActivity!=null)
				{
					loginActivity.backLogin();
					Toast.makeText(loginActivity, ""+user.getMsg(),loginActivity.showToastTimeShort).show();
				}
				if(splashActivity!=null)
				{
					Toast.makeText(splashActivity, ""+user.getMsg(),splashActivity.showToastTimeShort).show();
					Intent i = new Intent(splashActivity, LoginActivity.class);
					splashActivity.startActivity(i);
					splashActivity.finish();
					splashActivity.overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
				}
			}
		}else{
			if(loginActivity!=null)
			{
				loginActivity.backLogin();
				Toast.makeText(loginActivity, ""+user.getMsg(),loginActivity.showToastTimeShort).show();
			}
			if(splashActivity!=null)
			{
				Toast.makeText(splashActivity, ""+user.getMsg(),splashActivity.showToastTimeShort).show();
			}
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

}
