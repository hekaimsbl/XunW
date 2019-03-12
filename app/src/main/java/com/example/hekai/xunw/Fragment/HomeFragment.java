package com.example.hekai.xunw.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.FragmentAdapter;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class HomeFragment extends Fragment {

    public HomeFragment(){

    }

    @BindView(R.id.tab_layout_home)
    TabLayout tabLayout;

    @BindView(R.id.view_pager_home)
            ViewPager viewPager;

    @BindArray(R.array.tabs_home)
            String[] tabs_home;

    View view;

    private TabLayout.Tab tabLatest;
    private TabLayout.Tab tabHottest;
    private TabLayout.Tab tabRankWeek;
    private TabLayout.Tab tabRankMonth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (container.getTag() == null){
            view = inflater.inflate(R.layout.fragment_home,container,false);
            init();
            setupTab();
            container.setTag(view);
        }else {
            view = (View) container.getTag();
        }
        return view;

    }

    private void setupTab() {
        FragmentPagerAdapter adapter = new FragmentAdapter(tabs_home,getChildFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLatest = tabLayout.getTabAt(0);
        tabHottest = tabLayout.getTabAt(1);
        tabRankWeek = tabLayout.getTabAt(2);
        tabRankMonth = tabLayout.getTabAt(3);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == tabLatest){

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        tabLatest.select();
                        break;
                    case 1:
                        tabHottest.select();
                        break;
                    case 2:
                        tabRankWeek.select();
                        break;
                    case 3:
                        tabRankMonth.select();
                        break;
                        default:
                            break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void init() {
        ButterKnife.bind(this,view);
    }

}
