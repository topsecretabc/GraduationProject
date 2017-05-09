package com.jeff.footballmanager.param;

import java.io.Serializable;

public class PlayerParam implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String playerNo;
	
	private String name;
	
	private String age;
	
	private String mobile;
	
	private String status;
	
	private String height;
	
	private String weight;
	
	private String country;
	
	private String role;
	
	private String playPosition;
	
	private String introduction;
	
	private String createtime;
	
	private String updatetime;

	private String teamName;
	private String userNo;
	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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
	 * @return the playPosition
	 */
	public String getPlayPosition() {
		return playPosition;
	}

	/**
	 * @param playPosition the playPosition to set
	 */
	public void setPlayPosition(String playPosition) {
		this.playPosition = playPosition;
	}

	public String getAge() {
		return age;
	}

	/**
	 * @return the playerNo
	 */
	public String getPlayerNo() {
		return playerNo;
	}

	/**
	 * @param playerNo the playerNo to set
	 */
	public void setPlayerNo(String playerNo) {
		this.playerNo = playerNo;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("&playerNo=");
		builder.append(playerNo);
		builder.append("&name=");
		builder.append(name);
		builder.append("&userNo=");
		builder.append(userNo);
		builder.append("&height=");
		builder.append(height);
		builder.append("&weight=");
		builder.append(weight);
		builder.append("&country=");
		builder.append(country);
		builder.append("&role=");
		builder.append(role);
		builder.append("&age=");
		builder.append(age);
		builder.append("&mobile=");
		builder.append(mobile);
		builder.append("&teamName=");
		builder.append(teamName);
		builder.append("&playPosition=");
		builder.append(playPosition);
		return builder.toString();
	}
	
}
