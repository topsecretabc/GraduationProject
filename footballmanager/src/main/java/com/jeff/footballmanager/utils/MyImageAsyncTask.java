package com.jeff.footballmanager.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class MyImageAsyncTask extends AsyncTask<String, Integer, Drawable> {

	private TextView textView;
	private MyDrawable drawable;
	private Activity ac;
	
	public MyImageAsyncTask(TextView textView,MyDrawable drawable,Activity ac) {
		this.textView = textView;
		this.drawable = drawable;
		this.ac = ac;
	}
	
	@Override
	protected Drawable doInBackground(String... params) {
//		URL url = null;
//		HttpURLConnection conn = null;
//		InputStream in = null;
//		Drawable map = null;
//		try {
//			url = new URL(params[0]);
//			conn = (HttpURLConnection) url.openConnection();
//			conn.setReadTimeout(5000);
//			conn.setConnectTimeout(5000);
//			conn.setRequestMethod("GET");
//			conn.connect();
//			if(conn.getResponseCode()==200){
//				in = conn.getInputStream();
//				map = Drawable.createFromStream(in, null);
//				if(map!=null){
					//必须添加
//					DisplayMetrics dm = new DisplayMetrics();
//					ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
//					int w = dm.widthPixels-40;
//					double scale = w/dm.widthPixels;
//					int h = (int) (dm.heightPixels*scale);
//					map.setBounds(0,0,w,h);
//				}
//			}
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//			if(in!=null){
//				try {
//					in.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if(conn!=null){
//				conn.disconnect();
//			}
//		}
		HttpUtils conn = new HttpUtils();
		Drawable map = conn.getDrawable(params[0]);
		return map;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Drawable result) {
		super.onPostExecute(result);
		if(result!=null){
//			DisplayMetrics dm = new DisplayMetrics();
//			ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
//			int w = dm.widthPixels;
//			int h = w/(drawable.getIntrinsicWidth()/drawable.getIntrinsicHeight());
//			drawable.setBounds(0,0,w,h);
//			必须添加
			DisplayMetrics dm = new DisplayMetrics();
			ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
			int width = result.getIntrinsicWidth();
//			int height = result.getIntrinsicHeight();
			int w = dm.widthPixels-30;
			double scale = (w*0.98)/(width*1.0);
//			result.setBounds(0,0,(int)(result.getIntrinsicWidth()*scale),(int)(result.getIntrinsicHeight()*scale));
			drawable.setDrawable(result,scale);
//			drawable.drawable = result;
			textView.invalidate();
			//解决图文重叠
			textView.setText(textView.getText());
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	
	
}
