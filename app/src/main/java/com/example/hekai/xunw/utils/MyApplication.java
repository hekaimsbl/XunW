package com.example.hekai.xunw.utils;

import android.app.Application;
import android.content.Context;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2018/12/28
 **/
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
