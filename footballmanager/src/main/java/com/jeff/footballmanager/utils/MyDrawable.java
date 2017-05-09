package com.jeff.footballmanager.utils;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MyDrawable extends BitmapDrawable {

	Drawable drawable;

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(null!=drawable){
			drawable.draw(canvas);
		}
	}

	public void setDrawable(Drawable drawable,double scale) {
		this.drawable = drawable;
		drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*scale), (int)(drawable.getIntrinsicHeight()*scale));
		setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*scale), (int)(drawable.getIntrinsicHeight()*scale));
	}
	
}
