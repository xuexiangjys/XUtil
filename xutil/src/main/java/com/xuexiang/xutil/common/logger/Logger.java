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

package com.xuexiang.xutil.common.logger;

import android.support.annotation.NonNull;

import com.xuexiang.xutil.common.StringUtils;

/**
 * Logger日志记录
 *
 * @author xuexiang
 * @date 2018/3/9 上午12:29
 */
public final class Logger {

    public final static String DEFAULT_LOG_TAG = "Logger";

    /**
     * 最大日志等级【日志等级为最大日志等级，所有日志都不打印】
     */
    public final static int MAX_LOG_LEVEL = 10;

    /**
     * 最小日志等级【日志等级为最小日志等级，所有日志都打印】
     */
    public final static int MIN_LOG_LEVEL = 0;

    /**
     * 默认的日志记录为Logcat
     */
    private static ILogger sILogger = new LogcatLogger();

    /**
     * 设置日志记录者的接口
     *
     * @param logger
     */
    public static void setLogger(@NonNull ILogger logger) {
        sILogger = logger;
    }

    /**
     * 设置调试模式
     * @param tag
     */
    public static void debug(String tag) {
        if (!StringUtils.isEmpty(tag)) {
            debug(true);
            setLevel(MIN_LOG_LEVEL);
            setTag(tag);
        } else {
            debug(false);
            setLevel(MAX_LOG_LEVEL);
            setTag("");
        }
    }

    /**
     * 设置是否是调试模式
     *
     * @param isDebug
     */
    public static void debug(boolean isDebug) {
        if (sILogger != null) {
            sILogger.debug(isDebug);
        }
    }

    /**
     * 设置打印日志的等级
     *
     * @param level
     */
    public static void setLevel(int level) {
        if (sILogger != null) {
            sILogger.setLevel(level);
        }
    }

    /**
     * 设置日志的tag
     *
     * @param tag
     */
    public static void setTag(String tag) {
        if (sILogger != null) {
            sILogger.setTag(tag);
        }
    }

    /**
     * 打印任何（所有）信息
     *
     * @param msg
     */
    public static void v(String msg) {
        if (sILogger != null) {
            sILogger.v(msg);
        }
    }

    /**
     * 打印任何（所有）信息
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (sILogger != null) {
            sILogger.v(tag, msg);
        }
    }

    /**
     * 打印调试信息
     *
     * @param msg
     */
    public static void d(String msg) {
        if (sILogger != null) {
            sILogger.d(msg);
        }
    }

    /**
     * 打印调试信息
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (sILogger != null) {
            sILogger.d(tag, msg);
        }
    }

    /**
     * 打印提示性的信息
     *
     * @param msg
     */
    public static void i(String msg) {
        if (sILogger != null) {
            sILogger.i(msg);
        }
    }

    /**
     * 打印提示性的信息
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (sILogger != null) {
            sILogger.i(tag, msg);
        }
    }

    /**
     * 打印warning警告信息
     *
     * @param msg
     */
    public static void w(String msg) {
        if (sILogger != null) {
            sILogger.w(msg);
        }
    }

    /**
     * 打印warning警告信息
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (sILogger != null) {
            sILogger.w(tag, msg);
        }
    }

    /**
     * 打印出错信息
     *
     * @param msg
     */
    public static void e(String msg) {
        if (sILogger != null) {
            sILogger.e(msg);
        }
    }

    /**
     * 打印出错信息
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (sILogger != null) {
            sILogger.e(tag, msg);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param throwable
     */
    public static void e(Throwable throwable) {
        if (sILogger != null) {
            sILogger.e(throwable);
        }
    }

    /**
     * 打印出错堆栈信息
     *
     * @param tag
     * @param throwable
     */
    public static void e(String tag, Throwable throwable) {
        if (sILogger != null) {
            sILogger.e(tag, throwable);
        }
    }

    /**
     * 打印严重的错误信息
     *
     * @param msg
     */
    public static void wtf(String msg) {
        if (sILogger != null) {
            sILogger.wtf(msg);
        }
    }

    /**
     * 打印严重的错误信息
     *
     * @param tag
     * @param msg
     */
    public static void wtf(String tag, String msg) {
        if (sILogger != null) {
            sILogger.wtf(tag, msg);
        }
    }
}
