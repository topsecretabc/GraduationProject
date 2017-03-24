package com.licong.graduationproject.adapter;

import android.media.Image;

/**
 * Created by licong on 3/22/17.
 */

public class Setting {
    private  String item;

    private int image_id;
    public Setting(String item,int image_id){
        this.item=item;

        this.image_id=image_id;
    }

    public  String getItemText(){
        return  item;
    }

    public  int getImage_id(){
        return image_id;
    }


}
