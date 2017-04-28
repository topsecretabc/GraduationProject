package com.licong.graduationproject.bean;

/**
 * Created by licong on 4/28/17.
 * 本地视频的信息
 */

public class LocalVideo {
    private String displayName;
    private  String path;
    private  int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



}
