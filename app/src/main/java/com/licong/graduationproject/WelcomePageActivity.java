package com.licong.graduationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.licong.graduationproject.fragment.WelcomeFragmentFour;
import com.licong.graduationproject.fragment.WelcomeFragmentOne;
import com.licong.graduationproject.fragment.WelcomeFragmentTwo;
import com.licong.graduationproject.fragment.WelcomeFragmentThree;




public class WelcomePageActivity extends AppCompatActivity implements  BottomNavigationBar.OnTabSelectedListener,View.OnClickListener {
    private BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;
    private WelcomeFragmentOne mFragmentOne;
    private WelcomeFragmentTwo mFragmentTwo;
    private WelcomeFragmentThree mFragmentThree;
    private WelcomeFragmentFour mFragmentFour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

     bottomNavigationBar =(BottomNavigationBar)findViewById(R.id.bottom_navigation_bar) ;

        //设置4个按钮
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_live_like_01, R.string.jiangxi).setActiveColor(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.ic_live_like_02, R.string.ligong).setActiveColor(R.color.green))
                .addItem(new BottomNavigationItem(R.drawable.ic_live_like_03, R.string.daxue).setActiveColor(R.color.yellow))
                .addItem(new BottomNavigationItem(R.drawable.ic_live_like_04, R.string.JXUST).setActiveColor(R.color.blue))
                .setFirstSelectedPosition(lastSelectedPosition )
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);

    }
    //设置每个碎片
    @Override
    public void onTabSelected(int position) {
           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (position) {
               case 0:
                    while(true) {
                        if (mFragmentOne == null) {
                            mFragmentOne = WelcomeFragmentOne.newInstance();
                        }
                        transaction.replace(R.id.tb, mFragmentOne);
                        break;
                    }
                case 1: {
                    if (mFragmentTwo == null) {
                        mFragmentTwo=WelcomeFragmentTwo.newInstance();
                    }
                    transaction.replace(R.id.tb, mFragmentTwo);
                    break;
                }
                case 2: {
                    if (mFragmentThree == null) {
                        mFragmentThree=WelcomeFragmentThree.newInstance();
                    }
                    transaction.replace(R.id.tb, mFragmentThree);
                    break;
                }
                case 3:
                    if (mFragmentFour == null) {
                        mFragmentFour =WelcomeFragmentFour.newInstance();
                    }
                    transaction.replace(R.id.tb, mFragmentFour);
                    break;
            }
            transaction.commit();
        }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onClick(View v) {


    }
}
