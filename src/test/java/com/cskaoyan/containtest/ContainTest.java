package com.cskaoyan.containtest;

import org.junit.Test;

/**
 * @author Qingzhe
 * @create 2022-02-13 14:24
 */
public class ContainTest {
    @Test
    public void containTest() {
        String str = "https://item.jd.com/5008340.html";
        System.out.println(str.contains("item.jd.com"));

    }
}
