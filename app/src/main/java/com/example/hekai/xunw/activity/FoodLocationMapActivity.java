package com.example.hekai.xunw.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.ScaleAnimation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.example.hekai.xunw.Interface.PermissionListener;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.utils.BaseActivity;
import com.example.hekai.xunw.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FoodLocationMapActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab_nvi)
    FloatingActionButton fabNvi;


    /**
     * 地图视图
     */
    MapView mapView;
    /**
     * 地图对象
     */
    private AMap aMap;

    private UiSettings uiSettings;

    private ActionBar actionBar = null;

    private String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,};

    private LatLng foodPoint = null;

    private LatLng myLocation = null;

    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_location_map);

        init();
        requestPermission();
        setToolbar();
        getIntentInfo();
        setMap(savedInstanceState);
        fabClicked();
    }

    private void fabClicked() {
        fabNvi.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);

            /*//直接调用驾车导航
            Uri uri = Uri.parse("androidamap://navi?sourceApplication=高德地图&" +
                    "lat=" + foodPoint.latitude + "&" +
                    "lon=" + foodPoint.longitude + "&" +
                    "dev=1" + "&" +
                    "style=2");*/
            //调用路径规划
            Uri uri = Uri.parse("androidamap://route/plan/?sid=BGVIS1&" +
                    //起点纬度
                    "slat=" + "&" +
                    //起点经度
                    "slon=" + "&" +
                    //起点名称
                    "sname=起点&" +
                    "did=BGVIS2&" +
                    "dlat=" + foodPoint.latitude +"&" +
                    "dlon=" + foodPoint.longitude + "&" +
                    "dname=终点&" +
                    "dev=0&" +
                    //t = 0（驾车）= 1（公交）= 2（步行）= 3（骑行）= 4（火车）= 5（长途客车）
                    //（骑行仅在V7.8.8以上版本支持）
                    "t=0");
            intent.setData(uri);
            startActivity(intent);
        });
    }

    /**
     * 取出上一个活动传递的信息
     * Food_Point 美食的经纬度坐标
     * Food_Address 美食的地址
     */
    private void getIntentInfo() {
        Intent intent = this.getIntent();
        Double latitude = intent.getDoubleExtra("Food_Point_Latitude", 0);
        Double longitude = intent.getDoubleExtra("Food_Point_Longitude", 0);
        Logger.d("latitude:" + latitude + "\n" + "longitude:" + longitude + "\n");
        foodPoint = new LatLng(latitude, longitude);
        Logger.d("foodPoint:" + foodPoint);
        address = intent.getStringExtra("Food_Address");

        actionBar.setTitle(address);
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

    private void setToolbar() {
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    private void init() {
        ButterKnife.bind(this);
    }

    private void setMap(Bundle savedInstanceState) {
        mapView = findViewById(R.id.layout_food_location_map);
        mapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //设置地图中心点
        //CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLng(foodPoint);
        //改变地图状态
        //aMap.animateCamera(mCameraUpdate);
        //aMap.moveCamera(mCameraUpdate);
        //设置缩放级别
        //设置定位图标可见
        aMap.setMyLocationEnabled(true);


        //设置蓝点样式
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        //连续定位
        //LOCATION_TYPE_MAP_ROTATE_NO_CENTER 定位、但不会移动到地图中心点，地图依照设备方向旋转，并且会跟随设备移动。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);
        //定位时间间隔-毫秒
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);

        //定位监听回调
        aMap.setOnMyLocationChangeListener(location -> {
            myLocation = new LatLng(location.getLatitude(),location.getLongitude());
            Logger.d(location);
                });

        aMap.setOnMapLoadedListener(() ->{
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(foodPoint));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        });

        //地图交互-隐藏放大缩小
        uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);
        uiSettings.setScaleControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);

        markerFood();
    }



    private void markerFood() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(foodPoint);
        markerOptions.title(address);
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_food_location)));
        markerOptions.setFlat(true);
        Marker marker = aMap.addMarker(markerOptions);

        //设置marker 动画
        Animation markerAnimation = new ScaleAnimation(0,1,0,1);
        markerAnimation.setDuration(2000);
        marker.setAnimation(markerAnimation);
        marker.startAnimation();
    }

    AMapLocationListener aMapLocationListener = aMapLocation -> {
        if (null == aMapLocation){
            Logger.d("定位失败~~");
        }else {
            if(aMapLocation.getErrorCode() == 0){
                myLocation = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            }else {
                Logger.d(aMapLocation.getErrorCode());
            }
        }
    };

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
    protected void onDestroy() {
        super.onDestroy();
        if (null != mapView) {
            mapView.onDestroy();
            mapView = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mapView) {
            mapView.onPause();
            mapView = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mapView) {
            mapView.onResume();
            mapView = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != mapView) {
            mapView.onSaveInstanceState(outState);
            mapView = null;
        }
    }

}
