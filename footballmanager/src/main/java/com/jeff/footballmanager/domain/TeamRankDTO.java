package com.jeff.footballmanager.domain;


public class TeamRankDTO extends Competition {

	private String playPosition;
	
	private String trainer;

	public String getPlayPosition() {
		return playPosition;
	}

	public void setPlayPosition(String playPosition) {
		this.playPosition = playPosition;
	}

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	@Override
	public String toString() {
		return "TeamRankDTO [playPosition=" + playPosition + ", trainer=" + trainer + "]";
	}
	
}
