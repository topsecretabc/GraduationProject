package com.jeff.footballmanager.param;


/**
 * @author 邓纪富
 * @DateTime: 2017年2月6日 下午9:51:41
 * @Description: TODO
 *
 */
public class TeamParam {

	private int id;
	
	private String teamNo;
	
	private String teamName;
	
	private String trainer;
	
	private String nativePlace;
	
	private String slogan;
	
	private String createTime;
	
	private String updateTime;
	
	private String remark;
	//队伍人数
	private String num;
	private String userNo;
	
	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
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

	/**
	 * @return the num
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return the teamNo
	 */
	public String getTeamNo() {
		return teamNo;
	}

	/**
	 * @param teamNo the teamNo to set
	 */
	public void setTeamNo(String teamNo) {
		this.teamNo = teamNo;
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
	 * @return the trainer
	 */
	public String getTrainer() {
		return trainer;
	}

	/**
	 * @param trainer the trainer to set
	 */
	public void setTrainer(String trainer) {
		this.trainer = trainer;
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
	 * @return the nativePlace
	 */
	public String getNativePlace() {
		return nativePlace;
	}

	/**
	 * @param nativePlace the nativePlace to set
	 */
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	/**
	 * @return the slogan
	 */
	public String getSlogan() {
		return slogan;
	}

	/**
	 * @param slogan the slogan to set
	 */
	public void setSlogan(String slogan) {
		this.slogan = slogan;
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
		builder.append("&userNo=");
		builder.append(userNo);
		builder.append("&teamNo=");
		builder.append(teamNo);
		builder.append("&teamName=");
		builder.append(teamName);
		builder.append("&trainer=");
		builder.append(trainer);
		builder.append("&nativePlace=");
		builder.append(nativePlace);
		builder.append("&slogan=");
		builder.append(slogan);
		builder.append("&createtime=");
		builder.append(createTime);
		builder.append("&updatetime=");
		builder.append(updateTime);
		builder.append("&remark=");
		builder.append(remark);
		return builder.toString();
	}
	
}
