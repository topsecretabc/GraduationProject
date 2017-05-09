package com.jeff.footballmanager.param;


/**
 * @author 邓纪富
 * @DateTime: 2017年1月4日 下午3:13:10
 * @Description: TODO
 *
 */
public class User {
	
	private Integer id;
	
	private String name;
	
	private String age;
	
	private String address;
	
	private String psd;
	private String headPath;
	private String userNo;
	private String bgPath;

	public String getBgPath() {
		return bgPath;
	}

	public void setBgPath(String bgPath) {
		this.bgPath = bgPath;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getHeadPath() {
		return headPath;
	}
	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	private String lastLoginTime;
	
	private String createtime;
	
	private String updatetime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the psd
	 */
	public String getPsd() {
		return psd;
	}
	/**
	 * @param psd the psd to set
	 */
	public void setPsd(String psd) {
		this.psd = psd;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}
	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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
		builder.append("User [");
		builder.append("name=");
		builder.append(name);
		builder.append(",age=");
		builder.append(age);
		builder.append(", address=");
		builder.append(address);
		builder.append(", headPath=");
		builder.append(headPath);
		builder.append(", psd=");
		builder.append(psd);
		builder.append(", lastLoginTime=");
		builder.append(lastLoginTime);
		builder.append(", createtime=");
		builder.append(createtime);
		builder.append(", updatetime=");
		builder.append(updatetime);
		return builder.toString();
	}
}
