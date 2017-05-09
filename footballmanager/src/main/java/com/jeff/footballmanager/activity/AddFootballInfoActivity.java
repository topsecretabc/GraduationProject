package com.jeff.footballmanager.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.param.APIRetParam;
import com.jeff.footballmanager.param.CompetitionAPIRetParam;
import com.jeff.footballmanager.param.CompetitionParam;
import com.jeff.footballmanager.param.PlayerAPIRetParam;
import com.jeff.footballmanager.param.PlayerParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.TeamAPIRetParam;
import com.jeff.footballmanager.param.TeamParam;
import com.jeff.footballmanager.param.User;
import com.jeff.footballmanager.param.UserParam;
import com.jeff.footballmanager.utils.GetApiAsyncTask;
import com.jeff.footballmanager.utils.GsonUtils;
import com.jeff.footballmanager.utils.InitSpinnerAsyncTask;

public class AddFootballInfoActivity extends BaseActivity implements OnClickListener {

	private static final int ADD_PALYER = 0,SUCCESS=0;
	private static final int ADD_TEAM = 1;
	private static final int ADD_COMPEITITON = 2;
	private static int CURRENT_TYPE = ADD_PALYER;
	/**
	 * 球队名称
	 */
	List<String> items = null;
	
	public static final String TEAM = "team";
	public static final String PLAYER = "player";
	
	private boolean initSprinner = false;
	
