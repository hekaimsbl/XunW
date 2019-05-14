package com.example.hekai.xunw.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/22
 **/
public class SharedPreferenceUtil {

    private static SharedPreferences mSharedPreferences = null;

    /**
     * 单例模式
     */
    public static synchronized SharedPreferences getInstance(Context context,String fileName) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getApplicationContext().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    /**
     * SharedPreferences常用操作方法
     */
    public static void putBoolean(String fileName,String key, boolean value, Context context) {
        SharedPreferenceUtil.getInstance(context,fileName).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String fileName,String key, boolean defValue, Context context) {
        return SharedPreferenceUtil.getInstance(context,fileName).getBoolean(key, defValue);
    }

    public static void putString(String fileName,String key, String value, Context context) {
        SharedPreferenceUtil.getInstance(context,fileName).edit().putString(key, value).apply();
    }

    public static String getString(String fileName,String key, String defValue, Context context) {
        return SharedPreferenceUtil.getInstance(context,fileName).getString(key, defValue);
    }

    public static void putInt(String fileName,String key, int value, Context context) {
        SharedPreferenceUtil.getInstance(context,fileName).edit().putInt(key, value).apply();
    }

    public static int getInt(String fileName,String key, int defValue, Context context) {
        return SharedPreferenceUtil.getInstance(context,fileName).getInt(key, defValue);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(String fileName,String key, Context context) {
        SharedPreferenceUtil.getInstance(context,fileName).edit().remove(key).apply();
    }

    /**
     * 清除所有内容
     */
    public static void clear(String fileName,Context context) {
        SharedPreferenceUtil.getInstance(context,fileName).edit().clear().apply();
    }
}
