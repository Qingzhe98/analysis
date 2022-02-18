package com.cskaoyan.count.device;

import com.cskaoyan.count.device.output.DeviceSummary;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-13 13:01
 */
public class DeviceCountReducer extends Reducer<Text, IntWritable, DeviceSummary, IntWritable> {
    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        String[] parts = key.toString().split("---");
        String device = parts[0];
        String date = parts[1];
        int sum = 0;
        //IntWritable和int非常的类似，但是它是支持序列化的
        for (IntWritable value : values) {
            sum += value.get();
        }
        result.set(sum);
        DeviceSummary deviceSummary = new DeviceSummary(null, date, device, sum);
        context.write(deviceSummary, result);
        Thread.sleep(50);
    }
}
