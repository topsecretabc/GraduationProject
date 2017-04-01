package com.licong.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.licong.graduationproject.adapter.Setting;
import com.licong.graduationproject.adapter.SettingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by licong on 3/23/17.
 * 需要加入设置有：1.清晰度选择 2.播放完成后 3.解码设置 4.弹幕设置
 *               5.离线设置 6.不接受推送消息 7.禁用启动动画 8.清空缓存
 *               9.恢复初始设置  10.联系我）
 */

public class SettingActivity extends AppCompatActivity {

    //构建一个listview需要传入数据的对象


    private List<Setting> SettingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        initSetting();//初始化设置的项目
        SettingAdapter settingAdapter=
                new SettingAdapter(SettingActivity.this,R.layout.setting_item,SettingList);
        ListView listView=(ListView)findViewById(R.id.setting_listview);
        listView.setAdapter(settingAdapter);
        //得到Toolbar的实例传入setSupportActionBar()
        // 既使用了Toolbar有让它外观与功能与ActionBar一致
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        //调用getSupportActionBar()得到ActionBar实例，虽然ActionBar是由Toolbar完成的
        ActionBar actionBar = getSupportActionBar();
        //加个非空，防止出现错误
        if (actionBar != null) {
            //setDisplayHomeAsUpEnabled()方法让导航按钮显示出来
            // setHomeAsUpIndicator()设置一个导航按钮，
            // 实际上Toolbar最左侧的按钮就叫HomeAsUp，
            // 默认是一个返回的箭头，但我还是换成了back_black
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_setting,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.setting_home:
                Intent intent_setting_home=
                        new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent_setting_home);
                break;
        }
        return true;
    }
    private void initSetting() {
        Setting Clarity = new Setting("清晰度选择",R.drawable._ic_31);
        SettingList.add(Clarity);
        Setting Play_done = new Setting("播放完成后动作",R.drawable.ic_32);
        SettingList.add(Play_done);
        Setting Decode = new Setting("解码设置",R.drawable._ic_33);
        SettingList.add(Decode);
        Setting Barrage = new Setting("弹幕设置",R.drawable.ic_34);
        SettingList.add(Barrage);
        Setting Offline = new Setting("离线设置",R.drawable.ic_35);
        SettingList.add(Offline);
        Setting Push_the_message = new Setting("不接受推送消息",R.drawable.ic_36);
        SettingList.add(Push_the_message);
        Setting Start_the_animation = new Setting("禁用启动动画",R.drawable.ic_37);
        SettingList.add(Start_the_animation);
        Setting Cache = new Setting("清空缓存",R.drawable.ic_38);
        SettingList.add(Cache);
        Setting default_setting = new Setting("恢复初始设置",R.drawable.ic_39);
        SettingList.add(default_setting);
        Setting contact = new Setting("联系我",R.drawable.ic_40);
        SettingList.add(contact);
    }
}