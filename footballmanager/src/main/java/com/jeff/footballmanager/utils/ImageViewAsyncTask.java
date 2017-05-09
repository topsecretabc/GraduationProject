package com.jeff.footballmanager.utils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.jeff.footballmanager.R;

public class ImageViewAsyncTask extends AsyncTask<Void, Integer, Bitmap> {

	private ImageView imageView;
	private String http;
	
	public ImageViewAsyncTask(ImageView imageView,String http) {
		this.http = http;
		this.imageView = imageView;
	}

	@Override
	protected Bitmap doInBackground(Void... params) {
		HttpUtils httpUtils = new HttpUtils();
		return httpUtils.getBitMap(http);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if(result!=null){
			if(imageView!=null && imageView.getTag()!=null && imageView.getTag().equals(http))
				imageView.setImageBitmap(result);
		}else{
			imageView.setBackgroundResource(R.drawable.icon);
		}
	}
}
