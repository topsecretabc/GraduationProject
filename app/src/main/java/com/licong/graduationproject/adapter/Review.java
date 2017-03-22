package com.licong.graduationproject.adapter;

/**
 * Created by licong on 3/22/17.
 * 讨论布局的适配类型
 **/

public class Review {
        private String user_id;
        private int imageId;
    public Review(String user_id,int imageId){
        this.user_id=user_id;
        this.imageId=imageId;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getImageId() {
        return imageId;
    }
}
