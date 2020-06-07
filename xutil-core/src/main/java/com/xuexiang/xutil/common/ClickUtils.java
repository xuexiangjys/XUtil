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

import android.os.SystemClock;
import android.view.View;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 快速点击工具类
 *
 * @author xuexiang
 * @since 2019-08-08 16:35
 */
public final class ClickUtils {

    /**
     * 默认点击时间间期（毫秒）
     */
    private final static long DEFAULT_INTERVAL_MILLIS = 1000;
    /**
     * 最近一次点击的时间
     */
    private static long sLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static int sLastClickViewId;

    private ClickUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 是否是快速点击
     *
     * @param v 点击的控件
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(View v) {
        return isFastDoubleClick(v, DEFAULT_INTERVAL_MILLIS);
    }

    /**
     * 是否是快速点击
     *
     * @param v              点击的控件
     * @param intervalMillis 时间间期（毫秒）
     * @return
     */
    public static boolean isFastDoubleClick(View v, long intervalMillis) {
        long time = System.currentTimeMillis();
        int viewId = v.getId();
        long timeD = time - sLastClickTime;
        if (0 < timeD && timeD < intervalMillis && viewId == sLastClickViewId) {
            return true;
        } else {
            sLastClickTime = time;
            sLastClickViewId = viewId;
            return false;
        }
    }

    //====================多次点击==========================//

    /**
     * 点击次数
     */
    private final static int COUNTS = 5;
    private static long[] sHits = new long[COUNTS];
    /**
     * 规定有效时间
     */
    private final static long DEFAULT_DURATION = 1000;

    /**
     * 一秒内连续点击5次
     *
     * @param listener 多次点击的监听
     */
    public static void doClick(OnContinuousClickListener listener) {
        doClick(DEFAULT_DURATION, listener);
    }

    /**
     * 规定时间内连续点击5次
     *
     * @param duration 规定时间
     * @param listener 多次点击的监听
     */
    public static void doClick(long duration, OnContinuousClickListener listener) {
        //每次点击时，数组向前移动一位
        System.arraycopy(sHits, 1, sHits, 0, sHits.length - 1);
        //为数组最后一位赋值
        sHits[sHits.length - 1] = SystemClock.uptimeMillis();
        if (sHits[0] >= (SystemClock.uptimeMillis() - duration)) {
            //重新初始化数组
            sHits = new long[COUNTS];
            if (listener != null) {
                listener.onContinuousClick();
            }
        }
    }

    /**
     * 多次点击的监听
     */
    public interface OnContinuousClickListener {
        /**
         * 多次点击
         */
        void onContinuousClick();
    }

    //====================双击退出==========================//

    /**
     * 双击退出函数
     */
    private static boolean sIsExit = false;

    /**
     * 双击返回退出程序
     */
    public static void exitBy2Click() {
        exitBy2Click(2000, null);
    }

    /**
     * 双击返回退出程序
     *
     * @param intervalMillis 按键间隔
     * @param listener       退出监听
     */
    public static void exitBy2Click(long intervalMillis, OnClick2ExitListener listener) {
        if (!sIsExit) {
            sIsExit = true;
            // 准备退出
            if (listener != null) {
                listener.onRetry();
            } else {
                ToastUtils.toast("再按一次退出程序");
            }
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    // 取消退出
                    sIsExit = false;
                }
                // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            }, intervalMillis);
        } else {
            if (listener != null) {
                listener.onExit();
            } else {
                XUtil.exitApp();
            }
        }
    }

    /**
     * 点击返回退出监听
     */
    public interface OnClick2ExitListener {
        /**
         * 再点击一次
         */
        void onRetry();

        /**
         * 退出
         */
        void onExit();
    }
}
