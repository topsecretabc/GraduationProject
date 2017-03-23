package com.licong.graduationproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.licong.graduationproject.R;

import java.util.List;

/**
 * Created by licong on 3/22/17.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder>{

    private List<Setting> SettingList;
    //定义一个内部类ViewHolder，继承RecyclerView.ViewHolder
    static  class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView item;
        //super()里面传入View参数，通常就是RecyclerView子项的最外层布局
        //findViewId传入子项布局
        public ViewHolder(View view){
            super(view);
            item=(TextView) view
                    .findViewById(R.id.setting_item);
        }
    }
    //构造函数，把要展示的数据源传进来，赋值给mainInterfaceList
    public SettingAdapter(List<Setting> SettingList){
        this.SettingList=SettingList;
    }
    //下面3个方法是必须重写的，因为是继承自RecyclerView.Adapter
    //所以要进行抽象方法的重写，onCreateViewHolder()用于创建ViewHolder实例
    //并把加载出来的布局传入构造函数中
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.setting_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    //告诉recyclerView有多少子项，返回长度
    @Override
    public int getItemCount() {
        return SettingList.size();
    }
    //onBindViewHolder()方法用于对RecyclerView子项的数据进行赋值
    //会在每个子项被滚动到屏幕内执行通过position得到当前项的mainInterface实例
    //然后将数据设置到ViewHolder中
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Setting setting=SettingList.get(position);
        holder.item.setText(setting.getItemText());
    }

}



































