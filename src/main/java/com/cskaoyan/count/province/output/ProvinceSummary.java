package com.cskaoyan.count.province.output;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-15 11:23
 */
public class ProvinceSummary implements WritableComparable<ProvinceSummary> {
    private Integer id;

    private String date;

    private String province;

    private int sum;

    public ProvinceSummary(Integer id, String date, String province, int sum) {
        this.id = id;
        this.date = date;
        this.province = province;
        this.sum = sum;
    }
    public ProvinceSummary() {

    }

    @Override
    public int compareTo(ProvinceSummary o) {

        return 0;
    }

    //序列化相关，MR跨集群，需要持久化，会调用write方法，从硬盘重新读取数据到内存
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeUTF(date);
        dataOutput.writeUTF(province);
        dataOutput.writeInt(sum);

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.date = dataInput.readUTF();
        this.province = dataInput.readUTF();
        this.sum = dataInput.readInt();

    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getProvince() {
        return province;
    }

    public int getSum() {
        return sum;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }


}
