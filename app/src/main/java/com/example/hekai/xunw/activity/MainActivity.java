package com.example.hekai.xunw.activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hekai.xunw.Interface.PermissionListener;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.activityTest.CardItemActivity;
import com.example.hekai.xunw.adapter.MainListAdapter;
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
                    startActivity(new Intent(MainActivity.this,CommentReplyActivity.class));
                    break;
                case R.id.nav_location:
                    startActivity(new Intent(MainActivity.this,FoodDetailsActivity.class));
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
                Logger.d(TAG,"Toolbar_settings clicked");
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
}
