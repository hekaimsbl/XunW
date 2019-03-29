package com.example.hekai.xunw.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author HeKai
 * @author hekaimsbl@gmail.com
 * @date 2019/3/4
 **/
public class MD5 {
    public static String encrypt(String content) {
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder hex = new StringBuilder(hash.length*2);
        for (byte b : hash){
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
