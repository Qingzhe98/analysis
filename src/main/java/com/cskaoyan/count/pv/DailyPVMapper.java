package com.cskaoyan.count.pv;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ClassName DailyPVMapper
 * @Description:
 * 两种方式：
 * 方式一：每执行一行，输出一个键值对，reduce把相同的键的值合并，值相加
 * 方式二：借助于cleanup，每执行一行，先不去输出，内部统计，然后cleanup一并输出
 * @Author 远志 zhangsong@cskaoyan.onaliyun.com
 * @Date 2022/2/14 9:44
 * @Version V1.0
 **/
public class DailyPVMapper extends Mapper<Object, Text, NullWritable, IntWritable> {

    private IntWritable intWritable = new IntWritable(1);

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        //每解析一行，输出一个键值对   key,1----调用一个reducer  key,(1,1,1...1)
        //  2020-01-14, 1      2020-01-14,(1,1,1,1...1)
        context.write(NullWritable.get(), intWritable);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {

    }
}
