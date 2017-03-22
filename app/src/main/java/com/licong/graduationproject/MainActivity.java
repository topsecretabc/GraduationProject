package com.licong.graduationproject;

import android.content.Intent;
import android.media.midi.MidiInputPort;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.licong.graduationproject.adapter.MainInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<MainInterface> mainInterfacesList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //初始化界面
        initMainInterfaces();
        //得到recyclerView的实例
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.main_recycler);
        StaggeredGridLayoutManager  layoutManager=
             new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);


                        //得到Toolbar的实例传入setSupportActionBar()
        // 既使用了Toolbar又让它外观与功能与ActionBar一致
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_main);
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
            // 默认是一个返回的箭头，但我还是换成了drawer_home
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.drawer_home);
        }
    }

    private void initMainInterfaces() {
    }

    //给主界面创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //进入视频界面
            case R.id.main_to_video:
                Intent intent_to_video=new Intent(MainActivity.this,VideoActivity.class);
                startActivity(intent_to_video);
                break;
            //打开侧滑框
            //homeAsUp按钮id永远是android.R.id.home
            //openDrawer()将侧滑菜单展示出来，要求传入一个Gracity参数，这里传入了START
            case  android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
