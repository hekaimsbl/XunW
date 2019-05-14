package Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author Hekai
 * @Date 2019/4/3 13:48
 * @Description TODO
 **/
@Alias("Comment")
public class Comment {
    //@Expose(serialize = false,deserialize = false)
    @SerializedName("comment_id")
    private int commentId;

    @SerializedName("foodId")
    private String foodId;

    @SerializedName("content")
    private String content;

    @SerializedName("comment_userId")
    private String commentUserId;

    @SerializedName("likes_number")
    private int likesNumber;

    @SerializedName("reply_number")
    private int replyNumber;

    @SerializedName("create_time")
    private Date createTime;

    @SerializedName("user_img")
    private String userImg;

    @SerializedName("user_name")
    private String userName;

    public int getCommentId() {
        return commentId;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public void setCommentUserId(String commentUserId) {
        this.commentUserId = commentUserId;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }

    public int getReplyNumber() {
        return replyNumber;
    }

    public void setReplyNumber(int replyNumber) {
        this.replyNumber = replyNumber;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
