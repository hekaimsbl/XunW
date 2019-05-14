package com.example.hekai.xunw.services;

import com.example.hekai.xunw.utils.ApiResult;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/27
 **/
public interface UserApi {
    @FormUrlEncoded
    @POST("api/user/insertRecommend")
    Observable<ApiResult<String>> insertRecommend(@Field("userId")String userId,
                                                  @Field("foodId")String foodId);
    @FormUrlEncoded
    @POST("api/user/deleteRecommend")
    Observable<ApiResult<String>> deleteRecommend(@Field("userId")String userId,
                                                  @Field("foodId")String foodId);
}
