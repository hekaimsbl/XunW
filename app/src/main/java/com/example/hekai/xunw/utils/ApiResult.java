package com.example.hekai.xunw.utils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author Hekai
 * @Date 2019/3/24 17:01
 * @Description TODO
 **/
public class ApiResult<T> implements Serializable {
    @SerializedName(value = "statusCode",alternate = "status_code")
    private int Status;

    @SerializedName(value = "errorMsg",alternate = "err_msg")
    private String Error;

    @SerializedName(value = "Data",alternate = "data")
    private T Data;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
