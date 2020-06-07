/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.xutil.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数学工具类
 *
 * @author xuexiang
 * @since 2020/6/8 12:23 AM
 */
public final class MathUtils {

    private static final float FLOAT_SMALL_ENOUGH_NUM = 1.0E-7F;
    private static final double DOUBLE_SMALL_ENOUGH_NUM = 1.0E-7D;

    private MathUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean isEqual(float f1, float f2) {
        return Math.abs(f1 - f2) < FLOAT_SMALL_ENOUGH_NUM;
    }

    public static boolean isEqual(double f1, double f2) {
        return Math.abs(f1 - f2) < DOUBLE_SMALL_ENOUGH_NUM;
    }

    public static boolean biggerOrEqual(float f1, float f2) {
        return isEqual(f1, f2) || f1 > f2;
    }

    public static boolean biggerOrEqual(double f1, double f2) {
        return isEqual(f1, f2) || f1 > f2;
    }

    public static boolean isNumber(String str) {
        if (!StringUtils.isEmpty(str)) {
            Matcher isNum = Pattern.compile("[0-9]*").matcher(str);
            return isNum.matches();
        } else {
            return false;
        }
    }

    public static int compare(long x, long y) {
        return x < y ? -1 : (x == y ? 0 : 1);
    }

}
