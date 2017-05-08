package com.licong.graduationproject.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.licong.graduationproject.R;
import com.licong.graduationproject.adapter.LocalVideoListViewAdapter;
import com.licong.graduationproject.bean.LocalVideo;
import com.licong.graduationproject.utils.LoadedImage;
import com.licong.graduationproject.utils.LocalVideoUtils;


import java.util.List;

/**
 * Created by licong on 3/31/17.
 * 本地视频的Activity
 */

public class LocalVideoActivity extends Activity {

    ListView listView;
    LocalVideoListViewAdapter localVideoListViewAdapter;
    List<LocalVideo> listVideos;
    int videoSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localvideo_layout);
        //获得视频信息的对象
        LocalVideoUtils provider = new LocalVideoUtils(this);
        //通过LocalVideoUtils()中的getList方法获得LocalVideo的对象
        listVideos = provider.getList();
        //获得size
        videoSize = listVideos.size();
        //加载listview的适配器
        localVideoListViewAdapter = new LocalVideoListViewAdapter(this, listVideos);
        listView = (ListView) findViewById(R.id.local_listview);
        listView.setAdapter(localVideoListViewAdapter);
        //去除横线
        listView.setDivider(null);
        //返回按钮点击事件
        Button button_back = (Button)findViewById(R.id.local_main);
        button_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent_main = new Intent(LocalVideoActivity.this,MainActivity.class);
                LocalVideoActivity.this.startActivity(intent_main);
            }
        });

        //listview点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //跳转到PlayLocalVideoActivity同时将listvideo的信息先放入Bundle中再传递过去
                Intent intent_PlayVideo = new Intent(LocalVideoActivity.this, PlayLocalVideoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("video", listVideos.get(position));
                intent_PlayVideo.putExtras(bundle);
                startActivity(intent_PlayVideo);
            }
        });
        //加载图片
       loadImages();
    }

    /**
     * 加载图片
     */
    private void loadImages() {
        //   getLastNonConfigurationInstance(),当Device configuration发生改变时，将伴随Destroying被系统调用。
        //  通过这个方法可以像onSaveInstanceState()的方法一样保留变化前的Activity State。
        //  当Activity曾经通过某个资源得到一些图片或者信息，那么当再次恢复后，无需重新通过原始资源地址获取，可以快速的加载整个Activity状态信息。
        //  当Activity包含有许多线程时，在变化后依然可以持有原有线程，无需通过重新创建进程恢复原有状态。
        //  当Activity包含某些Connection Instance时，同样可以在整个变化过程中保持连接状态。
        final Object data = getLastNonConfigurationInstance();
        if (data == null) {
            //序列化
            new LoadImagesFromSDCard().execute();
        } else {
            final LoadedImage[] photos = (LoadedImage[]) data;
            if (photos.length == 0) {
                new LoadImagesFromSDCard().execute();
            }
            for (LoadedImage photo : photos) {
                addImage(photo);
            }
        }
    }
    //放入图片
    private void addImage(LoadedImage... value) {
        for (LoadedImage image : value) {
            localVideoListViewAdapter.addPhoto(image);
            localVideoListViewAdapter.notifyDataSetChanged();
        }
    }
    //加载图片，同loadImages()中的getLastNonConfigurationInstance()一起做activity状态的保存和保持。
    @Override
    public Object onRetainNonConfigurationInstance() {
        final ListView grid = listView;
        final int count = grid.getChildCount();
        final LoadedImage[] list = new LoadedImage[count];

        for (int i = 0; i < count; i++) {
            final ImageView v = (ImageView) grid.getChildAt(i);
            list[i] = new LoadedImage(
                    ((BitmapDrawable) v.getDrawable()).getBitmap());
        }

        return list;
    }
    //获取视频缩略图参数分别是地址，宽，高，类型
    private Bitmap getVideoThumbnail(String videoPath, int width , int height, int kind){
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
    //因为加载图片是一个耗时操作，所以开启一个线程池，做异步处理
    class LoadImagesFromSDCard extends AsyncTask<Object, LoadedImage, Object> {
        @Override
        protected Object doInBackground(Object... params) {
            Bitmap bitmap = null;
            for (int i = 0; i < videoSize; i++) {
                bitmap = getVideoThumbnail(listVideos.get(i).getPath(), 100, 80, MediaStore.Video.Thumbnails.MINI_KIND);
                if (bitmap != null) {
                    publishProgress(new LoadedImage(bitmap));
                }
            }
            return null;
        }

        @Override
        public void onProgressUpdate(LoadedImage... value) {
            addImage(value);
        }
    }
    //发送一个广播用以获得地址
    @Override
    protected void onDestroy() {
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath())));
        super.onDestroy();
    }
    //返回键的复写，因为原先除了一点buff，按返回键程序会直接退出，所以复写返回键用以回到主界面
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent_main = new Intent(LocalVideoActivity.this,MainActivity.class);
        LocalVideoActivity.this.startActivity(intent_main);
    }

}

