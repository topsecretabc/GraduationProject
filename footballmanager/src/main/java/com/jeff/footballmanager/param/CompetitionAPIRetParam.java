package com.jeff.footballmanager.param;

import java.util.List;

import com.jeff.footballmanager.domain.Competition;

public class CompetitionAPIRetParam {

	private String msg;
	
	private String statusCode;
	
	private List<Competition> data;
	
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

	public List<Competition> getData() {
		return data;
	}

	public void setData(List<Competition> data) {
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
