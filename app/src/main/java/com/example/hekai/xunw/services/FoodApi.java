package com.example.hekai.xunw.services;

import com.example.hekai.xunw.bean.Food;
import com.example.hekai.xunw.bean.KeySearchResult;
import com.example.hekai.xunw.bean.QueryData;
import com.example.hekai.xunw.utils.ApiResult;
import com.google.gson.JsonObject;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/8
 **/
public interface FoodServiceApi {
    /**
     * 最新美食信息
     * @param startPage
     * @param pageSize
     * @return Observable<Food>
     */
    @GET("api/food/latestFood")
    Call<ApiResult<List<Food>>> getLatestFoods(@Query("startPage") int startPage, @Query("pageSize") int pageSize);

    /**
     * 搜索框查询接口
     * @param startPage 开始页
     * @param keyWord 关键字
     * @return Observable<List<KeySearchResult>>
     */
    @GET("api/food/queryKey")
    Call<ApiResult<List<QueryData<List<KeySearchResult>>>>> queryKey(@Query("startPage") int startPage, @Query("key") String keyWord);

    @GET("api/food/queryKey")
    Call<String> queryKeys(@Query("startPage") int startPage, @Query("key") String keyWord);
}
