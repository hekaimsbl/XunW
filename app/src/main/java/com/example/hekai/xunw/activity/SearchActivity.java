package com.example.hekai.xunw.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.utils.ToastUtil;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type Search activity.
 */
public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SearchView searchView;
    private SearchView.SearchAutoComplete searchAutoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        setToolbar();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                closeSearchView();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //无文本时隐藏删除
        searchView.onActionViewExpanded();
        searchView.setIconified(false);
        //获取屏幕宽度，设置searchview 宽度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        ToastUtil.showMsg(width);
        //解决searchview展开覆盖其他item问题
        searchView.setMaxWidth(width-280);

        searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(R.id.search_src_text);
        return super.onCreateOptionsMenu(menu);
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
    public void closeSearchView(){
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
}