	//post请求参数实体
	private UserParam userParam;
	private PlayerParam player;
	private TeamParam team;
	private CompetitionParam competition;
	// 头部
	private ImageView addInfoBack,addInfoSave;
	private TextView titleText;
	// 添加球员相关变量
	private EditText playerName,playerHight,playerWeight,
	playerCountry,playerAge,playerTel,playerRole;
	// 添加球队相关变量
	private EditText teamName,teamTrainer,teamSologan,teamNativePalce;
	private Spinner playerPositionSprinner,teamNameSprinner;
	// 添加比赛相关变量
	private EditText hightPlayerScore,assistingPlayerScore,winScore,secondScore;
	private Spinner teamOneSpinner,teamTwoSpinner,highPlayerSpinner,assistingSpinner;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		
	}
	
	private void initTitle() {
		addInfoBack = (ImageView) findViewById(R.id.addInfoImg);
		addInfoSave = (ImageView) findViewById(R.id.addInfoSave);
		titleText = (TextView) findViewById(R.id.addInfoTitle);
		if(CURRENT_TYPE!=PersonManagerActivity.MODIFY_PSD){
			titleText.setText(getString(R.string.add));
		}else{
			titleText.setText(getString(R.string.modifyPsd));
		}
		addInfoBack.setOnClickListener(this);
		addInfoSave.setOnClickListener(this);
	}

	/**
	 * 根据添加的信息类型初始化不同的布局
	 */
	private void initView() {
		CURRENT_TYPE = getIntent().getIntExtra("type", ADD_PALYER);
		switch (CURRENT_TYPE) {
		case ADD_PALYER:
			initPlayerView();
			break;
		case ADD_TEAM:
			initTeamView();		
			break;
		case ADD_COMPEITITON:
			initCompetitionView();
			break;	
		case PersonManagerActivity.MODIFY_PSD:
			initModifyPsd();
			break;
		default:
			break;
		}
	}

	private void initModifyPsd() {
		setContentView(R.layout.add_team_info);
		//初始化相同的头部信息
		initTitle();
		initModifyPsdContentView();
	}

	private void initTeamView() {
		setContentView(R.layout.add_team_info);
		//初始化相同的头部信息
		initTitle();
		initTeamContentView();
	}

	private void initCompetitionView() {
		setContentView(R.layout.add_competition_info);
		//初始化相同的头部信息
		initTitle();
		initCompetitionContentView();
	}

	private void initPlayerView() {
		setContentView(R.layout.add_player_info);
		//初始化相同的头部信息
		initTitle();
		initPlayerContentView();
	}

	private void initModifyPsdContentView() {
		teamName = (EditText) findViewById(R.id.add_team_name);
		teamTrainer = (EditText) findViewById(R.id.add_team_Trainer);
		teamNativePalce = (EditText) findViewById(R.id.add_team_NativePlace);
		teamSologan = (EditText) findViewById(R.id.add_team_Sologan);
		teamSologan.setVisibility(View.GONE);
		teamName.setHint(getString(R.string.inputOldPsd));
		teamTrainer.setHint(getString(R.string.inputNewPsd));
		teamNativePalce.setHint(getString(R.string.inputNewPsdAgain));
		
		TransformationMethod method =  PasswordTransformationMethod.getInstance();
		teamName.setTransformationMethod(method);
		teamTrainer.setTransformationMethod(method);
		teamNativePalce.setTransformationMethod(method);
//		teamName.setInputType(PASSWORD_MINGWEN);
//		teamTrainer.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_NUMBER_VARIATION_PASSWORD);
	}

	private void initPlayerContentView() {
		playerName = (EditText) findViewById(R.id.add_player_name);
		playerWeight = (EditText) findViewById(R.id.add_player_weight);
		playerHight = (EditText) findViewById(R.id.add_player_hight);
		playerAge = (EditText) findViewById(R.id.add_player_age);
		playerTel = (EditText) findViewById(R.id.add_player_tel);
		playerRole = (EditText) findViewById(R.id.add_player_role);
		playerCountry = (EditText) findViewById(R.id.add_player_country);
		playerPositionSprinner = (Spinner) findViewById(R.id.playerPositionSpinner);
		teamNameSprinner = (Spinner) findViewById(R.id.teamNameSpinner);
		GetApiAsyncTask apiAsyncTask = new GetApiAsyncTask(AddFootballInfoActivity.this,
				StaticParam.API+StaticParam.API_GET_ALL_TEAM+StaticParam.API_TOKEN);
		apiAsyncTask.execute("&userNo="+StaticParam.USER_NO);
	}
	
	private void initTeamContentView() {
		teamName = (EditText) findViewById(R.id.add_team_name);
		teamTrainer = (EditText) findViewById(R.id.add_team_Trainer);
		teamSologan = (EditText) findViewById(R.id.add_team_Sologan);
		teamNativePalce = (EditText) findViewById(R.id.add_team_NativePlace);
	}

	private void initCompetitionContentView() {
		teamOneSpinner = (Spinner) findViewById(R.id.teamOneSpinner);
		teamTwoSpinner = (Spinner) findViewById(R.id.teamTwoSpinner);
		highPlayerSpinner = (Spinner) findViewById(R.id.hightPlayerSprinner);
		assistingSpinner = (Spinner) findViewById(R.id.assistingPlayerSprinner);
		hightPlayerScore = (EditText) findViewById(R.id.add_hightScore);
		assistingPlayerScore = (EditText) findViewById(R.id.add_assistingScore);
		winScore = (EditText) findViewById(R.id.add_win_score);
		secondScore = (EditText) findViewById(R.id.add_second_score);
		//异步获取spinner数据
		InitSpinnerAsyncTask teamSpinnerOne = new InitSpinnerAsyncTask(teamOneSpinner,
			StaticParam.API+StaticParam.API_GET_ALL_TEAM+StaticParam.API_TOKEN,
			TEAM,this);
		teamSpinnerOne.execute("&userNo="+StaticParam.USER_NO);
		InitSpinnerAsyncTask teamSpinnerTwo = new InitSpinnerAsyncTask(teamTwoSpinner,
			StaticParam.API+StaticParam.API_GET_ALL_TEAM+StaticParam.API_TOKEN,
			TEAM,this);
		teamSpinnerTwo.execute("&userNo="+StaticParam.USER_NO);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addInfoImg:
			backToDetails();
			break;
		case R.id.addInfoSave:
			saveInfo();
			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		backToDetails();
	}
	
	private void backToDetails() {
		setResult(CURRENT_TYPE);
		finish();		
		overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}

	/**
	 * 验证并保存添加的信息
	 */
	private void saveInfo() {
		if(!checkInfo()){
			saveInfoToServer();
		}else{
//			showToastSuccess(AddFootballInfoActivity.this,"信息添加出错，请重试！",1000);
		}
	}

	/**
	 * 保存信息到服务器数据库
	 */
	private void saveInfoToServer() {
		GetApiAsyncTask apiAsyncTask = null;
		switch (CURRENT_TYPE) {
		case ADD_PALYER:
			apiAsyncTask = new GetApiAsyncTask(AddFootballInfoActivity.this,
					StaticParam.API+StaticParam.API_ADD_PLAYER+StaticParam.API_TOKEN);
			apiAsyncTask.execute(player.toString());
			break;
		case ADD_TEAM:
			apiAsyncTask = new GetApiAsyncTask(AddFootballInfoActivity.this,
					StaticParam.API+StaticParam.API_ADD_TEAM+StaticParam.API_TOKEN);
			apiAsyncTask.execute(team.toString());
			break;
		case ADD_COMPEITITON:
			apiAsyncTask = new GetApiAsyncTask(AddFootballInfoActivity.this,
					StaticParam.API+StaticParam.API_ADD_COMPETITION+StaticParam.API_TOKEN);
			apiAsyncTask.execute(competition.toString());
			break;	
		case PersonManagerActivity.MODIFY_PSD:
			apiAsyncTask = new GetApiAsyncTask(AddFootballInfoActivity.this,
					StaticParam.API+StaticParam.API_TO_GET_PSD+StaticParam.API_TOKEN);
			apiAsyncTask.execute(userParam.toString());
			break;
		default:
			break;
		}
	}

	private boolean checkInfo() {
		boolean isNullOrEmpty = false;
		switch (CURRENT_TYPE) {
		case ADD_PALYER:
			isNullOrEmpty = checkPlayer();
			break;
		case ADD_TEAM:
			isNullOrEmpty = checkTeam();
			break;
		case ADD_COMPEITITON:
			isNullOrEmpty = checkCompetition();
			break;	
		case PersonManagerActivity.MODIFY_PSD:
			isNullOrEmpty = checkModifyPsd();
			break;
		default:
			break;
		}
		return isNullOrEmpty;
	}

	private boolean checkModifyPsd() {
		String teamNameStr = teamName.getText().toString();
		String teamTrainerStr = teamTrainer.getText().toString();
		String teamNativePalceStr = teamNativePalce.getText().toString();
		if(teamNameStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.userPsd),1000);
			return true;
		}
		if(teamTrainerStr.equals("")||teamNativePalceStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.userPsd),1000);
			return true;		
		}
		if(!teamTrainerStr.equals(teamNativePalceStr)){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.psdNotSame),1000);
			return true;
		}
		userParam = new UserParam();
		userParam.setUserNo(StaticParam.USER_NO);
		userParam.setNewPsd(teamTrainerStr);
		userParam.setName(StaticParam.USER_NAME);
		userParam.setPsd(teamNameStr);
		return false;
	}

	private boolean checkTeam() {
		String teamNameStr = teamName.getText().toString();
		String teamSologanStr = teamSologan.getText().toString();
		String teamTrainerStr = teamTrainer.getText().toString();
		String teamNativePalceStr = teamNativePalce.getText().toString();
		if(teamNameStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.teamName),1000);
			return true;
		}
		if(teamTrainerStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.teamTrainer),1000);
			return true;		
		}
		if(teamNativePalceStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.teamNativePlace),1000);
			return true;
		}
		if(teamSologanStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.teamSologan),1000);
			return true;
		}
		team = new TeamParam();
		team.setUserNo(StaticParam.USER_NO);
		team.setTeamName(teamNameStr);
		team.setTrainer(teamTrainerStr);
		team.setNativePlace(teamNativePalceStr);
		team.setSlogan(teamSologanStr);
		return false;
	}

	private boolean checkCompetition() {
		String teamOneName = teamOneSpinner.getSelectedItem().toString();
		String teamTwoName = teamTwoSpinner.getSelectedItem().toString();
		if(teamOneName.equals(getString(R.string.team_is_null))||teamTwoName.equals(getString(R.string.team_is_null))){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.team_null_to_add),1000);
			return true;
		}
		if(teamOneName.equals(teamTwoName)){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.team_can_not_same),1000);
			return true;
		}
		String highPlayerName = highPlayerSpinner.getSelectedItem().toString();
		String assistingName = assistingSpinner.getSelectedItem().toString();
		if(highPlayerName.equals(getString(R.string.player_is_null))||assistingName.equals(getString(R.string.player_is_null))){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.player_null_to_add),1000);
			return true;
		}
		String highScore = hightPlayerScore.getText().toString();
		String assistingScore = assistingPlayerScore.getText().toString();
		String winScoreStr = winScore.getText().toString();
		String secondScoreStr = secondScore.getText().toString();
		if(highScore.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),1000);
			return true;
		}
		if(assistingScore.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),1000);
			return true;
		}
		if(secondScoreStr.equals("")||winScoreStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),1000);
			return true;
		}
		competition = new CompetitionParam();
		competition.setChampionTeam(teamOneName);
		competition.setUserNo(StaticParam.USER_NO);
		competition.setSecondTeam(teamTwoName);
		competition.setChampionScore(Integer.parseInt(winScoreStr));
		competition.setSecondScore(Integer.parseInt(secondScoreStr));
		competition.setAssistingPlayer(assistingName);
		competition.setHighPlayer(highPlayerName);
		competition.setHighScore(Integer.parseInt(highScore));
		competition.setAssistingScore(Integer.parseInt(assistingScore));
		return false;
	}

	private boolean checkPlayer() {
		String nameStr = playerName.getText().toString();
		String ageStr = playerAge.getText().toString();
		String hightStr = playerHight.getText().toString();
		String weightStr = playerWeight.getText().toString();
		String telStr = playerTel.getText().toString();
		String roleStr = playerRole.getText().toString();
		String countryStr = playerCountry.getText().toString();
		if(nameStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),showToastTime);
			return true;
		}
		if(ageStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),showToastTime);
			return true;
		}
		if(hightStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),showToastTime);
			return true;
		}
		if(weightStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),showToastTime);
			return true;
		}
		if(telStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),showToastTime);
			return true;
		}
		if(roleStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),showToastTime);
			return true;
		}
		if(countryStr.equals("")){
			showToastSuccess(AddFootballInfoActivity.this,getString(R.string.no_data),showToastTime);
			return true;
		}
		String teamName = teamNameSprinner.getSelectedItem().toString();
		if(teamName.equals(getString(R.string.team_is_null))){
			showToastFault(AddFootballInfoActivity.this,getString(R.string.team_null_to_add),showToastTime);
			return true;
		}
		player = new PlayerParam();
		player.setAge(ageStr);
		player.setUserNo(StaticParam.USER_NO);
		player.setName(nameStr);
		player.setHeight(hightStr);
		player.setWeight(weightStr);
		player.setMobile(telStr);
		player.setRole(roleStr);
		player.setCountry(countryStr);
		player.setTeamName(teamName);
		player.setPlayPosition(playerPositionSprinner.getSelectedItem().toString());
		return false;
	}

	/**
	 * 异步完成的回调方法
	 */
	@Override
	public void doAfterGetData(String data) {
		switch (CURRENT_TYPE) {
		case ADD_PALYER:
			if(!initSprinner){
				TeamAPIRetParam teamAPIRetParam = 
						GsonUtils.getGson().fromJson(data,new TeamAPIRetParam().getClass());
					items = new ArrayList<String>();
					if(teamAPIRetParam!=null && teamAPIRetParam.getData()!=null && teamAPIRetParam.getData().size()>0)
					{
						for(int i=0,j=teamAPIRetParam.getData().size();i<j;i++)
							items.add(teamAPIRetParam.getData().get(i).getTeamName());
					}else{
						showToastFault(AddFootballInfoActivity.this,getString(R.string.team_null_to_add),showToastTime);
						items.add(getString(R.string.team_is_null));
					}
					ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					teamNameSprinner.setAdapter(adapter);
					initSprinner = true;
			}else{
				PlayerAPIRetParam apiRetParam = GsonUtils.getGson().fromJson(data,PlayerAPIRetParam.class);
				if(apiRetParam==null){
					showToastSuccess(AddFootballInfoActivity.this,getString(R.string.errorconnect),showToastTime);
					return ;
				}
				if(apiRetParam.getMsg().equals(StaticParam.SUCCESS)){
					showToastSuccess(AddFootballInfoActivity.this,getString(R.string.success_opeation),showToastTime);
					onBackPressed();
				}else{
					showToastSuccess(AddFootballInfoActivity.this,getString(R.string.fault_opeation)+apiRetParam.getMsg(),showToastTime);
				}
			}
			break;
		case ADD_TEAM:
			TeamAPIRetParam apiRetParam = GsonUtils.getGson().fromJson(data,TeamAPIRetParam.class);
			if(apiRetParam==null){
				showToastSuccess(AddFootballInfoActivity.this,getString(R.string.errorconnect),showToastTime);
				return ;
			}
			if(apiRetParam.getMsg().equals(StaticParam.SUCCESS)){
				showToastSuccess(AddFootballInfoActivity.this,getString(R.string.success_opeation),showToastTime);
				onBackPressed();
			}else{
				showToastSuccess(AddFootballInfoActivity.this,getString(R.string.fault_opeation)+apiRetParam.getMsg(),showToastTime);
			}
			break;
		case ADD_COMPEITITON:
			CompetitionAPIRetParam comApiRetParam =
				GsonUtils.getGson().fromJson(data,CompetitionAPIRetParam.class);
			if(comApiRetParam==null){
				showToastSuccess(AddFootballInfoActivity.this,getString(R.string.errorconnect),showToastTime);
				return ;
			}
			if(comApiRetParam.getMsg().equals(StaticParam.SUCCESS)){
				showToastSuccess(AddFootballInfoActivity.this,getString(R.string.success_opeation),showToastTime);
				onBackPressed();
			}else{
				showToastSuccess(AddFootballInfoActivity.this,getString(R.string.fault_opeation)+comApiRetParam.getMsg(),showToastTime);
			}
			break;	
		case PersonManagerActivity.MODIFY_PSD:
			APIRetParam<User> user = 
				GsonUtils.getGson().fromJson(data,APIRetParam.class);
			if(user==null){
				showToastSuccess(AddFootballInfoActivity.this,getString(R.string.errorconnect),showToastTime);
				return ;
			}
			if(user.getMsg().equals(StaticParam.SUCCESS)){
				showToastSuccess(AddFootballInfoActivity.this,getString(R.string.modify_psd_ok),showToastTime);
				deleteUserInfo();
				startMyActivity(AddFootballInfoActivity.this, new LoginActivity(), null, 0);
				AddFootballInfoActivity.this.finish();
				overridePendingTransition(R.anim.slide_top_to_bottom,
					R.anim.my_alpha_action);
			}else{
				showToastSuccess(AddFootballInfoActivity.this,""+user.getMsg(),showToastTime);
			}
			break;
		default:
			break;
		}
	}
	
	protected void deleteUserInfo() {
		Editor edit= getSharedPreferences("userInfo",MODE_PRIVATE).edit();
		edit.putBoolean("checkStatus",false);
		edit.putString("psd", "");
		edit.commit();		
	}
	
	/**
	 * 异步完成返回提示信息
	 */
	@Override
	public void doMessage(int i, String msg) {
		if(i!=SUCCESS)
		{
			showToastSuccess(this, msg, showToastTime);
		}
		else{
			teamOneSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					toGetPlayerNameSpinner(teamOneSpinner.getSelectedItem().toString(),null);
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			teamTwoSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					toGetPlayerNameSpinner(null,teamTwoSpinner.getSelectedItem().toString());
				}
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
		}
	}
	
	public void toGetPlayerNameSpinner(String teamOneName,String teamTwoName){
		if(teamOneName!=null)
		{
			InitSpinnerAsyncTask highPlayer = new InitSpinnerAsyncTask(highPlayerSpinner,
				StaticParam.API+StaticParam.API_GET_PLAYER_BY_TEAMNAME+StaticParam.API_TOKEN,
				PLAYER,this);
			PlayerParam param = new PlayerParam();
			param.setTeamName(teamOneName);
			highPlayer.execute(param.toString());
		}
		if(teamTwoName!=null){
			InitSpinnerAsyncTask assistingPlayer = new InitSpinnerAsyncTask(assistingSpinner,
				StaticParam.API+StaticParam.API_GET_PLAYER_BY_TEAMNAME+StaticParam.API_TOKEN,
				PLAYER,this);
			PlayerParam param = new PlayerParam();
			param.setTeamName(teamTwoName);
			assistingPlayer.execute(param.toString());
		}
	}
}
