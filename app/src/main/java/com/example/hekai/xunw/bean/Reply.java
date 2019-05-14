package Entity;

import com.google.gson.annotations.SerializedName;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @Author Hekai
 * @Date 2019/4/7 10:52
 * @Description TODO
 **/
@Alias("Reply")
public class Reply {
    @SerializedName("id")
    private int id;

    @SerializedName("comment_id")
    private int commentId;

    @SerializedName("content")
    private String content;

    @SerializedName("reply_userId")
    private String replyUserId;

    @SerializedName("user_name")
    private String userName;

    @SerializedName("user_img")
    private String userImg;

    @SerializedName("create_time")
    private Date date;

    @SerializedName("likes_number")
    private int likesNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLikesNumber() {
        return likesNumber;
    }

    public void setLikesNumber(int likesNumber) {
        this.likesNumber = likesNumber;
    }
}
