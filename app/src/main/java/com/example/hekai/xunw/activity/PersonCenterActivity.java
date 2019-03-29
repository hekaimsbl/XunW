package com.example.hekai.xunw.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hekai.xunw.Fragment.CollectionFragment;
import com.example.hekai.xunw.Fragment.FollowFragment;
import com.example.hekai.xunw.Fragment.RecommendFragment;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.MyViewPagerAdapter;
import com.example.hekai.xunw.adapter.PersonalViewPagerAdapter;
import com.example.hekai.xunw.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonCenterActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    @BindView(R.id.img_user)
    ImageView imgUser;
    @BindView(R.id.img_personal)
    ImageView imgPersonal;
    @BindView(R.id.userNickname)
    TextView userNickname;
    @BindView(R.id.tv_info)
    TextView userInfo;

    @BindView(R.id.personal_tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.personal_viewPager)
    ViewPager viewPager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindString(R.string.person_activity_toolbar_title)
    String title;

    private String[] titles = new String[]{
            "收藏",
            "关注",
            "推荐"
    };

    private MyViewPagerAdapter viewPagerAdapter;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_center);
        init();
        setToolbar();
        setHeader();
        setTabLayout();
    }

    private void setTabLayout() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (String tab:titles){
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
        tabLayout.addOnTabSelectedListener(this);
        fragments.add(new CollectionFragment());
        fragments.add(new FollowFragment());
        fragments.add(new RecommendFragment());

        viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    /**
     * 设置头部布局信息
     */
    private void setHeader() {
        String imgUserUrl = null;
        if (imgUserUrl == null) {
            Glide.with(this)
                    .load(R.drawable.default_head)
                    .into(imgUser);
        }else {
            Glide.with(this)
                    .load(imgUserUrl)
                    .thumbnail(0.5f)
                    .into(imgUser);
        }

        String imgPersonalUrl = null;
        imgPersonal.setBackgroundColor(ContextCompat.getColor(this,R.color.blue));
        if (imgPersonalUrl == null){
            Glide.with(this)
                    .load(R.drawable.ic_image_black_24dp)
                    .into(imgPersonal);
        }else {
            Glide.with(this)
                    .load(imgPersonalUrl)
                    .thumbnail(0.5f)
                    .into(imgPersonal);
        }
        userNickname.setTextColor(ContextCompat.getColor(this,R.color.black));
        userInfo.setTextColor(ContextCompat.getColor(this,R.color.black));
        userNickname.setText("userNickName");
        userInfo.setText("点赞 35 · 喜欢 5");
    }

    private void setToolbar() {

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        actionBar.setTitle(title);
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


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
}
