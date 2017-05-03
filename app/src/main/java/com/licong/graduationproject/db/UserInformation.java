package com.licong.graduationproject.db;

import org.litepal.crud.DataSupport;

/**
 * Created by licong on 4/25/17.
 * 用户信息数据库
 */

public class UserInformation extends DataSupport {
    //昵称
    private String name;
    //UID
    private int id;
    //性别
    private boolean gender;
    //个性签名
    private String signature;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
