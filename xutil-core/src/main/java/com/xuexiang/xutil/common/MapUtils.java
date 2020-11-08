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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * map相关工具类
 *
 * @author xuexiang
 * @since 2018/6/26 下午11:15
 */
public final class MapUtils {

    private static final Object LOCK = new Object();

    private MapUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取map所有的key
     *
     * @param map map集合
     * @return map所有的key
     */
    public static <T> String[] mapKeyToArray(@NonNull Map<String, T> map) {
        try {
            String[] array = new String[map.size()];
            Set<String> set = map.keySet();
            return set.toArray(array);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    /**
     * 根据map中的key获取value值: key -> value
     *
     * @param map map集合
     * @param key 键
     * @return 值
     */
    @Nullable
    public static <K, V> V getMapValueByKey(@NonNull Map<K, V> map, @NonNull K key) {
        return map.containsKey(key) ? map.get(key) : null;
    }

    /**
     * 根据map中的value获取对应的key: value -> key
     *
     * @param map   map集合
     * @param value 值
     * @return key键
     */
    @Nullable
    public static <K, V> K getMapKeyByValue(@NonNull Map<K, V> map, @NonNull V value) {
        for (K item : map.keySet()) {
            if (value.equals(map.get(item))) {
                return item;
            }
        }
        return null;
    }


    /**
     * 获取map中第一个value
     *
     * @param map 数据源
     * @return map中第一个非空value
     */
    @Nullable
    public static <K, V> V getFirstValue(LinkedHashMap<K, V> map) {
        for (V item : map.values()) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    /**
     * 获取map中第一个key
     *
     * @param map 数据源
     * @return map中第一个非空key
     */
    @Nullable
    public static <K, V> K getFirstKey(LinkedHashMap<K, V> map) {
        for (K item : map.keySet()) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    /**
     * 修改map集合
     *
     * @param map      数据源
     * @param listener 遍历修改map的监听
     * @param <K>
     * @param <V>
     */
    public static <K, V> void modifyMap(@NonNull Map<K, V> map, @NonNull OnModifyMapListener<K, V> listener) {
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        synchronized (LOCK) {
            while (it.hasNext()) {
                Map.Entry<K, V> entry = it.next();
                listener.onModifyMap(it, entry);
            }
        }
    }

    /**
     * 遍历修改map的监听
     *
     * @param <K>
     * @param <V>
     */
    public interface OnModifyMapListener<K, V> {
        /**
         * 修改map
         *
         * @param it    迭代器
         * @param entry 迭代元素
         */
        void onModifyMap(@NonNull Iterator<Map.Entry<K, V>> it, @NonNull Map.Entry<K, V> entry);
    }

}
