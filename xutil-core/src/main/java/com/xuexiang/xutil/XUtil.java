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
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresPermission;

import com.xuexiang.xutil.app.ActivityLifecycleHelper;
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
    private static Context sContext;
    private static XUtil sInstance;

    private ActivityLifecycleHelper mActivityLifecycleHelper;
    /**
     * 主线程Handler
     */
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    private XUtil() {
        mActivityLifecycleHelper = new ActivityLifecycleHelper();
    }

    /**
     * 初始化工具
     *
     * @param application
     */
    public static void init(Application application) {
        sContext = application.getApplicationContext();
        application.registerActivityLifecycleCallbacks(XUtil.get().getActivityLifecycleHelper());
    }

    /**
     * 初始化工具
     *
     * @param context
     */
    public static void init(Context context) {
        sContext = context.getApplicationContext();
    }

    /**
     * 注册activity的生命回调
     *
     * @param application
     * @param lifecycleHelper
     * @return
     */
    public XUtil registerLifecycleCallbacks(Application application, ActivityLifecycleHelper lifecycleHelper) {
        mActivityLifecycleHelper = lifecycleHelper;
        application.registerActivityLifecycleCallbacks(mActivityLifecycleHelper);
        return this;
    }

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static Context getContext() {
        testInitialize();
        return sContext;
    }

    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

    private static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 XUtil.init() 初始化！");
        }
    }

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

    /**
     * 获取主线程的Handler
     *
     * @return
     */
    public static Handler getMainHandler() {
        return sMainHandler;
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


    /**
     * 获取实例
     *
     * @return
     */
    public static XUtil get() {
        if (sInstance == null) {
            synchronized (XUtil.class) {
                if (sInstance == null) {
                    sInstance = new XUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 退出程序
     */
    @RequiresPermission(KILL_BACKGROUND_PROCESSES)
    public void exitApp() {
        if (mActivityLifecycleHelper != null) {
            mActivityLifecycleHelper.exit();
        }
        ServiceUtils.stopAllRunningService(getContext());
        ProcessUtils.killBackgroundProcesses(XUtil.getContext().getPackageName());
        System.exit(0);
    }

}
