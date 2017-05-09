package com.jeff.footballmanager.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Checksum;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.adapter.MyAdapter;
import com.jeff.footballmanager.adapter.MySimpleAdapter;
import com.jeff.footballmanager.param.CompetitionAPIRetParam;
import com.jeff.footballmanager.param.PlayerAPIRetParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.TeamAPIRetParam;
import com.jeff.footballmanager.utils.GetApiAsyncTask;
import com.jeff.footballmanager.utils.GsonUtils;

public class FootballDetailsActivity extends BaseActivity {

	private ImageView imgMenu,backTo;
	private TextView menuTitle;
	private ListView listView;
	/**
	 * 当前项，球员，球队，球赛已经排名
	 */
	private int type;
	public static final int GET_PLAYER = 0;
	public static final int GET_TEAM = 1;
	public static final int GET_COMPETITION = 2;
	public static final int GET_TECHNO = 3;
	private String json = null;
	
	private MySimpleAdapter adapter = null;
	private boolean firstInit = false;
	private List<Map<String,Object>> result = null;
	private List<Map<String,Object>> listViewContent = null;

	private static final int DELETE_INFO = 0;
	private static final int GET_INFO = 1;
	private static final int ADD_INFO = 2;
	private static final int EDIT_INFO = 3;
	/**
	 * 当前状态，获取信息，编辑信息，删除信息，添加信息
	 */
	private static int CURRENT_STATUS = GET_INFO; 
	private static int myShowType = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_football_details);
		
		initView();
		getAPIData();
		setListener();
	}

	//调用异步方法获取数据
	private void getAPIData() {
		GetApiAsyncTask asyncTask = null;
		switch (type) {
		case GET_PLAYER:
			menuTitle.setText(getString(R.string.player));
			asyncTask = new GetApiAsyncTask(
				FootballDetailsActivity.this,
					StaticParam.API+StaticParam.API_GET_ALL_PLAYER+StaticParam.API_TOKEN);
			break;
		case GET_TEAM:
			menuTitle.setText(getString(R.string.team));
			asyncTask = new GetApiAsyncTask(
				FootballDetailsActivity.this,
					StaticParam.API+StaticParam.API_GET_ALL_TEAM+StaticParam.API_TOKEN);
			break;
		case GET_COMPETITION:
			menuTitle.setText(getString(R.string.competition));
			asyncTask = new GetApiAsyncTask(
				FootballDetailsActivity.this,
					StaticParam.API+StaticParam.API_GET_ALL_COMPETITION+StaticParam.API_TOKEN);
			break;
		case GET_TECHNO:
			menuTitle.setText(getString(R.string.techno_count));
			doAfterGetData("");
			break;
		default:
			break;
		}
		if(null!=asyncTask)
		{
			if(StaticParam.USER_NO.equals("")){
				SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
				StaticParam.USER_NO = pref.getString("userNo","");
			}
			asyncTask.execute("&userNo="+StaticParam.USER_NO);
		}
	}

	//异步任务完成以后调用重写的方法得到数据
	@Override
	public void doAfterGetData(String data) {
		this.json =  data;
		result = getData();
		if(CURRENT_STATUS==GET_INFO){
			listViewContent = result;
			if(StaticParam.FAULT.equals(listViewContent.get(0).get("msg"))){
				showToastFault(FootballDetailsActivity.this,getString(R.string.errorconnect), 1000);
			}else if(StaticParam.SUCCESS.equals(listViewContent.get(0).get("msg"))){
				showToastFault(FootballDetailsActivity.this,getString(R.string.no_data), 1000);
			}else{
				//得到数据之后绑定适配器
				if(!firstInit){
					adapter = new MySimpleAdapter(FootballDetailsActivity.this,R.layout.foot_details_list_item,listViewContent);
					listView.setAdapter(adapter);
					firstInit = true;
				}else{
					adapter.notifyDataSetChanged();
				}
			}
		}else{
			if(result.get(0).get("msg").equals("fault")){
				showToastFault(FootballDetailsActivity.this,getString(R.string.errorconnect), 1000);
				//操作失败重新加载一遍数据提示用户
				CURRENT_STATUS = GET_INFO;
				getAPIData();
			}else{
//				showToastSuccess(FootballDetailsActivity.this,getString(R.string.success_opeation), 1000);			
			}
		}
	}
	
	//get data from server
	private List<Map<String,Object>> getData() {
		List<Map<String,Object>> result = null;
		switch (type) {
		case GET_PLAYER:
			result = getPlayerData();
			break;
		case GET_TEAM:
			result = getTeamData();
			break;
		case GET_COMPETITION:
			result = getCompetitionData();
			break;
		case GET_TECHNO:
			result = getTechnoData();
			break;
		default:
			break;
		}
		return result;
	}

	private List<Map<String, Object>> getTechnoData() {
		CURRENT_STATUS=GET_INFO;
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name",getString(R.string.rank_player_assting));
		map.put("country","");
		map.put("tel",getString(R.string.count_asst));
		result.add(map);
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("name",getString(R.string.rank_player_score));
		map1.put("country","");
		map1.put("tel",getString(R.string.count_score));
		result.add(map1);
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("name",getString(R.string.rank_player_history_assting));
		map2.put("country","");
		map2.put("tel",getString(R.string.count_his_assi));
		result.add(map2);
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("name",getString(R.string.rank_player_history_score));
		map3.put("country","");
		map3.put("tel",getString(R.string.count_his_scor));
		result.add(map3);
		Map<String,Object> map4 = new HashMap<String,Object>();
		map4.put("name",getString(R.string.rank_team));
		map4.put("country","");
		map4.put("tel",getString(R.string.count_team));
		result.add(map4);
		return result;
	}

	private List<Map<String, Object>> getCompetitionData() {
		Map<String,Object> map = null;
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		CompetitionAPIRetParam competitionData = GsonUtils.getGson().fromJson(json,new CompetitionAPIRetParam().getClass());
		if(CURRENT_STATUS==GET_INFO){
			if(competitionData!=null&&!competitionData.getMsg().equals(StaticParam.FAULT))
			{	
				if(null==competitionData.getData()||competitionData.getData().size()==0){
					map = new HashMap<String,Object>();
					map.put("msg",StaticParam.SUCCESS);
					result.add(map);
					return result;
				}
				for(int i=0,j=competitionData.getData().size();i<j;i++){
					map = new HashMap<String,Object>();
					map.put("name",getString(R.string.win_)+competitionData.getData().get(i).getChampionTeam());
					map.put("country",getString(R.string.score_)+competitionData.getData().get(i).getChampionScore());
					map.put("tel",getString(R.string.lost_)+competitionData.getData().get(i).getSecondTeam()+"  "+getString(R.string.score_)+competitionData.getData().get(i).getSecondScore());
					map.put("no",""+competitionData.getData().get(i).getCompetitionNo());
					result.add(map);
				}
			}else{
				map = new HashMap<String,Object>();
				map.put("msg",StaticParam.FAULT);
				result.add(map);
			}
		}else{
			map = new HashMap<String,Object>();
			if(competitionData==null||competitionData.getMsg().equals(StaticParam.FAULT)){
				map.put("msg",StaticParam.FAULT);
			}else{
				map.put("msg",StaticParam.SUCCESS);
			}
			result.add(map);
		}		
		return result;
	}

	@Override
	public void onBackPressed() {
		if(myShowType==0){
			CURRENT_STATUS = GET_INFO;
			FootballDetailsActivity.this.finish();
			overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
		}else{
			myShowType=0;
			backTo.setBackgroundResource(R.drawable.add);
			adapter.setShowType(MySimpleAdapter.CHECK_HIDDEN);
			adapter.notifyDataSetChanged();
		}
	}
	
	private List<Map<String, Object>> getTeamData() {
		Map<String,Object> map = null;
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		TeamAPIRetParam teamData = GsonUtils.getGson().fromJson(json,new TeamAPIRetParam().getClass());
		if(CURRENT_STATUS==GET_INFO){
			if(teamData!=null&&!teamData.getMsg().equals(StaticParam.FAULT))
			{	
				if(null==teamData.getData()||teamData.getData().size()==0){
					map = new HashMap<String,Object>();
					map.put("msg",StaticParam.SUCCESS);
					result.add(map);
					return result;
				}
				for(int i=0,j=teamData.getData().size();i<j;i++){
					map = new HashMap<String,Object>();
					map.put("name",teamData.getData().get(i).getTeamName());
					map.put("country",getString(R.string.native_place_)+teamData.getData().get(i).getNativePlace());
					map.put("tel",getString(R.string.trainer_)+teamData.getData().get(i).getTrainer());
					map.put("no",""+teamData.getData().get(i).getTeamNo());
					result.add(map);
				}
			}else{
				map = new HashMap<String,Object>();
				map.put("msg",StaticParam.FAULT);
				result.add(map);
			}
		}else{
			map = new HashMap<String,Object>();
			if(teamData==null||teamData.getMsg().equals(StaticParam.FAULT)){
				map.put("msg",StaticParam.FAULT);
			}else{
				map.put("msg",StaticParam.SUCCESS);
			}
			result.add(map);
		}		
		return result;
	}

	private List<Map<String, Object>> getPlayerData() {
		Map<String,Object> map = null;
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		PlayerAPIRetParam playerData  = GsonUtils.getGson().fromJson(json,new PlayerAPIRetParam().getClass());
		if(CURRENT_STATUS==GET_INFO){
			if(playerData!=null&&!playerData.getMsg().equals(StaticParam.FAULT))
			{	
				if(null==playerData.getData()||playerData.getData().size()==0){
					map = new HashMap<String,Object>();
					map.put("msg",StaticParam.SUCCESS);
					result.add(map);
					return result;
				}
				for(int i=0,j=playerData.getData().size();i<j;i++){
					map = new HashMap<String,Object>();
					map.put("name",playerData.getData().get(i).getName());
					map.put("country",getString(R.string.role_)+playerData.getData().get(i).getRole());
					map.put("tel",getString(R.string.mobile_)+playerData.getData().get(i).getMobile());
					map.put("no",""+playerData.getData().get(i).getPlayerNo());
					result.add(map);
				}
			}else{
				map = new HashMap<String,Object>();
				map.put("msg",StaticParam.FAULT);
				result.add(map);
			}
		}else{
			map = new HashMap<String,Object>();
			if(playerData==null||playerData.getMsg().equals(StaticParam.FAULT)){
				map.put("msg",StaticParam.FAULT);
			}else{
				map.put("msg",StaticParam.SUCCESS);
			}
			result.add(map);
		}		
		return result;	
	}

	private void setListener() {
		//添加信息
		backTo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(myShowType==0){
					CURRENT_STATUS = ADD_INFO;
					Intent intent = new Intent(FootballDetailsActivity.this,AddFootballInfoActivity.class);
					intent.putExtra("type", type);
					startActivityForResult(intent, 0);
					FootballDetailsActivity.this.overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
				}else{
//					Builder dialog = new AlertDialog.Builder(FootballDetailsActivity.this);
//					dialog.setTitle(getString(R.string.hint_info_)).setMessage(getString(R.string.sure_to_delete));
//					dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
							CURRENT_STATUS=DELETE_INFO;
							boolean[] checkStatus = adapter.getCheckStatus();
							for(int i=checkStatus.length-1;i>=0;i--){
								if(checkStatus[i]){
									deleteItem(i);
									listViewContent.remove(i);
								}
							}
							adapter.setShowType(MySimpleAdapter.CHECK_HIDDEN);
							backTo.setBackgroundResource(R.drawable.add);
							myShowType=0;
							adapter.notifyDataSetChanged();
//						}
//					});
//						dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								dialog.cancel();
//							}
//						});
//						dialog.setCancelable(true);
//						dialog.show();
				}
			}
		});
		
