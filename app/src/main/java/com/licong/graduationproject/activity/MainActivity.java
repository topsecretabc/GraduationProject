package com.licong.graduationproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.licong.graduationproject.API.API;
import com.licong.graduationproject.R;
import com.licong.graduationproject.bean.IntenetVideo;
import com.licong.graduationproject.bean.MainInterface;
import com.licong.graduationproject.adapter.MainInterfaceAdapter;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //侧滑控件
    private DrawerLayout mDrawerLayout;
    //标题的集合
    List<String> titles = new ArrayList<>();
    //标题的集合
    List<String> images = new ArrayList<>();
    //标题的集合
    List<String> contids = new ArrayList<>();
    //收到一个消息告诉主线程json字符串已经解析完，进行下一步将mainInterface放入适配器中
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    MainInterfaceAdapter mainInterfaceAdapter = new MainInterfaceAdapter(titles,images,contids,MainActivity.this);
                    recyclerView.setAdapter(mainInterfaceAdapter);
                    break;
            }

        }
    };

    private RecyclerView recyclerView;
    //侧滑菜单
    private NavigationView mNavigationView;
    //
    private IntenetVideo mTitlelist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        //得到NavigationView的实例
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        //设置mNavigationView中item的监听事件
        mNavigationView.setNavigationItemSelectedListener(this);
        //第一个参数指定瀑布流布局的列数，第二个指定布局的排列方向
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        //网络请求
        sendRequestWithOkHttp();
        //设置欢迎界面打开与否
        welcome();
        //初始化整个主界面
        initFace();
//        sendRequestWithHttpURLConnection();


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
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

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
//    用HttpURLConnection的方法做网络请求的方法
//    private void sendRequestWithHttpURLConnection() {
//        // 开启线程来发起网络请求
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpURLConnection connection = null;
//                BufferedReader reader = null;
//                try {
//                    URL url = new URL("http://app.pearvideo.com/clt/jsp/v1/home.jsp");
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setConnectTimeout(8000);
//                    connection.setReadTimeout(8000);
//                    InputStream in = connection.getInputStream();
//                    // 下面对获取到的输入流进行读取
//                    reader = new BufferedReader(new InputStreamReader(in));
//                    StringBuilder response = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        response.append(line);
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (reader != null) {
//                        try {
//                            reader.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (connection != null) {
//                        connection.disconnect();
//                    }
//                }
//            }
//        }).start();
//    }
    //发送网络请求因为是耗时操作所以开启子线程操作
    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(API.HOME_API)
                            //设置请求头
                            // X-Channel-Code 	固定值 official，可为空
//                    X-Client-Agent 	手机品牌，例如 Xiaomi，可为空
//                    X-Client-Hash 	长度为32的小写字母和数字混合字符串，例2f3d6ffkda95dlz2fhju8d3s6dfges3t，可为空
//                    X-Client-ID 	    长度为的15数字字符串，例123456789123456，可为空
//                    X-Client-Version 	为空即可
//                    X-Long-Token 	    为空即可
//                    X-Platform-Type 	为空即可
//                    X-Platform-Version 	为空即可
//                    X-Serial-Num 	Unix    时间戳
//                    X-User-ID 	    为空即可
                            //示例
//                    X-Channel-Code:official
//                    X-Client-Agent:Xiaomi
//                    X-Client-Hash:2f3d6ffkda95dlz2fhju8d3s6dfges3t
//                    X-Client-ID:123456789123456
//                    X-Client-Version:2.3.2
//                    X-Long-Token:
//                    X-Platform-Type:0
//                    X-Platform-Version:5.0
//                    X-Serial-Num:1492140134
//                    X-User-ID:
                            .addHeader("X-Channel-Code","official")
                            .addHeader("X-Client-Agent","Xiaomi")
                            .addHeader("X-Client-Hash","2f3d6ffkda95dlz2fhju8d3s6dfges3t")
                            .addHeader("X-Client-ID","123456789123456")
                            .addHeader("X-Client-Version","2.3.2")
                            .addHeader("X-Long-Token","")
                            .addHeader("X-Platform-Type","0")
                            .addHeader("X-Platform-Version","5.0")
                            .addHeader("X-Serial-Num",""+System.currentTimeMillis())
                            .addHeader("X-User-ID","")
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //解析json
    private void parseJSONWithGSON(String jsonData) {
        Gson gson = new Gson();
        //解析这种非常复杂的嵌套的json需要先创建一个基类的对象然后再用其拿到数据
        mTitlelist = gson.fromJson(jsonData, IntenetVideo.class);
        //遍历获得数据存入集合中
        for (int i = 0; i < mTitlelist.getDataList().size(); i++) {
            for (int b = 0; b < mTitlelist.getDataList().get(i).getContList().size(); b++) {
                String contid = mTitlelist.getDataList().get(i).getContList().get(b).getContId();
                contids.add(contid);
                String title = mTitlelist.getDataList().get(i).getContList().get(b).getName();
                titles.add(title);
               // Log.e("licong","aaa"+titles);
                String image = mTitlelist.getDataList().get(i).getContList().get(b).getPic();
                images.add(image);

        }
        }
        //发送一个消息告诉主线程json字符串已经解析完
        handler.sendEmptyMessage(1);
    }

//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1052276
//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1071092
//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1074991
//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1073806
//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1073926
//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1074852
//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1074760
//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1074645
//                05-05 16:46:22.522 21385-21781/com.licong.graduationproject E/lciong: aaaaa1074708

//        mImagelist =gson.fromJson(jsonData,InternetEntity.class);
//        for(int i=0;i<mImagelist.getItemList().size();i++){
//            String image = mImagelist.getItemList().get(i).getData().getCover().getFeed();
//            images.add(image);
//        }
//        Gson gson = new Gson();
//        List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>() {}.getType());
//        for (IntenetVideo.DataListBean.ContListBean app : jsonBean) {
//            Log.d("MainActivity", "id is " + app.getContId());
//            Log.d("MainActivity", "name is " + app.getName());
//        }
//        }


    //设置头像的跳转事件
    public void toLoginActivity(View v) {
        Intent intent_login = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent_login);
    }

    //点击电视图片关闭侧滑
    public void toMainActivity(View v) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
    //设置菜单点击时间
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        //跳转到本地视频
        else if (id == R.id.nav_collection) {
            Intent intent_to_collection= new Intent(MainActivity.this,LocalVideoActivity.class);
            startActivity(intent_to_collection);
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
