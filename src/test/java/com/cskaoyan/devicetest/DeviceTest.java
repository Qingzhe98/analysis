package com.cskaoyan.devicetest;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * @author Qingzhe
 * @create 2022-02-11 22:24
 */
public class DeviceTest {
    @Test
    public void test() throws IOException {
        RandomAccessFile s = new RandomAccessFile("src/main/resources/device.txt","r");
        for (int i = 0; i < 100; i++) {

            System.out.println(readFileRandom(s));
        }
    }

    private static String readFileRandom(RandomAccessFile inputFile) throws IOException {

        //文件长度
        long length = inputFile.length();
        //随机对象
        Random random = new Random();
        //随机数
        long rand;
        //结果
        String result =null;
        //直到结果不为null，因为有可能随机到最后一行，再读下一行就成null了
        while(result == null) {
            rand = Math.abs(random.nextLong());
            inputFile.seek(rand % length);
            inputFile.readLine();
            result = inputFile.readLine();
        }

        return result;


    }
}
