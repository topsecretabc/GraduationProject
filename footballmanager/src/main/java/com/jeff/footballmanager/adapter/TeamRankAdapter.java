package com.jeff.footballmanager.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jeff.footballmanager.R;
import com.jeff.footballmanager.domain.Competition;
import com.jeff.footballmanager.domain.TeamRankDTO;

public class TeamRankAdapter<T> extends BaseAdapter {

	private List<T> data;
	private int resource;
	private Context context;
	private int type;
	
	public TeamRankAdapter(Context context,int resource,List<T> data,int type) {
		this.resource = resource;
		this.context = context;
		this.data = data;
		this.type = type;
	}
	
	//返回数据总数
	@Override
	public int getCount() {
		return null!=data?data.size():0;
	}

	//返回当前项
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	//返回当前项id
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(resource, null);
			holder.sort = (TextView) view.findViewById(R.id.sort);
			holder.playerNameAndScore = (TextView) view.findViewById(R.id.teamPlayerName);
			holder.teamNameAndScore = (TextView) view.findViewById(R.id.teamNameScore);
			view.setTag(holder);
		}else{
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		holder.sort.setText(position+1+"");
		List<Competition> list = null;
		if(type==0){
			list = (List<Competition>) data;
			Competition com = list.get(position);
			holder.playerNameAndScore.setText(com.getAssistingPlayer()+"  助攻数:"+com.getAssistingScore());
			holder.teamNameAndScore.setText("球队:"+com.getTeamName()+"	队伍得分:"+com.getTeamScore());
		}else if(type==1){
			list = (List<Competition>) data;
			Competition com = list.get(position);
			holder.playerNameAndScore.setText(com.getHighPlayer()+"  得分:"+com.getHighScore());
			holder.teamNameAndScore.setText("球队:"+com.getTeamName()+"	队伍得分:"+com.getTeamScore());
		}else if(type==2){
			list = (List<Competition>) data;
			Competition com = list.get(position);
			holder.playerNameAndScore.setText(com.getAssistingPlayer()+"  总助攻数:"+com.getAssistingScore());
			holder.teamNameAndScore.setText("球队:"+com.getTeamName()+"	最佳助攻次数:"+com.getNum());
		}else if(type==3){
			list = (List<Competition>) data;
			Competition com = list.get(position);
			holder.playerNameAndScore.setText(com.getHighPlayer()+"  金脚次数:"+com.getNum());
			holder.teamNameAndScore.setText("球队:"+com.getTeamName()+"	总得分:"+com.getHighScore());
		}else if(type==4){
			List<TeamRankDTO> dtoList = (List<TeamRankDTO>) data;
			TeamRankDTO dto = dtoList.get(position);
			holder.playerNameAndScore.setText(dto.getChampionTeam()+"  胜利次数:"+dto.getNum());
			String tariner = dto.getTrainer();
			if(tariner==null || tariner.equals(""))
				tariner = "已删除";
			holder.teamNameAndScore.setText("教练："+tariner+"  比赛得分:"+dto.getChampionScore());
		}
		return view;
	}

	class ViewHolder{
		TextView sort,playerNameAndScore,teamNameAndScore;
	}
}
