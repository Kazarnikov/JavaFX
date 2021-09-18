package com.finance.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatUtil {
   private static final Locale locale = new Locale("en", "EN");
   private static final DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
   private static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.00", symbols);

   public static String numberToString(Number number) {
      return DECIMALFORMAT.format(number);
   }

   public static double stringToNumber(String str) {
      return Double.parseDouble(str.replaceAll(",", "."));
   }

   public static String sumString(String str1, String str2 ) {
      return numberToString(Double.parseDouble(str1.replaceAll(",", ".")) +  Double.parseDouble(str2.replaceAll(",", ".")));
   }

   public static String sumDouble(Number numb1, Number numb2 ) {
      return numberToString(numb1.doubleValue() + numb2.doubleValue());
   }

   public static String sumNumberAndDouble(Number numb, String str ) {
      return numberToString(numb.doubleValue() + stringToNumber(str.replaceAll(",", ".")));
   }

   public static double formatNumber(Number number) {
      return stringToNumber(DECIMALFORMAT.format(number));
   }
}
