package com.cskaoyan.count.pv.output;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @ClassName Summary
 * @Description: TODO
 * @Author 远志 zhangsong@cskaoyan.onaliyun.com
 * @Date 2022/2/14 10:19
 * @Version V1.0
 **/
public class Summary implements WritableComparable<Summary> {

    private Integer id;

    private String date;

    private String attribute;

    private String value;

    private String addition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    // string compareTo
    //在我们这个场景下，并不需要去比较，所以随便重写即可，但是一定要去重写该方法，否则会报错
    @Override
    public int compareTo(Summary o) {
        try {
            long date1 = new SimpleDateFormat("yyyy-MM-dd").parse(this.date).getTime();
            long date2 = new SimpleDateFormat("yyyy-MM-dd").parse(o.date).getTime();
            if(date1 > date2){
                return 1;
            }else if(date1 < date2){
                return -1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //下面两个方法实际上是和序列化相关的，MR计算的时候，跨集群间进行数据传递
    //需要将数据进行持久化，会调用write方法；从硬盘重新读取数据到内存
    //会调用readFields方法
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(date);
        out.writeUTF(attribute);
        out.writeUTF(value);
        out.writeUTF(addition);
    }

    //写入到硬盘的顺序一定要求和读取的顺序一致，这样才可以重新反序列化到内存中
    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.date = in.readUTF();
        this.attribute = in.readUTF();
        this.value = in.readUTF();
        this.addition = in.readUTF();
    }

    public Summary() {
    }

    public Summary(Integer id, String date, String attribute, String value, String addition) {
        this.id = id;
        this.date = date;
        this.attribute = attribute;
        this.value = value;
        this.addition = addition;
    }
}
