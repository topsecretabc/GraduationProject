package com.licong.graduationproject.recycleview_item;

/**
 * Created by licong on 3/29/17.
 */

public class Discuss {
    private  String name;
    private  int imageId;
    private  String comment;
    public Discuss(String name,String comment, int imageId) {
        this.name = name;
        this.comment=comment;
        this.imageId = imageId;
    }

    public String getName() {

        return name;
    }

    public String getComment() {
        return name;
    }

    public int getImageId() {

        return imageId;
    }

}
