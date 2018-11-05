package com.luckyshane.cnblogs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static Date parseUTCDateStr(String utcDateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINA);
        try {
            return formatter.parse(utcDateStr);
        } catch (ParseException e) {
            //格式化Sat, 17 Mar 2012 11:37:13 +0000
            //Sat, 17 Mar 2012 22:13:41 +0800
            try {
                SimpleDateFormat formatter2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.CHINA);
                return formatter2.parse(utcDateStr);
            } catch (ParseException ex) {
                return null;
            }
        }
    }

    public static Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }



}
