package com.example.hekai.xunw.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.hekai.xunw.activity.CommentReplyActivity;
import com.example.hekai.xunw.bean.Comment;
import com.example.hekai.xunw.services.CommentApi;
import com.example.hekai.xunw.utils.ApiResult;
import com.example.hekai.xunw.utils.Messages;
import com.example.hekai.xunw.utils.NetTool;
import com.example.hekai.xunw.utils.SharedPreferenceUtil;
import com.example.hekai.xunw.utils.TimeUtils;
import com.example.hekai.xunw.utils.ToastUtil;
import com.example.hekai.xunw.utils.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/3/7
 **/
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<Comment> comments;
    private boolean likes_flag = true;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comment, viewGroup, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        Comment comment = comments.get(i);

        commentViewHolder.layout.setOnClickListener(v -> {
            showDialog(comment);
            ToastUtil.show("layout clicked");
        });
        if (comment.getUserImg() == null) {
            Glide.with(context).load(R.drawable.default_head).thumbnail(0.5f).into(commentViewHolder.imageView);
        } else {
            Glide.with(context).load(comment.getUserImg()).thumbnail(0.5f).into(commentViewHolder.imageView);
        }
        commentViewHolder.userName.setText(comment.getUserName());
        commentViewHolder.createTime.setText(TimeUtils.utilDateToString(comment.getCreateTime()));
        commentViewHolder.likesNumber.setText(String.valueOf(comment.getLikesNumber()));
        commentViewHolder.content.setText(comment.getContent());
        if (comment.getReplyNumber() != 0) {
            commentViewHolder.reply.setText(String.valueOf(comment.getReplyNumber()) + "条回复 >");
            commentViewHolder.reply.setOnClickListener(v -> {
                Intent intent = new Intent(context, CommentReplyActivity.class);
                intent.putExtra("commentId",comment.getCommentId());
                context.startActivity(intent);
            });
        } else {
            commentViewHolder.reply.setVisibility(View.GONE);
        }

        commentViewHolder.imageViewLikes.setOnClickListener(v -> {
            ToastUtil.show("likes clicked");

            if (likes_flag) {
                String userId = SharedPreferenceUtil.getString(UserInfo.USER_FILENAME, UserInfo.USER_ID, UserInfo.DEFUALT_VALUE, context);
                Observable<ApiResult<String>> addCommentObservable = NetTool.getInstance(NetTool.HOST_LOCAL_URL_ONE_NET).getApiService(CommentApi.class).addCommentLike(userId, comment.getCommentId());
                addCommentObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ApiResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiResult<String> stringApiResult) {
                        if (stringApiResult.getStatus() == Messages.RESULT_OK) {
                            comment.setLikesNumber(comment.getLikesNumber() + 1);
                            String likes = String.valueOf(comment.getLikesNumber());
                            commentViewHolder.likesNumber.setText(likes);
                            int i1 = ContextCompat.getColor(context, R.color.comment_like_pressed_red);
                            commentViewHolder.likesNumber.setTextColor(i1);
                            likes_flag = false;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

            } else {
                String userId = SharedPreferenceUtil.getString(UserInfo.USER_FILENAME, UserInfo.USER_ID, UserInfo.DEFUALT_VALUE, context);
                Observable<ApiResult<String>> deleteCommentLike = NetTool.getInstance(NetTool.HOST_LOCAL_URL_ONE_NET).getApiService(CommentApi.class).deleteCommentLike(userId, comment.getCommentId());
                deleteCommentLike.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ApiResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ApiResult<String> stringApiResult) {
                        if (stringApiResult.getStatus() == Messages.RESULT_OK) {
                            comment.setLikesNumber(comment.getLikesNumber() - 1);
                            String likes = String.valueOf(comment.getLikesNumber());
                            commentViewHolder.likesNumber.setText(likes);
                            int i2 = ContextCompat.getColor(context, R.color.black);
                            commentViewHolder.likesNumber.setTextColor(i2);
                            likes_flag = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }
        });

    }

    private void showDialog(Comment comment) {
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
            addCommentReply(comment);
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

    private void addCommentReply(Comment comment) {
        String content = "这是我的回复";
        String userId = SharedPreferenceUtil.getString(UserInfo.USER_FILENAME, UserInfo.USER_ID, UserInfo.DEFUALT_VALUE, context);
        Observable<ApiResult<String>> addCommentReplyObservable = NetTool.getInstance(NetTool.HOST_LOCAL_URL_ONE_NET).getApiService(CommentApi.class)
                .addCommentReply(userId,comment.getCommentId(),content);
        addCommentReplyObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ApiResult<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ApiResult<String> stringApiResult) {
                if (stringApiResult.getStatus() == Messages.RESULT_OK){
                    ToastUtil.showMsg("评论发布成功");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
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
