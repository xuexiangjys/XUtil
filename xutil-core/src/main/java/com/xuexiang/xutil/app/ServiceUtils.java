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

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.logger.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *     desc   : 服务相关工具类
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:35
 * </pre>
 */
public final class ServiceUtils {

    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取所有运行的服务
     *
     * @return 服务名集合
     */
    public static Set getAllRunningService() {
        ActivityManager am =
                (ActivityManager) XUtil.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return Collections.emptySet();
        }
        List<RunningServiceInfo> info = am.getRunningServices(Integer.MAX_VALUE);
        Set<String> names = new HashSet<>();
        if (info == null || info.size() == 0) {
            return null;
        }
        for (RunningServiceInfo aInfo : info) {
            names.add(aInfo.service.getClassName());
        }
        return names;
    }

    /**
     * 启动服务
     *
     * @param className 完整包名的服务类名
     */
    public static void startService(final String className) {
        try {
            startService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动服务
     *
     * @param cls 服务类
     */
    public static void startService(final Class<?> cls) {
        Intent intent = new Intent(XUtil.getContext(), cls);
        XUtil.getContext().startService(intent);
    }

    /**
     * 停止服务
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(final String className) {
        try {
            return stopService(Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停止服务
     *
     * @param cls 服务类
     * @return {@code true}: 停止成功<br>{@code false}: 停止失败
     */
    public static boolean stopService(final Class<?> cls) {
        Intent intent = new Intent(XUtil.getContext(), cls);
        return XUtil.getContext().stopService(intent);
    }

    /**
     * 绑定服务
     *
     * @param className 完整包名的服务类名
     * @param conn      服务连接对象
     * @param flags     绑定选项
     *                  <ul>
     *                  <li>{@link Context#BIND_AUTO_CREATE}</li>
     *                  <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *                  <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *                  <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *                  <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *                  <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *                  </ul>
     */
    public static void bindService(final String className,
                                   final ServiceConnection conn,
                                   final int flags) {
        try {
            bindService(Class.forName(className), conn, flags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 绑定服务
     *
     * @param cls   服务类
     * @param conn  服务连接对象
     * @param flags 绑定选项
     *              <ul>
     *              <li>{@link Context#BIND_AUTO_CREATE}</li>
     *              <li>{@link Context#BIND_DEBUG_UNBIND}</li>
     *              <li>{@link Context#BIND_NOT_FOREGROUND}</li>
     *              <li>{@link Context#BIND_ABOVE_CLIENT}</li>
     *              <li>{@link Context#BIND_ALLOW_OOM_MANAGEMENT}</li>
     *              <li>{@link Context#BIND_WAIVE_PRIORITY}</li>
     *              </ul>
     */
    public static void bindService(final Class<?> cls,
                                   final ServiceConnection conn,
                                   final int flags) {
        Intent intent = new Intent(XUtil.getContext(), cls);
        XUtil.getContext().bindService(intent, conn, flags);
    }

    /**
     * 解绑服务
     *
     * @param conn 服务连接对象
     */
    public static void unbindService(final ServiceConnection conn) {
        XUtil.getContext().unbindService(conn);
    }

    /**
     * 判断服务是否运行
     *
     * @param className 完整包名的服务类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isServiceRunning(final String className) {
        ActivityManager am =
                (ActivityManager) XUtil.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) {
            return false;
        }
        List<RunningServiceInfo> info = am.getRunningServices(Integer.MAX_VALUE);
        if (info == null || info.size() == 0) {
            return false;
        }
        for (RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 停止指定应用的所以运行的服务
     *
     * @param context
     */
    public static void stopAllRunningService(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
        for (RunningServiceInfo si : servicesList) {
            if (context.getPackageName().equals(si.service.getPackageName())) {
                Logger.d("[stopService]:" + si.service.getClassName());
                stopService(si.service.getClassName());
            }
        }
    }

}
