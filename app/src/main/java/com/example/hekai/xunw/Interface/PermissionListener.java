package com.example.hekai.xunw.Interface;

import java.util.List;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public interface PermissionListener {
        //已授权
        void onGranted();
        //未授权
        void onDenied(List<String> deniedPermission);
}
