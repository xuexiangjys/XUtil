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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    private MapUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取map所有的key
     *
     * @param map
     * @return
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
     * @param map
     * @param key
     * @return
     */
    @Nullable
    public static <K, V> V getMapValueByKey(@NonNull Map<K, V> map, @NonNull K key) {
        return map.containsKey(key) ? map.get(key) : null;
    }

    /**
     * 根据map中的value获取对应的key: value -> key
     *
     * @param map
     * @param value
     * @return
     */
    @Nullable
    public static <K, V> K getMapKeyByValue(@NonNull Map<K, V> map, @NonNull V value) {
        K key = null;
        for (K item : map.keySet()) {
            if (value.equals(map.get(item))) {
                key = item;
                break;
            }
        }
        return key;
    }


    /**
     * 获取map中第一个value
     *
     * @param map 数据源
     * @return
     */
    @Nullable
    public static <K, V> V getFirstValue(LinkedHashMap<K, V> map) {
        V value = null;
        for (V item : map.values()) {
            if (item != null) {
                value = item;
                break;
            }
        }
        return value;
    }

    /**
     * 获取map中第一个key
     *
     * @param map 数据源
     * @return
     */
    @Nullable
    public static <K, V> K getFirstKey(LinkedHashMap<K, V> map) {
        K key = null;
        for (K item : map.keySet()) {
            if (item != null) {
                key = item;
                break;
            }
        }
        return key;
    }

    /**
     * 修改map集合
     * @param map
     * @param listener
     * @param <K>
     * @param <V>
     */
    public static <K, V> void modifyMap(@NonNull Map<K, V> map, @NonNull OnModifyMapListener<K, V> listener) {
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        synchronized (it) {
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
         * @param it
         * @param entry
         */
        void onModifyMap(@NonNull Iterator<Map.Entry<K, V>> it, @NonNull Map.Entry<K, V> entry);
    }

}
