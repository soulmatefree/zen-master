package com.soulmatefree.soulmate.practice;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimePractice {

    public static void main(String[] args) {

        /**
         * 1.Instant 瞬时时间，和UTC时区一致，一般比东八区少八个小时
         * 2.LocalDate 和 Date 之间的转换桥梁
         */

        //获取当前时间，默认是UTC 时间
        Instant instant = Instant.now();
        System.out.println("instant(UTC 时间) = " + instant); //instant = 2019-11-05T07:35:05.158Z

        //添加偏移量，即获取东八区的时间
        OffsetDateTime offsetDateTime=instant.atOffset(ZoneOffset.ofHours(8));
        System.out.println("offsetDateTime(偏移 时间) = " + offsetDateTime);


        //获取时区时间
        ZonedDateTime zoneDateTime=instant.atZone(ZoneId.systemDefault());
        System.out.println("zoneDateTime(时区 时间) = " + zoneDateTime);

        //根据给定的毫秒数/秒数等初始化时间 instant.plusSeconds(TimeUnit.HOURS.toSeconds(8)) 得到的还是Instant对象
        Instant instant2 = Instant.ofEpochSecond(instant.plusSeconds(TimeUnit.HOURS.toSeconds(8)).getEpochSecond());
        System.out.println("instant2（添加八小时） = " + instant2);

        //Instant获取long类型的10位秒数、13位毫秒数
        System.out.println("instant.getEpochSecond() = " + instant.getEpochSecond());
        System.out.println("instant2.toEpochMilli() = " + instant2.toEpochMilli());

        //LocalDateTime输出毫秒数的方式，需要借助于Instant
        LocalDateTime dateTime = LocalDateTime.now();
        Instant instant3 = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        System.out.println("instant3.toEpochMilli() = " + instant3.toEpochMilli());


        //Instant 转成 LocalDate
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("localDate = " + localDate);

        //LocalDate 转成 Instant
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant toInstant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        System.out.println("toInstant = " + toInstant);

        //Intant 转成  Date
        java.util.Date date = Date.from(instant);
        System.out.println("date = " + date); //date = Tue Nov 05 15:56:51 CST 2019 表示的北京时间

        //Date 转成 Instant
        Date date2 = new Date();
        Instant instant4 = date.toInstant();
        System.out.println("instant4 = " + instant4);

        //计算程序的运行时间
        Instant instantStart = Instant.now();
        Instant instantEnd = Instant.now();
        long seconds = Duration.between(instantStart, instantEnd).getSeconds();
        System.out.println("seconds = " + seconds);


        /**
         * LocalDate 根据系统的时区获得的时间
         */

        // 初始化
        LocalDate localDate2 = LocalDate.now();
        System.out.println("localDate2 = " + localDate2);

        LocalDate localDate3 = LocalDate.of(1993, 2, 14);
        System.out.println("localDate3 = " + localDate3);

        //添加时间
        LocalDate localDate4 = localDate2.plusYears(1);
        System.out.println("localDate4 = " + localDate4);
        
        //减少时间
        LocalDate localDate5 = localDate3.minusYears(1);
        System.out.println("localDate5 = " + localDate5);

        //直接修改时间
        LocalDate localDate7 = localDate5.withDayOfMonth(12);
        System.out.println("localDate7 = " + localDate7);

        //获取某一年
        System.out.println("localDate5.getYear() = " + localDate5.getYear());
        //当天是某一年中的第多少天
        System.out.println("localDate5.getDayOfYear() = " + localDate5.getDayOfYear());
        
        //获取日期的差值
        int days = Period.between(localDate2, localDate5).getDays();
        System.out.println("days = " + days);

        //获取时间的差值
        LocalDateTime startLocalTime = LocalDateTime.now();
        LocalDateTime endLocalTime = LocalDateTime.now();
        long hours = Duration.between(startLocalTime, endLocalTime).toHours();
        System.out.println("hours = " + hours);


        //日期字符串改成时间
        String str = "1993-02-14";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate6 = LocalDate.parse(str, dateFormatter);
        System.out.println("localDate6 = " + localDate6);

        //日期改成字符串
        String format = localDate6.format(dateFormatter);
        System.out.println("format = " + format);
    }


}
