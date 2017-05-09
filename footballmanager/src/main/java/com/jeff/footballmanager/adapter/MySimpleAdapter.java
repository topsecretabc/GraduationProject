package com.jeff.footballmanager.adapter;

import java.util.List;
import java.util.Map;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.activity.EditInfoActivity;
import com.jeff.footballmanager.activity.FootballDetailsActivity;
import com.jeff.footballmanager.activity.TeamRankActivity;
import com.jeff.footballmanager.adapter.MyNewsListViewAdapter.ViewHolder;

import android.R.layout;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MySimpleAdapter extends BaseAdapter {

	private List<Map<String,Object>> data;
	private int resource;
	private Context context;
	private boolean[] checkStatus = null;

	public static final int CHECK_HIDDEN = 0;
	public static final int CHECK_SHOW = 1;
	public static int showType = CHECK_HIDDEN; 
	public static int PAGE_ITEM = 0;
	
	public MySimpleAdapter(Context context,int resource,List<Map<String,Object>> data){
		this.context = context;
		this.data = data;
		this.resource = resource;
		if(data!=null)
		this.checkStatus = new boolean[data.size()];
	}
	
	public boolean[] getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(boolean[] checkStatus) {
		this.checkStatus = checkStatus;
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
			
			holder.layout = (LinearLayout) view.findViewById(R.id.lineay);
			holder.checkBox = (CheckBox) view.findViewById(R.id.mycheckBox);
			holder.nameText = (TextView) view.findViewById(R.id.details_name);
			holder.detailsMore = (TextView) view.findViewById(R.id.details_more);
			holder.telText = (TextView) view.findViewById(R.id.details_tel);
			
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		
		holder.nameText.setText(data.get(position).get("name").toString());
		holder.detailsMore.setText(data.get(position).get("country").toString());
		holder.telText.setText(data.get(position).get("tel").toString());
		
		final int item = position;
		if(showType==CHECK_HIDDEN){
			holder.checkBox.setVisibility(View.GONE);
			//跳转到详情页面
			holder.layout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context,EditInfoActivity.class);
					context.startActivity(intent);
					Toast.makeText(context, "click",1000).show();
				}
			});
			
			holder.layout.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					Toast.makeText(context, "long - click",1000).show();
					return true;
				}
			});
			
		}else{
			holder.checkBox.setVisibility(View.VISIBLE);
			holder.checkBox.setChecked(checkStatus[item]);
			
			holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					checkStatus[item] = isChecked;
				}
			});
		}

		return view;
	}
	
	//临时存储控件信息
	class ViewHolder{
		LinearLayout layout;
		CheckBox checkBox;
		TextView nameText,detailsMore,telText;
	}
}
