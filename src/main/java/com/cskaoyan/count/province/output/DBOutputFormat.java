package com.cskaoyan.count.province.output;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Qingzhe
 * @create 2022-02-15 11:21
 * 设计一个表，统计商品访问排行
 * id（主键），统计日期，商品链接，访问量
 */
public class DBOutputFormat extends OutputFormat<ProvinceSummary, IntWritable> {

    protected static class MysqlRecordWriter extends RecordWriter<ProvinceSummary, IntWritable> {
        private Connection connection = null;

        @Override
        public void write(ProvinceSummary provinceSummary, IntWritable intWritable) throws IOException, InterruptedException {
            //JDBC
            PreparedStatement preparedStatement = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/analysis", "root", "123456");
                preparedStatement = connection.prepareStatement("insert into provincesummary values (null,?,?,?)");
                preparedStatement.setString(1, provinceSummary.getDate());
                preparedStatement.setString(2, provinceSummary.getProvince());
                preparedStatement.setInt(3, provinceSummary.getSum());
                preparedStatement.executeUpdate();

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        @Override
        public void close(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public RecordWriter<ProvinceSummary, IntWritable> getRecordWriter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        return new MysqlRecordWriter();
    }

    @Override
    public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

    }

    //下面的这两个方法忽略，直接用就可以了，
    private FileOutputCommitter committer = null;

    public static Path getOutputPath(JobContext job) {
        String name = job.getConfiguration().get("mapred.output.dir");
        return name == null ? null : new Path(name);
    }

    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        if (committer == null) {
            Path output = getOutputPath(context);
            committer = new FileOutputCommitter(output, context);
        }
        return committer;
    }
}
