package com.example.hekai.xunw.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.FoodRecyclerViewAdapter;
import com.example.hekai.xunw.bean.DistanceQueryResult;
import com.example.hekai.xunw.bean.DistanceResultItem;
import com.example.hekai.xunw.bean.Food;
import com.example.hekai.xunw.services.FoodApi;
import com.example.hekai.xunw.utils.ApiResult;
import com.example.hekai.xunw.utils.Messages;
import com.example.hekai.xunw.utils.NetTool;
import com.example.hekai.xunw.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class RankWeekFragment extends Fragment{
    private View view;
    private Context mContext;

    private Retrofit retrofit;

    public RankWeekFragment(){}

    @BindView(R.id.latest_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.latest_recyclerView)
    RecyclerView recyclerView;

    private FoodRecyclerViewAdapter foodRecyclerViewAdapter;

    ArrayList<Food> foods = new ArrayList<>();

    List<Food> moreFoods = new ArrayList<>();
    List<Food> refreshFoods = new ArrayList<>();

    private int startPage = 0;
    private int pageSize = 20;

    private AMapLocationClient locationClientSingle = null;
    private double myLocationLo;
    private double myLocationLa;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_latest,container,false);
        init();
        setupRecycleView();
        setRefreshListener();
        return view;
    }

    /**
     * 单次定位
     */
    private void singleLocation() {
        if (null == locationClientSingle) {
            locationClientSingle = new AMapLocationClient(getActivity());
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
        if (null == aMapLocation) {
            Logger.d("定位失败~~");
        } else {
            Logger.d("AmapLocationErrorCode:" + aMapLocation.getErrorCode() + "info:" + aMapLocation.getErrorInfo());
            if (aMapLocation.getErrorCode() == 0) {
                //Message message = Message.obtain();
            } else {
                sb.append(aMapLocation.getErrorCode() + "\n");
                ToastUtil.show("定位错误码:" + sb.toString());
            }
        }
    };

    private void setRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(() ->{
            Observable<ApiResult<List<Food>>> foodDownLoadObservable = NetTool.getInstance(NetTool.HOST_LOCAL_URL_ONE_NET).getApiService(FoodApi.class).getFoodWithWeekRank(startPage,pageSize);
            foodDownLoadObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResult<List<Food>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApiResult<List<Food>> listApiResult) {
                            if (listApiResult.getStatus()== Messages.RESULT_OK){
                                refreshFoods.clear();
                                refreshFoods = listApiResult.getData();

                                foods.clear();
                                foodRecyclerViewAdapter.notifyDataSetChanged();
                                int startLocation = foods.size();
                                Logger.d("foodSize" + startLocation);
                                foods.addAll(refreshFoods);
                                foodRecyclerViewAdapter.notifyDataSetChanged();
                                getDistance(refreshFoods,startLocation);

                                swipeRefreshLayout.setRefreshing(false);
                                startPage = 0;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showMsg("获取最新美食失败" + e);
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } );

        foodRecyclerViewAdapter.setOnMoreDataLoadListener(() ->{
            foods.add(null);
            foodRecyclerViewAdapter.notifyDataSetChanged();

            Observable<ApiResult<List<Food>>> foodDownLoadObservable = NetTool.getInstance(NetTool.HOST_LOCAL_URL_ONE_NET).getApiService(FoodApi.class).getFoodWithWeekRank(startPage,pageSize);
            foodDownLoadObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResult<List<Food>>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApiResult<List<Food>> listApiResult) {
                            if (listApiResult.getStatus() == Messages.RESULT_OK){
                                foods.remove(foods.size() - 1);
                                int startLocation = foods.size();
                                foodRecyclerViewAdapter.notifyDataSetChanged();

                                moreFoods.clear();
                                moreFoods = listApiResult.getData();
                                foods.addAll(moreFoods);
                                foodRecyclerViewAdapter.notifyDataSetChanged();

                                getDistance(moreFoods,startLocation);
                                foodRecyclerViewAdapter.setLoaded();
                                startPage += pageSize;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            ToastUtil.showMsg("加载更多失败");
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        });
    }

    private void getDistance(List<Food> foodList, int startLocation) {
        //发起距离查询 start
        //开始url拼接
        String myWebServiceKey = "51483941503f9b9a6f537cd30df868d9";
        String distanceType = "0";

        StringBuffer sb = new StringBuffer();
        Map<String,String> queryDistanceMap = new HashMap<>();

        queryDistanceMap.put("type",distanceType);
        queryDistanceMap.put("key", myWebServiceKey);
        String myLocation = "112.503,28.204378";
        queryDistanceMap.put("destination",myLocation);


        for (int i = 0; i < foodList.size(); i++) {
            Food food = foodList.get(i);
            sb.append(food.getLongitude());
            sb.append(",");
            sb.append(food.getLatitude());
            if (i != (foodList.size()-1)){
                sb.append("|");}
        }
        queryDistanceMap.put("origins",sb.toString());
        Logger.d("queryMap"+queryDistanceMap);

        //url拼接结束

        //发起距离查询网络请求
        Observable<DistanceQueryResult> distanceQueryResultObservable1 = retrofit.create(FoodApi.class).getDistance(queryDistanceMap);
        distanceQueryResultObservable1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DistanceQueryResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DistanceQueryResult distanceQueryResult) {
                        if ("1".equals(distanceQueryResult.getStatus())) {
                            ToastUtil.showMsg("距离获取成功");
                            List<DistanceResultItem> distanceItems = distanceQueryResult.getResults();
                            Logger.d("startLocation"+startLocation + "foodsS" + foods.size());
                            for (int i = startLocation; i < foods.size(); i++) {
                                int a = Integer.parseInt(distanceItems.get((i-startLocation)).getDistance());
                                double b = a/1000.0;
                                DecimalFormat df = new DecimalFormat("0.00");
                                foods.get(i).setDistance(df.format(b) + "km");
                            }
                            foodRecyclerViewAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showMsg("距离获取失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void setupRecycleView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        foodRecyclerViewAdapter = new FoodRecyclerViewAdapter(mContext,recyclerView);
        recyclerView.setAdapter(foodRecyclerViewAdapter);
        foodRecyclerViewAdapter.setData(foods);
    }

    private void init() {
        ButterKnife.bind(this,view);

        retrofit = new Retrofit.Builder()
                .baseUrl(NetTool.DISTANCE_GET_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
