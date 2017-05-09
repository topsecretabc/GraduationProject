package com.jeff.footballmanager.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.domain.Competition;
import com.jeff.footballmanager.domain.Player;
import com.jeff.footballmanager.domain.Team;
import com.jeff.footballmanager.param.CompetitionAPIRetParam;
import com.jeff.footballmanager.param.CompetitionParam;
import com.jeff.footballmanager.param.PlayerAPIRetParam;
import com.jeff.footballmanager.param.PlayerParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.TeamAPIRetParam;
import com.jeff.footballmanager.param.TeamParam;
import com.jeff.footballmanager.utils.GetApiAsyncTask;
import com.jeff.footballmanager.utils.GsonUtils;

public class EditInfoActivity extends BaseActivity {
	/**
	 * 数据类型，player，team，competition
	 */
	private int type;
	private String NUMBER="";
	/**
	 * 第一次初始化加载
	 */
	private boolean isFirstInit = true;
	/**
	 * 操作 add,edit
	 */
	private ImageView editImg;
	private Button saveBtn;
	private TextView editTitle;
 	//球员变量
	private EditText playerName,playerHight,playerWeight,
	playerCountry,playerAge,playerTel,playerRole,playerPosition;
	//球队变量
	private EditText teamName,teamTrainer,teamSologan,teamNativePalce;
	//比赛详情变量
	private EditText championTeam,championScore,highPlayer,highScore,
	secondTeam,secondScore,assistingPlayer,assistingScore;
	
