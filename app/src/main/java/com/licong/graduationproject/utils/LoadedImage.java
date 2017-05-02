package com.licong.graduationproject.utils;

/**
 * Created by licong on 5/2/17.
 * 加载图片的
 */

import android.graphics.Bitmap;
public class LoadedImage {
    Bitmap mBitmap;
    public LoadedImage(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }
}
