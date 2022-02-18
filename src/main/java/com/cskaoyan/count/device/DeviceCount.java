package com.cskaoyan.count.device;

import com.cskaoyan.count.device.output.DBOutputFormat;
import com.cskaoyan.count.device.output.DeviceSummary;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;


import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-13 12:42
 */
public class DeviceCount {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //加载配置
        Configuration configuration = new Configuration(true);
        //任务对象
        Job job = Job.getInstance(configuration);
        //任务加载类
        job.setJarByClass(DeviceCount.class);
        //任务名
        job.setJobName("Device count");
        //把mysql-connector-java jar包路径为hdfs中的路径加入到classpath中
        job.addArchiveToClassPath(new Path("/lib/mysql-connector-java-5.1.37.jar"));
        //输入路径
        Path inputPath = new Path(args[0]);
        //输出路径
        //Path outputPath = new Path(args[1]);
        //加载输入路径
        TextInputFormat.addInputPath(job, inputPath);
        //设置输出路径，确保不存在
        //TextOutputFormat.setOutputPath(job,outputPath);
        //关闭reduce，看看map阶段输出有无问题
        //job.setNumReduceTasks(0);
        //写入mysql需要重写输出目的地
        job.setOutputFormatClass(DBOutputFormat.class);
        job.setOutputKeyClass(DeviceSummary.class);
        job.setOutputValueClass(IntWritable.class);
        //map类入口
        job.setMapperClass(DeviceCountMapper.class);
        //设置map程序的key,value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        //reduce类型
        job.setReducerClass(DeviceCountReducer.class);
        job.waitForCompletion(true);

    }
}
