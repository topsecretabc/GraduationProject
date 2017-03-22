package com.licong.graduationproject.adapter;

/**
 * Created by licong on 3/22/17.
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
