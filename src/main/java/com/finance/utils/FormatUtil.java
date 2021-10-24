package com.finance.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TimeZone;

public class FormatUtil {
    private static final Locale locale = new Locale("en", "EN");
    private static final DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
    private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#0.00", symbols);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//    private static final String REX_SUM = "(-?\\d{1,10}[,.]?\\d{0,2})";
    private static final String REX_SUM = "(^-?\\d+([,.]\\d{1,2})?$)";
    private static final String REX_DATE = "(^\\d{2}\\.\\d{2}\\.\\d{4}$)";
    private static final String REX_DATE_TIME = "(^\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}:\\d{2}:\\d{2}$)";
    //Date
    public static String getRexSum() {
        return REX_SUM;
    }
    public static String getRexDate() {
        return REX_DATE;
    }
    public static String getRexDateTime() {
        return REX_DATE_TIME;
    }

    public static DateTimeFormatter dateTimeFormatter() {
        return dateTimeFormatter;
    }

    public static LocalDateTime fromString(String string) {
        return LocalDateTime.parse(string, dateTimeFormatter);
    }

    public static String nowLocalDateTime() {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public static String nowLocalDate() {
        return LocalDate.now().format(dateFormatter);
    }

    public static String numberToString(Number number) {
        return DECIMALFORMAT.format(number);
    }

    private static LocalDateTime localDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp * 1000),
                TimeZone.getDefault().toZoneId());
    }

    private static Long timestamp(String args) {
        return Timestamp.valueOf(LocalDateTime.from(FormatUtil.dateTimeFormatter().parse(args))).getTime() / 1000;
    }

    //Number
    public static double stringToNumber(String str) {
        if (isNonEmpty(str)) {
            return Double.parseDouble(str.replaceAll(",", "."));
        } throw new NoSuchElementException("stringToNumber not null");
    }

    public static String sumString(String str1, String str2) {
        return numberToString(stringToNumber(str1) + stringToNumber(str2));
    }

    public static String sumDouble(Number number1, Number number2) {
        return numberToString(number1.doubleValue() + number2.doubleValue());
    }

    public static String sumNumberAndDouble(Number number, String str) {
        return numberToString(number.doubleValue() + stringToNumber(str));
    }

    public static double formatNumber(Number number) {
        return stringToNumber(DECIMALFORMAT.format(number));
    }

    public static String stringToString(String number) {
        return DECIMALFORMAT.format(stringToNumber(number));
    }

    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

    public static boolean isNonEmpty(String str){
        return !isEmpty(str);
    }
}
