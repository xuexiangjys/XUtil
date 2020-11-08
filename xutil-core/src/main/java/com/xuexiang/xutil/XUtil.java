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

package com.xuexiang.xutil;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import com.xuexiang.xutil.app.ActivityLifecycleHelper;
import com.xuexiang.xutil.app.AppUtils;
import com.xuexiang.xutil.app.ProcessUtils;
import com.xuexiang.xutil.app.ServiceUtils;
import com.xuexiang.xutil.common.logger.Logger;

import static android.Manifest.permission.KILL_BACKGROUND_PROCESSES;

/**
 * <pre>
 *     desc   : 全局工具管理
 *     author : xuexiang
 *     time   : 2018/4/27 下午8:38
 * </pre>
 */
public final class XUtil {
    /**
     * 全局上下文
     */
    private static Application sContext;
    /**
     * 生命周期管理
     */
    private static ActivityLifecycleHelper sLifecycleHelper = new ActivityLifecycleHelper();
    /**
     * 主线程Handler
     */
    private static final Handler MAIN_HANDLER = new Handler(Looper.getMainLooper());

    //===================初始化========================//

    /**
     * 是否自动初始化
     */
    private static boolean sAutoInit = true;

    /**
     * 禁止自动初始化
     * 请在Application的attachBaseContext方法前调用方可生效
     */
    public static void disableAutoInit() {
        XUtil.sAutoInit = false;
    }

    /**
     * @return 是否自动初始化
     */
    public static boolean isAutoInit() {
        return sAutoInit;
    }

    /**
     * 初始化工具【注册activity的生命回调】
     *
     * @param application
     */
    public static void init(Application application) {
        sContext = application;
        application.registerActivityLifecycleCallbacks(sLifecycleHelper);
    }

    /**
     * 初始化工具【不注册activity的生命回调】
     *
     * @param context
     */
    public static void init(Context context) {
        sContext = (Application) context.getApplicationContext();
    }

    /**
     * 注册activity的生命回调
     *
     * @param application
     * @param lifecycleHelper activity生命周期管理
     */
    public static void registerLifecycleCallbacks(Application application, ActivityLifecycleHelper lifecycleHelper) {
        sLifecycleHelper = lifecycleHelper;
        application.registerActivityLifecycleCallbacks(sLifecycleHelper);
    }

    //===================获取全局上下文========================//

    /**
     * 获取全局上下文
     *
     * @return 全局上下文
     */
    public static Context getContext() {
        testInitialize();
        return sContext;
    }

    /**
     * 获取全局ContentResolver
     *
     * @return ContentResolver
     */
    public static ContentResolver getContentResolver() {
        return getContext().getContentResolver();
    }

    /**
     * 获取全局资源
     *
     * @return 全局资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取全局Asset管理
     *
     * @return 全局Asset管理
     */
    public static AssetManager getAssetManager() {
        return getContext().getAssets();
    }

    /**
     * 获取包管理
     *
     * @return 包管理
     */
    public static PackageManager getPackageManager() {
        return getContext().getPackageManager();
    }

    /**
     * 获取系统服务
     *
     * @param name  服务名
     * @param clazz 服务类
     * @param <T>
     * @return 系统服务
     */
    public static <T> T getSystemService(String name, Class<T> clazz) {
        return getSystemService(getContext(), name, clazz);
    }

    /**
     * 获取系统服务
     *
     * @param context 上下文
     * @param name    服务名
     * @param clazz   服务类
     * @param <T>
     * @return 系统服务
     */
    public static <T> T getSystemService(Context context, String name, Class<T> clazz) {
        if (!TextUtils.isEmpty(name) && clazz != null && context != null) {
            Object obj = context.getSystemService(name);
            return clazz.isInstance(obj) ? (T) obj : null;
        } else {
            return null;
        }
    }

    /**
     * 获取生命周期管理
     *
     * @return 生命周期管理
     */
    public static ActivityLifecycleHelper getActivityLifecycleHelper() {
        return sLifecycleHelper;
    }

    private static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 XUtil.init() 初始化！");
        }
    }

    //===================调试模式========================//

    /**
     * 设置日志记录
     */
    public static void debug(boolean isDebug) {
        if (isDebug) {
            debug(Logger.DEFAULT_LOG_TAG);
        } else {
            debug("");
        }
    }

    /**
     * 设置日志记录
     *
     * @param tag
     */
    public static void debug(String tag) {
        Logger.debug(tag);
    }

    //===================全局Handler========================//

    /**
     * 获取主线程的Handler
     *
     * @return 主线程Handler
     */
    public static Handler getMainHandler() {
        return MAIN_HANDLER;
    }

    /**
     * 在主线程中执行
     *
     * @param runnable
     * @return
     */
    public static boolean runOnUiThread(Runnable runnable) {
        return getMainHandler().post(runnable);
    }

    //===================退出app========================//

    /**
     * 退出程序
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public static void exitApp() {
        if (sLifecycleHelper != null) {
            sLifecycleHelper.exit();
        }
        ServiceUtils.stopAllRunningService(getContext());
        ProcessUtils.killBackgroundProcesses(XUtil.getContext().getPackageName());
        System.exit(0);
    }

    /**
     * 重启app
     */
    public static void rebootApp() {
        AppUtils.rebootApp();
    }


}
