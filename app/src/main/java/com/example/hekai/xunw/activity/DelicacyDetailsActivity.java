package com.example.hekai.xunw.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bumptech.glide.Glide;
import com.example.hekai.xunw.Interface.PermissionListener;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.CommentAdapter;
import com.example.hekai.xunw.bean.Comment;
import com.example.hekai.xunw.bean.Food;
import com.example.hekai.xunw.utils.BaseActivity;
import com.example.hekai.xunw.utils.TimeUtils;
import com.example.hekai.xunw.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.zzhoujay.richtext.RichText;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Hekai
 * @author hekaimsbl@gmail.com
 * @date 2019/3/12
 **/
public class DelicacyDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbar_delicacy)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsing_toolbar;
    @BindView(R.id.delicacy_image_view)
    ImageView imageView;

    @BindView(R.id.tv_richText)
    TextView richTextView;
    @BindView(R.id.tv_author)
    TextView tv_author;
    @BindView(R.id.tv_createTime)
    TextView tv_createTime;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_businessTime)
    TextView tv_businessTime;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.comment_recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.bottom_toolbar_collection)
    TextView toolbarCollection;
    @BindView(R.id.bottom_toolbar_recommend)
    TextView toolbarRecommend;
    @BindView(R.id.bottom_toolbar_comment)
    TextView toolbarComment;
    @BindView(R.id.bottom_toolbar_share)
    TextView toolbarShare;

    private CommentAdapter adapter;
    private ArrayList<Comment> comments = new ArrayList<>();

    String textString = "##个人简历\n" + "\n" + "***\n" + "\n" + "###基本信息：\n" + "\n" + "个人资料：男 | 21岁 | 本科 | 未婚 | 共青团员 | 　　　　　　　\n" + "　　　　　　　　　　　18774521365 | hekaimsbl@gmail.com |\n" + "\n" + "---\n" + "###求职意向：\n" + "　　　意向岗位：Java　　　意向城市：长沙\n" + "\n" + "---\n" + "###教育背景：\n" + "  \n" + "　　　2015-至今　　　　　吉首大学　　　　 本科　　　软件工程  \n" + "\n" + "　　　2018.07-2018.11　　苏州易康萌思　　培训　　　Android\n" + "  \n" + "---\n" + "###项目经验：\n" + "　　　2018.09-2018.11  \n" + "　　　寻味App　　　Android+Service服务+Mysql  \n" + "　　　该项目后台使用SSM框架实现了用户和信息的增删查改，App采    \n" + "　　　用了okhttp，高德地图SDK，glide图片加载等技术。\n" + "\n" + "---\n" + "###技能特长：\n" + "　　　1.熟悉 Android开发  \n" + "\n" + "　　　2.熟悉 Mysql  \n" + "\n" + "　　　3.熟悉 SSM Web框架  \n" + "　　　  \n" + "　　　4.熟悉 IDEA，Eclipse等开发工具  \n" + "  \n" + "　　　5.了解 Java编程\n" + " \n" + "---\n" + "###自我评价：\n" + "　　　本人是软件工程Android方向的一名大四学生，掌握一定的专业基础知识，" + "且自学能力较强，逻辑思维能力较强，自律能力较强，能很好的与周围的人相处，为人乐观向上，诚实守信。";


    private MapView mapView = null;
    private AMap aMap;
    private RelativeLayout layout;

    private Food food;

    /**
     * 需要的权限
     */
    private String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,};

    /*定位*/
    private AMapLocationClient locationClientSingle = null;

    /**
     * 美食的经纬度坐标
     */
    private LatLng foodPoint = null;

    /**
     * 用户当前经纬度坐标
     */
    private LatLng myLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delicacy_details);
        init();

        requestPermission();

        singleLocation();


        setToolbar();
        setData();
        getCommentData();
        setCommentRecyclerViewData();

        setMap(savedInstanceState);

        bottomToolbarClicked();
    }

    private void bottomToolbarClicked() {

        toolbarRecommend.setOnClickListener(v -> {
            ToastUtil.show("toolbarRecommend");
        });

        toolbarCollection.setOnClickListener(v -> {
            ToastUtil.show("toolbarCollection");
        });

        toolbarComment.setOnClickListener(v -> {
            ToastUtil.show("toolbarComment");
        });

        toolbarShare.setOnClickListener(v -> {
            ToastUtil.show("toolbarShare");
        });
    }


    /**
     * 计算当前距离(直线)
     */
    private void setDistance() {
        if (null != foodPoint && null != myLocation) {
            float distance = AMapUtils.calculateLineDistance(myLocation, foodPoint);
            float distanceKm = distance / 1000;
            DecimalFormat decimalFormat = new DecimalFormat(".00");
            String result = decimalFormat.format(distanceKm);
            tv_distance.setText(result + " Km");
        }
    }

    /**
     * 单次定位
     */
    private void singleLocation() {
        if (null == locationClientSingle) {
            locationClientSingle = new AMapLocationClient(this.getApplicationContext());
        }

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        locationClientOption.setOnceLocation(true);

        //地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        locationClientSingle.setLocationListener(locationSingleListener);
        locationClientSingle.startLocation();
    }

    /**
     * 单次定位回调
     */
    AMapLocationListener locationSingleListener = aMapLocation -> {
        StringBuffer sb = new StringBuffer();
        String result = null;
        if (null == aMapLocation) {
            Logger.d("定位失败~~");
        } else {
            if (aMapLocation.getErrorCode() == 0) {
                myLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                Logger.d(myLocation.latitude);
                Logger.d(foodPoint.latitude);
                setDistance();
                Logger.d("定位结果:" + aMapLocation.getCity());
            } else {
                sb.append(aMapLocation.getErrorCode() + "\n");
                ToastUtil.show("定位错误码:" + sb.toString());
            }
        }
    };

    private void setMap(Bundle savedInstanceState) {
        layout = findViewById(R.id.layout_map);

        foodPoint = new LatLng(food.getLatitude(), food.getLongitude());
        AMapOptions aMapOptions = new AMapOptions();
        aMapOptions.camera(new CameraPosition(foodPoint, 8f, 0, 0));
        mapView = new MapView(this, aMapOptions);
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.addView(mapView, params);
        layout.setOnClickListener(v -> {
            ToastUtil.show("layout s");
        });
        final Marker marker = aMap.addMarker(new MarkerOptions().position(foodPoint).title(food.getTitle()));

        //地图交互-隐藏放大缩小an
        UiSettings uiSettings;
        uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
    }

    private void setCommentRecyclerViewData() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(this, comments);
        recyclerView.setAdapter(adapter);
    }

    private void getCommentData() {
        for (int i = 0; i < 4; i++) {
            Comment comment = new Comment();
            comment.setId(String.valueOf(i + 10));
            comment.setCommentTime(new Date());
            comment.setContent("评论内容" + i);
            comment.setFoodId(String.valueOf(i + 10));
            comment.setLikesNumber(i);
            comment.setReplyNumber(10 + i);
            comment.setLikesNumber(100 + i);
            comment.setUserNickName("nickname" + i);
            comments.add(comment);
        }
    }

    private void setData() {
        food = new Food();
        food.setAuthor("这是作者");
        food.setTime(new Date());
        food.setDistance(12.13);
        food.setImgUrl("http://dlzhengze.com/uploads/allimg/c180324/1521SIJ1C60-5P64.png");
        food.setMdText(textString);
        food.setAddress("这是地址这是地址这是地址可点击");
        food.setBusinessTime("周一至周五\n8.00 - 19.00");
        food.setPhone("184-4562-1256");
        food.setLatitude(27.642097281);
        food.setLongitude(113.155145645);

        Glide.with(this).load(food.getImgUrl()).into(imageView);
        RichText.fromMarkdown(food.getMdText()).into(richTextView);
        tv_author.setText(food.getAuthor());
        tv_createTime.setText(TimeUtils.utilDateToString(food.getTime()));
        tv_businessTime.setText(food.getBusinessTime());
        tv_distance.setText(food.getDistance() + "km");
        tv_phone.setText(food.getPhone());
        tv_address.setText(food.getAddress());
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        collapsing_toolbar.setTitleEnabled(false);
    }

    private void init() {
        ButterKnife.bind(this);
        RichText.initCacheDir(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.location:
                startFoodLocationActivity();
                break;
            case R.id.share:
                ToastUtil.show("分享");
                break;
            case R.id.settings:
                ToastUtil.show("设置");
                break;
            case R.id.settings_menu_feedback:
                ToastUtil.show("反馈");
                break;
            case R.id.settings_menu_mark:
                ToastUtil.show("评分");
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void startFoodLocationActivity() {
        Intent intent = new Intent(DelicacyDetailsActivity.this, FoodLocationMapActivity.class);
        if (foodPoint != null) {
            intent.putExtra("Food_Point_Longitude", foodPoint.longitude);
            intent.putExtra("Food_Point_Latitude", foodPoint.latitude);
        }
        Logger.d("La"+foodPoint.latitude+"\n"+
                "Lo"+foodPoint.longitude+"\n"+
                "address:"+food.getAddress());
        intent.putExtra("Food_Address", food.getAddress());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_delicacy_menu, menu);
        return true;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != locationClientSingle) {
            locationClientSingle.onDestroy();
            locationClientSingle = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
