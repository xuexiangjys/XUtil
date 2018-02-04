package com.xuexiang.xutil.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.xuexiang.xutil.common.LogUtils;
import com.xuexiang.xutil.common.StringUtils;

import java.util.Stack;

/**
 * activity生命周期管理
 * @author xuexiang
 * @date 2018/2/4 下午3:37
 */
public class ActivityLifecycleHelper implements Application.ActivityLifecycleCallbacks {

    private Stack<Activity> mActivityStack;

    public ActivityLifecycleHelper() {
        mActivityStack = new Stack<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtils.d("[onActivityCreated]:" + StringUtils.getName(activity));
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtils.d("[onActivityStarted]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogUtils.d("[onActivityResumed]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityPaused(Activity activity) {
        LogUtils.d("[onActivityResumed]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityStopped(Activity activity) {
        LogUtils.d("[onActivityStopped]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        LogUtils.d("[onActivitySaveInstanceState]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.d("[onActivityDestroyed]:" + StringUtils.getName(activity));
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
        Activity activity = mActivityStack.lastElement();
        return activity;
    }

    public Activity getPreActivity() {
        int size = mActivityStack.size();
        if (size < 2) return null;
        return mActivityStack.elementAt(size - 2);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = mActivityStack.lastElement();
        finishActivity(activity);
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
                if (null != mActivityStack.get(i)) {
                    Activity activity = mActivityStack.get(i);
                    if (!activity.isFinishing()) {
                        LogUtils.d("[FinishActivity]:" + StringUtils.getName(activity));
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

    public void removeAllWithoutItself(Activity activity) {
        mActivityStack.clear();
        addActivity(activity);
    }

    /**
     * 退出
     */
    public void exit() {
        finishAllActivity();
    }
}
