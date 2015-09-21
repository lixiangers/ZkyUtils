package com.zky.zkyutils.utils;

import android.content.Context;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

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

    public static String formatTemplateString(Context context, int templateId, Object... params) {
        String template = context.getResources().getText(templateId).toString();
        return format(template, params);
    }

    /**
     * 格式化文件大
     *
     * @param volume 文件大小
     * @return 格式化的字符
     */
    public static String getVolume(long volume) {

        float num = 1.0F;

        String str = null;

        if (volume < 1024) {
            str = volume + "B";
        } else if (volume < 1048576) {
            num = num * volume / 1024;
            str = format("%.1f", num) + "K";
        } else if (volume < 1073741824) {
            num = num * volume / 1048576;
            str = format("%.1f", num) + "M";
        } else if (volume < 1099511627776L) {
            num = num * volume / 1073741824;
            str = format("%.1f", num) + "G";
        }

        return str;
    }
}