package com.cskaoyan.timeTest;

/**
 * @author Qingzhe
 * @create 2022-02-13 17:13
 */
public class SearchTest {
    public static void main(String[] args) {
        String str = "2022-02-11 06:19";
        String substr = str.substring(11,13);
        System.out.println(substr);
        String onlydate = str.substring(0,10);
        System.out.println(onlydate);
    }
}
