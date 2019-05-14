package com.example.hekai.xunw.bean;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/4/23
 **/
public class EventBusMessage{
    private String Msg;

    public EventBusMessage(String msg){
        this.Msg = msg;
    }

    public EventBusMessage() {

    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

}
