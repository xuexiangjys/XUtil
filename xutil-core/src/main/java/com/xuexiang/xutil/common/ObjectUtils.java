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

package com.xuexiang.xutil.common;

import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import androidx.annotation.RequiresApi;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * <pre>
 *     desc   : 对象相关工具类
 *     author : xuexiang
 *     time   : 2018/4/28 下午2:52
 * </pre>
 */
public final class ObjectUtils {

    private ObjectUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 返回object是否为空
     *
     * @param obj The object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof CharSequence && obj.toString().length() == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SimpleArrayMap && ((SimpleArrayMap) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        if (obj instanceof LongSparseArray && ((LongSparseArray) obj).size() == 0) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return obj instanceof android.util.LongSparseArray
                    && ((android.util.LongSparseArray) obj).size() == 0;
        }
        return false;
    }

    public static boolean isEmpty(final CharSequence obj) {
        return obj == null || obj.toString().length() == 0;
    }

    public static boolean isEmpty(final Collection obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final Map obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final SimpleArrayMap obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final SparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final SparseBooleanArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final SparseIntArray obj) {
        return obj == null || obj.size() == 0;
    }

    public static boolean isEmpty(final LongSparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isEmpty(final SparseLongArray obj) {
        return obj == null || obj.size() == 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isEmpty(final android.util.LongSparseArray obj) {
        return obj == null || obj.size() == 0;
    }

    /**
     * Return whether object is not empty.
     *
     * @param obj The object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isNotEmpty(final Object obj) {
        return !isEmpty(obj);
    }


    public static boolean isNotEmpty(final CharSequence obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final Collection obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final Map obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SimpleArrayMap obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseBooleanArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final SparseIntArray obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(final LongSparseArray obj) {
        return !isEmpty(obj);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean isNotEmpty(final SparseLongArray obj) {
        return !isEmpty(obj);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isNotEmpty(final android.util.LongSparseArray obj) {
        return !isEmpty(obj);
    }

    /**
     * Return whether object1 is equals to object2.
     *
     * @param o1 The first object.
     * @param o2 The second object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(final Object o1, final Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    /**
     * Require the object is not null.
     *
     * @param object  The object.
     * @param message The message to use with the NullPointerException.
     * @param <T>     The value type.
     * @return the object
     * @throws NullPointerException if object is null
     */
    public static <T> T requireNonNull(final T object, final String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    /**
     * 类型强转
     *
     * @param object 需要强转的对象
     * @param clazz  需要强转的类型
     * @param <T>
     * @return 类型强转结果
     */
    public static <T> T cast(final Object object, Class<T> clazz) {
        return clazz != null && clazz.isInstance(object) ? (T) object : null;
    }

    /**
     * 类型强转
     *
     * @param object       需要强转的对象
     * @param defaultValue 强转的默认值
     * @param <T>
     * @return 类型强转结果
     */
    public static <T> T cast(Object object, T defaultValue) {
        if (defaultValue == null) {
            return null;
        } else if (object == null) {
            return null;
        } else {
            return defaultValue.getClass() == object.getClass() ? (T) object : defaultValue;
        }
    }

    /**
     * Require the objects are not null.
     *
     * @param message The message to use with the NullPointerException.
     * @param objects The object.
     * @throws NullPointerException if any object is null in objects
     */
    public static void requireNonNull(final String message, final Object... objects) {
        if (objects == null) {
            throw new NullPointerException(message);
        }
        for (Object object : objects) {
            if (object == null) {
                throw new NullPointerException(message);
            }
        }
    }


    /**
     * Return the nonnull object or default object.
     *
     * @param object        The object.
     * @param defaultObject The default object to use with the object is null.
     * @param <T>           The value type.
     * @return the nonnull object or default object
     */
    public static <T> T getOrDefault(final T object, final T defaultObject) {
        if (object == null) {
            return defaultObject;
        }
        return object;
    }

    /**
     * Return the hash code of object.
     *
     * @param o The object.
     * @return the hash code of object
     */
    public static int hashCode(final Object o) {
        return o != null ? o.hashCode() : 0;
    }
}
