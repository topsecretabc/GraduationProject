package com.licong.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.licong.graduationproject.adapter.Review;
import com.licong.graduationproject.adapter.ReviewAdapter;
import com.licong.graduationproject.adapter.Setting;
import com.licong.graduationproject.adapter.SettingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by licong on 3/23/17.
 * 需要加入设置有：1.清晰度选择 2.播放完成后 3.解码设置 4.弹幕设置
 *               5.离线设置 6.不接受推送消息 7.禁用启动动画 8.清空缓存
 *               9.恢复初始设置  10.帮助（里面是 1.关于 2.联系我）
 */

public class SettingActivity extends AppCompatActivity {

    //构建一个listview需要传入数据的对象
    private String[] item = {"清晰度选择" ,"播放完成后","解码设置", "弹幕设置" ,
                             "离线设置", "不接受推送消息", "禁用启动动画",
                             "清空缓存","恢复初始设置","帮助"};
    private List<Setting> SettingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        //创建SettingAdapter对象，作为适配器传给ListView
        SettingAdapter settingAdapter= new SettingAdapter
                (SettingActivity.this, R.layout.setting_item, SettingList);
        //得到Toolbar的实例传入setSupportActionBar()
        // 既使用了Toolbar有让它外观与功能与ActionBar一致
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_video);
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
            actionBar.setHomeAsUpIndicator(R.drawable.back_black);
        }
        //ArrayAdapter通过泛型来指定要适配的数据类型，提供多个构造函数重载
        //此处ArrayAdapter（当前上下文，ListView子项布局，适配的数据）
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (SettingActivity.this, android.R.layout.simple_list_item_1, item);
        ListView listView = (ListView) findViewById(R.id.setting_recyclerview);
        listView.setAdapter(adapter);
    }

    private void initItem(String item){
        new Setting(item);
        SettingList.add();
    }


}