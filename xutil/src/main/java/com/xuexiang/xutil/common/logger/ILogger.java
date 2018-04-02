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

/**
 * 简易的日志记录接口
 *
 * @author xuexiang
 * @date 2018/3/8 下午9:00
 */
public interface ILogger {

    /**
     * 设置是否是调试模式
     * @param isDebug
     */
    void debug(boolean isDebug);

    /**
     * 设置日志的tag
     * @param tag
     */
    void setTag(String tag);

    /**
     * 设置打印日志的等级（只打印改等级以上的日志）
     * @param level
     */
    void setLevel(int level);

    /**
     * 打印任何（所有）信息
     * @param msg
     */
    void v(String msg);

    /**
     * 打印任何（所有）信息
     * @param tag
     * @param msg
     */
    void v(String tag, String msg);

    /**
     * 打印调试信息
     * @param msg
     */
    void d(String msg);

    /**
     * 打印调试信息
     * @param tag
     * @param msg
     */
    void d(String tag, String msg);

    /**
     * 打印提示性的信息
     * @param msg
     */
    void i(String msg);

    /**
     * 打印提示性的信息
     * @param tag
     * @param msg
     */
    void i(String tag, String msg);

    /**
     * 打印warning警告信息
     * @param msg
     */
    void w(String msg);

    /**
     * 打印warning警告信息
     * @param tag
     * @param msg
     */
    void w(String tag, String msg);

    /**
     * 打印出错信息
     * @param msg
     */
    void e(String msg);

    /**
     * 打印出错信息
     * @param tag
     * @param msg
     */
    void e(String tag, String msg);

    /**
     * 打印出错堆栈信息
     * @param throwable
     */
    void e(Throwable throwable);

    /**
     * 打印出错堆栈信息
     * @param tag
     * @param throwable
     */
    void e(String tag, Throwable throwable);

    /**
     * 打印严重的错误信息
     * @param msg
     */
    void wtf(String msg);

    /**
     * 打印严重的错误信息
     * @param tag
     * @param msg
     */
    void wtf(String tag, String msg);

}
