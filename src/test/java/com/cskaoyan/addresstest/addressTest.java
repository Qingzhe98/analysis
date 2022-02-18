package com.cskaoyan.addresstest;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * @author Qingzhe
 * @create 2022-02-11 19:36
 *
 */
public class addressTest {
    /**
     *
     * @description: 用RandomAccessFile类实现随机读写文件，首先获得当前指针，在指针上随机加上一个int数
     *              再对文件长度取模，保证不会超出文件长度
     * @param
     * @return: void
     * @Author: qingzhe
     * @Create: 2022/2/11 20:56
     */
    @Test
    public void test() {
        int i =0;
        try {
            RandomAccessFile r = new RandomAccessFile("src/main/resources/ip.txt", "r");
            long length = r.length();
            long pointer = r.getFilePointer();
            Random random = new Random();
            int rand = random.nextInt(30);
            pointer = (pointer+rand)%length;
            while(i<100){
                pointer = r.getFilePointer();
                rand = random.nextInt(30);
                pointer = (pointer+rand)%length;
                r.seek(pointer);
                r.readLine();
                System.out.println(r.readLine());
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
