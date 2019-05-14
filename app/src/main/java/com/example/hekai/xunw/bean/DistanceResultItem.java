package com.example.hekai.xunw.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/24
 **/
public class DistanceResultItem implements Serializable {
    @SerializedName(value = "origin_id")
    private String originId;
    @SerializedName(value = "dest_id")
    private String destId;
    @SerializedName(value = "distance")
    private String distance;
    @SerializedName(value = "duration")
    private String duration;

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}