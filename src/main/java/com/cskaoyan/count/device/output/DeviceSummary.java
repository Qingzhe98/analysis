package com.cskaoyan.count.device.output;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-15 11:23
 */
public class DeviceSummary implements WritableComparable<DeviceSummary> {
    private Integer id;

    private String date;

    private String Device;

    private int sum;

    public DeviceSummary(Integer id, String date, String Device, int sum) {
        this.id = id;
        this.date = date;
        this.Device = Device;
        this.sum = sum;
    }

    public DeviceSummary() {

    }

    @Override
    public int compareTo(DeviceSummary o) {

        return 0;
    }

    //序列化相关，MR跨集群，需要持久化，会调用write方法，从硬盘重新读取数据到内存
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeUTF(date);
        dataOutput.writeUTF(Device);
        dataOutput.writeInt(sum);

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.date = dataInput.readUTF();
        this.Device = dataInput.readUTF();
        this.sum = dataInput.readInt();

    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDevice() {
        return Device;
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

    public void setDevice(String device) {
        this.Device = device;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }


}
