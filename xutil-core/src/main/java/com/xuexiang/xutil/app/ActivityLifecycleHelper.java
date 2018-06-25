package com.xuexiang.xutil.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

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

    private Stack<Activity> mActivityStack;

    public ActivityLifecycleHelper() {
        mActivityStack = new Stack<>();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.v("[onActivityCreated]:" + StringUtils.getName(activity));
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Logger.v("[onActivityStarted]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Logger.v("[onActivityResumed]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Logger.v("[onActivityPaused]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Logger.v("[onActivityStopped]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Logger.v("[onActivitySaveInstanceState]:" + StringUtils.getName(activity));
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Logger.v("[onActivityDestroyed]:" + StringUtils.getName(activity));
        removeActivity(activity);
    }

    /**
     * 添加Activity到堆栈
     */
    private void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
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
        if (mActivityStack != null) {
            Iterator<Activity> it = mActivityStack.iterator();
            synchronized (it) {
                while (it.hasNext()) {
                    Activity activity = it.next();
                    if (clazz.getCanonicalName().equals(activity.getClass().getCanonicalName())) {
                        if (!activity.isFinishing()) {
                            it.remove();
                            activity.finish();
                        }
                    }
                }
            }
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

    /**
     * 退出
     */
    public void exit() {
        finishAllActivity();
    }
}
