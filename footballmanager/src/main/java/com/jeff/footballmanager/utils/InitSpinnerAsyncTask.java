package com.jeff.footballmanager.utils;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.activity.AddFootballInfoActivity;
import com.jeff.footballmanager.activity.BaseActivity;
import com.jeff.footballmanager.param.PlayerAPIRetParam;
import com.jeff.footballmanager.param.StaticParam;
import com.jeff.footballmanager.param.TeamAPIRetParam;

public class InitSpinnerAsyncTask extends AsyncTask<String, Integer,String> {

	private static final int ERROR = -1;
	private static final int SUCCESS = 0;
	private static final int FAULT = 1;
	
	private Spinner spinner;
	private String baseApi;
	private String type;
	private BaseActivity ba;
	
	public InitSpinnerAsyncTask(Spinner spinner,String baseApi,String type,BaseActivity ba) {
		this.spinner = spinner;
		this.baseApi = baseApi;
		this.type = type;
		this.ba = ba;
	}
	
	@Override
	protected String doInBackground(String... params) {
		HttpUtils httpUtils = new HttpUtils();
		String json = httpUtils.getFootBallInfo(baseApi, params[0]);
		return json;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		if(result != null)
		{
			if(type.equals(AddFootballInfoActivity.TEAM)){
				initTeamSpinner(result);
			}else if(type.equals(AddFootballInfoActivity.PLAYER)){
				initPlayerSpinner(result);
			}
		}else{
			ba.doMessage(ERROR,ba.getString(R.string.errorconnect));
		}
	}

	private void initPlayerSpinner(String result) {
		PlayerAPIRetParam playerResult = 
			GsonUtils.getGson().fromJson(result,PlayerAPIRetParam.class);
		if(playerResult.getMsg().equals(StaticParam.SUCCESS)){
			List<String> items = new ArrayList<String>();
			if(playerResult.getData()!=null && playerResult.getData().size()>0 ){
				for(int i=0,j=playerResult.getData().size();i<j;i++)
					items.add(playerResult.getData().get(i).getName());
			}else{
				ba.doMessage(FAULT,ba.getString(R.string.player_null_to_add));
				items.add(ba.getString(R.string.player_is_null));
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(ba,android.R.layout.simple_spinner_item,items);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
		}else{
			ba.doMessage(FAULT,playerResult.getMsg());
		}
	}

	private void initTeamSpinner(String result) {
		TeamAPIRetParam teamResult = 
				GsonUtils.getGson().fromJson(result,TeamAPIRetParam.class);
			if(teamResult.getMsg().equals(StaticParam.SUCCESS)){
				List<String> teamName = new ArrayList<String>();
				if(teamResult.getData()!=null && teamResult.getData().size()>0 ){
					for(int i=0,j=teamResult.getData().size();i<j;i++)
						teamName.add(teamResult.getData().get(i).getTeamName());
					ba.doMessage(SUCCESS,null);
				}else{
					ba.doMessage(FAULT,ba.getString(R.string.team_null_to_add));
					teamName.add(ba.getString(R.string.team_is_null));
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(ba,android.R.layout.simple_spinner_item,teamName);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(adapter);
			}else{
				ba.doMessage(FAULT,teamResult.getMsg());
			}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

}
