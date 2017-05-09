package com.jeff.footballmanager.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeff.footballmanager.R;

public class NewsAdapter extends BaseAdapter {

	private List<String> data;
	private Context context;
	private int resource;
	
	public NewsAdapter(Context context,int resource,List<String> data) {
		this.resource = resource;
		this.context = context;
		this.data = data;
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
		String title = data.get(position);
		View view;
		ViewHolder holder;
		
		if(convertView==null){
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(resource, null);
			holder.textView = (TextView) view.findViewById(R.id.newsTextTitle);
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		holder.textView.setText(title);
		if(position==0){
			holder.textView.setTextColor(context.getResources().getColor(R.color.red));
		}
		return view;
	}
	
	//临时存储控件信息
		class ViewHolder{
			TextView textView;
		}

}
