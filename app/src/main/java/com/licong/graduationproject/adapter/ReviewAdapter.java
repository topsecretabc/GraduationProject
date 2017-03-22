package com.licong.graduationproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.licong.graduationproject.R;

import java.util.List;

/**
 * Created by licong on 3/22/17.
 * 讨论布局的适配器
 **/

public class ReviewAdapter extends ArrayAdapter<Review> {
        private  int resourceId;
        //重写父类构造函数分别传入上下文（即context），ListView子项布局id，数据
        public ReviewAdapter
                (Context context,int textViewResourceId,List<Review> objects){
            super(context,textViewResourceId,objects);
            resourceId=textViewResourceId;
        }
    //重写getView方法，这个方法在子项被滚动到屏幕内都会被调用
    //getItem()方法得到当前项的实例，然后使用LayoutInflater来为这个子项加载传入的布局
    //LayoutInflater的inflate()方法接受3个参数，最后一个指成false。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review review=getItem(position);//获取当前项的Review实例
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView ReviewImage=(ImageView)view.findViewById(R.id.review_userimage);
        TextView  ReviewUserId=(TextView)view.findViewById(R.id.review_userid);
        ReviewImage.setImageResource(review.getImageId());
        ReviewUserId.setText(review.getUser_id());
        return view;
    }
}


































