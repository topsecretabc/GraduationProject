package com.licong.graduationproject.utils;

/**
 * Created by licong on 4/28/17.
 * 获取本地视频的工具类
 */

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.licong.graduationproject.bean.LocalVideo;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;


public class LocalVideoUtils extends AsyncTask<Void,Integer,List<LocalVideo>>{

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     * videoPath 视频的路径
     * width 指定输出视频缩略图的宽度
     * height 指定输出视频缩略图的高度度
     * kind 参照MediaStore.Images(Video).Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * return 指定大小的视频缩略图
     */
    public  Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        if(bitmap!= null){
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * 获取指定路径中的视频文件
     * list 装扫描出来的视频文件实体类
     * file 指定的文件
     */
    private List<LocalVideo> allVideoList = new ArrayList<LocalVideo>();// 视频信息集合

    protected List<LocalVideo> doInBackground(Void... params) {
     getVideoFile(allVideoList, Environment.getExternalStorageDirectory());
        return allVideoList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<LocalVideo> allVideoList) {
        super.onPostExecute(allVideoList);
    }
    private List<LocalVideo> getVideoFile(final List<LocalVideo> list, File file) {
        // 获得视频文件
        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                // sdCard找到视频名称
                String name = file.getName();
                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);//获取文件后缀名
                    if (name.equalsIgnoreCase(".mp4")  //忽略大小写
                            || name.equalsIgnoreCase(".3gp")
                            || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts")
                            || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov")
                            || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi")
                            || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp")
                            || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv")
                            || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx")
                            || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm")
                            || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram")
                            || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8")
                            || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v")
                            || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra")
                            || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid")) {
                        LocalVideo localVideo = new LocalVideo();
                        file.getUsableSpace();
                        localVideo.setDisplayName(file.getName());//文件名
                        localVideo.setPath(file.getAbsolutePath());//文件路径
                        Log.i("licong","name"+localVideo.getPath());
                        list.add(localVideo);
                        return true;
                    }
                }//判断是不是目录
                else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });
        return list;
    }


}