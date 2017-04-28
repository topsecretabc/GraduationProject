package com.licong.graduationproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.licong.graduationproject.R;
import com.licong.graduationproject.adapter.LocalVideoAdapter;
import com.licong.graduationproject.bean.LocalVideo;
import com.licong.graduationproject.utils.LocalVideoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by licong on 3/31/17.
 * 本地视频的Activity
 */

public class CollectionActivity extends AppCompatActivity  {

    private List<LocalVideo>localVideos=new ArrayList<>();
    private LocalVideoUtils localVideoUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_layout);
        initToor();
        initVideoList();
        initinterface();
    }
    //初始化Toolbar
    private void initToor(){
        //得到Toolbar的实例传入setSupportActionBar()
        // 既使用了Toolbar又让它外观与功能与ActionBar一致
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_collection);
        setSupportActionBar(toolbar);
        //调用getSupportActionBar()得到ActionBar实例，虽然ActionBar是由Toolbar完成的
        ActionBar actionBar = getSupportActionBar();
        //加个非空，防止出现错误
        if (actionBar != null) {
            //setDisplayHomeAsUpEnabled()方法让导航按钮显示出来
            // setHomeAsUpIndicator()设置一个导航按钮，
            // 实际上Toolbar最左侧的按钮就叫HomeAsUp，
            // 默认是一个返回的箭头，但我还是换成了drawer_home
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        }
    }
    //初始化界面
    public void initinterface(){
        //获取RecyclerView实例，同时使用recyclerView.setLayoutManager(linearLayoutManager)
        //将recyclerView变为列表
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.collection_recyclerview);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        LocalVideoAdapter adapter = new LocalVideoAdapter(localVideos);
        recyclerView.setAdapter(adapter);
    }
    //菜单点击时间
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //进入视频界面
            case R.id.bendi_button:
                break;
            //打开侧滑框
            //homeAsUp按钮id永远是android.R.id.home
            //openDrawer()将侧滑菜单展示出来，要求传入一个Gracity参数，这里传入了START
            case android.R.id.home:
                Intent intent_to_main = new Intent(CollectionActivity.this,MainActivity.class);
                startActivity(intent_to_main);
                finish();
                break;
        }
        return true;
    }
    //初始化视频数据
//    public void initVideoList(String text,int id){
//
//    }
}
