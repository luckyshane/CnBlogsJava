package com.luckyshane.cnblogs.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Formatter {
    private static final long ONE_MINUTE_IN_MILLS = 60 * 1000;
    private static final long ONE_HOUR_IN_MILLS = 60 * ONE_MINUTE_IN_MILLS;
    private static final long ONE_DAY_IN_MILLS = 24 * ONE_HOUR_IN_MILLS;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.CHINESE);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.CHINESE);


    public static String formatDateCompareNow(Date timestamp) {
        if (timestamp == null) {
            return "";
        }
        Calendar today = Calendar.getInstance();
        Calendar compareTime = Calendar.getInstance();
        compareTime.setTime(timestamp);

        long deltaMillis = today.getTimeInMillis() - compareTime.getTimeInMillis();
        if (deltaMillis >= 0) {
            if (deltaMillis <= ONE_MINUTE_IN_MILLS) {
                return "刚刚";
            } else if (deltaMillis <= ONE_HOUR_IN_MILLS) {
                int mins = (int) (deltaMillis / ONE_MINUTE_IN_MILLS);
                return String.format("%d分钟之前", mins);
            } else {
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);
                compareTime.set(Calendar.HOUR_OF_DAY, 0);
                compareTime.set(Calendar.MINUTE, 0);
                compareTime.set(Calendar.SECOND, 0);
                compareTime.set(Calendar.MILLISECOND, 0);

                deltaMillis = today.getTimeInMillis() - compareTime.getTimeInMillis();
                int day = (int) (deltaMillis / ONE_DAY_IN_MILLS);
                if (day == 0) {
                    return "今天 " + TIME_FORMAT.format(timestamp);
                } else if (day == 1) {
                    return "昨天 " + TIME_FORMAT.format(timestamp);
                }
            }
        }
        return DATE_FORMAT.format(timestamp);
    }


}
