package com.example.hekai.xunw.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author Hekai
 * @Date 2019/4/8 14:32
 * @Description TODO
 **/
public class KeySearchResult implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("describe")
    private String describe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
