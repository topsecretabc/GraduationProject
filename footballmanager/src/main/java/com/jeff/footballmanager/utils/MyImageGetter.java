package com.jeff.footballmanager.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.widget.TextView;

public class MyImageGetter implements ImageGetter {

	private TextView textView;
	private Activity ac;
	
	public MyImageGetter(TextView textView,Activity ac) {
		this.ac = ac;
		this.textView = textView;
	}
	
	@Override
	public Drawable getDrawable(String source) {
		MyDrawable drawable = new MyDrawable();
		MyImageAsyncTask task = new MyImageAsyncTask(textView, drawable,ac);
		task.execute(source);
//		DisplayMetrics dm = new DisplayMetrics();
//		ac.getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int w = dm.widthPixels-40;
//		double scale = w/dm.widthPixels;
//		int h = (int) (dm.heightPixels*scale);
//		drawable.setBounds(0,0,w,h);
		return drawable;
	}

}
