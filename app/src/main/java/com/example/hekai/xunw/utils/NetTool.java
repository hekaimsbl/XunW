package com.example.hekai.xunw.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/17
 * @describe Retrofit 网络请求
 **/
public class NetTool {
    /**
     * 后台地址
     */
    public static final String HOST_URL = "";
    /***
     * 真机测试地址
     */
    public static final String HOST_LOCAL_URL_TEST = "http://localhost:8080/FINDD/";

    /**
     * 模拟器测试地址
     */
    public static final String HOST_URL_EMULATOR = "http://10.0.2.2:8080/FINDD/";

    /**
     * 真机测试 手机usb共享网络条件下
     */
    public static final String HOST_LOCAL_URL_ONE_NET = "http://192.168.42.19:8080/FINDD/";

    public static final String HOST_URL_IMAGE = "http://10.0.2.2:8080";

    public static final String HOST_URL_EMULATOR_IMAGE = "http://10.0.2.2:8080";

    public static final String HOST_URL_ONE_IMAGE = "http://192.168.42.19:8080";


    public static final String DISTANCE_GET_URL = "https://restapi.amap.com/";


    public static NetTool instance;
    private Retrofit retrofit;

    private static final int TIMEOUT = 15;
    private boolean debugMode = true;

    public <T> T getApiService(Class<T> service){
        return retrofit.create(service);
    }

    private NetTool(String hostUrl){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);

        if (debugMode){
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(hostUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static NetTool getInstance(String hostUrl){
        if (instance == null){
            synchronized (NetTool.class){
                if (instance == null){
                    instance = new NetTool(hostUrl);
                }
            }
        }
        return instance;
    }


    /*private static Retrofit retrofit;

    private NetTool(){};

    public static Retrofit getNetTool(String baseUrl){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit;
        }else {
            return retrofit;
        }
    }
*/
}
