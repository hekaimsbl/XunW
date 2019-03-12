package com.example.hekai.xunw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hekai.xunw.Fragment.FindFragment;
import com.example.hekai.xunw.Fragment.HomeFragment;
import com.example.hekai.xunw.Fragment.MeFragment;
import com.example.hekai.xunw.Fragment.WaitFragment;

import java.util.List;

/**
 * @author HeKai
 * @date 2018/12/19
 * @Email hekaimsbl@gmail.com
 * @Describe
 **/
public class ViewPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragmentList;
    private List<String> titleList;

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new WaitFragment();
                break;
            case 2:
                fragment = new FindFragment();
                break;
            case 3:
                fragment = new MeFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
