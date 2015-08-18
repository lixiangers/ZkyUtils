package com.zky.zkyutils.utils;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {

    public static final Pattern mobileNumberPattern = Pattern.compile("^\\d{11}$");
    public static final String EMPTY = "";
    private static final Pattern whiteSpacePattern = Pattern.compile("\\s+");
    private static final Pattern numberPattern = Pattern.compile("[0-9]*");
    private static final Pattern bankCardNumberPattern = Pattern.compile("[0-9]{19}");

    public static boolean isNotBlank(String text) {
        return text != null && !EMPTY.equalsIgnoreCase(text.trim()) && !" ".equals(text);
    }

    public static boolean isBlank(String text) {
        return !isNotBlank(text);
    }

    public static boolean isNumber(String text) {
        return text != null && numberPattern.matcher(text).matches();
    }

    public static String removeAllWhiteSpace(String text) {
        return text == null ? null : whiteSpacePattern.matcher(text).replaceAll(EMPTY);
    }

    public static boolean isEqual(String text, String target) {
        return !isBlank(text) && text.equals(target);
    }

    public static String formatCashierId(String cashId) {
        int id = Integer.parseInt(cashId);
        return String.format("%04d", id);
    }

    public static String formatOrderCreateTime(String time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(time);
    }

    public static boolean isMobileNumber(String mobiles) {
        if (isBlank(mobiles))
            return false;

        Matcher m = mobileNumberPattern.matcher(mobiles);
        return m.matches();
    }

    public static boolean isBankCardNumber(String text) {
        return isNotBlank(text) && bankCardNumberPattern.matcher(text).matches();
    }

    public static String getHexString(byte[] b) throws Exception {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public static byte[] getHexStringByteArray(String hexString) {
        return new BigInteger(hexString, 16).toByteArray();
    }

    public static String generateRandomCode() {
        return String.valueOf(getRandom(1, 100000));
    }

    private static Long getRandom(int min, int max) {
        return Math.round(Math.random() * (max - min) + min);
    }
}