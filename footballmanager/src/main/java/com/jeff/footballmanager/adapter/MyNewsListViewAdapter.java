package com.jeff.footballmanager.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.domain.news;
import com.jeff.footballmanager.utils.ImageCacheUtils;
import com.jeff.footballmanager.utils.ImageViewAsyncTask;

public class MyNewsListViewAdapter extends BaseAdapter {

	private List<news> data;
	private Context context;
	private int resource;
	private news news;
	
	private ImageCacheUtils cacheUtils = null;
	
	public MyNewsListViewAdapter(Context context,int resource,List<news> data) {
		this.data = data;
		this.resource = resource;
		this.context = context;
		cacheUtils = new ImageCacheUtils(context);
	}
	
	@Override
	public int getCount() {
		return null!=data?data.size():0;
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view;
		ViewHolder holder;
	
		if(convertView==null){
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(resource, null);
			holder.textView = (TextView) view.findViewById(R.id.news_list_title);
			holder.imgView = (ImageView) view.findViewById(R.id.news_list_img);
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		if(data!=null && data.size()>0){
			news = data.get(position);
			// 给ImageView设置一个Tag，保证异步加载图片时不会乱序
			holder.imgView.setTag(news.getTop_image());
			holder.textView.setText(news.getTitle());
			if(news!=null && news.getTopmap()!=null){
				holder.imgView.setImageBitmap(news.getTopmap());
			}else{
				holder.imgView.setImageBitmap(null);
				holder.imgView.setBackgroundResource(R.drawable.bg);
			}
//			cacheUtils.disPlayImage(news.getTop_image())
		}

		return view;
	}
	
	//临时存储控件信息
	class ViewHolder{
		TextView textView;
		ImageView imgView;
	}

}
