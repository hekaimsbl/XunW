package com.example.hekai.xunw.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hekai.xunw.Fragment.HottestFragment;
import com.example.hekai.xunw.Fragment.LatestFragment;
import com.example.hekai.xunw.Fragment.RankMonthFragment;
import com.example.hekai.xunw.Fragment.RankWeekFragment;


/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class FragmentAdapter extends FragmentPagerAdapter {
    private String[] TABS;
    private Fragment fragment;

    public FragmentAdapter(String[] tabs,FragmentManager fm){
        super(fm);
        this.TABS = tabs;
    }
    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                fragment = new LatestFragment();
                break;
            case 1:
                fragment = new HottestFragment();
                break;
            case 2:
                fragment = new RankWeekFragment();
                break;
            case 3:
                fragment = new RankMonthFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TABS.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TABS[position];
    }
}
