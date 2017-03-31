package com.licong.graduationproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.licong.graduationproject.adapter.ProfileItem;
import com.licong.graduationproject.adapter.ProfileItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by licong on 3/31/17.
 */

public class MyprofileActivity extends AppCompatActivity {
    private List<ProfileItem> ProfileList = new ArrayList<ProfileItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_layout);
        initProfiles(); // 初始化数据
       ProfileItemAdapter profileItemAdapter = new ProfileItemAdapter(MyprofileActivity.this, R.layout.profile_item, ProfileList);
        ListView listView = (ListView) findViewById(R.id.profile_listview);
        listView.setAdapter(profileItemAdapter);

    }

    private void initProfiles() {
            ProfileItem avater = new ProfileItem("Apple", "");
            ProfileList.add(avater);
            ProfileItem UID = new ProfileItem("UID", "");
            ProfileList.add(UID);
            ProfileItem nickname= new ProfileItem("Banana", "");
            ProfileList.add(nickname);
            ProfileItem gender = new ProfileItem("Orange","");
            ProfileList.add(gender);
    }

























}