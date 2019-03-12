package com.example.hekai.xunw.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/3/7
 **/
public class Comment implements Serializable {
    private String Id;
    private String foodId;
    private String userImgUrl;
    private String userNickName;
    private Date commentTime;
    private int likesNumber;
    private String content;
    private int replyNumber;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(int replyNumber) {
        this.replyNumber = replyNumber;
    }
}
