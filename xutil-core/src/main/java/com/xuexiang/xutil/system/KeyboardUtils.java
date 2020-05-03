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

package com.xuexiang.xutil.system;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.xuexiang.xutil.XUtil;

import java.lang.reflect.Field;

/**
 * <pre>
 *     desc   : 键盘相关工具类
 *     author : xuexiang
 *     time   : 2018/4/27 下午8:39
 * </pre>
 */
public final class KeyboardUtils {

    private static int sContentViewInvisibleHeightPre;
    private static OnGlobalLayoutListener onGlobalLayoutListener;
    private static OnSoftInputChangedListener onSoftInputChangedListener;
    private static int sContentViewInvisibleHeightPre5497;

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /*
      避免输入法面板遮挡
      <p>在 manifest.xml 中 activity 中设置</p>
      <p>android:windowSoftInputMode="adjustPan"</p>
     */

    /**
     * 动态显示软键盘
     *
     * @param activity activity
     */
    public static void showSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        }
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 动态显示软键盘
     *
     * @param view 视图
     */
    public static void showSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) XUtil.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null || view == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    public static void hideSoftInput(final Activity activity) {
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 动态隐藏软键盘
     *
     * @param view 视图
     */
    public static void hideSoftInput(final View view) {
        InputMethodManager imm =
                (InputMethodManager) XUtil.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 切换软键盘显示与否状态
     */
    public static void toggleSoftInput() {
        InputMethodManager imm =
                (InputMethodManager) XUtil.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /**
     * 判断软键盘是否可见
     * <p>默认软键盘最小高度为 200</p>
     *
     * @param activity activity
     * @return {@code true}: 可见<br>{@code false}: 不可见
     */
    public static boolean isSoftInputVisible(final Activity activity) {
        return isSoftInputVisible(activity, 200);
    }

    /**
     * 判断软键盘是否可见
     *
     * @param activity             activity
     * @param minHeightOfSoftInput 软键盘最小高度
     * @return {@code true}: 可见<br>{@code false}: 不可见
     */
    public static boolean isSoftInputVisible(final Activity activity,
                                             final int minHeightOfSoftInput) {
        return getContentViewInvisibleHeight(activity) >= minHeightOfSoftInput;
    }

    private static int getContentViewInvisibleHeight(final Activity activity) {
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final Rect outRect = new Rect();
        contentViewChild.getWindowVisibleDisplayFrame(outRect);
        return contentViewChild.getBottom() - outRect.bottom;
    }

    /**
     * 注册软键盘改变监听器
     *
     * @param activity activity
     * @param listener listener
     */
    public static void registerSoftInputChangedListener(final Activity activity,
                                                        final OnSoftInputChangedListener listener) {
        final int flags = activity.getWindow().getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        sContentViewInvisibleHeightPre = getContentViewInvisibleHeight(activity);
        onSoftInputChangedListener = listener;
        onGlobalLayoutListener = new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (onSoftInputChangedListener != null) {
                    int height = getContentViewInvisibleHeight(activity);
                    if (sContentViewInvisibleHeightPre != height) {
                        onSoftInputChangedListener.onSoftInputChanged(height);
                        sContentViewInvisibleHeightPre = height;
                    }
                }
            }
        };
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    /**
     * Register soft input changed listener.
     *
     * @param activity The activity.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void unregisterSoftInputChangedListener(final Activity activity) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        onSoftInputChangedListener = null;
        onGlobalLayoutListener = null;
    }

    /**
     * Fix the bug of 5497 in Android.
     *
     * @param activity The activity.
     */
    public static void fixAndroidBug5497(final Activity activity) {
        final int flags = activity.getWindow().getAttributes().flags;
        if ((flags & WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) != 0) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        final FrameLayout contentView = activity.findViewById(android.R.id.content);
        final View contentViewChild = contentView.getChildAt(0);
        final int paddingBottom = contentViewChild.getPaddingBottom();
        sContentViewInvisibleHeightPre5497 = getContentViewInvisibleHeight(activity);
        contentView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = getContentViewInvisibleHeight(activity);
                        if (sContentViewInvisibleHeightPre5497 != height) {
                            contentViewChild.setPadding(
                                    contentViewChild.getPaddingLeft(),
                                    contentViewChild.getPaddingTop(),
                                    contentViewChild.getPaddingRight(),
                                    paddingBottom + height
                            );
                            sContentViewInvisibleHeightPre5497 = height;
                        }
                    }
                });
    }

    /**
     * 修复软键盘内存泄漏
     * <p>在 中使用</p>
     *
     * @param context context
     */
    public static void fixSoftInputLeaks(final Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager imm =
                (InputMethodManager) XUtil.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }
        String[] strArr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        for (int i = 0; i < 3; i++) {
            try {
                Field declaredField = imm.getClass().getDeclaredField(strArr[i]);
                if (declaredField == null) {
                    continue;
                }
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(imm);
                if (obj == null || !(obj instanceof View)) {
                    continue;
                }
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(imm, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     * <p>根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘</p>
     * <p>需重写 dispatchTouchEvent</p>
     *
     * @param ev
     * @param activity 窗口
     * @return
     */
    public static void onClickBlankArea2HideSoftInput(MotionEvent ev, Activity activity) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = activity.getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideSoftInput(v);
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     * <p>根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘</p>
     * <p>需重写 dispatchTouchEvent</p>
     *
     * @param ev
     * @param dialog 窗口
     * @return
     */
    public static void onClickBlankArea2HideSoftInput(MotionEvent ev, Dialog dialog) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = dialog.getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideSoftInput(v);
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     * <p>根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘</p>
     * <p>需重写 dispatchTouchEvent</p>
     *
     * @param ev
     * @param window 窗口
     * @return
     */
    public static void onClickBlankArea2HideSoftInput(MotionEvent ev, Window window) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = window.getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideSoftInput(v);
            }
        }
    }

    /**
     * 点击屏幕空白区域隐藏软键盘
     * <p>根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘</p>
     * <p>需重写 dispatchTouchEvent</p>
     *
     * @param ev
     * @param focusView 聚焦的控件
     * @return
     */
    public static void onClickBlankArea2HideSoftInput(MotionEvent ev, View focusView) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isShouldHideKeyboard(focusView, ev)) {
                hideSoftInput(focusView);
            }
        }
    }

    /**
     * 禁用物理返回键
     * <p>
     * 使用方法：
     * <p>需重写 onKeyDown</p>
     * public boolean onKeyDown(int keyCode, KeyEvent event) {
     * return KeyboardUtils.onDisableBackKeyDown(keyCode) && super.onKeyDown(keyCode, event) ;
     * }
     *
     * @param keyCode
     */
    public static boolean onDisableBackKeyDown(int keyCode) {
        return keyCode != KeyEvent.KEYCODE_BACK && keyCode != KeyEvent.KEYCODE_HOME;
    }

    /**
     * 根据 EditText 所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     *
     * @param v
     * @param event
     * @return
     */
    private static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    public interface OnSoftInputChangedListener {
        /**
         * 键盘输入法状态改变
         *
         * @param height
         */
        void onSoftInputChanged(int height);
    }
}
