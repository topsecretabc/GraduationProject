package com.jeff.footballmanager.domain;

import java.util.List;
//*		   1 =>  头条
//*        2 =>  娱乐
//*        3 =>  军事
//*        4 =>  汽车
//*        5 =>  财经
//*        6 =>  笑话
//*        7 =>  体育
//*        8 =>  科技
public class Result {
	
	private int status;
	
	private String error;
	
	private int count;
	
	private List<news> data;
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<news> getData() {
		return data;
	}

	public void setData(List<news> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Result=[");
		sb.append("status=");
		sb.append(status);
		sb.append(",error=");
		sb.append(error);
		sb.append(",count=");
		sb.append(count);
		sb.append(",data=");
		sb.append(data.toString());
		sb.append("]");
		return sb.toString();
	}
}
