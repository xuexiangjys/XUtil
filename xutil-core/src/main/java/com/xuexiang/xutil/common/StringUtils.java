/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xutil.common;

import androidx.annotation.NonNull;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     desc   : String相关工具类
 *     author : xuexiang
 *     time   : 2018/2/4 下午6:37
 * </pre>
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public final static String EMPTY = "";

    /**
     * 判断字符串是否为 null 或长度为 0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为 null 或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空格<br> {@code false}: 不为 null 且不全空格
     */
    public static boolean isEmptyTrim(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null 或全空白字符<br> {@code false}: 不为 null 且不全空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(final CharSequence a, final CharSequence b) {
        if (a == b) {
            return true;
        }
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串 a
     * @param b 待校验字符串 b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * 获取String内容
     *
     * @param s
     * @return
     */
    public static String getString(String s) {
        return isEmptyTrim(s) ? "" : s;
    }

    /**
     * 获取String内容，去除前后空格
     *
     * @param s
     * @return
     */
    public static String getStringTrim(String s) {
        return isEmptyTrim(s) ? "" : s.trim();
    }

    /**
     * 获取String内容，去除所有空格
     *
     * @param s
     * @return
     */
    public static String getStringNoSpace(String s) {
        return isEmptyTrim(s) ? "" : replaceBlank(s);
    }

    /**
     * 裁剪字符串
     *
     * @param originalStr 原字符串
     * @param beginIndex  开始的索引
     * @param endIndex    结束的索引
     * @return
     */
    public static String cutString(String originalStr, int beginIndex, int endIndex) {
        if (isEmpty(originalStr)) {
            return originalStr;
        } else {
            try {
                return originalStr.substring(beginIndex, endIndex);
            } catch (IndexOutOfBoundsException e) {
                return originalStr;
            }
        }
    }

    /**
     * String转Int（防止崩溃）
     *
     * @param value
     * @return
     */
    public static int toInt(final String value) {
        return toInt(value, 0);
    }

    /**
     * String转Int（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static int toInt(final String value, final int defValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * String转Float（防止崩溃）
     *
     * @param value
     * @return
     */
    public static float toFloat(final String value) {
        return toFloat(value, 0);
    }

    /**
     * String转Float（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static float toFloat(final String value, final float defValue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }


    /**
     * String转Short（防止崩溃）
     *
     * @param value
     * @return
     */
    public static short toShort(final String value) {
        return toShort(value, (short) 0);
    }

    /**
     * String转Short（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static short toShort(final String value, final short defValue) {
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * String转Long（防止崩溃）
     *
     * @param value
     * @return
     */
    public static long toLong(final String value) {
        return toLong(value, 0);
    }

    /**
     * String转Long（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static long toLong(final String value, final long defValue) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * String转Double（防止崩溃）
     *
     * @param value
     * @return
     */
    public static double toDouble(final String value) {
        return toDouble(value, 0);
    }


    /**
     * String转Double（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static double toDouble(final String value, final double defValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    /**
     * String转Boolean（防止崩溃）
     *
     * @param value
     * @return
     */
    public static boolean toBoolean(final String value) {
        return toBoolean(value, false);
    }

    /**
     * String转Boolean（防止崩溃）
     *
     * @param value
     * @param defValue 默认值
     * @return
     */
    public static boolean toBoolean(final String value, final boolean defValue) {
        try {
            return Boolean.parseBoolean(value);
        } catch (Exception e) {
            return defValue;
        }
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是双精度浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null 返回 0，其他返回自身长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) - 32) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) + 32) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(final String s) {
        int len = length(s);
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 获取异常栈信息，不同于Log.getStackTraceString()，该方法不会过滤掉UnknownHostException.
     *
     * @param t {@link Throwable}
     * @return 异常栈里的信息
     */
    public static String getStackTraceString(@NonNull Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 字符串连接，将参数列表拼接为一个字符串
     *
     * @param more 追加
     * @return 返回拼接后的字符串
     */
    public static String concat(Object... more) {
        return concatSpiltWith("", more);
    }

    /**
     * 字符串连接，将参数列表通过分隔符拼接为一个字符串
     *
     * @param split
     * @param more
     * @return 回拼接后的字符串
     */
    public static String concatSpiltWith(String split, Object... more) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < more.length; i++) {
            if (i != 0) {
                buf.append(split);
            }
            buf.append(toString(more[i]));
        }
        return buf.toString();
    }

    /**
     * 判断一个数组里是否包含指定对象
     *
     * @param array 对象数组
     * @param obj   要判断的对象
     * @return 是否包含
     */
    public static boolean contains(Object[] array, Object... obj) {
        if (array == null || obj == null || array.length == 0) {
            return false;
        }
        return Arrays.asList(array).containsAll(Arrays.asList(obj));
    }

    /**
     * 将对象转化为String
     *
     * @param object
     * @return
     */
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }

    /**
     * 过滤字符串中所有的特殊字符
     *
     * @param str
     * @return
     */
    public static String replaceSpecialCharacter(final String str) {
        String dest = "";
        if (str != null) {
            String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("").trim();
        }
        return dest;
    }

    /**
     * 过滤字符串中的[和]
     *
     * @param str
     * @return
     */
    public static String replaceBracket(final String str) {
        String dest = "";
        if (str != null) {
            String regEx = "[\\[\\]]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("").trim();
        }
        return dest;
    }

    /**
     * 过滤字符串中的空格
     *
     * @param str
     * @return
     */
    public static String replaceBlank(final String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 根据分隔符将String转换为List
     *
     * <p>例如:aa,bb,cc --> {"aa","bb","cc"}</p>
     *
     * @param str
     * @param separator 分隔符
     * @return
     */
    public static List<String> stringToList(final String str, final String separator) {
        return Arrays.asList(str.split(separator));
    }

    /**
     * 根据分隔符将List转换为String
     *
     * @param list
     * @param separator
     * @return
     */
    public static String listToString(final List<String> list, final String separator) {
        if (list == null || list.size() == 0) {
            return EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * 获取对象的类名
     *
     * @param object
     * @return
     */
    public static String getSimpleName(final Object object) {
        return object != null ? object.getClass().getSimpleName() : "NULL";
    }

    /**
     * 获取对象的类名
     *
     * @param object
     * @return
     */
    public static String getName(final Object object) {
        return object != null ? object.getClass().getName() : "NULL";
    }

    /**
     * 将字符串格式化为带两位小数的字符串
     *
     * @param str 字符串
     * @return
     */
    public static String format2Decimals(final String str) {
        // 构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return isEmpty(str) ? "" : decimalFormat.format(toDouble(str, -1));
    }

    /**
     * 将浮点数转化为带两位小数的字符串
     *
     * @param number 字符串
     * @return
     */
    public static String format2Decimals(final double number) {
        // 构造方法的字符格式这里如果小数不足2位,会以0补足.
        return new DecimalFormat("0.00").format(number);
    }

    /**
     * 将浮点数转化为带两位小数的字符串
     *
     * @param number 字符串
     * @return
     */
    public static String format2Decimals(final float number) {
        // 构造方法的字符格式这里如果小数不足2位,会以0补足.
        return new DecimalFormat("0.00").format(number);
    }

    /**
     * 比较两个版本号
     *
     * @param versionName1 比较版本1
     * @param versionName2 比较版本2
     * @return [> 0 versionName1 > versionName2] [= 0 versionName1 = versionName2]  [< 0 versionName1 < versionName2]
     */
    public static int compareVersionName(@NonNull String versionName1, @NonNull String versionName2) {
        if (versionName1.equals(versionName2)) {
            return 0;
        }
        //注意此处为正则匹配，不能用"."；
        String[] versionArray1 = versionName1.split("\\.");
        String[] versionArray2 = versionName2.split("\\.");
        int idx = 0;
        //取最小长度值
        int minLength = Math.min(versionArray1.length, versionArray2.length);
        int diff = 0;
        while (idx < minLength
                //先比较长度
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0
                //再比较字符
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

}
