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

import android.view.View;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *     desc   :	快速点击工具类
 *     author : xuexiang
 *     time   : 2018/4/23 下午2:45
 * </pre>
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

    /**
     * 双击退出函数
     */
    private static boolean sIsExit = false;

    /**
     * 双击返回退出程序
     */
    public static void exitBy2Click() {
        if (!sIsExit) {
            sIsExit = true; // 准备退出
            ToastUtils.toast("再按一次退出程序");
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    sIsExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            XUtil.get().exitApp();
        }
    }
}