//		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					int position, long id) {
//
//				final int item = position;
//				if(type != GET_TECHNO){
//					MySimpleAdapter.showType = MySimpleAdapter.CHECK_SHOW;
//					adapter.notifyDataSetChanged();
//					View layout = LayoutInflater.from(FootballDetailsActivity.this).inflate(R.layout.my_dialog,null);
//					ListView listV = (ListView) layout.findViewById(R.id.mydialog);
//					List<Map<String,String>> data = new ArrayList<>();
//					Map<String,String> map = new HashMap<>();
//					map.put("item","删除此项");
//					Map<String,String> map1 = new HashMap<>();
//					map1.put("item","多选删除");
//					Map<String,String> map2 = new HashMap<>();
//					map2.put("item","删除所有");
//					data.add(map);data.add(map1);data.add(map2);
//					listV.setAdapter(new SimpleAdapter(FootballDetailsActivity.this, data,R.layout.item,new String[]{"item"},new int[]{R.id.itemText}));
					
//					Builder dialog = new AlertDialog.Builder(FootballDetailsActivity.this);
//					dialog.setView(layout);
//					dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							CURRENT_STATUS=DELETE_INFO;
//							deleteItem(item);
//						}
//					});
//					dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							dialog.cancel();
//						}
//					});
//					dialog.setCancelable(true);
//					dialog.show();
					
