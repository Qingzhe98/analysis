package com.cskaoyan.etl.parse;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * @ClassName IPParse
 * @Description:
 * 61.237.124.223---2022-01-14 00:00:00---http://115.29.141.32:8085/#/mall/goods/484---firefox
 * 61.237.124.223---江苏省---南京市---2022-01-14 00:00:00---http://115.29.141.32:8085/#/mall/goods/484---firefox
 * @Author 远志 zhangsong@cskaoyan.onaliyun.com
 * @Date 2022/2/12 10:02
 * @Version V1.0
 **/
public class IPParse {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration configuration = new Configuration(true);
        Job job = Job.getInstance(configuration);
        job.setJarByClass(IPParse.class);
        job.setJobName("ip parser");
        Path input = new Path(args[0]);
        Path output = new Path(args[1]);
        TextInputFormat.addInputPath(job, input);

        TextOutputFormat.setOutputPath(job, output);
        //不执行reduce,将reduce任务设置为0
        job.setNumReduceTasks(0);

        //设置最大分片8m，将文件分为多个片分发到集群中去执行
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job, 5242880);
        job.setMapperClass(IPParseMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);


        //job.setReducerClass();
        job.waitForCompletion(true);

    }
}
