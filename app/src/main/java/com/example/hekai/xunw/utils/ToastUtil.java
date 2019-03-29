package com.example.hekai.xunw.utils;

import android.widget.Toast;

import java.util.logging.Level;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/3/3
 **/
public class ToastUtil {
    private static final int LEVEL_D = 0;
    private static final int LEVEL_N = 1;

    private static final int level = LEVEL_D;

    private static Toast toast = null;

    public static void show(String msg) {
        if (level < LEVEL_N) {
            if (toast == null) {
                toast = Toast.makeText(MyApplication.getContext(), msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    public static void showMsg(Object msg){
        if (level < LEVEL_N) {
            if (toast == null) {
                toast = Toast.makeText(MyApplication.getContext(), msg.toString(), Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg.toString());
            }
            toast.show();
        }
    }
}
