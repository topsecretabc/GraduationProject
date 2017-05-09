package com.jeff.footballmanager.param;

import java.util.List;

import com.jeff.footballmanager.domain.Team;

public class TeamAPIRetParam {
private String msg;
	
	private String statusCode;
	
	private List<Team> data;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public List<Team> getData() {
		return data;
	}

	public void setData(List<Team> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("APIRetParam [");
		sb.append("msg=");
		sb.append(msg);
		sb.append(", statusCode=");
		sb.append(statusCode);
		sb.append(", data=");
		sb.append(data);
		sb.append("]");
		return sb.toString();
	}
}
