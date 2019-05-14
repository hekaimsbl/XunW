package com.example.hekai.xunw.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.example.hekai.xunw.Fragment.SearchKeyResultFragment;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.bean.KeySearchResult;
import com.example.hekai.xunw.bean.QueryData;
import com.example.hekai.xunw.services.FoodApi;
import com.example.hekai.xunw.utils.ApiResult;
import com.example.hekai.xunw.utils.Info;
import com.example.hekai.xunw.utils.Messages;
import com.example.hekai.xunw.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The type Search activity.
 */
public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_result_list)
    FrameLayout frameLayout;
    @BindView(R.id.search_scrollView)
    ScrollView scrollView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SearchKeyResultFragment keyResultFragment;

    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;

    private FoodApi foodService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        createRetrofit();
        setToolbar();
    }

    private void createRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit searchRetrofit = new Retrofit.Builder()
                .baseUrl(Info.HOST_URL_AUMTLER_TEST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //.client(client)
                .build();
        foodService = searchRetrofit.create(FoodApi.class);
    }




    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void init() {
        ButterKnife.bind(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        keyResultFragment = new SearchKeyResultFragment();
        transaction.replace(R.id.search_result_list,keyResultFragment);
        transaction.commit();
        frameLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                closeSearchView();
                break;
            case R.id.toolbar_setting:
                retrofitTest();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void retrofitTest() {
        Call<ApiResult<List<QueryData<List<KeySearchResult>>>>> call = foodService.queryKey(0,"吃");
        call.enqueue(new Callback<ApiResult<List<QueryData<List<KeySearchResult>>>>>() {
            @Override
            public void onResponse(Call<ApiResult<List<QueryData<List<KeySearchResult>>>>> call, Response<ApiResult<List<QueryData<List<KeySearchResult>>>>> response) {
                ApiResult<List<QueryData<List<KeySearchResult>>>> result = response.body();
                Logger.d("data" + result.getData());
                List<QueryData<List<KeySearchResult>>> te = new ArrayList<>();
                Gson gson = new Gson();
                Logger.d("gson " + gson.toJson(te));
                Logger.d("data " + result.getData().size());
                Logger.d("data a " + result.getData().get(1).getMsg());
                for (int i = 0; i < result.getData().size(); i++) {
                    if (result.getData().get(i).getData().isEmpty()) {
                        Logger.d("dataaa0" + result.getData().get(i).getMsg());
                    }
                    if (!result.getData().get(i).getData().isEmpty()){
                    for (int j = 0; j < result.getData().get(i).getData().size(); j++) {

                        Logger.d("datata " + result.getData().get(i).getData().get(j).getDescribe());
                    }}
                }
                //Logger.d(response.body().getData().get(0).getData().get(0).getDescribe());
            }

            @Override
            public void onFailure(Call<ApiResult<List<QueryData<List<KeySearchResult>>>>> call, Throwable t) {

            }
        });
        /*Call<String> stringCall = foodService.queryKeys(0,"吃");
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Logger.d(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //无文本时隐藏删除
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        //获取屏幕宽度，设置searchview 宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        //ToastUtil.showMsg(width);
        //解决searchview展开覆盖其他item问题
        searchView.setMaxWidth(width - 280);

        searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        return super.onCreateOptionsMenu(menu);
    }


    private List<QueryData<List<KeySearchResult>>> deserializeKeyResult(String json) {
        if (json == null ){
            return null;
        }
        com.orhanobut.logger.Logger.d(json);
        List<QueryData<List<KeySearchResult>>> result = null;
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if (Messages.RESULT_OK == jsonObject.get("status_code").getAsInt()) {
            JsonArray data = jsonObject.get("data").getAsJsonArray();
            result = gson.fromJson(data, new TypeToken<List<QueryData<List<KeySearchResult>>>>() {
            }.getType());
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        if (searchAutoComplete.isShown()) {
            try {
                searchAutoComplete.setText("");
                //利用反射调用收起SearchView的onCloseClicked()方法
                Method method = searchView.getClass().getDeclaredMethod("onCloseClicked");
                method.setAccessible(true);
                method.invoke(searchView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            super.onBackPressed();
        }
    }


    /**
     * 当搜索框展开时，点击返回关闭搜索框
     */
    private void closeSearchView() {
        if (searchAutoComplete.isShown()) {
            try {
                searchAutoComplete.setText("");
                //利用反射调用收起SearchView的onCloseClicked()方法
                Method method = searchView.getClass().getDeclaredMethod("onCloseClicked");
                method.setAccessible(true);
                method.invoke(searchView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            finish();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
