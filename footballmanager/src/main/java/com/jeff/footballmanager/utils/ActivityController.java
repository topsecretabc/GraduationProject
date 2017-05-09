package com.jeff.footballmanager.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityController {

	private static List<Activity> activities = new ArrayList<Activity>();
	
	public static void addActivity(Activity activity){
		activities.add(activity);
	}
	
	public static void removeActivity(Activity activity){
		if(!activity.isFinishing())
			activities.remove(activity);
	}
	
	public static void finishAllActivity(){
		for(Activity activity:activities)
			if(!activity.isFinishing()){
				activity.finish();
			}
	}
	
}
