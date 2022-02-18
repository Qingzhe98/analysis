package com.cskaoyan.logcreation;

import java.io.*;

/**
 * @author Qingzhe
 * @create 2022-02-12 11:45
 */
public class LogCreate {
    public static void main(String[] args) throws IOException, InterruptedException {
        File inputFile = new File("/home/ubuntu/compute/input/access.log");
        File outputFile = new File("/home/ubuntu/compute/output/run.log");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
        String str = null;
        while((str = bufferedReader.readLine())!=null){
            bufferedWriter.write(str);
            bufferedWriter.newLine();
            //Thread.sleep(1);
        }

        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }

}
