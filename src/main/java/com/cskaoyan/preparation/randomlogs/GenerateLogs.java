package com.cskaoyan.preparation.randomlogs;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

/**
 * @author Qingzhe
 * @create 2022-02-11 17:01
 * 生成伪日志文件
 * 每条访问记录格式为：ip地址---访问时间---访问地址---访问设备
 */
public class GenerateLogs {
    public static void main(String[] args) {
        //文件对象
        File ipFile = new File("src/main/resources/ip.txt");
        File addressFile = new File("src/main/resources/address.txt");
        File deviceFile = new File("src/main/resources/device.txt");
        File logFile = new File("access.log");
        //ip和address随机访问对象
        RandomAccessFile ipRandomAccessFile = null;
        RandomAccessFile addressRandomAccessFile = null;
        RandomAccessFile deviceRandomAccessFile = null;
        //输出流
        BufferedWriter logBufferedWriter = null;
        //创建对象
        try {
            ipRandomAccessFile = new RandomAccessFile(ipFile, "r");
            addressRandomAccessFile = new RandomAccessFile(addressFile, "r");
            deviceRandomAccessFile = new RandomAccessFile(deviceFile, "r");
            logBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFile, true)));
            for (int i = 0; i < 1000000; i++) {
                logBufferedWriter.write(readFileRandom(ipRandomAccessFile) + "---" + randomDateTime() + "---" + readFileRandom(addressRandomAccessFile) + "---" + readFileRandom(deviceRandomAccessFile));
                logBufferedWriter.newLine();
            }

            logBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        String result = null;

        //直到结果不为null，因为有可能随机到最后一行，再读下一行就成null了
        while (result == null) {
            rand = Math.abs(random.nextLong());
            inputFile.seek(rand % length);
            inputFile.readLine();
            result = inputFile.readLine();
        }
        return result;


    }

    private static String randomDateTime() {
        Random random = new Random();
        LocalDate date = LocalDate.of(2022, 2, 11);
        LocalTime time;
        String dateTimeStr;
        time = LocalTime.of(random.nextInt(24), random.nextInt(60));
        dateTimeStr = date.toString() + " " + time;
        return dateTimeStr;

    }


}
