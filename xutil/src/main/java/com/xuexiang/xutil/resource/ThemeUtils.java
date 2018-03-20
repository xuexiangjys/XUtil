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
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.util.TypedValue;

import com.xuexiang.xutil.display.ColorUtils;

/**
 * 主题工具（R.attr.xx)
 *
 * @author xuexiang
 * @date 2018/2/4 下午3:45
 */
public final class ThemeUtils {

    /**
     * 解析主题的Color属性
     *
     * @param context
     * @param attr    属性名
     * @return
     */
    @ColorInt
    public static int resolveColor(Context context, @AttrRes int attr) {
        return resolveColor(context, attr, 0);
    }

    /**
     * 解析主题的Color属性
     *
     * @param context
     * @param attr     属性名
     * @param defValue 默认值
     * @return
     */
    @ColorInt
    public static int resolveColor(Context context, @AttrRes int attr, int defValue) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getColor(0, defValue);
        } finally {
            a.recycle();
        }
    }

    /**
     * 解析主题的Dimension属性
     *
     * @param context
     * @param attr    属性名
     * @return
     */
    public static int resolveDimension(Context context, @AttrRes int attr) {
        return resolveDimension(context, attr, -1);
    }

    /**
     * 解析主题的Dimension属性
     *
     * @param context
     * @param attr     属性名
     * @param defValue 默认值
     * @return
     */
    public static int resolveDimension(Context context, @AttrRes int attr, int defValue) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getDimensionPixelSize(0, defValue);
        } finally {
            a.recycle();
        }
    }

    /**
     * 解析主题的Boolean属性
     *
     * @param context
     * @param attr    属性名
     * @return
     */
    public static boolean resolveBoolean(Context context, @AttrRes int attr) {
        return resolveBoolean(context, attr, false);
    }

    /**
     * 解析主题的Boolean属性
     *
     * @param context
     * @param attr     属性名
     * @param defValue 默认值
     * @return
     */
    public static boolean resolveBoolean(Context context, @AttrRes int attr, boolean defValue) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            return a.getBoolean(0, defValue);
        } finally {
            a.recycle();
        }
    }

    /**
     * 解析主题的Drawable属性
     *
     * @param context
     * @param attr    属性名
     * @return
     */
    public static Drawable resolveDrawable(Context context, @AttrRes int attr) {
        return resolveDrawable(context, attr, null);
    }

    /**
     * 解析主题的Drawable属性
     *
     * @param context
     * @param attr     属性名
     * @param defValue 默认值
     * @return
     */
    private static Drawable resolveDrawable(
            Context context,
            @AttrRes int attr,
            @SuppressWarnings("SameParameterValue") Drawable defValue) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        try {
            Drawable d = a.getDrawable(0);
            if (d == null && defValue != null) {
                d = defValue;
            }
            return d;
        } finally {
            a.recycle();
        }
    }

    /**
     * 解析主题的String属性
     *
     * @param context
     * @param attr
     * @return
     */
    public static String resolveString(Context context, @AttrRes int attr) {
        return resolveString(context.getTheme(), attr);
    }

    /**
     * 解析主题的String属性
     *
     * @param theme
     * @param attr
     * @return
     */
    public static String resolveString(Resources.Theme theme, @AttrRes int attr) {
        TypedValue v = new TypedValue();
        theme.resolveAttribute(attr, v, true);
        return (String) v.string;
    }

    public static int getColorFromAttrRes(Context context, int attrRes, int defaultValue) {
        TypedArray a = context.obtainStyledAttributes(new int[]{attrRes});
        try {
            return a.getColor(0, defaultValue);
        } finally {
            a.recycle();
        }
    }

    public static float getFloatFromAttrRes(Context context, int attrRes, float defaultValue) {
        TypedArray a = context.obtainStyledAttributes(new int[]{attrRes});
        try {
            return a.getFloat(0, defaultValue);
        } finally {
            a.recycle();
        }
    }

    // Try to resolve the colorAttr attribute.
    public static ColorStateList resolveActionTextColorStateList(
            Context context, @AttrRes int colorAttr, ColorStateList defValue) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{colorAttr});
        try {
            final TypedValue value = a.peekValue(0);
            if (value == null) {
                return defValue;
            }
            if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT
                    && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                return getActionTextStateList(context, value.data);
            } else {
                final ColorStateList stateList = a.getColorStateList(0);
                if (stateList != null) {
                    return stateList;
                } else {
                    return defValue;
                }
            }
        } finally {
            a.recycle();
        }
    }

    // Get the specified color resource, creating a ColorStateList if the resource
    // points to a color value.
    public static ColorStateList getActionTextColorStateList(Context context, @ColorRes int colorId) {
        final TypedValue value = new TypedValue();
        context.getResources().getValue(colorId, value, true);
        if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT
                && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return getActionTextStateList(context, value.data);
        } else {

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
                //noinspection deprecation
                return context.getResources().getColorStateList(colorId);
            } else {
                return context.getColorStateList(colorId);
            }
        }
    }

    public static ColorStateList getActionTextStateList(Context context, int newPrimaryColor) {
        final int fallBackButtonColor =
                ThemeUtils.resolveColor(context, android.R.attr.textColorPrimary);
        if (newPrimaryColor == 0) {
            newPrimaryColor = fallBackButtonColor;
        }
        int[][] states =
                new int[][]{
                        new int[]{-android.R.attr.state_enabled}, // disabled
                        new int[]{} // enabled
                };
        int[] colors = new int[]{ColorUtils.adjustAlpha(newPrimaryColor, 0.4f), newPrimaryColor};
        return new ColorStateList(states, colors);
    }

    public static int[] getColorArray(@NonNull Context context, @ArrayRes int array) {
        if (array == 0) {
            return null;
        }
        TypedArray ta = context.getResources().obtainTypedArray(array);
        int[] colors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        return colors;
    }


}
