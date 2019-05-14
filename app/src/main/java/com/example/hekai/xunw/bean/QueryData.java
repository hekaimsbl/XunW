package Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author Hekai
 * @Date 2019/4/9 9:04
 * @Description TODO
 **/
public class QueryData<T> implements Serializable {
    @SerializedName("msg")
    private String msg;
    @SerializedName("data_queryKey")
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
