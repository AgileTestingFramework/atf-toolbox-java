package com.agiletestingframework.toolbox.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static String timeStampAsString() {
        return timeStampAsString(TestConstant.GENERATED_DATE.length());
    }

    public static String timeStampAsString(int numberOfPlaces) {
        String answer = StringUtils.EMPTY;
        Calendar cal = Calendar.getInstance();
        String genDate = DateFormatUtils.format(cal, TestConstant.GENERATED_DATE);
        answer = StringUtils.right(genDate, numberOfPlaces);
        return answer;
    }

    public static String timeAsHexDigitsString() {
        return timeAsHexDigitsString(Integer.MAX_VALUE);
    }

    public static String timeAsHexDigitsString(int numberOfPlaces) {
        String answer = StringUtils.EMPTY;
        Calendar cal = Calendar.getInstance();
        String year = DateFormatUtils.format(cal, "yy");
        String month = DateFormatUtils.format(cal, "MM");
        String day = DateFormatUtils.format(cal, "dd");
        String hour = DateFormatUtils.format(cal, "kk");
        String minute = DateFormatUtils.format(cal, "mm");
        String second = DateFormatUtils.format(cal, "ss");

        answer += Long.toHexString(Long.parseLong(year));
        answer += Long.toHexString(Long.parseLong(month));
        answer += Long.toHexString(Long.parseLong(day));
        answer += Long.toHexString(Long.parseLong(hour));
        answer += Long.toHexString(Long.parseLong(minute));
        answer += Long.toHexString(Long.parseLong(second));

        answer = StringUtils.right(answer, numberOfPlaces);
        return answer;
    }

    public static String addDayString(String dateString, int days, String dateFormat) {
        if (StringUtils.isEmpty(dateString)) {
            return dateString;
        }

        Date passedDateAsDate = convertToDate(dateString, dateFormat);
        Date newDate = DateUtils.addDays(passedDateAsDate, days);
        String newDateAsString = DateFormatUtils.format(newDate, dateFormat);
        return newDateAsString;
    }

    public static String subtractDayString(String dateString, int days, String dateFormat) {
        int subtractDays = days;
        subtractDays = subtractDays * -1;
        return addDayString(dateString, subtractDays, dateFormat);
    }

    public static Date convertToDate(String dateAsString, String format) {
        Date answer = null;
        try {
            answer = DateUtils.parseDate(dateAsString, new String[] { format });
        } catch (ParseException e) {
            logger.error("Parsing date '%s' failed", dateAsString, e);
        }
        return answer;
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        if (date == null) {
            return null;
        }
        return formatter.format(date);
    }
}
