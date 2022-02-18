package com.cskaoyan.addresstest;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * @author Qingzhe
 * @create 2022-02-11 20:59
 *
 */
public class addressTest2 {
    /**
     *
     * @description: 随机一个long型数，对文件长度取余保证不超出文件范围，读取一行使坐标从下一行头部开始，再读下一行，保证能
     * 读取到完整一行，而不是从随机数开始的后面一行
     * @param
     * @return: void
     * @Author: qingzhe
     * @Create: 2022/2/11 21:00
     */
    @Test
    public void test() {
        int i =0;
        try {
            RandomAccessFile r = new RandomAccessFile("src/main/resources/address.txt", "r");
            //文件长度
            long length = r.length();
            Random random = new Random();
            long rand ;

            while(i<100){
                rand = Math.abs(random.nextLong());
                r.seek(rand%length);
                r.readLine();
                System.out.println(r.readLine());
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
