package com.licong.graduationproject;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.licong.graduationproject.adapter.Review;
import com.licong.graduationproject.adapter.ReviewAdapter;

import java.util.ArrayList;
import java.util.List;

public class  VideoActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    //构建一个listview需要传入数据的对象
    private String[] data={"sdasd","dqqtweg","dadadafweg",
            "fsdfwegwg","fsdggww","fwegwre","wegwhuig","dqwtqeyg","qfgqeqegqeg","gwrgwgwh","wheheh","tjtrjy","ert",",qwr","wegwgw"};
    private List<Review> ReviewList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);
        initReview();//初始化讨论数据
        //创建ReviewAdapter对象，作为适配器传给ListView
        ReviewAdapter reviewadapter=new ReviewAdapter
                (VideoActivity.this,R.layout.video_review,ReviewList);
        //得到Toolbar的实例传入setSupportActionBar()
        // 既使用了Toolbar有让它外观与功能与ActionBar一致
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_video);
        setSupportActionBar(toolbar);
        //取得DrawerLayout实例,DrawerLayout为主界面布局
        mDrawerLayout=(DrawerLayout) findViewById(R.id.main_layout);
        //调用getSupportActionBar()得到ActionBar实例，虽然ActionBar是由Toolbar完成的
        ActionBar actionBar=getSupportActionBar();
        //加个非空，防止出现错误
        if(actionBar != null){
            //setDisplayHomeAsUpEnabled()方法让导航按钮显示出来
            // setHomeAsUpIndicator()设置一个导航按钮，
            // 实际上Toolbar最左侧的按钮就叫HomeAsUp，
            // 默认是一个返回的箭头，但我还是换成了back_black
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back_black);
        }
        //ArrayAdapter通过泛型来指定要适配的数据类型，提供多个构造函数重载
        //此处ArrayAdapter（当前上下文，ListView子项布局，适配的数据）
        ArrayAdapter<String>adapter=new ArrayAdapter<String>
                (VideoActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listView=(ListView)findViewById(R.id.video_listview);
        listView.setAdapter(adapter);
        /*
        隐藏ActionBar（标题栏）
        ActionBar actionBar=getSupportActionBar();
        if(actionBar !=null){
           actionBar.hide();
         }
         */
    }
    //加入讨论数据（等api申请好）
    private void initReview() {

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
                Log.e("wanghao","intent_video_setting");
                startActivity(intent_video_setting);
                break;

        }
        return true;
    }

}
