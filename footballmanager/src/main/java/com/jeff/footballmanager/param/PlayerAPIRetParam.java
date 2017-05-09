package com.jeff.footballmanager.param;

import java.util.List;

import com.jeff.footballmanager.domain.Player;
public class PlayerAPIRetParam{
	
	private String msg;
	
	private String statusCode;
	
	private List<Player> data;

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

	public List<Player> getData() {
		return data;
	}

	public void setData(List<Player> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PlayerAPIRetParam [msg=" + msg + ", statusCode=" + statusCode
				+ ", data=" + data + "]";
	}
}
