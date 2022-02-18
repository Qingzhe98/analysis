package com.cskaoyan.count.item.output;
import org.apache.hadoop.io.WritableComparable;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Qingzhe
 * @create 2022-02-15 11:23
 */
public class ItemSummary implements WritableComparable<ItemSummary> {
    private Integer id;

    private String date;

    private String URL;

    private int sum;

    public ItemSummary(Integer id, String date, String URL, int sum) {
        this.id = id;
        this.date = date;
        this.URL = URL;
        this.sum = sum;
    }
    public ItemSummary() {

    }

    @Override
    public int compareTo(ItemSummary o) {

        return 0;
    }

    //序列化相关，MR跨集群，需要持久化，会调用write方法，从硬盘重新读取数据到内存
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        dataOutput.writeUTF(date);
        dataOutput.writeUTF(URL);
        dataOutput.writeInt(sum);

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.date = dataInput.readUTF();
        this.URL = dataInput.readUTF();
        this.sum = dataInput.readInt();

    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getURL() {
        return URL;
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

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }


}
