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
import android.util.Log;


/**
 * 默认Logcat日志记录
 *
 * @author xuexiang
 * @date 2018/3/9 上午12:33
 */
public class LogcatLogger implements ILogger {

    private String mTag = Logger.DEFAULT_LOG_TAG;

    /**
     * 是否是调试模式
     */
    private boolean mIsDebug = false;

    /**
     * 日志打印等级
     */
    private int mLogLevel = Logger.MAX_LOG_LEVEL;

    @Override
    public void debug(boolean isDebug) {
        mIsDebug = isDebug;
    }

    @Override
    public void setTag(@NonNull String tag) {
        mTag = tag;
    }

    /**
     * 设置打印日志的等级
     *
     * @param level
     */
    @Override
    public void setLevel(int level) {
        mLogLevel = level;
    }

    @Override
    public void v(String msg) {
        v(mTag, msg);
    }

    @Override
    public void v(String tag, String msg) {
        if (enableLog(Log.VERBOSE)) {
            Log.v(tag, msg);
        }
    }

    @Override
    public void d(String msg) {
        d(mTag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        if (enableLog(Log.DEBUG)) {
            Log.d(tag, msg);
        }
    }

    @Override
    public void i(String msg) {
        i(mTag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        if (enableLog(Log.INFO)) {
            Log.i(tag, msg);
        }
    }

    @Override
    public void w(String msg) {
        w(mTag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        if (enableLog(Log.WARN)) {
            Log.w(tag, msg);
        }
    }

    @Override
    public void e(String msg) {
        e(mTag, msg);
    }

    @Override
    public void e(String tag, String msg) {
        if (enableLog(Log.ERROR)) {
            Log.e(tag, msg);
        }
    }

    @Override
    public void e(Throwable throwable) {
        e(mTag, throwable);
    }

    @Override
    public void e(String tag, Throwable throwable) {
        if (enableLog(Log.ERROR)) {
            Log.e(tag, "错误堆栈信息：" + Log.getStackTraceString(throwable));
        }
    }

    @Override
    public void wtf(String msg) {
        wtf(mTag, msg);
    }

    @Override
    public void wtf(String tag, String msg) {
        if (enableLog(Log.ASSERT)) {
            Log.wtf(tag, msg);
        }
    }

    /**
     * 能否打印
     *
     * @param logLevel
     * @return
     */
    private boolean enableLog(int logLevel) {
        return mIsDebug && logLevel >= mLogLevel;
    }

}
