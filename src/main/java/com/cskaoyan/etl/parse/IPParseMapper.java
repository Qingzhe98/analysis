package com.cskaoyan.etl.parse;

import com.cskaoyan.etl.ip.QQWryExt;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ClassName IPParseMapper
 * @Description:
 * 61.237.124.223---2022-01-14 00:00:00---http://115.29.141.32:8085/#/mall/goods/484---firefox
 * @Author 远志 zhangsong@cskaoyan.onaliyun.com
 * @Date 2022/2/12 10:07
 * @Version V1.0
 **/
public class IPParseMapper extends Mapper<Object, Text, Text, NullWritable> {

    private Text text = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // value表示的是每一行的数据
        String line = value.toString();
        //被解析成了4个部分  0-3
        String[] parts = line.split("---");
        String ip = parts[0];
        String date = parts[1];
        String url = parts[2];
        String device = parts[3];
        QQWryExt ext = new QQWryExt();
        QQWryExt.RegionInfo regionInfo = ext.analyticIP(ip);
        String province = regionInfo.getProvince();
        String city = regionInfo.getCity();
        String paserData = ip + "---" + province + "---"+ city +"---" + date + "---" + url + "---" + device;
        text.set(paserData);
        context.write(text, NullWritable.get());
    }
}
