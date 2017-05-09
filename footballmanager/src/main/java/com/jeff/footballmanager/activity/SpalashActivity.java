package com.jeff.footballmanager.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.param.APIRetParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.User;
import com.jeff.footballmanager.param.UserParam;
import com.jeff.footballmanager.service.UpdateNewsService;
import com.jeff.footballmanager.service.UpdateTimeService;
import com.jeff.footballmanager.utils.LoginAsyncTask;

public class SpalashActivity extends BaseActivity {
	APIRetParam<User> result = new APIRetParam();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_spalash);

		Intent timeService = new Intent(this,UpdateTimeService.class);
		Intent newsService = new Intent(this,UpdateNewsService.class);
		this.startService(newsService);
		this.startService(timeService);
		Log.i("jeff","start service");

		if(getUserInfo()){
			Timer time = new Timer();
			time.schedule(new TimerTask() {
				@Override
				public void run() {
					Intent intent = new Intent(SpalashActivity.this,LoginActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.push_left_in,
							R.anim.push_left_out);
					finish();
				}
			},2000);
		}
	}

	protected boolean getUserInfo() {
		SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
		boolean checkStatus = pref.getBoolean("checkStatus",false);
		boolean login = pref.getBoolean("login",false);

		UserParam user = new UserParam();
		user.setName(pref.getString("name",""));
		user.setPsd(pref.getString("psd",""));

		if(login){
			doLogin(user);
			return false;
		}
		if(checkStatus){
			doLogin(user);
			return false;
		}else{
			return true;
		}
	}


	private void doLogin(UserParam user) {
		//验证账号密码是否正确
		LoginAsyncTask<User> asyncTask = new LoginAsyncTask(result, SpalashActivity.this,MainActivity.class);
		asyncTask.execute(user);
	}

}
