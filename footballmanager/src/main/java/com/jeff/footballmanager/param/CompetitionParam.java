package com.jeff.footballmanager.param;

public class CompetitionParam {
	private int id;
	
	private String competitionNo;
	private String championTeam;
	
	private String secondTeam;
	
	private int championScore;
	
	private int secondScore;
	
	private int highScore;
	
	private String highPlayer;
	
	private String status;
	
	private int highPlayerId;
	
	private String assistingPlayer;
	private int assistingScore;
	
	private int assistingPlayerId;
	
	private String createTime;
	
	private String updateTime;
	private String competitionTime;
	
	private String remark;
	
	private String teamName;
	private String winStatus;
	private int teamScore;
	private String otherTeam;
	//参赛次数
	private int num;
	private String userNo;
	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}


	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}


	public String getOtherTeam() {
		return otherTeam;
	}


	public void setOtherTeam(String otherTeam) {
		this.otherTeam = otherTeam;
	}


	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}


	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	/**
	 * @return the winStatus
	 */
	public String getWinStatus() {
		return winStatus;
	}


	/**
	 * @param winStatus the winStatus to set
	 */
	public void setWinStatus(String winStatus) {
		this.winStatus = winStatus;
	}


	/**
	 * @return the teamScore
	 */
	public int getTeamScore() {
		return teamScore;
	}


	/**
	 * @param teamScore the teamScore to set
	 */
	public void setTeamScore(int teamScore) {
		this.teamScore = teamScore;
	}


	/**
	 * @return the assistingScore
	 */
	public int getAssistingScore() {
		return assistingScore;
	}


	/**
	 * @param assistingScore the assistingScore to set
	 */
	public void setAssistingScore(int assistingScore) {
		this.assistingScore = assistingScore;
	}


	/**
	 * @return the competitionNo
	 */
	public String getCompetitionNo() {
		return competitionNo;
	}


	/**
	 * @param competitionNo the competitionNo to set
	 */
	public void setCompetitionNo(String competitionNo) {
		this.competitionNo = competitionNo;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the championTeam
	 */
	public String getChampionTeam() {
		return championTeam;
	}


	/**
	 * @param championTeam the championTeam to set
	 */
	public void setChampionTeam(String championTeam) {
		this.championTeam = championTeam;
	}


	/**
	 * @return the secondTeam
	 */
	public String getSecondTeam() {
		return secondTeam;
	}


	/**
	 * @param secondTeam the secondTeam to set
	 */
	public void setSecondTeam(String secondTeam) {
		this.secondTeam = secondTeam;
	}


	/**
	 * @return the championScore
	 */
	public int getChampionScore() {
		return championScore;
	}


	/**
	 * @param championScore the championScore to set
	 */
	public void setChampionScore(int championScore) {
		this.championScore = championScore;
	}


	/**
	 * @return the secondScore
	 */
	public int getSecondScore() {
		return secondScore;
	}


	/**
	 * @param secondScore the secondScore to set
	 */
	public void setSecondScore(int secondScore) {
		this.secondScore = secondScore;
	}


	/**
	 * @return the highScore
	 */
	public int getHighScore() {
		return highScore;
	}


	/**
	 * @param highScore the highScore to set
	 */
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}


	/**
	 * @return the highPlayer
	 */
	public String getHighPlayer() {
		return highPlayer;
	}


	/**
	 * @param highPlayer the highPlayer to set
	 */
	public void setHighPlayer(String highPlayer) {
		this.highPlayer = highPlayer;
	}


	/**
	 * @return the highPlayerId
	 */
	public int getHighPlayerId() {
		return highPlayerId;
	}


	/**
	 * @param highPlayerId the highPlayerId to set
	 */
	public void setHighPlayerId(int highPlayerId) {
		this.highPlayerId = highPlayerId;
	}


	/**
	 * @return the assistingPlayer
	 */
	public String getAssistingPlayer() {
		return assistingPlayer;
	}


	/**
	 * @param assistingPlayer the assistingPlayer to set
	 */
	public void setAssistingPlayer(String assistingPlayer) {
		this.assistingPlayer = assistingPlayer;
	}


	/**
	 * @return the assistingPlayerId
	 */
	public int getAssistingPlayerId() {
		return assistingPlayerId;
	}


	/**
	 * @param assistingPlayerId the assistingPlayerId to set
	 */
	public void setAssistingPlayerId(int assistingPlayerId) {
		this.assistingPlayerId = assistingPlayerId;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	public String getCompetitionTime() {
		return competitionTime;
	}


	public void setCompetitionTime(String competitionTime) {
		this.competitionTime = competitionTime;
	}


	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}


	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("&championTeam=");
		builder.append(championTeam);
		builder.append("&competitionNo=");
		builder.append(competitionNo);
		if(userNo!=null && !userNo.equals("")){
			builder.append("&userNo=");
			builder.append(userNo);
		}
		builder.append("&secondTeam=");
		builder.append(secondTeam);
		builder.append("&championScore=");
		builder.append(championScore);
		builder.append("&secondScore=");
		builder.append(secondScore);
		builder.append("&highScore=");
		builder.append(highScore);
		builder.append("&highPlayer=");
		builder.append(highPlayer);
		builder.append("&assistingPlayer=");
		builder.append(assistingPlayer);
		builder.append("&assistingScore=");
		builder.append(assistingScore);
		return builder.toString();
	}
}
