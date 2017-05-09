package com.jeff.footballmanager.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jeff.footballmanager.R;

public class FootManagerActivity extends BaseActivity {

	private ImageView imgMenu,backTo;
	private TextView menuTitle;
	private ListView listView;
	private List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		//初始化
		initView();
		//添加监听器
		setListener();
		//添加适配器
		setAdapter();
		
	}

	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				FootballDetailsActivity activity = new FootballDetailsActivity();
				startMyActivity(FootManagerActivity.this,activity,null,position);
				overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
			}
		});
		imgMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	@Override
	public void onBackPressed() {
		FootManagerActivity.this.finish();
		overridePendingTransition(R.anim.slide_from_bottom,
				R.anim.slide_to_top);
	}
	
	private void setAdapter() {
		SimpleAdapter adapter = new SimpleAdapter(this, getData(), R.layout.football_list_item, new String[]{
			"icon","name","descp","next"	
		}, new int[]{
			R.id.football_icon,R.id.foot_name,R.id.foot_discrpt,R.id.foot_next
		});
		listView.setAdapter(adapter);
	}

	private List<? extends Map<String, ?>> getData() {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("icon",R.drawable.palyer);
		map.put("name",getString(R.string.player));
		map.put("descp",getString(R.string.manager_player));
		map.put("next",R.drawable.next);
		data.add(map);
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("icon",R.drawable.team);
		map1.put("name",getString(R.string.team));
		map1.put("descp",getString(R.string.manager_team));
		map1.put("next",R.drawable.next);
		data.add(map1);
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("icon",R.drawable.competition);
		map3.put("name",getString(R.string.competition));
		map3.put("descp",getString(R.string.manager_competition));
		map3.put("next",R.drawable.next);
		data.add(map3);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("icon",R.drawable.techno);
		map2.put("name",getString(R.string.techno_count));
		map2.put("descp",getString(R.string.count_info));
		map2.put("next",R.drawable.next);
		data.add(map2);
		return data;
	}

	private void initView() {
		//初始化标题
		imgMenu = (ImageView) findViewById(R.id.imgMenumanager);
		imgMenu.setBackgroundResource(R.drawable.left171);
		menuTitle = (TextView) findViewById(R.id.menuTitlemanager);
		menuTitle.setText(getString(R.string.footballmanager));
		backTo = (ImageView) findViewById(R.id.backTomanager);
		backTo.setVisibility(View.GONE);
		listView = (ListView) findViewById(R.id.player_list_view);
	}

}
