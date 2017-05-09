package com.jeff.footballmanager.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.domain.news;

public class NewsXiaoHuaAdapter extends BaseAdapter {

	private List<news> data;
	private Context context;
	private int resource;
	private Bitmap map = null;
	private news news;
	
	@Override
	public int getCount() {
		return null!=data?data.size():0;
	}

	public NewsXiaoHuaAdapter(Context context,int resource,List<news> data) {
		this.data = data;
		this.resource = resource;
		this.context = context;
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
			holder.content = (TextView) view.findViewById(R.id.news_xiaohua_content);
			holder.news_source = (TextView) view.findViewById(R.id.news_title_source);
			holder.textView = (TextView) view.findViewById(R.id.news_xiaohua_title);
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		if(data!=null && data.size()>0){
			news = data.get(position);
			holder.textView.setText(news.getTitle());
			holder.news_source.setText("["+news.getSource().toString()+"]");
			if("".equals(news.getContent())){
				holder.content.setText(news.getDigest().toString());
			}else{
				holder.content.setText(news.getContent().toString());
			}
		}else{
		}

		return view;
	}
	
	//临时存储控件信息
	class ViewHolder{
		TextView textView,content,news_source;
	}

}
