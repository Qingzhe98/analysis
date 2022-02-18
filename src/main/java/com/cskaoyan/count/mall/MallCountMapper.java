package com.cskaoyan.count.mall;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-13 12:59
 */
public class MallCountMapper extends Mapper<Object, Text, Text, IntWritable> {
    private Text text = new Text();
    private final static IntWritable one = new IntWritable(1);

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] parts = line.split("---");
        String ip = parts[0];
        String province = parts[1];
        String city = parts[2];
        String date = parts[3];
        String url = parts[4];
        String device = parts[5];
        if(url.contains("mall.jd.com")){
            text.set(url);
            context.write(text, one);
        }


    }
}
