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
package com.xuexiang.xutil.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.text.TextUtils;

import com.xuexiang.xutil.XUtil;

/**
 * <pre>
 *     desc   :	轮询服务工具类
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:24
 * </pre>
 */
public final class PollingUtils {

    private static final String TAG = "PollingUtils";

    /**
     * Don't let anyone instantiate this class.
     */
    private PollingUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 判断是否存在轮询服务
     *
     * @param context 上下文
     * @param cls     Class
     * @return
     */
    public static boolean isPollingServiceExist(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }

    /**
     * 开启轮询服务
     *
     * @param context  上下文
     * @param interval 时间间隔，单位为秒
     * @param cls      Class
     */
    public static void startPollingService(Context context, int interval, Class<?> cls) {
        startPollingService(context, interval, cls, null);
    }

    /**
     * 开启轮询服务
     *
     * @param context  上下文
     * @param interval 时间间隔，单位为秒
     * @param cls      Class
     * @param action   Action
     */
    public static void startPollingService(Context context, int interval, Class<?> cls, String action) {
        AlarmManager manager = XUtil.getSystemService(Context.ALARM_SERVICE, AlarmManager.class);
        Intent intent = new Intent(context, cls);
        if (!TextUtils.isEmpty(action)) {
            intent.setAction(action);
        }
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long triggerAtTime = SystemClock.elapsedRealtime();
        if (manager != null) {
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime, interval * 1000, pendingIntent);
        }
    }

    /**
     * 停止轮询服务
     *
     * @param context 上下文
     * @param cls     Class
     */
    public static void stopPollingService(Context context, Class<?> cls) {
        stopPollingService(context, cls, null);
    }

    /**
     * 停止轮询服务
     *
     * @param context 上下文
     * @param cls     Class
     * @param action  Action
     */
    public static void stopPollingService(Context context, Class<?> cls, String action) {
        AlarmManager manager = XUtil.getSystemService(Context.ALARM_SERVICE, AlarmManager.class);
        Intent intent = new Intent(context, cls);
        if (!TextUtils.isEmpty(action)) {
            intent.setAction(action);
        }
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (manager != null) {
            manager.cancel(pendingIntent);
        }
    }

}
