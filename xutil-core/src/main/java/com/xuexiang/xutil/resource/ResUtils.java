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
package com.xuexiang.xutil.resource;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.AnimRes;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleableRes;
import androidx.appcompat.content.res.AppCompatResources;

import com.xuexiang.xutil.XUtil;

/**
 * <pre>
 *     desc   : 获取res中的资源
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:54
 * </pre>
 */
public final class ResUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private ResUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取resources对象
     *
     * @return
     */
    public static Resources getResources() {
        return XUtil.getContext().getResources();
    }

    /**
     * 获取字符串
     *
     * @param resId
     * @return
     */
    public static String getString(@StringRes int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取资源图片
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(@DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return XUtil.getContext().getDrawable(resId);
        }
        return getResources().getDrawable(resId);
    }

    /**
     * 获取资源图片【和主体有关】
     *
     * @param resId
     * @return
     */
    public static Drawable getDrawable(Context context, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(resId);
        }
        return context.getResources().getDrawable(resId);
    }

    /**
     * 获取svg资源图片
     * @param context
     * @param resId
     * @return
     */
    public static Drawable getVectorDrawable(Context context, @DrawableRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(resId);
        }
        return AppCompatResources.getDrawable(context, resId);
    }

    /**
     * 获取Drawable属性（兼容VectorDrawable）
     * @param context
     * @param typedArray
     * @param index
     * @return
     */
    public static Drawable getDrawableAttrRes(Context context, TypedArray typedArray, @StyleableRes int index) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return typedArray.getDrawable(index);
        } else {
            int resourceId = typedArray.getResourceId(index, -1);
            if (resourceId != -1) {
                return AppCompatResources.getDrawable(context, resourceId);
            }
        }
        return null;
    }

    /**
     * 获取dimes值，返回的是精确的值
     *
     * @param resId
     * @return
     */
    public static float getDimens(@DimenRes int resId) {
        return getResources().getDimension(resId);
    }

    /**
     * 获取Color值
     *
     * @param resId
     * @return
     */
    public static int getColor(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取ColorStateList值
     *
     * @param resId
     * @return
     */
    public static ColorStateList getColors(@ColorRes int resId) {
        return getResources().getColorStateList(resId);
    }

    /**
     * 获取dimes值，返回的是【去余取整】的值
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelOffset(@DimenRes int resId) {
        return getResources().getDimensionPixelOffset(resId);
    }

    /**
     * 获取dimes值，返回的是【4舍5入】的值
     *
     * @param resId
     * @return
     */
    public static int getDimensionPixelSize(@DimenRes int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取字符串的数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(@ArrayRes int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取数字的数组
     *
     * @param resId
     * @return
     */
    public static int[] getIntArray(@ArrayRes int resId) {
        return getResources().getIntArray(resId);
    }

    /**
     * 获取动画
     *
     * @param resId
     * @return
     */
    public static Animation getAnim(@AnimRes int resId) {
        return AnimationUtils.loadAnimation(XUtil.getContext(), resId);
    }

    /**
     * 设置控件的背景
     * @param view
     * @param d
     */
    public static void setBackgroundCompat(View view, Drawable d) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            view.setBackgroundDrawable(d);
        } else {
            view.setBackground(d);
        }
    }

    /**
     * Check if layout direction is RTL
     *
     * @return {@code true} if the layout direction is right-to-left
     */
    public static boolean isRtl() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
                getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    /**
     * 是否在数组资源中
     * @param find
     * @param ary
     * @param <T>
     * @return
     */
    public static <T> boolean isIn(@NonNull T find, @Nullable T[] ary) {
        if (ary == null || ary.length == 0) {
            return false;
        }
        for (T item : ary) {
            if (item.equals(find)) {
                return true;
            }
        }
        return false;
    }

}
