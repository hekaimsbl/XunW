package com.example.hekai.xunw.activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hekai.xunw.Fragment.FindFragment;
import com.example.hekai.xunw.Fragment.HomeFragment;
import com.example.hekai.xunw.Fragment.MeFragment;
import com.example.hekai.xunw.Fragment.WaitFragment;
import com.example.hekai.xunw.Interface.PermissionListener;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.activityTest.CardItemActivity;
import com.example.hekai.xunw.adapter.MainListAdapter;
import com.example.hekai.xunw.adapter.MyViewPagerAdapter;
import com.example.hekai.xunw.adapter.ViewPagerAdapter;
import com.example.hekai.xunw.utils.BaseActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    /**
     * The Color primary.
     */
    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    /**
     * 侧滑控件
     */
    private DrawerLayout mDrawerLayout;

    String[] titles = new String[]{
            "首页",
            "附近",
            "社群",
            "我"
    };

    /**
     * The View pager.
     */
    @BindView(R.id.view_pager2)
    ViewPager viewPager;
    /**
     * The Tab layout.
     */
    @BindView(R.id.tab_layout2)
    TabLayout tabLayout;

    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Logger.addLogAdapter(new AndroidLogAdapter());

        requestPermission();
        setupToolbarAndDrawerLayout();
        setupNavigationView();
        setPager();
    }

    private void setPager() {
        /**
         * 菜单标题
         */
         int[] TAB_TITLES = new int[]{
                R.string.tab_Home,
                R.string.tab_Near,
                R.string.tab_Community,
                R.string.tab_Me};

        /**
         * 菜单图标
         */
        int[] TAB_IMAGES = new int[]{
                R.drawable.tab_main_home_selector,
                R.drawable.tab_main_near_selector,
                R.drawable.tab_main_community_selector,
                R.drawable.tab_main_person_selector
        };

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new WaitFragment());
        fragments.add(new FindFragment());
        fragments.add(new MeFragment());

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        //设置自定义tab
        for (int i = 0; i < myViewPagerAdapter.getCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(R.layout.item_main_menu);
            TextView textView = tab.getCustomView().findViewById(R.id.txt_tab);
            ImageView imageView = tab.getCustomView().findViewById(R.id.img_tab);
            textView.setText(TAB_TITLES[i]);
            imageView.setImageResource(TAB_IMAGES[i]);
            View view = tab.getCustomView();
            if (i == 3){
                view.setOnClickListener(v -> {
                    startActivity(new Intent(MainActivity.this,PersonCenterActivity.class));
                    tabLayout.getTabAt(0).select();
                });
            }
        }
    }

    private void requestPermission() {
        requestRunPermission(PERMISSIONS, new PermissionListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> deniedPermission) {

            }
        });
    }

    private void setupNavigationView() {
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_personal);
        navView.setNavigationItemSelectedListener((menuItem) ->{
            switch (menuItem.getItemId()){
                case R.id.nav_personal:
                    startActivity(new Intent(MainActivity.this,PersonCenterActivity.class));
                    break;
                case R.id.nav_location:
                    startActivity(new Intent(MainActivity.this,PublishActivity.class));
                    break;
                case R.id.nav_cardTest:
                    startActivity(new Intent(MainActivity.this,DelicacyDetailsActivity.class));
                    break;
                default:
            }
                mDrawerLayout.closeDrawers();
                return true;
        });
    }

    private void setupToolbarAndDrawerLayout() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.toolbar_settings:
                startActivity(new Intent(MainActivity.this,CommentTestActivity.class));
                break;
            case R.id.toolbar_search:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
