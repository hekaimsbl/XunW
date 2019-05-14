package com.example.hekai.xunw.activity;

import android.content.Intent;
import android.support.annotation.UiThread;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.utils.FlagUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.zzhoujay.richtext.RichText;

import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 
 *   @author Hekai
 *   @author hekaimsbl@gmail.com   
 *   @date 2018/12/28
 **/
public class FoodDetailsActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.imageFoodDetail)
    ImageView imageFood;
    @BindView(R.id.textFoodName)
    TextView textFoodName;
    @BindView(R.id.distance)
    TextView textDistance;
    @BindView(R.id.textFoodAuthor)
    TextView textFoodAuthor;
    @BindView(R.id.textAddress)
    TextView textAddress;
    @BindView(R.id.textBusinessTime)
    TextView textBusinessTime;
    @BindView(R.id.textPhone)
    TextView textPhone;
    @BindView(R.id.RichTextView)
    TextView richTextView;
    @BindColor(R.color.white)
    int white;
    @BindDimen(R.dimen.small_s)
    int big;

    private String foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        getFoodId();
        init();
        setupToolbar();
        setData();
        getData();
    }

    private void getFoodId() {
        Intent intent = getIntent();
        foodId = intent.getStringExtra(FlagUtils.FOOD_ID);
    }

    private void setData() {
        String textString = "##个人简历\n" +
                "\n" +
                "***\n" +
                "\n" +
                "###基本信息：\n" +
                "\n" +
                "个人资料：男 | 21岁 | 本科 | 未婚 | 共青团员 | 　　　　　　　\n" +
                "　　　　　　　　　　　18774521365 | hekaimsbl@gmail.com |\n" +
                "\n" +
                "---\n" +
                "###求职意向：\n" +
                "　　　意向岗位：Java　　　意向城市：长沙\n" +
                "\n" +
                "---\n" +
                "###教育背景：\n" +
                "  \n" +
                "　　　2015-至今　　　　　吉首大学　　　　 本科　　　软件工程  \n" +
                "\n" +
                "　　　2018.07-2018.11　　苏州易康萌思　　培训　　　Android\n" +
                "  \n" +
                "---\n" +
                "###项目经验：\n" +
                "　　　2018.09-2018.11  \n" +
                "　　　寻味App　　　Android+Service服务+Mysql  \n" +
                "　　　该项目后台使用SSM框架实现了用户和信息的增删查改，App采    \n" +
                "　　　用了okhttp，高德地图SDK，glide图片加载等技术。\n" +
                "\n" +
                "---\n" +
                "###技能特长：\n" +
                "　　　1.熟悉 Android开发  \n" +
                "\n" +
                "　　　2.熟悉 Mysql  \n" +
                "\n" +
                "　　　3.熟悉 SSM Web框架  \n" +
                "　　　  \n" +
                "　　　4.熟悉 IDEA，Eclipse等开发工具  \n" +
                "  \n" +
                "　　　5.了解 Java编程\n" +
                " \n" +
                "---\n" +
                "###自我评价：\n" +
                "　　　本人是软件工程Android方向的一名大四学生，掌握一定的专业基础知识，" +
                "且自学能力较强，逻辑思维能力较强，自律能力较强，能很好的与周围的人相处，为人乐观向上，诚实守信。";

        Glide.with(this)
                .load("https://img.chinatimes.com/newsphoto/2016-08-16/656/20160816002633.jpg")
                .into(imageFood);
        textFoodName.setTextColor(white);
        textFoodName.setTextSize(big);
        textFoodName.setText("名称表特名称表特  ");
        textFoodAuthor.setTextColor(white);
        textFoodAuthor.setText("小星星");
        textDistance.setTextColor(white);
        textDistance.setText("000.00 Km");
        textAddress.setText("长沙市某某区缪缪杰");
        textBusinessTime.setText("7:00 - 19:00");
        textPhone.setText("187-1001-1002");

        RichText.fromMarkdown(textString).into(richTextView);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }

    private void getData() {
        //String sId = getIntent().getStringExtra(FlagUtils.FOOD_ID);
        /*int id = Integer.parseInt(sId);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(id);
            }
        });*/
        //Logger.d(id);
    }

    private void init() {
        ButterKnife.bind(this);
        RichText.initCacheDir(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.toolbar_settings:
                Toast.makeText(getApplicationContext(),"click toolbar_settings",Toast.LENGTH_LONG).show();
                break;
                default:
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }
}
