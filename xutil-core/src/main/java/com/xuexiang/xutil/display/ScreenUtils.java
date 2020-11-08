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

package com.xuexiang.xutil.display;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.logger.Logger;
import com.xuexiang.xutil.resource.ResUtils;

/**
 * <pre>
 *     desc   : 屏幕相关工具类
 *     author : xuexiang
 *     time   : 2018/4/30 下午12:23
 * </pre>
 */
public final class ScreenUtils {

    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽
     */
    public static int getScreenWidth() {
        WindowManager wm = XUtil.getSystemService(Context.WINDOW_SERVICE, WindowManager.class);
        if (wm == null) {
            return ResUtils.getResources().getDisplayMetrics().widthPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高
     */
    public static int getScreenHeight() {
        WindowManager wm = XUtil.getSystemService(Context.WINDOW_SERVICE, WindowManager.class);
        if (wm == null) {
            return ResUtils.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取屏幕高度尺寸 <BR>
     *
     * @param isReal 是否忽略导航栏显示隐藏
     * @return 屏幕高度尺寸
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getDisplayMetrics(boolean isReal) {
        WindowManager wm = XUtil.getSystemService(Context.WINDOW_SERVICE, WindowManager.class);
        DisplayMetrics dm = new DisplayMetrics();
        if (wm != null) {
            if (isReal) {
                wm.getDefaultDisplay().getRealMetrics(dm);
            } else {
                wm.getDefaultDisplay().getMetrics(dm);
            }
        }
        return dm.heightPixels;
    }

    /**
     * 获取屏幕实际尺寸<BR>
     * 忽略导航栏显示隐藏
     *
     * @return 屏幕实际尺寸
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static DisplayMetrics getRealDisplayMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = XUtil.getSystemService(Context.WINDOW_SERVICE, WindowManager.class);
        if (wm != null) {
            wm.getDefaultDisplay().getRealMetrics(dm);
        }
        return dm;
    }

    /**
     * 获取屏幕的尺寸
     *
     * @return 屏幕的尺寸
     */
    private static Point getDisplaySize() {
        WindowManager wm = XUtil.getSystemService(Context.WINDOW_SERVICE, WindowManager.class);
        Point size = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getSize(size);
        }
        return size;
    }

    /**
     * 获取显示的实际大小，而不减去应用任何兼容性比例因子的任何窗口装饰。
     *
     * @return 屏幕的实际尺寸
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static Point getDisplayRealSize() {
        WindowManager wm = XUtil.getSystemService(Context.WINDOW_SERVICE, WindowManager.class);
        Point size = new Point();
        if (wm != null) {
            wm.getDefaultDisplay().getRealSize(size);
        }
        return size;
    }

    /**
     * 计算出设备的物理尺寸
     *
     * @param context
     * @return
     */
    public static double getDevicePhysicalSize(Context context) {
        int densityDpi = context.getResources().getDisplayMetrics().densityDpi;
        float xdpi = context.getResources().getDisplayMetrics().xdpi;
        float ydpi = context.getResources().getDisplayMetrics().ydpi;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        double size = Math.sqrt(Math.pow(width / xdpi, 2) + Math.pow(height / ydpi, 2));
        Logger.d("计算出来的物理尺寸:" + size + " , [width:" + width + ", height:" + height + ", xdpi:" + xdpi + ", ydpi:" + ydpi + ", densityDpi:" + densityDpi + "]");
        return size;
    }

    /**
     * 计算出设备屏幕密度
     *
     * @param context
     * @param inch    设备的物理尺寸(英寸)
     * @return
     */
    public static double getDensityDpi(Context context, float inch) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        double densityDpi = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / inch;
        Logger.d("计算出来的屏幕密度:" + densityDpi + " , [width:" + width + ", height:" + height + "]");
        return densityDpi;
    }


    /**
     * 获取屏幕密度
     *
     * @return 屏幕密度
     */
    public static float getScreenDensity() {
        return ResUtils.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取屏幕密度 DPI
     *
     * @return 屏幕密度 DPI
     */
    public static int getScreenDensityDpi() {
        return ResUtils.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 设置屏幕为全屏
     *
     * @param activity activity
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Set non full screen.
     *
     * @param activity The activity.
     */
    public static void setNonFullScreen(@NonNull final Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * Toggle full screen.
     *
     * @param activity The activity.
     */
    public static void toggleFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & fullScreenFlag) == fullScreenFlag) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * Return whether screen is full.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在 Activity 中加属性 android:screenOrientation="landscape"</p>
     * <p>不设置 Activity 的 android:configChanges 时，
     * 切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置 Activity 的 android:configChanges="orientation"时，
     * 切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置 Activity 的 android:configChanges="orientation|keyboardHidden|screenSize"
     * （4.0 以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行 onConfigurationChanged 方法</p>
     *
     * @param activity activity
     */
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape() {
        return ResUtils.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait() {
        return ResUtils.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * 截屏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     * 截屏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bmp = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret;
        if (isDeleteStatusBar) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
            int statusBarHeight = resources.getDimensionPixelSize(resourceId);
            ret = Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
        decorView.destroyDrawingCache();
        return ret;
    }

    /**
     * 判断是否锁屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock() {
        KeyguardManager km =
                (KeyguardManager) XUtil.getContext().getSystemService(Context.KEYGUARD_SERVICE);
        return km != null && km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置进入休眠时长
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration 时长
     */
    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                XUtil.getContext().getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回-1
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    XUtil.getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断是否是平板
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isTablet() {
        return (ResUtils.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //=======================屏幕适配==========================//

    /**
     * 屏幕适配
     *
     * @param activity
     * @param designDp 竖屏下，适配屏幕的宽dp
     */
    public static void adaptScreen(final Activity activity,
                                   final int designDp) {
        if (ScreenUtils.isPortrait()) { //竖屏
            ScreenUtils.adaptScreen4VerticalSlide(activity, designDp);
        } else {
            ScreenUtils.adaptScreen4HorizontalSlide(activity, designDp);
        }
    }


    /**
     * Adapt the screen for vertical slide.
     *
     * @param designWidthInDp The size of design diagram's width, in dp,
     *                        e.g. the design diagram width is 720px, in XHDPI device,
     *                        the designWidthInDp = 720 / 2.
     */
    public static void adaptScreen4VerticalSlide(final Activity activity,
                                                 final int designWidthInDp) {
        adaptScreen(activity, designWidthInDp, true);
    }

    /**
     * Adapt the screen for horizontal slide.
     *
     * @param designHeightInDp The size of design diagram's height, in dp,
     *                         e.g. the design diagram height is 1080px, in XXHDPI device,
     *                         the designHeightInDp = 1080 / 3.
     */
    public static void adaptScreen4HorizontalSlide(final Activity activity,
                                                   final int designHeightInDp) {
        adaptScreen(activity, designHeightInDp, false);
    }

    /**
     * Cancel adapt the screen.
     *
     * @param activity The activity.
     */
    public static void cancelAdaptScreen(final Activity activity) {
        final DisplayMetrics appDm = XUtil.getContext().getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = appDm.density;
        activityDm.scaledDensity = appDm.scaledDensity;
        activityDm.densityDpi = appDm.densityDpi;
    }

    /**
     * Reference from: https://mp.weixin.qq.com/s/d9QCoBP6kV9VSWvVldVVwA
     */
    private static void adaptScreen(final Activity activity,
                                    final float sizeInDp,
                                    final boolean isVerticalSlide) {
        final DisplayMetrics appDm = ResUtils.getResources().getDisplayMetrics();
        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        if (isVerticalSlide) {
            activityDm.density = activityDm.widthPixels / sizeInDp;
        } else {
            activityDm.density = activityDm.heightPixels / sizeInDp;
        }
        activityDm.scaledDensity = activityDm.density * (appDm.scaledDensity / appDm.density);
        activityDm.densityDpi = (int) (160 * activityDm.density);
    }
}
