package de.safespacegerman.core.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 * SpaceCore; de.safespacegerman.core.utils:DateUtils
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 28.03.2023
 */
public class DateUtils {

    private DateUtils() {} // prevent instantiation

    public static String formatDate(Date now, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(now);
    }

    public static String formatDate(LocalDate now, String format) {
        DateFormat df = new SimpleDateFormat(format);
        return df.format(now);
    }

    public static String convertDateToHHMMSS(Date now) {
        return formatDate(now, "HH:mm:ss");
    }

    public static String convertDateToHHMMSS(LocalDate now) {
        return formatDate(now, "HH:mm:ss");
    }

    public static String convertDateToString(Date now) {
        return formatDate(now, "dd/MM/yyyy HH:mm:ss");
    }

    public static String convertDateToString(LocalDate now) {
        return formatDate(now, "dd/MM/yyyy HH:mm:ss");
    }

    public static String formatDateNow() {
        LocalDate localDate = LocalDate.now();
        int dd = localDate.getDayOfMonth();
        int mm = localDate.getMonthValue();
        int yyyy = localDate.getYear();
        String date = dd + ":" + mm + ":" + yyyy;
        if (dd < 10) {
            date = "0" + dd + ":" + mm + ":" + yyyy;
        }
        if (mm < 10) {
            date = dd + ":0" + mm + ":" + yyyy;
        }
        if (dd < 10 && mm < 10) {
            date = "0" + dd + ":0" + mm + ":" + yyyy;
        }
        return date;
    }

    public static String getDateAsString(LocalDate localDate) {
        int dd = localDate.getDayOfMonth();
        int mm = localDate.getMonthValue();
        int yyyy = localDate.getYear();
        String date = dd + ":" + mm + ":" + yyyy;
        if (dd < 10) {
            date = "0" + dd + ":" + mm + ":" + yyyy;
        }
        if (mm < 10) {
            date = dd + ":0" + mm + ":" + yyyy;
        }
        if (dd < 10 && mm < 10) {
            date = "0" + dd + ":0" + mm + ":" + yyyy;
        }
        return date;
    }

}
