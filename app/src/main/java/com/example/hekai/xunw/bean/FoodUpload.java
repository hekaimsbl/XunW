package com.example.hekai.xunw.bean;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/22
 **/
public class FoodUpload {
    @SerializedName("title")
    private String Title;

    @SerializedName("phone")
    private String Phone;

    @SerializedName("businessTime")
    private String BusinessTime;

    @SerializedName("tags")
    private List<String> Tags;

    @SerializedName("author_id")
    private String AuthorId;

    @SerializedName("longitude")
    private Double Longitude;

    @SerializedName("latitude")
    private Double Latitude;

    @SerializedName("address")
    private String Address;

    @SerializedName("city_code")
    private String CityCode;

    @SerializedName("content")
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getBusinessTime() {
        return BusinessTime;
    }

    public void setBusinessTime(String businessTime) {
        BusinessTime = businessTime;
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

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public List<String> getTags() {
        return Tags;
    }

    public void setTags(List<String> tags) {
        Tags = tags;
    }
}
