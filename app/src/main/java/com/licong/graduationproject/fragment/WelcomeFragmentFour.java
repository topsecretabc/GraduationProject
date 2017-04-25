package com.licong.graduationproject.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.licong.graduationproject.MainActivity;
import com.licong.graduationproject.R;
import com.licong.graduationproject.WelcomePageActivity;

/**
 * Created by licong on 4/25/17.
 */

public class WelcomeFragmentFour extends BaseFragment implements View.OnClickListener {


    public static WelcomeFragmentFour newInstance() {
        WelcomeFragmentFour fragment = new WelcomeFragmentFour();
        return fragment;
    }

    public WelcomeFragmentFour() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.welcome_view4, container, false);
        ImageView imageView =(ImageView)view.findViewById(R.id.welcome_view4);
        Button to_main_button = (Button)view.findViewById(R.id.btn_main);
        Log.e("wanghao","to_main_button");
        to_main_button.setOnClickListener(this);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                break;
        }
    }
}
