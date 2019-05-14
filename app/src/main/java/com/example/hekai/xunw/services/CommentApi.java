package com.example.hekai.xunw.services;

import com.example.hekai.xunw.bean.Comment;
import com.example.hekai.xunw.utils.ApiResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/26
 **/
public interface CommentApi {
    @FormUrlEncoded
    @POST("api/comment/addCommentLike")
    Observable<ApiResult<String>> addCommentLike(@Field("userId")String userId,
                                                 @Field("commentId")int commentId);

    @FormUrlEncoded
    @POST("api/comment/deleteCommentLike")
    Observable<ApiResult<String>> deleteCommentLike(@Field("userId")String userId,
                                                    @Field("commentId")int commentId);

    @POST("api/comment/addCommentReply")
    Observable<ApiResult<String>> addCommentReply(@Field("userId") String userId,
                                                  @Field("commentId") int commentId,
                                                  @Field("content") String content);

    @FormUrlEncoded
    @POST("api/comment/addComment")
    Observable<ApiResult<String>> addComment(@Field("commentInfo") String commentInfo);

    @GET("api/comment/queryCommentsByFoodId")
    Observable<ApiResult<List<Comment>>> getComments(@Query("foodId") String foodId,
                                                     @Query("startPage") int startPage,
                                                     @Query("pageSize") int pageSize);
}