	private PlayerParam param = new PlayerParam();
	private TeamParam teamParam = new TeamParam();
	private CompetitionParam competitionParam = new CompetitionParam();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		setListener();
	}

	private void setListener() {
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveEdit();
			}
		});
		editImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				backToDetails();				
			}
		});
	}

	protected void saveEdit() {
		if(checkInfo()){
			sendRequest();
		}
	}

	private void sendRequest() {
		GetApiAsyncTask apiAsyncTask = null;
		switch (type) {
		case FootballDetailsActivity.GET_PLAYER:
			apiAsyncTask = new GetApiAsyncTask(EditInfoActivity.this,
				StaticParam.API+StaticParam.API_EDIT_PALYER_BY_NO+StaticParam.API_TOKEN);
			apiAsyncTask.execute(param.toString());
			break;
		case FootballDetailsActivity.GET_TEAM:
			apiAsyncTask = new GetApiAsyncTask(EditInfoActivity.this,
					StaticParam.API+StaticParam.API_EDIT_TEAM_BY_NO+StaticParam.API_TOKEN);
			apiAsyncTask.execute(teamParam.toString());
			break;
		case FootballDetailsActivity.GET_COMPETITION:
			apiAsyncTask = new GetApiAsyncTask(EditInfoActivity.this,
					StaticParam.API+StaticParam.API_EDIT_COMPETITION_BY_NO+StaticParam.API_TOKEN);
			apiAsyncTask.execute(competitionParam.toString());
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
		setResult(type);
		finish();		
		overridePendingTransition(R.anim.push_right_in,
				R.anim.push_right_out);
	}
	
	private boolean checkInfo() {
		boolean isOk = true;
		switch (type) {
		case FootballDetailsActivity.GET_PLAYER:
			isOk = checkPlayer();
			break;
		case FootballDetailsActivity.GET_TEAM:
			isOk = checkTeam();
			break;
		case FootballDetailsActivity.GET_COMPETITION:
			isOk = checkCompetition();
			break;
		default:
			break;
		}
		return isOk;
	}

	private boolean checkCompetition() {
		String chamName = championTeam.getText().toString();
		String chamScore = championScore.getText().toString();
		String highPlayerStr = highPlayer.getText().toString();
		String highScoreStr = highScore.getText().toString();
		String secondNameStr = secondTeam.getText().toString();
		String secondScoreStr = secondScore.getText().toString();
		String assistingPlayerStr = assistingPlayer.getText().toString();
		String assistingScoreStr = assistingScore.getText().toString();
		competitionParam.setCompetitionNo(NUMBER);
		competitionParam.setChampionTeam(chamName);
		competitionParam.setChampionScore(Integer.parseInt(chamScore));
		competitionParam.setSecondTeam(secondNameStr);
		competitionParam.setSecondScore(Integer.parseInt(secondScoreStr));
		competitionParam.setHighPlayer(highPlayerStr);
		competitionParam.setHighScore(Integer.parseInt(highScoreStr));
		competitionParam.setAssistingPlayer(assistingPlayerStr);
		competitionParam.setAssistingScore(Integer.parseInt(assistingScoreStr));
		return true;
	}

	private boolean checkTeam() {
		String trainer = teamTrainer.getText().toString();
		String nativePlace = teamNativePalce.getText().toString();
		String slogan = teamSologan.getText().toString();
		if(trainer.equals("")||nativePlace.equals("")||
			slogan.equals("")){
			showToastSuccess(EditInfoActivity.this,getString(R.string.can_not_null),showToastTime);
			return false;
		}
		teamParam.setTeamNo(NUMBER);
		teamParam.setTrainer(trainer);
		teamParam.setNativePlace(nativePlace);
		teamParam.setSlogan(slogan);
		return true;
	}

	private boolean checkPlayer() {
		String name = playerName.getText().toString();
		String height = playerHight.getText().toString();
		String weight = playerWeight.getText().toString();
		String age = playerAge.getText().toString();
		String role = playerRole.getText().toString();
		String tel = playerTel.getText().toString();
		String country = playerCountry.getText().toString();
		if(name.equals("")||height.equals("")||weight.equals("")||
			age.equals("")||role.equals("")||tel.equals("")||country.equals("")){
			showToastSuccess(EditInfoActivity.this,getString(R.string.can_not_null),showToastTime);
			return false;
		}
		param.setPlayerNo(NUMBER);
		param.setHeight(height);
		param.setWeight(weight);
		param.setAge(age);
		param.setRole(role);
		param.setMobile(tel);
		param.setCountry(country);
		param.setName(name);
		return true;
	}

	private void initView() {
		type = getIntent().getIntExtra("type",0);
		NUMBER = getIntent().getStringExtra("no");
		switch (type) {
		case FootballDetailsActivity.GET_PLAYER:
			initPlayerView();
			break;
		case FootballDetailsActivity.GET_TEAM:
			initTeamView();		
			break;
		case FootballDetailsActivity.GET_COMPETITION:
			initCompetitionView();
			break;
		case FootballDetailsActivity.GET_TECHNO:
			initPlayerView();
			break;
		default:
			break;
		}
	}

	private void initCompetitionView() {
		setContentView(R.layout.edit_competition);
		initTitle();
		initCompetitionEdit();
		getInfoFromServer("&competitionNo=",StaticParam.API_GET_COMPETITION_BY_NO);
	}

	private void initCompetitionEdit() {
		championTeam = (EditText) findViewById(R.id.edit_ChampionTeam);
		championScore = (EditText) findViewById(R.id.edit_ChampionScore);
		highPlayer = (EditText) findViewById(R.id.edit_HighPlayer);
		highScore = (EditText) findViewById(R.id.edit_HighScore);
		secondTeam = (EditText) findViewById(R.id.edit_SecondTeam);
		secondScore = (EditText) findViewById(R.id.edit_SecondScore);
		assistingPlayer = (EditText) findViewById(R.id.edit_AssistingPlayer);
		assistingScore = (EditText) findViewById(R.id.edit_AssistingScore);
	}

	private void initTeamEdit() {
		teamName = (EditText) findViewById(R.id.edit_team_name);
		teamTrainer = (EditText) findViewById(R.id.edit_team_trainer);
		teamSologan = (EditText) findViewById(R.id.edit_team_slogan);
		teamNativePalce = (EditText) findViewById(R.id.edit_team_native_place);
	}

	private void initTeamView() {
		setContentView(R.layout.edit_team);
		initTitle();
		initTeamEdit();
		getInfoFromServer("&teamNo=",StaticParam.API_GET_TEAM_BY_NO);
	}

	private void initPlayerView() {
		setContentView(R.layout.edit_player);
		initTitle();
		initPlayerEdit();
		getInfoFromServer("&playerNo=",StaticParam.API_GET_PALYER_BY_NO);
	}

	private void initPlayerEdit() {
		playerName = (EditText) findViewById(R.id.edit_player_name);
		playerHight = (EditText) findViewById(R.id.edit_player_hight);
		playerWeight = (EditText) findViewById(R.id.edit_player_weight);
		playerCountry = (EditText) findViewById(R.id.edit_player_country);
		playerAge = (EditText) findViewById(R.id.edit_player_age);
		playerTel = (EditText) findViewById(R.id.edit_player_tel);
		playerRole = (EditText) findViewById(R.id.edit_player_role);
		playerPosition = (EditText) findViewById(R.id.edit_player_position);		
	}

	/**
	 * api,要调用的接口.
	 * flag,接口接收的参数,eg:"&playerNo="
	 * @param flag
	 * @param api
	 */
	private void getInfoFromServer(String flag,String api) {
		GetApiAsyncTask apiAsyncTask = new GetApiAsyncTask(EditInfoActivity.this,
			StaticParam.API+api+StaticParam.API_TOKEN);
		apiAsyncTask.execute(flag+NUMBER);
	}

	
	private void setPlayerEditInfo(String json) {
		PlayerAPIRetParam result = 
			GsonUtils.getGson().fromJson(json,PlayerAPIRetParam.class);
		if(result!=null){
			if(isFirstInit){
				if(result.getMsg().equals(StaticParam.SUCCESS) && result.getData().size()>0){
					setPlayerView(result.getData().get(0));
					isFirstInit = false;
				}else{
					showToastSuccess(EditInfoActivity.this,result.getMsg(),showToastTime);
				}
			}else{
				if(result.getMsg().equals(StaticParam.SUCCESS)){
					showToastSuccess(EditInfoActivity.this,getString(R.string.success_opeation),showToastTime);
					onBackPressed();
				}else{
					showToastSuccess(EditInfoActivity.this,result.getMsg(),showToastTime);
				}
			}
		}else{
			
		}
	}

	/**
	 * 设置球员界面editView默认值
	 * @param player 
	 */
	private void setPlayerView(Player player) {
		playerName.setText(player.getName());
		playerHight.setText(player.getHeight());
		playerWeight.setText(player.getWeight());
		playerCountry.setText(player.getCountry());
		playerAge.setText(player.getAge());
		playerTel.setText(player.getMobile());
		playerRole.setText(player.getRole());
		playerPosition.setText(player.getPlayPosition());
	}

	private void initTitle() {
		editImg = (ImageView) findViewById(R.id.editImg);
		editTitle = (TextView) findViewById(R.id.editTitle);
		saveBtn = (Button) findViewById(R.id.saveBtn);		
	}
	
	@Override
	public void doAfterGetData(String data) {
		switch (type) {
		case FootballDetailsActivity.GET_PLAYER:
			setPlayerEditInfo(data);
			break;
		case FootballDetailsActivity.GET_TEAM:
			setTeamEditInfo(data);
			break;
		case FootballDetailsActivity.GET_COMPETITION:
			setCompetitionEditInfo(data);
			break;
		default:
			break;
		}
	}

	private void setCompetitionEditInfo(String data) {
		CompetitionAPIRetParam result = 
				GsonUtils.getGson().fromJson(data,CompetitionAPIRetParam.class);
			if(result!=null){
				if(isFirstInit){
					if(result.getMsg().equals(StaticParam.SUCCESS) && result.getData().size()>0){
						setCompetitionView(result.getData().get(0));
						isFirstInit = false;
					}else{
						showToastSuccess(EditInfoActivity.this,result.getMsg(),Toast.LENGTH_LONG);
					}
				}else{
					if(result.getMsg().equals(StaticParam.SUCCESS)){
						showToastSuccess(EditInfoActivity.this,getString(R.string.success_opeation),Toast.LENGTH_LONG);
						onBackPressed();
					}else{
						showToastSuccess(EditInfoActivity.this,result.getMsg(),Toast.LENGTH_LONG);
					}
				}
			}else{
				
			}
	}

	private void setCompetitionView(Competition competition) {
		championTeam.setText(competition.getChampionTeam());
		championScore.setText(competition.getChampionScore()+"");
		highPlayer.setText(competition.getHighPlayer());
		highScore.setText(competition.getHighScore()+"");
		secondTeam.setText(competition.getSecondTeam());
		secondScore.setText(competition.getSecondScore()+"");
		assistingPlayer.setText(competition.getAssistingPlayer());
		assistingScore.setText(competition.getAssistingScore()+"");
	}

	private void setTeamEditInfo(String json) {
		TeamAPIRetParam result = 
				GsonUtils.getGson().fromJson(json,TeamAPIRetParam.class);
			if(result!=null){
				if(isFirstInit){
					if(result.getMsg().equals(StaticParam.SUCCESS) && result.getData().size()>0){
						setTeamView(result.getData().get(0));
						isFirstInit = false;
					}else{
						showToastSuccess(EditInfoActivity.this,result.getMsg(),Toast.LENGTH_LONG);
					}
				}else{
					if(result.getMsg().equals(StaticParam.SUCCESS)){
						showToastSuccess(EditInfoActivity.this,getString(R.string.success_opeation),Toast.LENGTH_LONG);
						onBackPressed();
					}else{
						showToastSuccess(EditInfoActivity.this,result.getMsg(),Toast.LENGTH_LONG);
					}
				}
			}else{
				
			}
	}

	private void setTeamView(Team team) {
		teamName.setText(team.getTeamName());
		teamTrainer.setText(team.getTrainer());
		teamSologan.setText(team.getSlogan());
		teamNativePalce.setText(team.getNativePlace());
	}

}
