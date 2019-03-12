package com.example.hekai.xunw.activityTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.FoodAdapter;
import com.example.hekai.xunw.bean.Food;
import com.example.hekai.xunw.utils.MyApplication;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2018/12/28
 **/
public class CardItemActivity extends AppCompatActivity {

    @BindView(R.id.testRecycleView)
    RecyclerView recyclerView;

    ArrayList<Food> foods = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_item);
        ButterKnife.bind(this);
        //setupData();
        setupRecycleView();
    }

    private void setupRecycleView() {
        FoodAdapter adapter;
        setupData();
        //recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FoodAdapter(this,foods);
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
}