//					listV.setOnItemClickListener(new OnItemClickListener() {
//						@Override
//						public void onItemClick(AdapterView<?> parent, View view,
//								int position, long id) {
//							switch (position) {
//							case 0:
//								showToastSuccess(FootballDetailsActivity.this,"删除当前项",1000);
//								CURRENT_STATUS=DELETE_INFO;
//								deleteItem(item);
//								break;
//							case 1:
//								showToastSuccess(FootballDetailsActivity.this,"多选删除",1000);				
//								break;
//							case 2:
//								showToastSuccess(FootballDetailsActivity.this,"删除所有项",1000);
//								break;
//							default:
//								break;
//							}
//						}
//					});
//				}
//				return true;
//			}
//		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(type == GET_TECHNO){
					startMyActivity(FootballDetailsActivity.this,new TeamRankActivity(),null, position);
					overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
				}else{
					Intent intent = new Intent(FootballDetailsActivity.this,EditInfoActivity.class);
					intent.putExtra("type",type);
					intent.putExtra("no",listViewContent.get(position).get("no").toString());
					startActivityForResult(intent, 1);
					overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
				}
			}
		});
		
		imgMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
	}

	//长按删除选中项
	protected void deleteItem(int position) {
		GetApiAsyncTask asyncTask = null;
		switch (type) {
		case GET_PLAYER:
			asyncTask = new GetApiAsyncTask(
				FootballDetailsActivity.this,
					StaticParam.API+StaticParam.API_DELETE_PLAYER
						+StaticParam.API_TOKEN
							+"&playerNo="+listViewContent.get(position).get("no"));
			break;
		case GET_TEAM:
			asyncTask = new GetApiAsyncTask(
				FootballDetailsActivity.this,
					StaticParam.API+StaticParam.API_DELETE_TEAM
						+StaticParam.API_TOKEN
							+"&teamNo="+listViewContent.get(position).get("no"));
			break;
		case GET_COMPETITION:
			asyncTask = new GetApiAsyncTask(
				FootballDetailsActivity.this,
					StaticParam.API+StaticParam.API_DELETE_COMPETITION
						+StaticParam.API_TOKEN
							+"&competitionNo="+listViewContent.get(position).get("no"));
			break;
		default:
			break;
		}
//		listViewContent.remove(position);
//		adapter.notifyDataSetChanged();		
		asyncTask.execute("");
	}

	private void initView() {
		//初始化标题
		imgMenu = (ImageView) findViewById(R.id.imgMenu2);
		imgMenu.setBackgroundResource(R.drawable.left171);
		menuTitle = (TextView) findViewById(R.id.menuTitle2);
		type = getIntent().getIntExtra("num",0);
		backTo = (ImageView) findViewById(R.id.backTo2);
		backTo.setBackgroundResource(R.drawable.add);
		if(type==3)	
			backTo.setVisibility(View.GONE);
		listView = (ListView) findViewById(R.id.foot_details_listview);
	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		switch (arg1) {
		case GET_PLAYER:
			type=GET_PLAYER;
			break;
		case GET_TEAM:
			type=GET_TEAM;
			break;
		case GET_COMPETITION:
			type=GET_COMPETITION;
			break;
		default:
			break;
		}
		CURRENT_STATUS=GET_INFO;
		getAPIData();
		firstInit=false;
	}

	
	public class MySimpleAdapter extends BaseAdapter {

		private List<Map<String,Object>> data;
		private int resource;
		private Context context;
		private boolean[] checkStatus = null;

		public static final int CHECK_HIDDEN = 0;
		public static final int CHECK_SHOW = 1;
		public int showType = CHECK_HIDDEN; 

		public int getShowType() {
			return showType;
		}

		public void setShowType(int showType) {
			this.showType = showType;
		}

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
						if(type == GET_TECHNO){
							startMyActivity(FootballDetailsActivity.this,new TeamRankActivity(),null,item);
							overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
						}else{
							Intent intent = new Intent(FootballDetailsActivity.this,EditInfoActivity.class);
							intent.putExtra("type",type);
							intent.putExtra("no",data.get(item).get("no").toString());
							startActivityForResult(intent, 1);
							overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
						}
					}
				});
				
				holder.layout.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						if(type != GET_TECHNO){
							Toast.makeText(context, getString(R.string.check_delete),Toast.LENGTH_LONG).show();
							showType = CHECK_SHOW;
							myShowType = CHECK_SHOW;
							backTo.setBackgroundResource(R.drawable.ok);
							adapter.notifyDataSetChanged();	
						}
						return true;
					}
				});
				
			}else{
				
				holder.layout.setOnClickListener(null);
				holder.layout.setOnLongClickListener(null);
				holder.checkBox.setVisibility(View.VISIBLE);
				holder.checkBox.setChecked(checkStatus[item]);
				
				holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						checkStatus[item] = isChecked;
					}
				});
			}

			return view;
		}
	}
	
	//临时存储控件信息
	class ViewHolder{
		LinearLayout layout;
		CheckBox checkBox;
		TextView nameText,detailsMore,telText;
	}
}
