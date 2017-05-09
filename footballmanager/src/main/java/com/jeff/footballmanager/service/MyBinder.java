package com.jeff.footballmanager.service;

import com.jeff.footballmanager.utils.ImageCacheUtils;

import android.os.Binder;

public class MyBinder extends Binder {
	
	private boolean status;
	
	public void clearCache(){
//		ImageCacheUtils.clearLocalBitmap();
	}
	
}
