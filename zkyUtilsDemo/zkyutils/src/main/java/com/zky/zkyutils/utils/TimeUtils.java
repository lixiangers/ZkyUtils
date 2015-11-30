package com.zky.zkyutils.utils;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_DOT = "yyyy.MM.dd";

    public static DateTime todayStartTime() {
        return DateTime.now().withTimeAtStartOfDay();
    }

    public static DateTime todayEndTime() {
        return DateTime.now().withTimeAtStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59);
    }

    public static DateTime endTimeOfDateTime(DateTime time) {
        return time.withTimeAtStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59);
    }

    public static DateTime yesterday() {
        return todayStartTime().minusDays(1);
    }

    public static DateTime tomorrow() {
        return todayStartTime().plusDays(1);
    }

    public static Date getDate(String express) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            date = sdf.parse(express);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static boolean isSameDay(DateTime dateTime1, DateTime dateTime2) {
        if (dateTime1 == null || dateTime2 == null)
            return false;

        return dateTime1.getYear() == dateTime2.getYear()
                && dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear()
                && dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth();
    }

    public static int getIntervalMinute(DateTime dateTime1, DateTime dateTime2) {
        return Minutes.minutesBetween(dateTime1, dateTime2).getMinutes();
    }

    public static long getBeginDateTimeLongValue(Date date) {
        return new DateTime(date).toDateMidnight().getMillis();
    }

    public static String formatTimeToStringOfYMD(long time) {
        Date date = new Date(time);
        return time <= 0 ? null : new SimpleDateFormat(YYYY_MM_DD).format(date);
    }

    public static String formatTimeToStringOfYMD(Date time) {
        return time == null ? null : new SimpleDateFormat(YYYY_MM_DD).format(time);
    }

    public static String formatTimeToStringOfYMDHMS(DateTime time) {
        return time == null ? null : time.toString(YYYY_MM_DD_HH_MM_SS);
    }

    public static String formatTimeToStringOfYMDDot(Date time) {
        return time == null ? null : new SimpleDateFormat(YYYY_MM_DD_DOT).format(time);
    }

    public static DateTime formatStringToTimeOfYMD(String timeString) {
        if (StringUtils.isBlank(timeString))
            return null;

        DateTime date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
            date = new DateTime(sdf.parse(timeString));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

    public static String getWeekInfoOfTime(DateTime dateTime) {
        String result = "";
        switch (dateTime.getDayOfWeek()) {
            case 0:
                result = "周日";
                break;
            case 1:
                result = "周一";
                break;
            case 2:
                result = "周二";
                break;
            case 3:
                result = "周三";
                break;
            case 4:
                result = "周四";
                break;
            case 5:
                result = "周五";
                break;
            case 6:
                result = "周六";
                break;
        }

        return result;
    }
}
