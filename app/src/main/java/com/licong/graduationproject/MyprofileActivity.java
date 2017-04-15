package com.licong.graduationproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.licong.graduationproject.adapter.ProfileItem;
import com.licong.graduationproject.adapter.ProfileItemAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by licong on 3/31/17.
 */

public class MyprofileActivity extends AppCompatActivity {

    public  static  final int TAKE_PHOTO = 1;

   // private FloatingActionButton takePhoto;

    private CircleImageView picture;

    private Uri imageUri;

    private List<ProfileItem> ProfileList = new ArrayList<ProfileItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprofile_layout);
        // 获取头像CircleImageView的实例
        picture = (CircleImageView ) findViewById(R.id.profile_avater);
        //注册CircleImageView点击事件
        picture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //创建File对象，用于存储拍照后的图片
                //getExternalCacheDir()可以得到缓存目录
                //这里我们把图片命名为output_image，存放在缓存目录
                File outputImage = new File(getExternalCacheDir(),
                        "output_image.jpg");
                try {
                if(outputImage.exists()){
                    outputImage.delete();
                }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //判断版本是否低于Android7.0，从而选择获得imageUri方式
                //从7.0开始直接使用本地真实路径的Uri被认为不安全
                if (Build.VERSION.SDK_INT >=24){
                    //FileProvider.getUriForFile（）第一个参数context对象，
                    // 第二个可以使任意唯一的字符串，第三个就是刚刚创建的File对象
                    imageUri = FileProvider.getUriForFile(MyprofileActivity.this,
                            "com.licong.graduationproject.fileprovider",outputImage);
                }else {
                    //低于7.0就直接调用fromFile()将File对象转换成Uri对象
                    //这个Uri对象标识这图片的本地真事路径
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
//        initProfiles(); // 初始化数据
//        ProfileItemAdapter profileItemAdapter = new ProfileItemAdapter(MyprofileActivity.this, R.layout.profile_item, ProfileList);
//        ListView listView = (ListView) findViewById(R.id.profile_listview);
//        listView.setAdapter(profileItemAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
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