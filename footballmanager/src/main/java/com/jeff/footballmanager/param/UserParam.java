package com.jeff.footballmanager.param;

public class UserParam {

	private String name;
	
	private String psd;

	private String newPsd;
	
	private String address;
	
	private String openid;
	private String bgPath;

	private String age;
	private String userNo;
	private String headPath;
	
	public String getBgPath() {
		return bgPath;
	}

	public void setBgPath(String bgPath) {
		this.bgPath = bgPath;
	}

	public String getHeadPath() {
		return headPath;
	}

	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getNewPsd() {
		return newPsd;
	}

	public void setNewPsd(String newPsd) {
		this.newPsd = newPsd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPsd() {
		return psd;
	}

	public void setPsd(String psd) {
		this.psd = psd;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(name!=null&&!name.equals("")){
			sb.append("&name="+name);
		}
		if(psd!=null&&!psd.equals("")){
			sb.append("&psd="+psd);
		}
		if(newPsd!=null&&!newPsd.equals("")){
			sb.append("&newPsd="+newPsd);
		}
		if(age!=null&&!age.equals("")){
			sb.append("&age="+age);
		}
		if(address!=null&&!address.equals("")){
			sb.append("&address="+address);
		}
		if(bgPath!=null&&!bgPath.equals("")){
			sb.append("&bgPath="+bgPath);
		}
		if(openid!=null&&!openid.equals("")){
			sb.append("&openid="+openid);
		}
		if(userNo!=null&&!userNo.equals("")){
			sb.append("&userNo="+userNo);
		}
		if(headPath!=null&&!headPath.equals("")){
			sb.append("&headPath="+headPath);
		}
		return sb.toString();
	}
}
