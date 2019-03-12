package com.example.hekai.xunw.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.CommentAdapter;
import com.example.hekai.xunw.bean.Comment;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 *   @author Hekai
 *   @author hekaimsbl@gmail.com
 *   @date 2019/3/10
 **/
public class CommentTestActivity extends AppCompatActivity {
    private CommentAdapter adapter;

    ArrayList<Comment> comments = new ArrayList<>();

    @BindView(R.id.testRecycleView)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_test);
        init();
        setupData();
        setupRe();
    }

    private void init() {
        ButterKnife.bind(this);
    }

    private void setupRe() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(this,comments);
        recyclerView.setAdapter(adapter);
    }

    private void setupData() {
        for (int i = 0; i < 4; i++) {
            Comment comment = new Comment();
            comment.setId(String.valueOf(i+10));
            comment.setCommentTime(new Date());
            comment.setContent("评论内容"+i);
            comment.setFoodId(String.valueOf(i+10));
            comment.setLikesNumber(i);
            comment.setReplyNumber(10+i);
            comment.setLikesNumber(100+i);
            comment.setUserNickName("nickname"+i);
            comments.add(comment);
        }
    }

}
