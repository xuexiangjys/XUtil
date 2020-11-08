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

import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;

import com.xuexiang.xutil.resource.ResUtils;

import java.util.Random;

/**
 * <pre>
 *     desc   : 颜色工具
 *     author : xuexiang
 *     time   : 2018/4/30 下午12:23
 * </pre>
 */
public final class ColorUtils {

    private static final int ENABLE_ATTR = android.R.attr.state_enabled;
    private static final int CHECKED_ATTR = android.R.attr.state_checked;
    private static final int PRESSED_ATTR = android.R.attr.state_pressed;
    private static final int FOCUSED_ATTR = android.R.attr.state_focused;

    /**
     * Don't let anyone instantiate this class.
     */
    private ColorUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 矫正颜色的透明度
     *
     * @param color
     * @param factor
     * @return
     */
    @ColorInt
    public static int adjustAlpha(@ColorInt int color, @SuppressWarnings("SameParameterValue") float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 设置颜色的alpha值
     *
     * @param color
     * @param alpha 取值为[0,1]，0表示全透明，1表示不透明
     * @return
     */
    public static int setColorAlpha(@ColorInt int color, float alpha) {
        return color & 0x00ffffff | (int) (alpha * 255) << 24;
    }

    /**
     * 将 color 颜色值转换为十六进制字符串
     *
     * @param color 颜色值
     * @return 转换后的字符串
     */
    public static String colorToString(@ColorInt int color) {
        return String.format("#%08X", color);
    }

    /**
     * 加深颜色
     *
     * @param color 需要加深的颜色
     */
    public static int darker(int color) {
        return darker(color, 0.8F);
    }

    /**
     * 加深颜色
     *
     * @param color  需要加深的颜色
     * @param factor The factor to darken the color.
     * @return darker version of specified color.
     */
    public static int darker(int color, float factor) {
        return Color.argb(Color.alpha(color), Math.max((int) (Color.red(color) * factor), 0),
                Math.max((int) (Color.green(color) * factor), 0),
                Math.max((int) (Color.blue(color) * factor), 0));
    }

    /**
     * 变浅颜色
     *
     * @param color  需要变浅的颜色
     * @param factor The factor to lighten the color. 0 will make the color unchanged. 1 will make the
     *               color white.
     * @return lighter version of the specified color.
     */
    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    /**
     * 是否是深色的颜色
     *
     * @param color
     * @return
     */
    public static boolean isColorDark(@ColorInt int color) {
        double darkness =
                1
                        - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color))
                        / 255;
        return darkness >= 0.5;
    }

    /**
     * 颜色选择器
     *
     * @param pressedColor 按下的颜色
     * @param normalColor  正常的颜色
     * @return 颜色选择器
     */
    public static ColorStateList getColorStateList(int pressedColor, int normalColor) {
        //其他状态默认为白色
        return new ColorStateList(
                new int[][]{{android.R.attr.state_enabled,
                        android.R.attr.state_pressed}, {android.R.attr.state_enabled}, {}},
                new int[]{pressedColor, normalColor, Color.WHITE});
    }

    public static ColorStateList generateThumbColorWithTintColor(final int tintColor) {
        int[][] states = new int[][]{
                {-ENABLE_ATTR, CHECKED_ATTR},
                {-ENABLE_ATTR},
                {PRESSED_ATTR, -CHECKED_ATTR},
                {PRESSED_ATTR, CHECKED_ATTR},
                {CHECKED_ATTR},
                {-CHECKED_ATTR}
        };

        int[] colors = new int[]{
                tintColor - 0xAA000000,
                0xFFBABABA,
                tintColor - 0x99000000,
                tintColor - 0x99000000,
                tintColor | 0xFF000000,
                0xFFEEEEEE
        };
        return new ColorStateList(states, colors);
    }

    public static ColorStateList generateBackColorWithTintColor(final int tintColor) {
        int[][] states = new int[][]{
                {-ENABLE_ATTR, CHECKED_ATTR},
                {-ENABLE_ATTR},
                {CHECKED_ATTR, PRESSED_ATTR},
                {-CHECKED_ATTR, PRESSED_ATTR},
                {CHECKED_ATTR},
                {-CHECKED_ATTR}
        };

        int[] colors = new int[]{
                tintColor - 0xE1000000,
                0x10000000,
                tintColor - 0xD0000000,
                0x20000000,
                tintColor - 0xD0000000,
                0x20000000
        };
        return new ColorStateList(states, colors);
    }

    public static int getColorForState(@NonNull ColorStateList colorStateList, int[] stateSet, int defaultColor) {
        return colorStateList.getColorForState(stateSet, defaultColor);
    }

    public static int getColorForState(@NonNull ColorStateList colorStateList, int state, int defaultColor) {
        return colorStateList.getColorForState(new int[]{state}, defaultColor);
    }

    /**
     * 获取某状态颜色
     *
     * @param colorStateList
     * @param state          颜色状态
     * @return
     */
    public static int getStateColor(@NonNull ColorStateList colorStateList, int state) {
        return colorStateList.getColorForState(new int[]{state}, -1);
    }

    /**
     * 获取可点击时的颜色
     *
     * @param colorResId
     * @return
     */
    public static int getEnableColor(@ColorRes int colorResId) {
        return ResUtils.getColors(colorResId).getColorForState(new int[]{ENABLE_ATTR}, -1);
    }

    /**
     * 获取不可点击时的颜色
     *
     * @param colorResId
     * @return
     */
    public static int getDisableColor(@ColorRes int colorResId) {
        return ResUtils.getColors(colorResId).getColorForState(new int[]{-ENABLE_ATTR}, -1);
    }

    /**
     * 获取默认的颜色
     *
     * @param colorResId 颜色资源id
     * @return
     */
    public static int getDefaultColor(@ColorRes int colorResId) {
        return ResUtils.getColors(colorResId).getDefaultColor();
    }

    /**
     * 按条件的到随机颜色
     *
     * @param alpha 透明
     * @param lower 下边界
     * @param upper 上边界
     * @return 颜色值
     */

    public static int getRandomColor(int alpha, int lower, int upper) {
        return new RandomColor(alpha, lower, upper).getColor();
    }

    /**
     * @return 获取随机色
     */
    public static int getRandomColor() {
        return new RandomColor(255, 0, 255).getColor();
    }

    /**
     * 随机颜色
     */
    public static class RandomColor {
        int alpha;
        int lower;
        int upper;

        RandomColor(int alpha, int lower, int upper) {
            if (upper <= lower) {
                throw new IllegalArgumentException("must be lower < upper");
            }
            setAlpha(alpha);
            setLower(lower);
            setUpper(upper);
        }

        public int getColor() {
            //随机数是前闭  后开
            int red = getLower() + new Random().nextInt(getUpper() - getLower() + 1);
            int green = getLower() + new Random().nextInt(getUpper() - getLower() + 1);
            int blue = getLower() + new Random().nextInt(getUpper() - getLower() + 1);
            return Color.argb(getAlpha(), red, green, blue);
        }

        public int getAlpha() {
            return alpha;
        }

        public void setAlpha(int alpha) {
            if (alpha > 255) {
                alpha = 255;
            }
            if (alpha < 0) {
                alpha = 0;
            }
            this.alpha = alpha;
        }

        int getLower() {
            return lower;
        }

        void setLower(int lower) {
            if (lower < 0) {
                lower = 0;
            }
            this.lower = lower;
        }

        int getUpper() {
            return upper;
        }

        void setUpper(int upper) {
            if (upper > 255) {
                upper = 255;
            }
            this.upper = upper;
        }
    }


}
