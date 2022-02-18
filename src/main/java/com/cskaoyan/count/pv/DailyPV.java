package com.cskaoyan.count.pv;

import com.cskaoyan.count.pv.output.DBOutputFormat;
import com.cskaoyan.count.pv.output.Summary;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;

import java.io.IOException;

/**
 * @ClassName DailyPV
 * @Description: TODO
 * @Author 远志 zhangsong@cskaoyan.onaliyun.com
 * @Date 2022/2/14 9:44
 * @Version V1.0
 **/
public class DailyPV {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration(true);
        Job job = Job.getInstance(configuration);
        job.setJarByClass(DailyPV.class);
        job.setJobName("dailyPV");
        //把mysql-connector-java jar包路径为hdfs中的路径加入到classpath中
        job.addArchiveToClassPath(new Path("/lib/mysql-connector-java-5.1.37.jar"));
        Path inputPath = new Path(args[0]);
//        Path outputPath = new Path(args[1]);
        TextInputFormat.addInputPath(job, inputPath);
//        TextOutputFormat.setOutputPath(job, outputPath);
        //之前我们设置TextOutputFormat
        //需要我们自己去重写输出的目的地
        job.setOutputFormatClass(DBOutputFormat.class);
        //下面两行设置的是最终输出的key和value的类型
        job.setOutputKeyClass(Summary.class);
        job.setOutputValueClass(Text.class);

        job.setMapperClass(DailyPVMapper.class);
        //设置的是map程序执行完成之后的key和value类型
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setReducerClass(DailyPVReducer.class);
        job.waitForCompletion(true);
    }
}
