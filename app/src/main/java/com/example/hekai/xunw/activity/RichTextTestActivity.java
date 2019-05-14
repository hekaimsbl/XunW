package com.example.hekai.xunw.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.hekai.xunw.R;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RichTextTestActivity extends AppCompatActivity {
    @BindView(R.id.richTextView)
    TextView textView;

    public final String md = "##个人简历\n" +
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rich_text_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        RichText.initCacheDir(this);
        RichText.fromMarkdown(md).into(textView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RichText.clear(this);
    }
}
