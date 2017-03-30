package com.licong.graduationproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.licong.graduationproject.fragment.VideoLeftFragment;

import java.util.List;

/**
 * Created by licong on 3/29/17.
 */

public class VideoLeftFragmentAdapter extends FragmentPagerAdapter {

    private List<String> mLeftVideo;

    public VideoLeftFragmentAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.mLeftVideo = list;
    }

    @Override
    public Fragment getItem(int position) {
        return VideoLeftFragment.newInstance(mLeftVideo.get(position));
    }

    @Override
    public int getCount() {
        return mLeftVideo.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mLeftVideo.get(position);
    }
}
