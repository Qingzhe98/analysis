package com.cskaoyan.count.pv.output;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @ClassName DBOutputFormat
 * @Description:
 * 假如一个数据库表一行数据有多列，那么输出text，intwritable可能无法满足需求
 * 可以输出一个对象
 * 首先先设计一个表，对象需要和表一一对应，我们需要将刚刚产生的dailyPV  1000000写入到数据库
 * id、date、attribute（统计名称，dailyPV,IP,KeyWords,TOP3 area）、value、addition
 * create table summary(id int primary key auto_increment,date varchar(40),attribute varchar(30),value varchar(40),addition varchar(40));
 * 统计搜索量最高的关键字及次数 keywords  iphone 200000
 * 搜索量最高的商品次数（结合订单，商品转化率）product ysl 300000
 * 如果需要往数据库里面写入数据，那么需要重新OutputFormat类
 * 其实最终写的过程是由OutputFormat类中的RecordWriter来去写的
 * RecordWriter首先会调用write方法去写入数据，写入完成之后会调用close方法关闭
 * @Author 远志 zhangsong@cskaoyan.onaliyun.com
 * @Date 2022/2/14 10:09
 * @Version V1.0
 **/
public class DBOutputFormat extends OutputFormat<Summary, Text> {

    protected static class MysqlRecordWriter extends RecordWriter<Summary, Text>{

        private Connection connection = null;

        @Override
        public void write(Summary key, Text value) throws IOException, InterruptedException {
            //怎么写  insert into summary values ()
            //JDBC程序
            PreparedStatement preparedStatement = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/analysis", "root", "123456");
                preparedStatement = connection.prepareStatement("insert into summary values (null,?,?,?,?)");
                preparedStatement.setString(1, key.getDate());
                preparedStatement.setString(2, key.getAttribute());
                preparedStatement.setString(3, key.getValue());
                preparedStatement.setString(4, key.getAddition());
                preparedStatement.executeUpdate();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(preparedStatement != null){
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            //如何关闭
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public RecordWriter<Summary, Text> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new MysqlRecordWriter();
    }

    //下面的这两个方法忽略，直接用就可以了，
    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {

    }


    private FileOutputCommitter committer = null;
    public static Path getOutputPath(JobContext job) {
        String name = job.getConfiguration().get("mapred.output.dir");
        return name == null?null:new Path(name);
    }
    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        if (committer == null) {
            Path output = getOutputPath(context);
            committer = new FileOutputCommitter(output, context);
        }
        return committer;    }
}
