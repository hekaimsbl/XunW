package com.example.hekai.xunw.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hekai.xunw.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class HottestFragment extends Fragment {
    View view;

    public HottestFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* if (container.getTag() == null){
            view = inflater.inflate(R.layout.fragment_hottest,container,false);
            container.setTag(view);
        }else {
            view = (View) container.getTag();
        }*/
        view = inflater.inflate(R.layout.fragment_hottest,container,false);
        init();
        return view;

    }

    private void init() {
        ButterKnife.bind(this,view);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
