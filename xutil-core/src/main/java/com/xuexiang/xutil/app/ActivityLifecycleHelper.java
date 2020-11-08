/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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
 *
 */

package com.xuexiang.xutil.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.common.logger.Logger;

import java.util.Iterator;
import java.util.Stack;

/**
 * <pre>
 *     desc   : activity生命周期管理
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:26
 * </pre>
 */
public class ActivityLifecycleHelper implements Application.ActivityLifecycleCallbacks {

    private final Stack<Activity> mActivityStack = new Stack<>();

    private final Object mLock = new Object();

    public ActivityLifecycleHelper() {

    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        Logger.v("[onActivityCreated]:" + StringUtils.getName(activity));
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Logger.v("[onActivityStarted]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Logger.v("[onActivityResumed]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Logger.v("[onActivityPaused]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Logger.v("[onActivityStopped]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Logger.v("[onActivitySaveInstanceState]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Logger.v("[onActivityDestroyed]:" + StringUtils.getName(activity));
        removeActivity(activity);
    }

    /**
     * 添加Activity到堆栈
     */
    private void addActivity(Activity activity) {
        mActivityStack.add(activity);
    }

    /**
     * 将Activity移出堆栈
     *
     * @param activity
     */
    private void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        return mActivityStack.lastElement();
    }

    /**
     * 获取上一个Activity
     *
     * @return 上一个Activity
     */
    public Activity getPreActivity() {
        int size = mActivityStack.size();
        if (size < 2) {
            return null;
        }
        return mActivityStack.elementAt(size - 2);
    }

    /**
     * 某一个Activity是否存在
     *
     * @return true : 存在, false: 不存在
     */
    public boolean isActivityExist(@NonNull Class<? extends Activity> clazz) {
        for (Activity activity : mActivityStack) {
            if (activity.getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        finishActivity(getCurrentActivity());
    }

    /**
     * 结束上一个Activity
     */
    public void finishPreActivity() {
        finishActivity(getPreActivity());
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity
     *
     * @param clazz activity的类
     */
    public void finishActivity(@NonNull Class<? extends Activity> clazz) {
        Iterator<Activity> it = mActivityStack.iterator();
        synchronized (mLock) {
            while (it.hasNext()) {
                Activity activity = it.next();
                if (StringUtils.equals(clazz.getCanonicalName(), activity.getClass().getCanonicalName())) {
                    if (!activity.isFinishing()) {
                        it.remove();
                        activity.finish();
                    }
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            Activity activity = mActivityStack.get(i);
            if (activity != null) {
                if (!activity.isFinishing()) {
                    Logger.d("[FinishActivity]:" + StringUtils.getName(activity));
                    activity.finish();
                }
            }
        }
        mActivityStack.clear();
    }

    /**
     * 获取当前Activity的活动栈
     *
     * @return 当前Activity的活动栈
     */
    public Stack<Activity> getActivityStack() {
        return mActivityStack;
    }

    /**
     * 退出
     */
    public void exit() {
        finishAllActivity();
    }
}
