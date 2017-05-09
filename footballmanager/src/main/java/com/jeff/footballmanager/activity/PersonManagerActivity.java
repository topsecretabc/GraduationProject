package com.jeff.footballmanager.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import com.jeff.footballmanager.param.StaticParam;
import com.tencent.tauth.Tencent;

public class PersonManagerActivity extends BaseActivity {

	protected static final int MODIFY_PSD = 11;
	private ImageView imgMenu,backTo;
	private TextView textTitle;
	private ListView listView;
	private Tencent mTencent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_manager);
		
		initView();
		setAdapter();
		setListener();
	}

	private void setListener() {
		imgMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0){
					SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
					boolean qqLogin = pref.getBoolean("login",false);
					if(!qqLogin){
						Intent intent = new Intent(PersonManagerActivity.this,AddFootballInfoActivity.class);
						intent.putExtra("type", MODIFY_PSD);
						startActivity(intent);
						overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
					}else{
						showToastSuccess(PersonManagerActivity.this,getString(R.string.to_register_info), 1000);
					}
				}else if(position==1){
					deleteUserInfo();
					if(!mTencent.isSessionValid())
						mTencent.logout(PersonManagerActivity.this);
					PersonManagerActivity.this.finish();
					startMyActivity(PersonManagerActivity.this,new LoginActivity(),null, 0);
					overridePendingTransition(R.anim.slide_from_bottom,
							R.anim.slide_to_top);
				}
			}
		});
	}

	protected void deleteUserInfo() {
		Editor edit= getSharedPreferences("userInfo",MODE_PRIVATE).edit();
		edit.putBoolean("checkStatus",false);
		edit.putString("name", "");
		edit.putString("psd", "");
		edit.putBoolean("login",false);
		edit.putString("userNo","");

		edit.commit();
		
		StaticParam.USER_NAME = "";
		StaticParam.USER_NO = "";
		StaticParam.HEAD_PATH = "";
		StaticParam.BG_PATH = "";
	}

	private void setAdapter() {
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("icon",R.drawable.modifypsd);
		map.put("name",getString(R.string.modifyPsd));
		map.put("descp",getString(R.string.modifyPsd_not_for_third_other));
		map.put("next",R.drawable.next);
		data.add(map);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("icon",R.drawable.outlogin);
		map2.put("name",getString(R.string.exit_and_clear));
		map2.put("descp",getString(R.string.exit_and_clear_info));
		map2.put("next",R.drawable.next);
		data.add(map2);
		SimpleAdapter adapter = new SimpleAdapter(PersonManagerActivity.this,
			data,R.layout.football_list_item,new String[]{
			"icon","name","descp","next"	
		},new int[]{
			R.id.football_icon,R.id.foot_name,R.id.foot_discrpt,R.id.foot_next	
		});
		listView.setAdapter(adapter);
	}

	@Override
	public void onBackPressed() {
		PersonManagerActivity.this.finish();
		overridePendingTransition(R.anim.slide_from_bottom,
				R.anim.slide_to_top);
	}
	
	private void initView() {
		imgMenu = (ImageView) findViewById(R.id.imgMenuperson);
		imgMenu.setBackgroundResource(R.drawable.left171);
		backTo = (ImageView) findViewById(R.id.backToperson);
		textTitle = (TextView) findViewById(R.id.menuTitleperson);
		listView = (ListView) findViewById(R.id.person_list_view);
		mTencent = Tencent.createInstance(StaticParam.QQ_APP_ID,PersonManagerActivity.this);
	}
}
