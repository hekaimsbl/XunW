package com.example.hekai.xunw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2018/12/28
 **/
public class TimeUtils {
    private static String timeFormat = "yyyy/MM/dd";

    public static String utilDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        return simpleDateFormat.format(date);
    }

    public static void main(String[] args) throws ParseException{
        Date date = new Date();
        date.setTime(2018-8-6);
        String a = utilDateToString(date);
        System.out.print(a);
    }
}
