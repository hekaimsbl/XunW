package com.example.hekai.xunw.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.adapter.CommentAdapter;
import com.example.hekai.xunw.bean.Comment;
import com.example.hekai.xunw.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentReplyActivity extends AppCompatActivity {

    @BindView(R.id.reply_toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView_reply)
    RecyclerView recyclerView;

    @BindView(R.id.reply_page_userLogo)
    ImageView userLogo;
    @BindView(R.id.reply_page_userName)
    TextView userName;
    @BindView(R.id.reply_page_time)
            TextView replyTime;
    @BindView(R.id.reply_page_likes_number)
            TextView likesNumber;
    @BindView(R.id.reply_page_content)
            TextView content;

    ArrayList<Comment> comments = new ArrayList<>();
    Comment reply = new Comment();

    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_reply);
        init();
        getData();
        setToolbar();
        setData();
    }

    private void setData() {
        if (reply.getUserImg() != null){
            Glide.with(this)
                    .load(reply.getUserImg())
                    .thumbnail(0.5f)
                    .into(userLogo);
        }
        userName.setText(reply.getUserName());
        replyTime.setText(TimeUtils.utilDateToString(reply.getCreateTime()));
        content.setText(reply.getContent());
        likesNumber.setText(String.valueOf(reply.getLikesNumber()));

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(this,comments);
        recyclerView.setAdapter(adapter);
    }

    private void getData() {

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            String replyTitle = "回复(" + comments.size() +")";
            actionBar.setTitle(replyTitle);
        }
    }

    private void init() {
        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
                default:
        }
        return super.onOptionsItemSelected(item);
    }
}
