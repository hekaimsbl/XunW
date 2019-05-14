package com.example.hekai.xunw.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.bean.KeySearchResult;
import com.example.hekai.xunw.bean.QueryData;
import com.example.hekai.xunw.utils.Messages;
import com.example.hekai.xunw.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/15
 **/
public class SearchKeyResultFragment extends Fragment {
    private View view;
    private Context mContext;

    List<KeySearchResult> foodList = new ArrayList<>();
    List<KeySearchResult> tagList = new ArrayList<>();

    @BindColor(R.color.black)
    int color_black;

    @BindString(R.string.key_search_fragment_food_title)
    String string_foodTitle;

    @BindView(R.id.search_scrollView)
    ScrollView scrollView;

    public SearchKeyResultFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_search_key_result, container, false);
            ButterKnife.bind(this, view);
        }
        mContext = getActivity();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(List<QueryData<List<KeySearchResult>>> keyQueryResult) {

        for (QueryData<List<KeySearchResult>> data : keyQueryResult) {
            if (data.getMsg().equals(Messages.TABLE_FOOD)) {
                foodList = data.getData();
            }
            if (data.getMsg().equals(Messages.TABLE_TAG)) {
                tagList = data.getData();
            }
        }
        if (foodList!=null){
            TextView textFood = new TextView(scrollView.getContext());
            LinearLayout.LayoutParams textFoodLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            textFood.setTextColor(color_black);
            textFood.setText(string_foodTitle);
            scrollView.addView(textFood,textFoodLayout);

            ListView listView = new ListView(scrollView.getContext());
            List<String> stringList = new ArrayList<>();
            for (int i = 0; i < foodList.size(); i++) {
                stringList.add(foodList.get(i).getDescribe());
            }
            scrollView.addView(listView,textFoodLayout);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,stringList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    queryFoodDataById(foodList.get(position).getId());
                }


            });
        }

    }

    private void queryFoodDataById(String foodId) {
        ToastUtil.showMsg("foodId + " + foodId);
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
