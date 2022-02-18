package com.cskaoyan.count.pv;

import com.cskaoyan.count.pv.output.Summary;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ClassName DailyPVReducer
 * @Description: TODO
 * @Author 远志 zhangsong@cskaoyan.onaliyun.com
 * @Date 2022/2/14 9:51
 * @Version V1.0
 **/
public class DailyPVReducer extends Reducer<NullWritable, IntWritable, Summary,Text> {

    private Text text = new Text();

    private IntWritable dailyPV = new IntWritable();

    @Override
    protected void reduce(NullWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values) {
            sum += 1;
        }
//        text.set("dailyPV");
//        dailyPV.set(sum);
        //context.write(text, dailyPV);
        // ip---省份---时间----url---device
        //统计日活，每天都需要进行统计  2020-02-11
        //note：我这里为了省事，直接写固定的，但是接下来，今后如果你需要统计每一天的日活，那么需要从数据中取出该字段
        Summary dailyPV = new Summary(null, "2020-01-14", "dailyPV", sum + "", null);
        context.write(dailyPV, new Text());

    }
}
