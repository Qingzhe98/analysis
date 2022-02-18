package com.cskaoyan.count.item;
import com.cskaoyan.count.item.output.ItemSummary;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-13 13:01
 */
public class ItemCountReducer extends Reducer<Text, IntWritable, ItemSummary, IntWritable> {
    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        //拆分
        String[] parts = key.toString().split("---");
        String URL = parts[0];
        String DATE = parts[1];
        //String URL = key.toString();
        //IntWritable和int非常的类似，但是它是支持序列化的
        for (IntWritable value : values) {
            sum += value.get();
        }
        result.set(sum);
        ItemSummary itemSummary = new ItemSummary(null,DATE,URL,sum);
        context.write(itemSummary, result);
        Thread.sleep(5);
    }
}
