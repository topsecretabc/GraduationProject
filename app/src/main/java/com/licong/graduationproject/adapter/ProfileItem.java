package com.licong.graduationproject.adapter;

/**
 * Created by licong on 3/31/17.
 */

public class ProfileItem {
    private String profileItem;
    private String profileContent;

    public ProfileItem(String profileItem,String profileContent){
        this.profileItem=profileItem;

        this.profileContent=profileContent;
    }

    public  String getProfileItem(){
        return  profileItem;
    }

    public  String getProfileContent(){
        return profileContent;
    }


}
