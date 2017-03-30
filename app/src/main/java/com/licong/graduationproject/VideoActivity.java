package com.licong.graduationproject;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.view.PagerAdapter;


import com.licong.graduationproject.adapter.VideoLeftFragmentAdapter;
import com.licong.graduationproject.adapter.VideoRightFragmentAdapter;
import com.licong.graduationproject.fragment.VideoLeftFragment;
import com.licong.graduationproject.fragment.VideoRightFragment;


import java.util.ArrayList;
import java.util.List;

public class  VideoActivity extends AppCompatActivity {

    //构建一个listview需要传入数据的对象
//    private String[] data = {"sdasd", "dqqtweg", "dadadafweg",
//            "fsdfwegwg", "fsdggww", "fwegwre", "wegwhuig", "dqwtqeyg", "qfgqeqegqeg", "gwrgwgwh", "wheheh", "tjtrjy", "ert", ",qwr", "wegwgw"};
    private ViewPager mRightViewPager;
    private ViewPager mLeftViewPager;
    private PercentRelativeLayout mPercentRelativeLayout;
    private Toolbar toolbar;
    private TabLayout mTabLayout;
    private VideoRightFragmentAdapter mRightFragmentAdapteradapter;
    private VideoLeftFragmentAdapter mleftFragmentAdapteradapter;
    private List<String>leftFramgmentlist;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);
        mPercentRelativeLayout = (PercentRelativeLayout) findViewById(R.id.video_layout);
        //初始化界面
        initViewPager();
    }

    public void initViewPager() {
        //得到Toolbar的实例传入setSupportActionBar()
        // 既使用了Toolbar又让它外观与功能与ActionBar一致
        toolbar = (Toolbar) findViewById(R.id.toolbar_video);
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
        //悬浮按钮的点击事件
        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.play_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //等api申请好
            }
        });
        List<String> titles = new ArrayList<>();
        titles.add("简介");
        titles.add("讨论");
        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new VideoLeftFragment());
        fragments.add(new VideoRightFragment());
        //创建一个mRightFragmentAdapteradapter
        mRightFragmentAdapteradapter =
                new VideoRightFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        //给RightViewPager设置适配器
        mRightViewPager.setAdapter(mRightFragmentAdapteradapter);
        //将TabLayout和ViewPager关联起来
        mTabLayout.setupWithViewPager(mRightViewPager);
//        //给TabLayout设置适配器
//        mTabLayout.setTabsFromPagerAdapter(mRightFragmentAdapteradapter);
        mLeftViewPager.setAdapter
                (new VideoLeftFragmentAdapter(getSupportFragmentManager(),leftFramgmentlist));
        mTabLayout.setupWithViewPager(mLeftViewPager);
        this.mLeftViewPager = (ViewPager) findViewById(R.id.video_viewPager);
        this.mRightViewPager = (ViewPager) findViewById(R.id.video_viewPager);
        this.tab = (TabLayout) findViewById(R.id.video_tabLayout);
}
    //给视频界面创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_video,menu);
        return true;
    }
    //定义视频界面菜单响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //进入关于界面
            case R.id.video_about:
                Intent intent_video_about=new Intent(VideoActivity.this,AboutActivity.class);
                startActivity(intent_video_about);
                break;
            //返回主界面
            //homeAsUp按钮id永远是android.R.id.home
            case android.R.id.home:
                finish();
                break;
            case R.id.video_setup:
                Intent intent_video_setting=new Intent(VideoActivity.this,SettingActivity.class);
                startActivity(intent_video_setting);
                break;
        }
        return true;
    }

}
