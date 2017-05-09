package com.jeff.footballmanager.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jeff.footballmanager.fragment.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter{

	private List<BaseFragment> fragments = new ArrayList<BaseFragment>();
	
	public MyPagerAdapter(FragmentManager fm,List<BaseFragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	

	
}
