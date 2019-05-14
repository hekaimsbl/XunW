package com.example.hekai.xunw.services;

import com.amap.api.services.route.DistanceItem;
import com.example.hekai.xunw.bean.DistanceQueryResult;
import com.example.hekai.xunw.bean.Food;
import com.example.hekai.xunw.bean.FoodDownload;
import com.example.hekai.xunw.bean.FoodUpload;
import com.example.hekai.xunw.bean.KeySearchResult;
import com.example.hekai.xunw.bean.QueryData;
import com.example.hekai.xunw.utils.ApiResult;
import com.google.gson.JsonObject;


import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/8
 **/
public interface FoodApi {
    /**
     * 最新美食信息
     * @param startPage
     * @param pageSize
     * @return Observable<Food>
     */
    @GET("api/food/latestFood")
    Observable<ApiResult<List<Food>>> getLatestFoods(@Query("startPage") int startPage, @Query("pageSize") int pageSize);

    /**
     * 周排行信息
     * @param startPage
     * @param pageSize
     * @return Observable<List<Food>>
     */
    @GET("api/food/foodRankOfWeek")
    Observable<ApiResult<List<Food>>> getFoodWithMonthRank(@Query("startPage") int startPage, @Query("pageSize") int pageSize);

    /**
     * 月排行信息
     * @param startPage
     * @param pageSize
     * @return Observable<Food>
     */
    @GET("api/food/foodRankOfWeek")
    Observable<ApiResult<List<Food>>> getFoodWithWeekRank(@Query("startPage") int startPage, @Query("pageSize") int pageSize);

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

    /**
     * 发布上传接口
     * @param foodUpload
     * @return
     */
    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("api/food/publishFood")
    Observable<ApiResult<String>> publishFood(@Body FoodUpload foodUpload);

    /**
     * 发布上传接口
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("api/food/publishFood")
    Call<ApiResult<String>> publishFoodCall(@Field("foodInfo") String foodInfo);

    @Multipart
    @POST("api/food//uploadImage")
    Observable<ApiResult<String>> uploadImages(@Part("pictures") RequestBody picture);

    /**
     * 实体 + 多文件上传
     * @param data
     * @param picture
     * @return
     */
    @Multipart
    @POST("api/food//upload")
    Observable<ApiResult<String>> upload(@Part("data") RequestBody data,@Part("pictures") RequestBody picture);

    @GET("v3/distance")
    Observable<DistanceQueryResult> getDistance(@QueryMap Map<String,String> queryMap);

    @GET("api/food/queryOne")
    Observable<ApiResult<Food>> queryOneById(@Query("foodId") String foodId);

}
