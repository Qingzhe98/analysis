package com.cskaoyan.count.province;

import com.cskaoyan.count.province.output.ProvinceSummary;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-13 13:01
 */
public class ProvinceCountReducer extends Reducer<Text, IntWritable, ProvinceSummary,IntWritable> {
    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        String[] parts = key.toString().split("---");
        String province = parts[0];
        String DATE = parts[1];
        int sum = 0;
        //IntWritable和int非常的类似，但是它是支持序列化的
        for (IntWritable value : values) {
            sum += value.get();
        }
        ProvinceSummary provinceSummary = new ProvinceSummary(null, DATE, province, sum);
        result.set(sum);
        context.write(provinceSummary, result);
        Thread.sleep(500);
    }
}
