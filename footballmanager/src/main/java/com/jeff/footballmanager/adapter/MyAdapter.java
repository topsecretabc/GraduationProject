package com.jeff.footballmanager.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.domain.MenuItem;

public class MyAdapter extends BaseAdapter {

	private List<MenuItem> data;
	private int resource;
	private Context context;
	
	public MyAdapter(Context context,int resource,List<MenuItem> data) {
		this.resource = resource;
		this.context = context;
		this.data = data;
	}
	
	//返回当前项视图到容器中
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//获取当前项数据，在加载好控件信息后添加此信息
		MenuItem item = data.get(position);
		//初始化视图
		View view;
		//初始化视图控件类，加载完成视图控件后存储，避免重复加载
		ViewHolder holder;
		
		//主要思路
		//1.如果未加载过view中控件信息，则加载，完成之后存储到view的tag中
		//2.若加载过，则view会返回之前加载过的控件信息，可直接从view中获取到
		//3.给控件添加数据
		if(convertView==null){
			holder = new ViewHolder();
			//如果没有缓存，第一次进入加载控件视图
			view = LayoutInflater.from(context).inflate(resource, null);
			//获取到视图下的控件
			holder.imageView = (ImageView) view.findViewById(R.id.itemImg);
			holder.textView = (TextView) view.findViewById(R.id.itemName);
			//将加载完成后的控件信息存储到view中，下个item时会返回此view不用再次获取控件
			view.setTag(holder);
		}else{
			//将返回的数据赋值给view
			view = convertView;
			//将上次已经加载好的控件信息获取
			holder = (ViewHolder) view.getTag();
		}
		//添加数据给控件
		holder.imageView.setBackgroundResource(item.getImageId());
		holder.textView.setText(item.getItemName());
		//返回view
		return view;
	}

	//返回数据总数
	@Override
	public int getCount() {
		return null!=data?data.size():0;
	}

	//返回当前项
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	//返回当前项id
	@Override
	public long getItemId(int position) {
		return position;
	}

	//临时存储控件信息
	class ViewHolder{
		
		ImageView imageView;
		TextView textView;
		
	}
}
