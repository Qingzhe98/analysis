package com.cskaoyan.timeTest;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

/**
 * @author Qingzhe
 * @create 2022-02-11 21:38
 */
public class TimeTest {
    @Test
    public void timeTest() {
        Random random = new Random();
        LocalDate date = LocalDate.of(2022, 2, 11);
        LocalTime time;
        String timeStr;
        time = LocalTime.of(random.nextInt(24), random.nextInt(60));
        timeStr = date.toString() + " " + time;
        System.out.println(timeStr);


    }
}
