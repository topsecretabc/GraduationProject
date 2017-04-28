package com.licong.graduationproject.bean;

/**
 * Created by licong on 3/22/17.
 * 这是主界面的各个视频item的类
 */

public class MainInterface {
    private  int imageId;
    private  String imageText;
    public MainInterface(int imageId,String imageText){
        this.imageText=imageText;
        this.imageId=imageId;
    }

    public int getImageId() {
        return imageId;
    }
    public  String getImageText(){
        return  imageText;
    }
}
