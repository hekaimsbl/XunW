package com.example.hekai.xunw.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.activity.MainActivity;
import com.example.hekai.xunw.activity.PersonCenterActivity;

import java.util.List;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/3/23
 **/
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private String[] titles;
    public MyViewPagerAdapter(FragmentManager fm,List<Fragment> fragments,String[] titles){
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
