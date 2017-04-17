package com.licong.graduationproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.licong.graduationproject.fragment.VideoLeftFragment;

import java.util.List;

/**
 * Created by licong on 3/29/17.
 * 视频界面左边简介适配器
 */

public class VideoLeftFragmentAdapter extends FragmentPagerAdapter {


    public VideoLeftFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
