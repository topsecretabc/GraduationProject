package com.licong.graduationproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.licong.graduationproject.R;
import com.licong.graduationproject.activity.LocalVideoActivity;
import com.licong.graduationproject.activity.PlayLocalVideoActivity;
import com.licong.graduationproject.bean.IntenetVideo;
import com.licong.graduationproject.bean.MainInterface;

import java.util.List;

/**
 * Created by licong on 3/22/17.
 * 主页面适配器
 */

public class MainInterfaceAdapter extends RecyclerView.Adapter<MainInterfaceAdapter.ViewHolder>{
    private Context context;

    private List<String> titles;
    private List<String> images;
    private List<String> contids;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    //定义一个内部类ViewHolder，继承RecyclerView.ViewHolder
    static  class  ViewHolder extends  RecyclerView.ViewHolder{
        View InternetVideoView;
       ImageView mainInterfaceImage;
        TextView mainInterfaceText;
        //super()里面传入View参数，通常就是RecyclerView子项的最外层布局
        public ViewHolder(View view){
            super(view);
            InternetVideoView = view;
            mainInterfaceImage=(ImageView)view
                    .findViewById(R.id.main_interface_image);
            mainInterfaceText=(TextView) view
                    .findViewById(R.id.main_interface_name);
        }
    }
    //构造函数，把要展示的数据源传进来，赋值给mainInterfaceList
    public  MainInterfaceAdapter(List<String>titles,List<String>images,List<String>contids,Context context){//List<IntenetVideo.DataListBean.ContListBean> mainInterfaceList){
        this.titles=titles;
        this.images=images;
        this.contids=contids;
        this.context=context;

    }
    //下面3个方法是必须重写的，以为继承自RecyclerView.Adapter
    //要进行抽象方法的重写，onCreateViewHolder()用于创建ViewHolder实例
    //并把加载出来的布局传入构造函数中
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_interface, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //给item设置点击事件
//        final ViewHolder holder=new ViewHolder(view);
//        holder.InternetVideoView.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Intent intent_PlayVideo = new Intent(context, PlayLocalVideoActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("video", contids.get(position));
//                intent_PlayVideo.putExtras(bundle);
//                context.startActivity(intent_PlayVideo);
        //----------------------------------------------------------------
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position,
//                                            long id) {
//                        Intent intent_PlayVideo = new Intent(LocalVideoActivity.this, PlayLocalVideoActivity.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("video", listVideos.get(position));
//                        intent_PlayVideo.putExtras(bundle);
//                        startActivity(intent_PlayVideo);
//                    }
//                });
//            }
//        });
        //-----------------------------------------------------------
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
        //------------------------------------------------------------------
        return holder;
    }
    //告诉recyclerView有多少子项，返回长度
    @Override
    public int getItemCount() {
        return titles.size();
    }
    //onBindViewHolder()方法用于对RecyclerView子项的数据进行赋值
    //会在每个子项被滚动到屏幕内执行通过position得到当前项的mainInterface实例
    //然后将数据设置到ViewHolder中
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide
                .with(context)//上下文
                .load(images.get(position))//url地址
                .override(500,(int)(100 + Math.random() * 300))//设置宽高
                .into(holder.mainInterfaceImage);//要放入的地方
          holder.mainInterfaceText.setText(titles.get(position));


    }
//------------------------------------------------------------------
    //设置item的点击事件的方法
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

}



































