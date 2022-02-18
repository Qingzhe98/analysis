package com.cskaoyan.count.ip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-13 12:42
 */
public class IPCount {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //加载配置
        Configuration configuration = new Configuration(true);
        //任务对象
        Job job = Job.getInstance(configuration);
        //任务加载类
        job.setJarByClass(IPCount.class);
        //任务名
        job.setJobName("Ip count");
        //输入路径
        Path inputPath = new Path(args[0]);
        //输出路径
        Path outputPath = new Path(args[1]);
        //加载输入路径
        TextInputFormat.addInputPath(job,inputPath);
        //设置输出路径，确保不存在
        TextOutputFormat.setOutputPath(job,outputPath);
        //map类入口
        job.setMapperClass(IPCountMapper.class);
        //key的类型
        job.setMapOutputKeyClass(Text.class);
        //value类型
        job.setMapOutputValueClass(IntWritable.class);
        //reduce类型
        job.setReducerClass(IPCountReducer.class);
        job.waitForCompletion(true);

    }
}
