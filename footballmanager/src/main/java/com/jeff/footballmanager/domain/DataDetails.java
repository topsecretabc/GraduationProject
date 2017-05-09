package com.jeff.footballmanager.domain;


public class DataDetails {

	private int status;
	private int count;
	private String error;
	private news data;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public news getData() {
		return data;
	}
	public void setData(news data) {
		this.data = data;
	}
	
}
