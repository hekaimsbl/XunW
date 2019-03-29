package com.example.hekai.xunw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hekai.xunw.Fragment.CollectionFragment;
import com.example.hekai.xunw.Fragment.FollowFragment;
import com.example.hekai.xunw.Fragment.RecommendFragment;

import java.util.HashMap;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/3/22
 **/
public class PersonalViewPagerAdapter extends FragmentPagerAdapter {
    private int num;
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public PersonalViewPagerAdapter(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int i) {
        return createFragment(i);
    }

    private Fragment createFragment(int i) {
        Fragment fragment = fragmentHashMap.get(i);
        if (fragment == null){
            switch (i){
                case 0:
                    fragment = new CollectionFragment();
                    break;
                case 1:
                    fragment = new FollowFragment();
                    break;
                case 2:
                    fragment = new RecommendFragment();
                default:
            }
            fragmentHashMap.put(i,fragment);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return num;
    }
}
