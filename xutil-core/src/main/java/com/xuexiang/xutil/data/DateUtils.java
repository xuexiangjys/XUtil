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

package com.xuexiang.xutil.data;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import com.xuexiang.constant.DateFormatConstants;
import com.xuexiang.constant.TimeConstants;
import com.xuexiang.xutil.common.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xuexiang.constant.TimeConstants.DAY;
import static com.xuexiang.constant.TimeConstants.HOUR;
import static com.xuexiang.constant.TimeConstants.MIN;
import static com.xuexiang.constant.TimeConstants.SEC;
import static com.xuexiang.xutil.common.StringUtils.EMPTY;

/**
 * <pre>
 *     desc   : 日期工具
 *     author : xuexiang
 *     time   : 2018/4/30 下午12:22
 * </pre>
 */
public final class DateUtils {

    /**
     * yyyy-MM-dd
     */
    public static final ThreadLocal<DateFormat> yyyyMMdd = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateFormatConstants.yyyyMMdd);
        }
    };
    /**
     * yyyyMMdd
     */
    public static final ThreadLocal<DateFormat> yyyyMMddNoSep = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateFormatConstants.yyyyMMddNoSep);
        }
    };
    /**
     * HH:mm:ss
     */
    public static final ThreadLocal<DateFormat> HHmmss = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateFormatConstants.HHmmss);
        }
    };
    /**
     * HH:mm
     */
    public static final ThreadLocal<DateFormat> HHmm = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateFormatConstants.HHmm);
        }
    };
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final ThreadLocal<DateFormat> yyyyMMddHHmmss = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateFormatConstants.yyyyMMddHHmmss);
        }
    };
    /**
     * yyyyMMddHHmmss
     */
    public static final ThreadLocal<DateFormat> yyyyMMddHHmmssNoSep = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateFormatConstants.yyyyMMddHHmmssNoSep);
        }
    };
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final ThreadLocal<DateFormat> yyyyMMddHHmm = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateFormatConstants.yyyyMMddHHmm);
        }
    };
    /**
     * yyyy-MM-dd HH:mm:ss:SSS
     */
    public static final ThreadLocal<DateFormat> yyyyMMddHHmmssSSS = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat(DateFormatConstants.yyyyMMddHHmmssSSS);
        }
    };
    //============================时间类型转化================================//

    /**
     * Don't let anyone instantiate this class.
     */
    private DateUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 将时间戳转为时间字符串
     * <p>格式为 format</p>
     *
     * @param millis 毫秒时间戳
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String millis2String(final long millis, final DateFormat format) {
        return date2String(new Date(millis), format);
    }

    /**
     * 将 Date 类型转为时间字符串
     * <p>格式为 format</p>
     *
     * @param date   Date 类型时间
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String date2String(final Date date, final DateFormat format) {
        if (format != null) {
            return format.format(date);
        } else {
            return EMPTY;
        }
    }

    /**
     * 将时间字符串转为时间戳
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(final String time, final DateFormat format) {
        try {
            if (format != null) {
                return format.parse(time).getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将时间字符串转为 Date 类型
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return Date 类型
     */
    public static Date string2Date(final String time, final DateFormat format) {
        try {
            if (format != null) {
                return format.parse(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将 Date 类型转为时间戳
     *
     * @param date Date 类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(final Date date) {
        return date != null ? date.getTime() : -1;
    }

    /**
     * 将时间戳转为 Date 类型
     *
     * @param millis 毫秒时间戳
     * @return Date 类型时间
     */
    public static Date millis2Date(final long millis) {
        return new Date(millis);
    }

    /**
     * 转换日期格式 oldFormat ---> newFormat
     *
     * @param time      时间字符串
     * @param oldFormat 旧格式
     * @param newFormat 新格式
     * @return 转换成功：新时间格式，转换失败：{@link StringUtils#EMPTY}
     */
    public static String translateDateFormat(final String time, final DateFormat oldFormat, final DateFormat newFormat) {
        if (StringUtils.isSpace(time)) {
            return EMPTY;
        }

        Date date = string2Date(time, oldFormat); //String -> Date
        return date != null ? date2String(date, newFormat) : EMPTY; //Date -> String
    }

    /**
     * 转换日期格式   oldFormatType --->  newFormatType
     *
     * @param time
     * @param oldFormatType 旧格式
     * @param newFormatType 新格式
     * @return 转换成功：新时间格式，转换失败：{@link StringUtils#EMPTY}
     */
    public static String translateDateFormat(final String time, final String oldFormatType, final String newFormatType) {
        return translateDateFormat(time, new SimpleDateFormat(oldFormatType), new SimpleDateFormat(newFormatType));
    }

    /**
     * 判断时间字符串的格式是否正确
     *
     * @param time   时间字符串
     * @param format 时间的格式
     * @return
     */
    public static boolean isDateFormatRight(final String time, final DateFormat format) {
        //内容和格式出错，直接返回false
        if (StringUtils.isSpace(time) || format == null) {
            return false;
        }

        try {
            Date date = format.parse(time);
            String s = format.format(date);
            return time.equals(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将时间字符串转换为文件名称
     *
     * @param dateTime 时间字符串
     * @param suffix   文件名后缀
     * @return
     */
    @Nullable
    public static String convertTimeToFileName(String dateTime, String suffix) {
        if (StringUtils.isSpace(dateTime)) {
            return null;
        }

        Pattern p = Pattern.compile("[^\\d]+");
        Matcher m = p.matcher(dateTime);
        return m.replaceAll("").trim() + suffix;
    }

    //===============================时间计算==================================//

    /**
     * 获取日期当天的最早时间
     *
     * <p>例如：今天日期是 2018-4-24 12:34:58，结果就是 2018-4-24 00:00:00:000</p>
     *
     * @param date 日期
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取n天后最早的时间
     *
     * <p>例如：今天日期是 2018-4-24 12:34:58，n=2，结果就是 2018-4-26 00:00:00:000</p>
     *
     * @param date     日期
     * @param dayAfter 几天后
     * @return
     */
    public static Date getStartOfDay(Date date, int dayAfter) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, dayAfter);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取日期当天的最后时间
     *
     * <p>例如：今天日期是 2018-4-24 12:34:58，结果就是 2018-4-24 23:59:59:999</p>
     *
     * @param date 日期
     * @return
     */
    public static Date getEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    /**
     * 获取日期当天的最后时间
     *
     * <p>例如：今天日期是 2018-4-24 12:34:58，n=2，结果就是 2018-4-26 23:59:59:999</p>
     *
     * @param date     日期
     * @param dayAfter 几天后
     * @return
     */
    public static Date getEndOfDay(Date date, int dayAfter) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, dayAfter);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }


    /**
     * 获取当前日期n天前的日期，返回String
     * <p>例如：今天是2018-4-24，day=2， return 2018-4-22</p>
     * <p>day=-2， return 2018-4-26</p>
     *
     * @param day               【1：1天前， -1：1天后】
     * @param day
     * @param isNeedHMS:是否需要时分秒
     * @return
     */
    public static String nDaysBeforeToday(int day, boolean isNeedHMS) {
        return nDaysAfterToday(-day, isNeedHMS);
    }

    /**
     * 获取当前日期n天后的日期，返回String
     * <p>例如：今天是2018-4-24，day=2， return 2018-4-26</p>
     * <p>day=-2， return 2018-4-22</p>
     *
     * @param day               【1：1天后，-1：1天前】
     * @param isNeedHMS:是否需要时分秒
     * @return
     */
    public static String nDaysAfterToday(int day, boolean isNeedHMS) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTimeInMillis(System.currentTimeMillis());
        rightNow.add(Calendar.DAY_OF_MONTH, day);
        if (isNeedHMS) {
            return DateUtils.date2String(rightNow.getTime(), yyyyMMddHHmmss.get());
        } else {
            return DateUtils.date2String(rightNow.getTime(), yyyyMMdd.get());
        }
    }

    /**
     * 获取当前日期n天前的日期，返回String
     * <p>例如：今天是2018-4-24，day=2， return 2018-4-22</p>
     * <p>day=-2， return 2018-4-26</p>
     *
     * @param day 【1：1天前， -1：1天后】
     * @param day
     * @return
     */
    public static Date nDaysBeforeToday(int day) {
        return nDaysAfterToday(-day);
    }

    /**
     * 获取当前日期n天后的日期，返回Date
     * <p>例如：今天是2018-4-24，day=2， return 2018-4-26</p>
     * <p>day=-2， return 2018-4-22</p>
     *
     * @param day 【1：1天后，-1：1天前】
     * @return
     */
    public static Date nDaysAfterToday(int day) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTimeInMillis(System.currentTimeMillis());
        rightNow.add(Calendar.DAY_OF_MONTH, day);
        return rightNow.getTime();
    }

    /**
     * 获取当前日期n月前的日期，返回Date
     * <p>例如：今天是2018-04-24，month=2， return 2018-02-24</p>
     * <p>month=-2， return 2018-06-24</p>
     *
     * @param month 【1：1月前，-1：1月后】
     * @return 当前日期n月前的日期
     */
    public static Date nMonthsBeforeToday(int month) {
        return nMonthsAfterToday(-month);
    }

    /**
     * 获取当前日期n月后的日期，返回Date
     * <p>例如：今天是2018-04-24，month=2， return 2018-06-24</p>
     * <p>month=-2， return 2018-02-24</p>
     *
     * @param month 【1：1月后，-1：1月前】
     * @return 当前日期n月后的日期
     */
    public static Date nMonthsAfterToday(int month) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTimeInMillis(System.currentTimeMillis());
        rightNow.add(Calendar.MONTH, month);
        return rightNow.getTime();
    }

    /**
     * 获取两个时间差（单位：unit）
     * <p>time0 和 time1 格式都为 format</p>
     *
     * @param time0  时间字符串 0
     * @param time1  时间字符串 1
     * @param format 时间格式
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小时</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpan(final String time0,
                                   final String time1,
                                   final DateFormat format,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(
                Math.abs(string2Millis(time0, format) - string2Millis(time1, format)), unit
        );
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * @param date0 Date 类型时间 0
     * @param date1 Date 类型时间 1
     * @param unit  单位类型
     *              <ul>
     *              <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *              <li>{@link TimeConstants#SEC }: 秒</li>
     *              <li>{@link TimeConstants#MIN }: 分</li>
     *              <li>{@link TimeConstants#HOUR}: 小时</li>
     *              <li>{@link TimeConstants#DAY }: 天</li>
     *              </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpan(final Date date0,
                                   final Date date1,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), unit);
    }

    /**
     * 获取两个时间差（单位：unit）
     *
     * @param millis0 毫秒时间戳 0
     * @param millis1 毫秒时间戳 1
     * @param unit    单位类型
     *                <ul>
     *                <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *                <li>{@link TimeConstants#SEC }: 秒</li>
     *                <li>{@link TimeConstants#MIN }: 分</li>
     *                <li>{@link TimeConstants#HOUR}: 小时</li>
     *                <li>{@link TimeConstants#DAY }: 天</li>
     *                </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpan(final long millis0,
                                   final long millis1,
                                   @TimeConstants.Unit final int unit) {
        return millis2TimeSpan(Math.abs(millis0 - millis1), unit);
    }

    /**
     * 获取合适型两个时间差
     * <p>time0 和 time1 格式都为 format</p>
     *
     * @param time0     时间字符串 0
     * @param time1     时间字符串 1
     * @param format    时间格式
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision = 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(final String time0,
                                        final String time1,
                                        final DateFormat format,
                                        final int precision) {
        long delta = string2Millis(time0, format) - string2Millis(time1, format);
        return millis2FitTimeSpan(Math.abs(delta), precision);
    }

    /**
     * 获取合适型两个时间差
     *
     * @param date0     Date 类型时间 0
     * @param date1     Date 类型时间 1
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision = 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(final Date date0, final Date date1, final int precision) {
        return millis2FitTimeSpan(Math.abs(date2Millis(date0) - date2Millis(date1)), precision);
    }

    /**
     * 获取合适型两个时间差
     *
     * @param millis0   毫秒时间戳 1
     * @param millis1   毫秒时间戳 2
     * @param precision 精度
     *                  <p>precision = 0，返回 null</p>
     *                  <p>precision = 1，返回天</p>
     *                  <p>precision = 2，返回天和小时</p>
     *                  <p>precision = 3，返回天、小时和分钟</p>
     *                  <p>precision = 4，返回天、小时、分钟和秒</p>
     *                  <p>precision = 5，返回天、小时、分钟、秒和毫秒</p>
     * @return 合适型两个时间差
     */
    public static String getFitTimeSpan(final long millis0,
                                        final long millis1,
                                        final int precision) {
        return millis2FitTimeSpan(Math.abs(millis0 - millis1), precision);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小时</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpanByNow(final String time,
                                        final DateFormat format,
                                        @TimeConstants.Unit final int unit) {
        return getTimeSpan(getNowString(format), time, format, unit);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * @param date Date 类型时间
     * @param unit 单位类型
     *             <ul>
     *             <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *             <li>{@link TimeConstants#SEC }: 秒</li>
     *             <li>{@link TimeConstants#MIN }: 分</li>
     *             <li>{@link TimeConstants#HOUR}: 小时</li>
     *             <li>{@link TimeConstants#DAY }: 天</li>
     *             </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpanByNow(final Date date, @TimeConstants.Unit final int unit) {
        return getTimeSpan(new Date(), date, unit);
    }

    /**
     * 获取与当前时间的差（单位：unit）
     *
     * @param millis 毫秒时间戳
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link TimeConstants#MSEC}: 毫秒</li>
     *               <li>{@link TimeConstants#SEC }: 秒</li>
     *               <li>{@link TimeConstants#MIN }: 分</li>
     *               <li>{@link TimeConstants#HOUR}: 小时</li>
     *               <li>{@link TimeConstants#DAY }: 天</li>
     *               </ul>
     * @return unit 时间戳
     */
    public static long getTimeSpanByNow(final long millis, @TimeConstants.Unit final int unit) {
        return getTimeSpan(System.currentTimeMillis(), millis, unit);
    }

    /**
     * 获取合适型与当前时间的差
     * <p>time 格式为 format</p>
     *
     * @param time      时间字符串
     * @param format    时间格式
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision = 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(final String time,
                                             final DateFormat format,
                                             final int precision) {
        return getFitTimeSpan(getNowString(format), time, format, precision);
    }

    /**
     * 获取合适型与当前时间的差
     *
     * @param date      Date 类型时间
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision = 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(final Date date, final int precision) {
        return getFitTimeSpan(getNowDate(), date, precision);
    }

    /**
     * 获取合适型与当前时间的差
     *
     * @param millis    毫秒时间戳
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回 null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision = 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适型与当前时间的差
     */
    public static String getFitTimeSpanByNow(final long millis, final int precision) {
        return getFitTimeSpan(System.currentTimeMillis(), millis, precision);
    }

    /**
     * 获取友好型与当前时间的差
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final String time, final DateFormat format) {
        return getFriendlyTimeSpanByNow(string2Millis(time, format));
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param date Date 类型时间
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final Date date) {
        return getFriendlyTimeSpanByNow(date.getTime());
    }

    /**
     * 获取友好型与当前时间的差
     *
     * @param millis 毫秒时间戳
     * @return 友好型与当前时间的差
     * <ul>
     * <li>如果小于 1 秒钟内，显示刚刚</li>
     * <li>如果在 1 分钟内，显示 XXX秒前</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 小时外的今天内，显示今天15:32</li>
     * <li>如果是昨天的，显示昨天15:32</li>
     * <li>其余显示，2016-10-15</li>
     * <li>时间不合法的情况全部日期和时间信息，如星期六 十月 27 14:21:20 CST 2007</li>
     * </ul>
     */
    public static String getFriendlyTimeSpanByNow(final long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0) {
            return String.format("%tc", millis);
        }
        if (span < 1000) {
            return "刚刚";
        } else if (span < MIN) {
            return String.format(Locale.getDefault(), "%d秒前", span / SEC);
        } else if (span < HOUR) {
            return String.format(Locale.getDefault(), "%d分钟前", span / MIN);
        }
        // 获取当天 00:00
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return String.format("今天%tR", millis);
        } else if (millis >= wee - DAY) {
            return String.format("昨天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    private static long getWeeOfToday() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 年，单位【s】
     */
    private static final int YEAR_S = 365 * 24 * 60 * 60;
    /**
     * 月，单位【s】
     */
    private static final int MONTH_S = 30 * 24 * 60 * 60;
    /**
     * 天，单位【s】
     */
    private static final int DAY_S = 24 * 60 * 60;
    /**
     * 小时，单位【s】
     */
    private static final int HOUR_S = 60 * 60;
    /**
     * 分钟，单位【s】
     */
    private static final int MINUTE_S = 60;

    /**
     * 根据时间戳获取模糊型的时间描述。
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 模糊型的与当前时间的差
     * <ul>
     * <li>如果在 1 分钟内或者时间是未来的时间，显示刚刚</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 天内，显示XXX小时前</li>
     * <li>如果在 1 月内，显示XXX天前</li>
     * <li>如果在 1 年内，显示XXX月前</li>
     * <li>如果在 1 年外，显示XXX年前</li>
     * </ul>
     */
    public static String getFuzzyTimeDescriptionByNow(final String time, final DateFormat format) {
        return getFuzzyTimeDescriptionByNow(string2Millis(time, format));
    }

    /**
     * 根据时间戳获取模糊型的时间描述。
     *
     * @param date Date 类型时间
     * @return 模糊型的与当前时间的差
     * <ul>
     * <li>如果在 1 分钟内或者时间是未来的时间，显示刚刚</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 天内，显示XXX小时前</li>
     * <li>如果在 1 月内，显示XXX天前</li>
     * <li>如果在 1 年内，显示XXX月前</li>
     * <li>如果在 1 年外，显示XXX年前</li>
     * </ul>
     */
    public static String getFuzzyTimeDescriptionByNow(final Date date) {
        return getFuzzyTimeDescriptionByNow(date.getTime());
    }

    /**
     * 根据时间戳获取模糊型的时间描述。
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 模糊型的与当前时间的差
     * <ul>
     * <li>如果在 1 分钟内或者时间是未来的时间，显示刚刚</li>
     * <li>如果在 1 小时内，显示 XXX分钟前</li>
     * <li>如果在 1 天内，显示XXX小时前</li>
     * <li>如果在 1 月内，显示XXX天前</li>
     * <li>如果在 1 年内，显示XXX月前</li>
     * <li>如果在 1 年外，显示XXX年前</li>
     * </ul>
     */
    public static String getFuzzyTimeDescriptionByNow(final long timestamp) {
        long currentTime = System.currentTimeMillis();
        // 与现在时间相差秒数
        long timeGap = (currentTime - timestamp) / 1000;
        String timeStr;
        long span;
        if ((span = Math.round((float) timeGap / YEAR_S)) > 0) {
            timeStr = span + "年前";
        } else if ((span = Math.round((float) timeGap / MONTH_S)) > 0) {
            timeStr = span + "个月前";
        } else if ((span = Math.round((float) timeGap / DAY_S)) > 0) {// 1天以上
            timeStr = span + "天前";
        } else if ((span = Math.round((float) timeGap / HOUR_S)) > 0) {// 1小时-24小时
            timeStr = span + "小时前";
        } else if ((span = Math.round((float) timeGap / MINUTE_S)) > 0) {// 1分钟-59分钟
            timeStr = span + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    private static long timeSpan2Millis(final long timeSpan, @TimeConstants.Unit final int unit) {
        return timeSpan * unit;
    }

    private static long millis2TimeSpan(final long millis, @TimeConstants.Unit final int unit) {
        return millis / unit;
    }

    /**
     * @param millis    时间戳
     * @param precision 精度
     * @return
     */
    private static String millis2FitTimeSpan(long millis, int precision) {
        if (millis < 0 || precision <= 0) {
            return null;
        }
        precision = Math.min(precision, 5);
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        if (millis == 0) {
            return 0 + units[precision - 1];
        }
        StringBuilder sb = new StringBuilder();
        int[] unitLen = {DAY, HOUR, MIN, SEC, 1};
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 根据出生日期获取年龄
     *
     * @param birthDay 出生日期字符串
     * @param format   日期格式
     * @return 计算出的年龄
     */
    public static int getAgeByBirthDay(final String birthDay, final DateFormat format) throws IllegalArgumentException {
        return getAgeByBirthDay(DateUtils.string2Date(birthDay, format));
    }

    /**
     * 根据出生日期获取年龄
     *
     * @param birthDay 出生日期
     * @return 计算出的年龄
     */
    public static int getAgeByBirthDay(Date birthDay) throws IllegalArgumentException {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayNow < dayBirth) {
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    //==============================时间获取===============================//

    /**
     * 获取当前毫秒时间戳
     *
     * @return 毫秒时间戳
     */
    public static long getNowMills() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间字符串
     * <p>格式为 format</p>
     *
     * @param format 时间格式
     * @return 时间字符串
     */
    public static String getNowString(final DateFormat format) {
        return millis2String(System.currentTimeMillis(), format);
    }

    /**
     * 获取当前 Date
     *
     * @return Date 类型时间
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 判断是否今天
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final String time, final DateFormat format) {
        return isToday(string2Millis(time, format));
    }

    /**
     * 判断是否今天
     *
     * @param date Date 类型时间
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final Date date) {
        return isToday(date.getTime());
    }

    /**
     * 判断是否今天
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isToday(final long millis) {
        long wee = getWeeOfToday();
        return millis >= wee && millis < wee + DAY;
    }

    /**
     * 获取星期索引
     * <p>注意：周日的 Index 才是 1，周六为 7</p>
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final String time, final DateFormat format) {
        return getWeekIndex(string2Date(time, format));
    }

    /**
     * 得到年
     *
     * @param date Date对象
     * @return 年
     */
    public static int getYear(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 得到月
     *
     * @param date Date对象
     * @return 月
     */
    public static int getMonth(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到日
     *
     * @param date Date对象
     * @return 日
     */
    public static int getDay(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取星期索引
     * <p>注意：周日的 Index 才是 1，周六为 7</p>
     *
     * @param date Date 类型时间
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期索引
     * <p>注意：周日的 Index 才是 1，周六为 7</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...7
     * @see Calendar#SUNDAY
     * @see Calendar#MONDAY
     * @see Calendar#TUESDAY
     * @see Calendar#WEDNESDAY
     * @see Calendar#THURSDAY
     * @see Calendar#FRIDAY
     * @see Calendar#SATURDAY
     */
    public static int getWeekIndex(final long millis) {
        return getWeekIndex(millis2Date(millis));
    }


    //=====================获取生肖、星座=========================//

    private static final String[] CHINESE_ZODIAC =
            {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    /**
     * 获取生肖
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 生肖
     */
    public static String getChineseZodiac(final String time, final DateFormat format) {
        return getChineseZodiac(string2Date(time, format));
    }

    /**
     * 获取生肖
     *
     * @param date Date 类型时间
     * @return 生肖
     */
    public static String getChineseZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 获取生肖
     *
     * @param millis 毫秒时间戳
     * @return 生肖
     */
    public static String getChineseZodiac(final long millis) {
        return getChineseZodiac(millis2Date(millis));
    }

    /**
     * 获取生肖
     *
     * @param year 年
     * @return 生肖
     */
    public static String getChineseZodiac(final int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};
    private static final String[] ZODIAC = {
            "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座",
            "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"
    };

    /**
     * 获取星座
     * <p>time 格式为 format</p>
     *
     * @param time   时间字符串
     * @param format 时间格式
     * @return 生肖
     */
    public static String getZodiac(final String time, final DateFormat format) {
        return getZodiac(string2Date(time, format));
    }

    /**
     * 获取星座
     *
     * @param date Date 类型时间
     * @return 星座
     */
    public static String getZodiac(final Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * 获取星座
     *
     * @param millis 毫秒时间戳
     * @return 星座
     */
    public static String getZodiac(final long millis) {
        return getZodiac(millis2Date(millis));
    }

    /**
     * 获取星座
     *
     * @param month 月
     * @param day   日
     * @return 星座
     */
    public static String getZodiac(final int month, final int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }

}
