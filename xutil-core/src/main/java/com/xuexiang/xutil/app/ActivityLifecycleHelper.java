package com.xuexiang.xutil.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.common.logger.Logger;

import java.util.Stack;

/**
 * <pre>
 *     desc   : activity生命周期管理
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:26
 * </pre>
 */
public class ActivityLifecycleHelper implements Application.ActivityLifecycleCallbacks {

    private Stack<Activity> mActivityStack;

    public ActivityLifecycleHelper() {
        mActivityStack = new Stack<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.d("[onActivityCreated]:" + StringUtils.getName(activity));
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Logger.d("[onActivityStarted]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Logger.d("[onActivityResumed]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Logger.d("[onActivityResumed]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Logger.d("[onActivityStopped]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Logger.d("[onActivitySaveInstanceState]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Logger.d("[onActivityDestroyed]:" + StringUtils.getName(activity));
        removeActivity(activity);
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
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
     * @return
     */
    public Activity getPreActivity() {
        int size = mActivityStack.size();
        if (size < 2) return null;
        return mActivityStack.elementAt(size - 2);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        finishActivity(getCurrentActivity());
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
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivityStack != null) {
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
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 退出
     */
    public void exit() {
        finishAllActivity();
    }
}
