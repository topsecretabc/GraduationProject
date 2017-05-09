package com.jeff.footballmanager.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.adapter.TeamRankAdapter;
import com.jeff.footballmanager.domain.Competition;
import com.jeff.footballmanager.domain.TeamRankDTO;
import com.jeff.footballmanager.param.CompetitionAPIRetParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.TeamRankAPIRetParam;
import com.jeff.footballmanager.utils.GetApiAsyncTask;
import com.jeff.footballmanager.utils.GsonUtils;

public class TeamRankActivity extends BaseActivity {

	private TextView title;
	private ImageView imgBack,imgAdd;
	private ListView listView;
	
	private List<Competition> lists = null;
	private List<TeamRankDTO> teamRank = null;
	
	private static final int ASSISTING = 0;
	private static final int GOOD_PLAYER = 1;
	private static final int ASSISTING_HISTORY = 2;
	private static final int GOOD_PLAYER_HISTORY = 3;
	private static final int TEAM_RANK = 4;
	private static int CURRENT_RANK = ASSISTING;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_rank);
		
		initView();
		getDataFromServer();
		setListener();
	}

	private void setListener() {
		imgBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}

	//获取排名信息
	private void getDataFromServer() {
		GetApiAsyncTask apiAsyncTask = null;
		switch (CURRENT_RANK) {
		case ASSISTING:
			apiAsyncTask = new GetApiAsyncTask(TeamRankActivity.this,
					StaticParam.API+StaticParam.API_TECHNO_ASSITING+StaticParam.API_TOKEN);
			break;
		case ASSISTING_HISTORY:
			apiAsyncTask = new GetApiAsyncTask(TeamRankActivity.this,
					StaticParam.API+StaticParam.API_TECHNO_HISTORY_ASSITING+StaticParam.API_TOKEN);		
					break;
		case GOOD_PLAYER:
			apiAsyncTask = new GetApiAsyncTask(TeamRankActivity.this,
					StaticParam.API+StaticParam.API_TECHNO_HIGH+StaticParam.API_TOKEN);
			break;
		case GOOD_PLAYER_HISTORY:
			apiAsyncTask = new GetApiAsyncTask(TeamRankActivity.this,
					StaticParam.API+StaticParam.API_TECHNO_HISTORY_HIGHT+StaticParam.API_TOKEN);
			break;
		case TEAM_RANK:
			apiAsyncTask = new GetApiAsyncTask(TeamRankActivity.this,
					StaticParam.API+StaticParam.API_TECHNO_TEAM_RANK+StaticParam.API_TOKEN);
			break;
		default:
			break;
		}
		apiAsyncTask.execute("&userNo="+StaticParam.USER_NO);
	}

	private void setAdapter(int i) {
		if(i!=TEAM_RANK)
		{
			TeamRankAdapter<Competition> adapter = new TeamRankAdapter<Competition>(this, R.layout.team_rank_item,
			lists, i);
			listView.setAdapter(adapter);
		}else{
			TeamRankAdapter<TeamRankDTO> adapter = new TeamRankAdapter<TeamRankDTO>(this, R.layout.team_rank_item,
			teamRank, i);
			listView.setAdapter(adapter);
		}
		
	}

	private void initView() {
		CURRENT_RANK = getIntent().getIntExtra("num",0);
		title = (TextView) findViewById(R.id.addInfoTitle);
		imgBack = (ImageView) findViewById(R.id.addInfoImg);
		imgAdd = (ImageView) findViewById(R.id.addInfoSave);
		imgAdd.setVisibility(View.GONE);
		listView = (ListView) findViewById(R.id.teamRankListView);
	}

	//解析数据并绑定到适配器
	@Override
	public void doAfterGetData(String data) {
		if(CURRENT_RANK!=TEAM_RANK){
			getData(data);
		}else{
			getTeamData(data);
		}
		setAdapter(CURRENT_RANK);
	}

	private void getTeamData(String data) {
		TeamRankAPIRetParam apiRetParam = GsonUtils.getGson().fromJson(data,
				TeamRankAPIRetParam.class);
			if(apiRetParam!=null){
				if(apiRetParam.getMsg()!=null && apiRetParam.getMsg().equals(StaticParam.SUCCESS)){
					teamRank = apiRetParam.getData();
					if(teamRank.size()>0){
						
					}else{
						showToastSuccess(this,getString(R.string.no_rank_data_to_add),showToastTimeShort);
					}
				}else{
					showToastFault(this,apiRetParam.getMsg(),1000);
				}
			}else{
				showToastFault(this,getString(R.string.errorconnect),1000);
			}
	}

	private void getData(String data) {
		CompetitionAPIRetParam apiRetParam = GsonUtils.getGson().fromJson(data,
			CompetitionAPIRetParam.class);
		if(apiRetParam!=null){
			if(apiRetParam.getMsg()!=null && apiRetParam.getMsg().equals(StaticParam.SUCCESS)){
				lists = apiRetParam.getData();
				if(lists.size()>0){
					
				}else{
					showToastSuccess(this,getString(R.string.no_rank_data_to_add),showToastTimeShort);
				}
			}else{
				showToastFault(this,apiRetParam.getMsg(),1000);
			}
		}else{
			showToastFault(this,getString(R.string.errorconnect),1000);
		}
	}
}
