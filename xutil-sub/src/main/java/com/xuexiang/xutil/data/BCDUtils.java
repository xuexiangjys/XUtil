package com.xuexiang.xutil.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * BCD编码工具类
 *
 * @author XUE
 * @since 2019/3/6 8:50
 */
public final class BCDUtils {

    private BCDUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * BCD转String
     *
     * @param bcd
     * @return
     */
    public static String bcd2String(byte[] bcd) {
        StringBuilder res = new StringBuilder();
        if (null == bcd) {
            return res.toString();
        }
        for (int b : bcd) {
            if (b < 0) {
                b = 256 + b;
            }
            b = b / 16 * 10 + b % 16;
            if (b < 10) {
                res.append("0").append(b);
            } else {
                res.append(b);
            }
        }
        return res.toString();
    }

    /**
     * BCD转int
     *
     * @param bcd
     * @return
     */
    public static int bcd2Int(byte[] bcd) {
        return toInt(bcd2String(bcd), 0);
    }

    /**
     * BCD转double
     *
     * @param bcd    BCD编码
     * @param format double的格式【例如：XXX.XX】
     * @return
     */
    public static double bcd2Double(byte[] bcd, String format) {
        int point = getPointLength(format);
        return (bcd2Int(bcd) / (Math.pow(10, point)));
    }

    /**
     * BCD转float
     *
     * @param bcd    BCD编码
     * @param format float的格式【例如：XXX.XX】
     * @return
     */
    public static float bcd2Float(byte[] bcd, String format) {
        int point = getPointLength(format);
        return (float) (bcd2Int(bcd) / (Math.pow(10, point)));
    }

    /**
     * BCD转日期
     *
     * @param bcd
     * @return
     */
    public static Date bcd2Date(byte[] bcd, String sFormat) {
        if (bcd == null) {
            return new Date();
        }
        return string2Date(bcd2String(bcd), sFormat);
    }

    //==================================//
    /**
     * String转BCD
     *
     * @param sIn 字符串
     * @param formatIn BCD格式
     * @return
     */
    public static byte[] string2Bcd(String sIn, String formatIn) {
        if (sIn == null) {
            return null;
        }
        StringBuilder s = formatString(sIn);
        StringBuilder format = formatString(formatIn);

        byte[] res = null;
        try {
            int len = s.length();
            if (len % 2 != 0) {
                s.insert(0, "0");
            }
            if (format.length() % 2 != 0) {
                format.insert(0, "0");
            }
            int allLength = format.length() / 2;
            byte[] resValue = new byte[len / 2];
            for (int i = 0; i <= len - 2; i += 2) {
                resValue[i / 2] = (byte) ((byte) (s.charAt(i) - '0') * 16 + (byte) (s.charAt(i + 1) - '0'));
            }
            res = new byte[allLength];
            if (allLength >= resValue.length) {
                System.arraycopy(resValue, 0, res, res.length - resValue.length, resValue.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static byte[] string2Bcd(String sIn) {
        return string2Bcd(sIn, sIn);
    }

    /**
     * int转bcd
     *
     * @param value 值
     * @param format bcd格式
     * @return
     */
    public static byte[] int2Bcd(int value, String format) {
        return string2Bcd(String.valueOf(value), format);
    }

    /**
     * double转bcd
     *
     * @param value 值
     * @param format bcd格式
     * @return
     */
    public static byte[] double2Bcd(double value, String format) {
        int point = getPointLength(format);
        return int2Bcd((int) (value * Math.pow(10, point)), format);
    }

    /**
     * float转bcd
     *
     * @param value 值
     * @param format bcd格式
     * @return
     */
    public static byte[] float2Bcd(float value, String format) {
        int point = getPointLength(format);
        return int2Bcd((int) (value * Math.pow(10, point)), format);
    }

    /**
     * 日期转BCD
     *
     * @param date
     * @param format BCD格式
     * @return
     */
    public static byte[] date2Bcd(Date date, String format) {
        String sDate = date2String(date, format);
        format = format.replaceAll("[^a-zA-Z0-9]*", "");
        sDate = sDate.replaceAll("[^a-zA-Z0-9]*", "");
        return string2Bcd(sDate, format);
    }

    //=========================================//
    /**
     * 获取小数点的位数
     *
     * @param format
     * @return
     */
    private static int getPointLength(String format) {
        int index = format.indexOf(".");
        if (index >= 0) {
            index++;
        } else {
            index = format.length();
        }
        //得到小数点的位数
        return format.length() - index;
    }

    /**
     * String转Int（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    private static int toInt(final String value, final int defValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 日期转为字符串
     *
     * @param date
     * @return
     */
    public static String date2String(Date date, String type) {
        return date2String(date, new SimpleDateFormat(type));
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 format</p>
     *
     * @param date   Date 类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    private static String date2String(final Date date, final DateFormat format) {
        if (date != null && format != null) {
            return format.format(date);
        } else {
            return "";
        }
    }

    /**
     * 字符串转日期
     *
     * @param str
     * @param sFormat
     * @return
     */
    private static Date string2Date(String str, String sFormat) {
        if (str == null) {
            return null;
        }
        StringBuilder sFormat2Parse = new StringBuilder();
        StringBuilder s2Parse = new StringBuilder();
        // 去掉格式化的字符
        for (int i = 0; i < sFormat.length(); i++) {
            char c = sFormat.charAt(i);
            if (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z'))) {
                sFormat2Parse.append(c);
            }
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((c >= '0') && (c <= '9')) {
                s2Parse.append(c);
            }
        }
        // 去掉内容的字符
        SimpleDateFormat format = new SimpleDateFormat(sFormat2Parse.toString());
        Date date = null;
        try {
            date = format.parse(s2Parse.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 去掉格式化的字符
     * @param sIn
     * @return
     */
    private static StringBuilder formatString(String sIn) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < sIn.length(); i++) {
            char c = sIn.charAt(i);
            if (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))) {
                s.append(c);
            }
        }
        return s;
    }
}
