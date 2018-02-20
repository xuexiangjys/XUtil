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

import android.util.Log;

/**
 * 日志记录工具
 * @author xuexiang
 * @date 2018/2/4 下午6:42
 */
public final class LogUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private LogUtils() {
        throw new Error("Do not need instantiate!");
    }

    public final static String DEFAULT_LOG_TAG = "XUtil";

    /**
     * Master switch.To catch error info you need set this value below Log.WARN
     */
    public static int gDebugLevel = Log.ASSERT;

    /**
     * 'System.out' switch.When it is true, you can see the 'System.out' log.
     * Otherwise, you cannot.
     */
    public static boolean DEBUG_SYSOUT = false;

    public static String sLogTag = DEFAULT_LOG_TAG;

    /**
     * 设置调试模式
     * @param tag
     */
    public static void debug(String tag) {
        if (!StringUtils.isEmpty(tag)) {
            setDebugMode(true);
            setLogTag(tag);
        } else {
            setDebugMode(false);
        }
    }

    /**
     * 设置调试的模式
     * @param isDebug
     */
    public static void setDebugMode(boolean isDebug) {
        if (isDebug) {
            gDebugLevel = 0;
        } else {
            gDebugLevel = Log.ASSERT;
        }
    }

    /**
     * 设置日志tag
     * @param tag
     */
    public static void setLogTag(String tag) {
        sLogTag = tag;
    }

    public static void setDebugLevel(int level) {
        gDebugLevel = level;
    }

    public static void setIsDebugSysout(boolean isDebugSysout) {
        DEBUG_SYSOUT = isDebugSysout;
    }

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v( String msg) {
        if (Log.VERBOSE > gDebugLevel) {
            Log.v(sLogTag, msg);
        }
    }

    /**
     * Send a {@link #gDebugLevel} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void d(String msg) {
        if (Log.DEBUG > gDebugLevel) {
            Log.d(sLogTag, msg);
        }
    }

    /**
     * Send an {@link Log#INFO} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
        if (Log.INFO > gDebugLevel) {
            Log.i(sLogTag, msg);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
        if (Log.WARN > gDebugLevel) {
            Log.w(sLogTag, msg);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg) {
        if (Log.ERROR > gDebugLevel) {
            Log.e(sLogTag, msg);
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param throwable The message you would like logged.
     */
    public static void e(Throwable throwable) {
        if (Log.ERROR > gDebugLevel) {
            Log.e(sLogTag, Log.getStackTraceString(throwable));
        }
    }

    /**
     * Send an {@link Log#ERROR} log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String msg, Throwable throwable) {
        if (Log.ERROR > gDebugLevel) {
            Log.e(sLogTag, msg, throwable);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The
     * error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param msg The message you would like logged.
     */
    public static void wtf(String msg) {
        if (Log.ASSERT > gDebugLevel) {
            Log.wtf(sLogTag, msg);
        }
    }

    /**
     * Send a {@link android.util.Log#VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (Log.VERBOSE > gDebugLevel) {
            Log.v(tag, msg);
        }
    }

    /**
     * Send a {@link #gDebugLevel} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (Log.DEBUG > gDebugLevel) {
            Log.d(tag, msg);
        }
    }

    /**
     * Send an {@link android.util.Log#INFO} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (Log.INFO > gDebugLevel) {
            Log.i(tag, msg);
        }
    }

    /**
     * Send a {@link android.util.Log#WARN} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (Log.WARN > gDebugLevel) {
            Log.w(tag, msg);
        }
    }

    /**
     * Send an {@link android.util.Log#ERROR} log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (Log.ERROR > gDebugLevel) {
            Log.e(tag, msg);
        }
    }

    /**
     * What a Terrible Failure: Report a condition that should never happen. The
     * error will always be logged at level ASSERT with the call stack.
     * Depending on system configuration, a report may be added to the
     * {@link android.os.DropBoxManager} and/or the process may be terminated
     * immediately with an error dialog.
     *
     * @param tag Used to identify the source of a log message.
     * @param msg The message you would like logged.
     */
    public static void wtf(String tag, String msg) {
        if (Log.ASSERT > gDebugLevel) {
            Log.wtf(tag, msg);
        }
    }

    /**
     * Send a {@link Log#VERBOSE} log message. And just print method name and
     * position in black.
     */
    public static void print() {
        if (Log.VERBOSE > gDebugLevel) {
            String method = callMethodAndLine();
            Log.v(sLogTag, method);
            if (DEBUG_SYSOUT) {
                System.out.println(sLogTag + "  " + method);
            }
        }
    }

    /**
     * Send a {@link #gDebugLevel} log message.
     *
     * @param object The object to print.
     */
    public static void print(Object object) {
        if (Log.DEBUG > gDebugLevel) {
            String method = callMethodAndLine();
            String content = "";
            if (object != null) {
                content = StringUtils.toString(object) + "                    ----    "
                        + method;
            } else {
                content = " ## " + "                ----    " + method;
            }
            Log.d(sLogTag, content);
            if (DEBUG_SYSOUT) {
                System.out.println(sLogTag + "  " + content + "  " + method);
            }
        }
    }

    /**
     * Realization of double click jump events.
     *
     * @return
     */
    private static String callMethodAndLine() {
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];
        result += thisMethodStack.getClassName() + ".";
        result += thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";
        return result;
    }

}
