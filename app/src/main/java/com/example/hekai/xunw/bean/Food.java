package com.example.hekai.xunw.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2018/12/28
 **/
public class Food implements Serializable {
    private int FoodId;
    private String imgUrl;
    private String author;
    private Date time;
    private String title;
    private Double distance;
    private String mdText;
    private int likes;
    private String tips;
    private String businessTime;
    private String phone;
    private String address;

    public String getMdText() {
        return mdText;
    }

    public void setMdText(String mdText) {
        this.mdText = mdText;
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(String businessTime) {
        this.businessTime = businessTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
