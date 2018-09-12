package com.zhitou.job.main.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LCH on 2018/9/12.
 */
public class TimeUtils {

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param time 时间毫秒值
     * @return
     */
    public static String getTimeFormatText(long time) {
        Date date = new Date(time);
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    /**
     * 返回文字描述的日期
     * yyyy-MM-dd hh:mm:ss
     * @param time_yyyy_mm_dd_ss_ff_mm 格式化时间
     * @return
     */
    public static String getTimeFormatText(String time_yyyy_mm_dd_ss_ff_mm) {
        Date date = new Date(getTimeLong(time_yyyy_mm_dd_ss_ff_mm));
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

    public static long getTimeLong(String time_yyyy_mm_dd_ss_ff_mm){
        String str = time_yyyy_mm_dd_ss_ff_mm;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return millionSeconds;
    }
}
