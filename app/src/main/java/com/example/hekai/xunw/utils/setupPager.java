package com.example.hekai.xunw.utils;

import android.content.Context;
import android.service.quicksettings.TileService;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class setupPager {
    private Context context;
    private PagerAdapter adapter;
    private TabLayout tabLayout;

    public void setupPager(Context context,PagerAdapter adapter, TabLayout tabLayout){
        this.context = context;
        this.adapter = adapter;
        this.tabLayout = tabLayout;
    }
    public void initPager(){

    }
}
