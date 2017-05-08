package com.licong.graduationproject.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.licong.graduationproject.bean.LocalVideo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by licong on 5/2/17.
 * 获得视频信息的工具类
 */

public class LocalVideoUtils implements AbstructLocalVideo{
    private Context context;
    //给外面提供一个构造方法以获得对象
    public LocalVideoUtils(Context context) {
        this.context = context;
    }
    //获得视频信息的方法
    @Override
    public List<LocalVideo> getList() {
        List<LocalVideo> list = null;
        if (context != null) {
            //从系统获得时间的内容提供者内容
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                list = new ArrayList<LocalVideo>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    long duration = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    LocalVideo video = new LocalVideo(id, title, path, duration);
                    list.add(video);
                }
                cursor.close();
            }
        }
        return list;
    }

}

