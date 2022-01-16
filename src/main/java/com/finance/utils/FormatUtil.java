package com.finance.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
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
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
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
        return DATE_TIME_FORMATTER;
    }

    public static DateTimeFormatter dateFormatter() {
        return DATE_FORMATTER;
    }

    public static LocalDateTime fromStringLDT(String string) {
        return LocalDateTime.parse(string, DATE_TIME_FORMATTER);
    }

    public static LocalDate fromStringLD(String string) {
        return LocalDate.parse(string, DATE_FORMATTER);
    }

    public static String nowLocalDateTime() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }

    public static String nowLocalDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static String number2String(Number number) {
        return DECIMALFORMAT.format(number);
    }


    public static Number string2Number(String str) throws ParseException {
        return DECIMALFORMAT.parse(str);
    }

    private static LocalDateTime localDateTime(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp * 1000),
                TimeZone.getDefault().toZoneId());
    }

    public static Long timestamp(LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime).getTime() / 1000;
    }

    //Number
    public static BigDecimal bigDecimal(String str){
       return new BigDecimal(str.isBlank() ? "0" : str);
    }


    public static double stringToNumber(String str) {
        if (isNonEmpty(str)) {
            return Double.parseDouble(str.replaceAll(",", "."));
        } throw new NoSuchElementException("stringToNumber not null");
    }

    public static String sumString(String str1, String str2) {
        return number2String(stringToNumber(str1) + stringToNumber(str2));
    }

    public static String sumDouble(Number number1, Number number2) {
        return number2String(number1.doubleValue() + number2.doubleValue());
    }

    public static String sumNumberAndDouble(Number number, String str) {
        return number2String(number.doubleValue() + stringToNumber(str));
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
