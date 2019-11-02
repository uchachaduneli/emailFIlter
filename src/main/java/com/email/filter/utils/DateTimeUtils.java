/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.email.filter.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author
 */
public class DateTimeUtils {

    public static Date string2Date(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.parse(date);
    }

    public static String date2String(Date date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static String date2StringWithTime(Date date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public static String date2Time(Date date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    public static Date getMidnigthTime() {
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(Calendar.HOUR, 0);
        currentDay.set(Calendar.MINUTE, 0);
        currentDay.set(Calendar.SECOND, 0);
        currentDay.set(Calendar.AM_PM, Calendar.AM);
        return currentDay.getTime();
    }

    public static Date getNextDay() {
        Calendar c = Calendar.getInstance();
        c.setTime(getMidnigthTime());
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public static Date getStartOfMonth(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        startDate.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        startDate.set(Calendar.DAY_OF_MONTH, 1);
        return startDate.getTime();
    }

    public static Date getEndOfMonth(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        Calendar endDate = Calendar.getInstance();
        endDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        endDate.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        endDate.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return endDate.getTime();
    }
}
