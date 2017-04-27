package com.licong.graduationproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.midi.MidiInputPort;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.licong.graduationproject.adapter.MainInterface;
import com.licong.graduationproject.adapter.MainInterfaceAdapter;

import java.util.ArrayList;
import java.util.List;

import java.lang.String;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //侧滑控件
    private DrawerLayout mDrawerLayout;
    //瀑布流布局的
    private List<MainInterface> mainInterfaceList = new ArrayList<>();

    private RecyclerView recyclerView;
    //侧滑菜单
    private NavigationView mNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //设置欢迎界面打开与否
        welcome();
        //初始化瀑布流界面
        initMainInterfaces();
        //初始化整个主界面
        initFace();

    }
    public void initFace(){
        //得到RecyclerView的实例
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        //得到NavigationView的实例
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置mNavigationView中item的监听事件
        mNavigationView.setNavigationItemSelectedListener(this);
        //第一个参数指定瀑布流布局的列数，第二个指定布局的排列方向
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //将mainInterface放入适配器中
        MainInterfaceAdapter mainInterfaceAdapter = new MainInterfaceAdapter(mainInterfaceList);
        recyclerView.setAdapter(mainInterfaceAdapter);
        //得到Toolbar的实例传入setSupportActionBar()
        // 既使用了Toolbar又让它外观与功能与ActionBar一致
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //取得DrawerLayout实例,DrawerLayout为主界面布局
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        //调用getSupportActionBar()得到ActionBar实例，虽然ActionBar是由Toolbar完成的
        ActionBar actionBar = getSupportActionBar();
        //加个非空，防止出现错误
        if (actionBar != null) {
            //setDisplayHomeAsUpEnabled()方法让导航按钮显示出来
            // setHomeAsUpIndicator()设置一个导航按钮，
            // 实际上Toolbar最左侧的按钮就叫HomeAsUp，
            // 默认是一个返回的箭头，但我还是换成了drawer_home
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.list_icn_mng);
        }
        //将nav_header布局渲染进来
        //  View view=LayoutInflater.from(this).inflate(R.layout.nav_header,mDrawerLayout,false);
        //   mAvatar = (CircleImageView)view.findViewById(R.id.nav_header_image);

    }

    //设置头像的跳转事件
    public void toLoginActivity(View v) {
        Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent_login);
    }

    //点击电视图片关闭侧滑
    public void toMainActivity(View v) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
    //设置瀑布流界面
    private void initMainInterfaces() {
    }

    //给主界面创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //进入视频界面
            case R.id.main_to_video:
                Intent intent_to_video = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent_to_video);
                break;
            //打开侧滑框
            //homeAsUp按钮id永远是android.R.id.home
            //openDrawer()将侧滑菜单展示出来，要求传入一个Gracity参数，这里传入了START
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }


    //设置mNavigationView中item的监听事件
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        //回到主页
        if (id == R.id.nav_home) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        //跳转到个人资料
        else if (id == R.id.nav_myprofile) {
            Intent intent_to_myprofile = new Intent(MainActivity.this, MyprofileActivity.class);
            startActivity(intent_to_myprofile);
        }
        //跳转到我的收藏
        else if (id == R.id.nav_collection) {
            Intent intent_to_collection= new Intent(MainActivity.this, CollectionActivity.class);
            startActivity(intent_to_collection);
        }
        //跳转到历史记录
        else if (id == R.id.nav_historyrecord) {
            Intent intent_to_historyrecord= new Intent(MainActivity.this, HistoryRecordActivity.class);
            startActivity(intent_to_historyrecord);
        }
        //跳转到设置与帮助
        else if (id == R.id.nav_settings) {
            Intent intent_to_settings = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent_to_settings);
        }
        else if (id == R.id.nav_textapp) {
            Intent intent_to_textapp = new Intent(MainActivity.this,ToTextAppActivity.class);
            startActivity(intent_to_textapp);
        }
        //退出
        else if (id == R.id.nav_dropout) {
                finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //设置欢迎界面打开与否
    public void welcome() {
        SharedPreferences preferences = getSharedPreferences("isFirstIn", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isFirstIn", true);
        // 提交修改
        editor.commit();
    }
}
