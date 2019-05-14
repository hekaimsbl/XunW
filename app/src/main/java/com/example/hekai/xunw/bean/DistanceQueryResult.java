package com.example.hekai.xunw.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/24
 **/
public class DistanceQueryResult implements Serializable {
    @SerializedName(value = "status")
    private String status;
    @SerializedName(value = "info")
    private String info;
    @SerializedName(value = "infocode")
    private String infoCode;
    @SerializedName(value = "results")
    private List<DistanceResultItem> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfoCode() {
        return infoCode;
    }

    public void setInfoCode(String infoCode) {
        this.infoCode = infoCode;
    }

    public List<DistanceResultItem> getResults() {
        return results;
    }

    public void setResults(List<DistanceResultItem> results) {
        this.results = results;
    }
}
