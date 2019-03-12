package com.example.hekai.xunw;

import com.example.hekai.xunw.utils.MD5;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertNotEquals("MD5测试",MD5.encrypt("hello"));
    }
}