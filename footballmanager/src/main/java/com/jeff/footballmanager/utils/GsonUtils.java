package com.jeff.footballmanager.utils;

import com.google.gson.Gson;

public class GsonUtils {
	
	private static Gson gson = new Gson();
	
	public static Gson getGson(){
		return gson;
	}
}
