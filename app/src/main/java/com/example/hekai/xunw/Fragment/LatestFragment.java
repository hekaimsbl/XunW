package com.example.hekai.xunw.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.FoodAdapter;
import com.example.hekai.xunw.bean.Food;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/25
 **/
public class LatestFragment extends Fragment {
    View view;

    public LatestFragment(){}

    @BindView(R.id.latest_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.latest_recyclerView)
    RecyclerView recyclerView;

    private FoodAdapter adapter;

    ArrayList<Food> foods = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_latest,container,false);
        init();
        setupRecycleView();
        return view;
    }

    private void setupRecycleView() {
        setupData();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FoodAdapter(getActivity(),foods);
        recyclerView.setAdapter(adapter);
    }

    private void setupData() {

        for (int i = 0; i < 4; i++) {
            Food food = new Food();
            food.setAuthor("hello word"+i);
            food.setDistance(Double.valueOf(14+i));
            food.setFoodId(i);
            food.setImgUrl("https://img.chinatimes.com/newsphoto/2016-08-16/656/20160816002633.jpg");
            food.setLikes(13);
            food.setTips("#好吃");
            food.setTitle("长沙小龙小"+i);
            food.setTime(new Date());

            foods.add(food);
        }
        Logger.d(foods);
        Logger.d(foods.get(0).getTitle());
    }

    private void init() {
        ButterKnife.bind(this,view);
    }
}
