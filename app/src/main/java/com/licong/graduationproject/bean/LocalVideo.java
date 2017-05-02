package com.licong.graduationproject.bean;

import com.licong.graduationproject.utils.LoadedImage;

import java.io.Serializable;

/**
 * Created by licong on 4/28/17.
 * 本地视频的信息
 */
//加个implements Serializable 使其可序列化
public class LocalVideo implements Serializable{
    //序列号
    private static final long serialVersionUID = -7920222595800367956L;
    //视频id
    private int id;
    //视频标题
    private String title;
    //视频链接
    private String path;
    //视频时间
    private long duration;
    //视频缩略图
    private LoadedImage image;
    public LocalVideo() {
        super();
    }
    public LocalVideo(int id, String title, String path, long duration) {
        super();
        this.id = id;
        this.title = title;
        this.path = path;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public LoadedImage getImage(){
        return image;
    }

    public void setImage(LoadedImage image){
        this.image = image;
    }

}