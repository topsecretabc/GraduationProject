package com.licong.graduationproject.adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.List;
/**
 * Created by licong on 3/29/17.
 * 视频右边评论界面
 */

public class VideoRightFragmentAdapter extends FragmentStatePagerAdapter {
    public VideoRightFragmentAdapter(FragmentManager fm) {
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

