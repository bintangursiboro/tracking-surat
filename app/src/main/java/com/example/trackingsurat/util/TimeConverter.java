package com.example.trackingsurat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeConverter {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
    public static String longToDate(long time) {
        Date date = new Date(time);
        String dates = dateFormat.format(date);
        return dates;
    }

    public static long dateToLong(String date) throws ParseException {
        Date sdate = dateFormat.parse(date);
        return sdate.getTime();
    }

}
