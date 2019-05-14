package com.example.hekai.xunw.bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/24
 **/
public class FoodDownload {
    @SerializedName("id")
    private String FoodId;

    @SerializedName("title")
    private String Title;

    @SerializedName("author_id")
    private String AuthorId;

    @SerializedName("user_name")
    private String UserName;

    @SerializedName("longitude")
    private Double Longitude;

    @SerializedName("latitude")
    private Double Latitude;

    @SerializedName("address")
    private String Address;

    @SerializedName("create_time")
    private java.util.Date Date;

    @SerializedName("image_url")
    private List<String> ImageUrls;

    @SerializedName("tags")
    private List<String> Tags;

    @SerializedName("recommend_number")
    private int RecommendNumber;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFoodId() {
        return FoodId;
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(String authorId) {
        AuthorId = authorId;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public List<String> getImageUrls() {
        return ImageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        ImageUrls = imageUrls;
    }

    public List<String> getTags() {
        return Tags;
    }

    public void setTags(List<String> tags) {
        Tags = tags;
    }

    public int getRecommendNumber() {
        return RecommendNumber;
    }

    public void setRecommendNumber(int recommendNumber) {
        RecommendNumber = recommendNumber;
    }
}
