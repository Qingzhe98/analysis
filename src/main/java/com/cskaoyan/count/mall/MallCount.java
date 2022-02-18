package com.cskaoyan.count.mall;

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
public class MallCount {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        //加载配置
        Configuration configuration = new Configuration(true);
        //任务对象
        Job job = Job.getInstance(configuration);
        //任务加载类
        job.setJarByClass(MallCount.class);
        //任务名
        job.setJobName("Mall count");
        //输入路径
        Path inputPath = new Path(args[0]);
        //输出路径
        Path outputPath = new Path(args[1]);
        //加载输入路径
        TextInputFormat.addInputPath(job,inputPath);
        //设置输出路径，确保不存在
        TextOutputFormat.setOutputPath(job,outputPath);
        //map类入口
        job.setMapperClass(MallCountMapper.class);
        //key的类型
        job.setMapOutputKeyClass(Text.class);
        //value类型
        job.setMapOutputValueClass(IntWritable.class);
        //reduce类型
        job.setReducerClass(MallCountReducer.class);
        job.waitForCompletion(true);

    }
}
