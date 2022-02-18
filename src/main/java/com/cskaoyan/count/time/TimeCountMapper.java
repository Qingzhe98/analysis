package com.cskaoyan.count.time;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-13 12:59
 */
public class TimeCountMapper extends Mapper<Object, Text, Text, IntWritable> {
    private Text text = new Text();
    private final static IntWritable one = new IntWritable(1);

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String hour = null;
        String[] parts = line.split("---");
        String ip = parts[0];
        String province = parts[1];
        String city = parts[2];
        String date = parts[3];
        String url = parts[4];
        String device = parts[5];

        String substr = date.substring(11, 13);
        text.set(substr);
        context.write(text, one);


    }
}
