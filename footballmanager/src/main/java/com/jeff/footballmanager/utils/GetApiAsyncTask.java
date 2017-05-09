package com.jeff.footballmanager.utils;

import android.os.AsyncTask;

import com.jeff.footballmanager.activity.BaseActivity;

//获取api数据工具类
public class GetApiAsyncTask extends AsyncTask<String, Integer,String> {

	private BaseActivity baseActivity;
	private String data = "";
	private String baseApi;
	
	public GetApiAsyncTask(BaseActivity baseActivity,String baseApi) {
		this.baseActivity = baseActivity;
		this.baseApi = baseApi;
	}
	
	//获取接口数据
	@Override
	protected String doInBackground(String... params){
		HttpUtils httpUtils = new HttpUtils();
		String json = httpUtils.getFootBallInfo(baseApi, params[0]);
		return json;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		this.data = result;
		//上转型对象，调用子类重写的doAfterGetData()方法
		baseActivity.doAfterGetData(data);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}
}
