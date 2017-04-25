package com.licong.graduationproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.licong.graduationproject.R;

/**
 * Created by licong on 4/25/17.
 */

public class WelcomeFragmentOne extends BaseFragment {


    public static WelcomeFragmentOne newInstance() {
        WelcomeFragmentOne fragment = new WelcomeFragmentOne();
        return fragment;
    }

    public WelcomeFragmentOne() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome_view1, container, false);
        return view;
    }



}
