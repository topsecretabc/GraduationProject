package com.jeff.footballmanager.fragment;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	
	public void clearList(){
	}
	
	private boolean notShowPage = false;
	
	public boolean isNotShowPage() {
		return notShowPage;
	}
	public void setNotShowPage(boolean notShowPage) {
		this.notShowPage = notShowPage;
	}
	public void getData() {
	}
}
