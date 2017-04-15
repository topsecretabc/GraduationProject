package com.licong.graduationproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licong.graduationproject.R;


/**
 * Created by licong on 3/29/17.
 */

public class VideoLeftFragment extends BaseFragment {


    public VideoLeftFragment () {
    }

    public static VideoLeftFragment newInstance(String text){
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        VideoLeftFragment videoLeftFragment = new VideoLeftFragment();
        videoLeftFragment.setArguments(bundle);
        return  videoLeftFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.video_left_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = (TextView) view.findViewById(R.id.tv_description);
        textView.setText(getArguments().getString("text"));
    }
}
