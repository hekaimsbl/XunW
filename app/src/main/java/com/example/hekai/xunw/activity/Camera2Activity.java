package com.example.hekai.xunw.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.hekai.xunw.Fragment.MyCamera2Fragment;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.camera2.Camera2Fragment;

public class Camera2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_camera2);

        /*Camera2Fragment fragment = new Camera2Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.camera2,fragment);
        transaction.commit();*/


        MyCamera2Fragment camera2Fragment = new MyCamera2Fragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        //transaction.add(android.R.id.content,camera2Fragment).addToBackStack(null);
        //camera2Fragment.show(getSupportFragmentManager(),"");
        camera2Fragment.show(getSupportFragmentManager(),"");
        transaction.commit();
    }
}
