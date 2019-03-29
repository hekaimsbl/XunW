package com.example.hekai.xunw.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.ViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class TabTestActivity extends AppCompatActivity {
    /**
     * 菜单标题
     */
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_Home,
            R.string.tab_Near,
            R.string.tab_Community,
            R.string.tab_Me};

    /**
     * 菜单图标
     */
    private final int[] TAB_IMAGES = new int[]{
            R.drawable.tab_main_home_selector,
            R.drawable.tab_main_near_selector,
            R.drawable.tab_main_community_selector,
            R.drawable.tab_main_person_selector
    };
    private PagerAdapter adapter;

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_test);
        ButterKnife.bind(this);
        initPager();
        setupTabs(tabLayout,getLayoutInflater(),TAB_TITLES,TAB_IMAGES);
    }

    private void initPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //取消平滑切换
                viewPager.setCurrentItem(tab.getPosition(),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] TAB_TITLES, int[] TAB_IMAGES) {
        for (int i = 0; i < TAB_IMAGES.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.item_main_menu,null);
            tab.setCustomView(view);
            TextView textView = view.findViewById(R.id.txt_tab);
            textView.setText(TAB_TITLES[i]);
            ImageView imageView = view.findViewById(R.id.img_tab);
            imageView.setImageResource(TAB_IMAGES[i]);
            tabLayout.addTab(tab);
        }
    }
}
