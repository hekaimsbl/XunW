package com.example.hekai.xunw.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hekai.xunw.R;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class RankMonthFragment extends Fragment {
    View view;

    public RankMonthFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       /* if (container.getTag() == null){
            view = inflater.inflate(R.layout.fragment_rank_month,container,false);
            container.setTag(view);
        }else {
            view = (View) container.getTag();
        }*/
        view = inflater.inflate(R.layout.fragment_rank_month,container,false);

        return view;

    }
}
