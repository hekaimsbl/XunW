package com.example.hekai.xunw.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hekai.xunw.R;
import com.example.hekai.xunw.bean.Comment;
import com.example.hekai.xunw.utils.TimeUtils;
import com.example.hekai.xunw.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/3/7
 **/
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private ArrayList<Comment> comments;
    private boolean likes_flag = true;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comment, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        Comment comment = comments.get(i);

        commentViewHolder.layout.setOnClickListener(v -> {
            showDialog();
            ToastUtil.show("layout clicked");
        });
        if (comment.getUserImgUrl() == null) {
            Glide.with(context)
                    .load(R.drawable.default_head)
                    .thumbnail(0.5f)
                    .into(commentViewHolder.imageView);
        } else {
            Glide.with(context)
                    .load(comment.getUserImgUrl())
                    .thumbnail(0.5f)
                    .into(commentViewHolder.imageView);
        }
        commentViewHolder.userName.setText(comment.getUserNickName());
        commentViewHolder.createTime.setText(TimeUtils.utilDateToString(comment.getCommentTime()));
        commentViewHolder.likesNumber.setText(String.valueOf(comment.getLikesNumber()));
        commentViewHolder.content.setText(comment.getContent());
        if (comment.getReplyNumber() != 0) {
            commentViewHolder.reply.setText(String.valueOf(comment.getReplyNumber()) + "条回复 >");
            commentViewHolder.reply.setOnClickListener(v -> {
                ToastUtil.show("reply clicked");
            });
        } else {
            commentViewHolder.reply.setVisibility(View.GONE);
        }

        commentViewHolder.imageViewLikes.setOnClickListener(v -> {
            ToastUtil.show("likes clicked");
            if (likes_flag) {
                comment.setLikesNumber(comment.getLikesNumber() + 1);
                String likes = String.valueOf(comment.getLikesNumber());
                commentViewHolder.likesNumber.setText(likes);
                int i1 = ContextCompat.getColor(context, R.color.comment_like_pressed_red);
                commentViewHolder.likesNumber.setTextColor(i1);
                likes_flag = false;
            } else {
                comment.setLikesNumber(comment.getLikesNumber() - 1);
                String likes = String.valueOf(comment.getLikesNumber());
                commentViewHolder.likesNumber.setText(likes);
                int i2 = ContextCompat.getColor(context, R.color.black);
                commentViewHolder.likesNumber.setTextColor(i2);
                likes_flag = true;
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_comment, null);

        TextView dialog_reply = v.findViewById(R.id.dialog_comment_reply);
        TextView dialog_share = v.findViewById(R.id.dialog_comment_share);
        TextView dialog_copy = v.findViewById(R.id.dialog_comment_copy);
        TextView dialog_report = v.findViewById(R.id.dialog_comment_report);

        dialog_reply.setText(R.string.dialog_comment_reply);
        dialog_share.setText(R.string.dialog_comment_share);
        dialog_copy.setText(R.string.dialog_comment_copy);
        dialog_report.setText(R.string.dialog_comment_report);

        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog_reply.setOnClickListener(v1 -> {
            ToastUtil.show("reply");
        });

        dialog_share.setOnClickListener(v1 -> {
            ToastUtil.show("share");
        });

        dialog_copy.setOnClickListener(v1 -> {
            ToastUtil.show("copy");
        });

        dialog_report.setOnClickListener(v1 -> {
            ToastUtil.show("report");
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_comment)
        LinearLayout layout;
        @BindView(R.id.comment_page_userLogo)
        ImageView imageView;
        @BindView(R.id.comment_page_likes)
        ImageView imageViewLikes;
        @BindView(R.id.comment_page_userName)
        TextView userName;
        @BindView(R.id.comment_page_time)
        TextView createTime;
        @BindView(R.id.comment_page_likes_number)
        TextView likesNumber;
        @BindView(R.id.comment_page_content)
        TextView content;
        @BindView(R.id.comment_page_reply)
        TextView reply;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
