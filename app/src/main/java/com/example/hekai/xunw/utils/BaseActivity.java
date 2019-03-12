package com.example.hekai.xunw.utils;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.hekai.xunw.Interface.PermissionListener;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class BaseActivity extends AppCompatActivity{
    private PermissionListener permissionListener;
    private static final int PERMISSION_REQUESTCODE = 100;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void requestRunPermission(String[] permissions, PermissionListener listener){
        permissionListener = listener;
        List<String> permissionLists = new ArrayList<>();
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,permission) !=
                    PackageManager.PERMISSION_GRANTED){
                permissionLists.add(permission);
            }
        }

        if (!permissionLists.isEmpty()){
            ActivityCompat.requestPermissions(this,
                    permissionLists.toArray(new String[permissionLists.size()]),
                    PERMISSION_REQUESTCODE);
        }else{
            permissionListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUESTCODE:
                if (grantResults.length > 0){
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++){
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED){
                            deniedPermissions.add(permission);
                        }
                        if (deniedPermissions.isEmpty()){
                            permissionListener.onGranted();
                        }else {
                            permissionListener.onDenied(deniedPermissions);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
