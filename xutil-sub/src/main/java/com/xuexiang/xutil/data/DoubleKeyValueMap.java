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
package com.xuexiang.xutil.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 双键值对
 *
 * @author xuexiang
 * @since 2018/6/27 下午5:12
 */
public class DoubleKeyValueMap<K1, K2, V> {

    private final ConcurrentHashMap<K1, ConcurrentHashMap<K2, V>> m_k1_k2V_map;

    public DoubleKeyValueMap() {
        m_k1_k2V_map = new ConcurrentHashMap<>();
    }

    /**
     * 存放数据
     *
     * @param key1
     * @param key2
     * @param value
     */
    public void put(K1 key1, K2 key2, V value) {
        if (key1 == null || key2 == null || value == null) {
            return;
        }
        if (m_k1_k2V_map.containsKey(key1)) {
            ConcurrentHashMap<K2, V> k2V_map = m_k1_k2V_map.get(key1);
            if (k2V_map != null) {
                k2V_map.put(key2, value);
            } else {
                k2V_map = new ConcurrentHashMap<>();
                k2V_map.put(key2, value);
                m_k1_k2V_map.put(key1, k2V_map);
            }
        } else {
            ConcurrentHashMap<K2, V> k2V_map = new ConcurrentHashMap<>();
            k2V_map.put(key2, value);
            m_k1_k2V_map.put(key1, k2V_map);
        }
    }

    /**
     * 获取所有第一个键值的集合
     *
     * @return
     */
    public Set<K1> getFirstKeys() {
        return m_k1_k2V_map.keySet();
    }

    public ConcurrentHashMap<K2, V> get(K1 key1) {
        return m_k1_k2V_map.get(key1);
    }

    public V get(K1 key1, K2 key2) {
        ConcurrentHashMap<K2, V> k2_v = m_k1_k2V_map.get(key1);
        return k2_v == null ? null : k2_v.get(key2);
    }

    /**
     * 获取第一个键值为key1的所有value值
     *
     * @return
     */
    public Collection<V> getAllValues(K1 key1) {
        ConcurrentHashMap<K2, V> k2_v = m_k1_k2V_map.get(key1);
        return k2_v == null ? null : k2_v.values();
    }

    /**
     * 获取所有value值
     *
     * @return
     */
    public Collection<V> getAllValues() {
        Collection<V> result;
        Set<K1> k1Set = m_k1_k2V_map.keySet();
        result = new ArrayList<>();
        for (K1 k1 : k1Set) {
            Collection<V> values = m_k1_k2V_map.get(k1).values();
            result.addAll(values);
        }
        return result;
    }

    public boolean containsKey(K1 key1, K2 key2) {
        return m_k1_k2V_map.containsKey(key1) && m_k1_k2V_map.get(key1).containsKey(key2);
    }

    public boolean containsKey(K1 key1) {
        return m_k1_k2V_map.containsKey(key1);
    }

    /**
     * 所有Value的大小
     *
     * @return
     */
    public int size() {
        if (m_k1_k2V_map.size() == 0) {
            return 0;
        }

        int result = 0;
        for (ConcurrentHashMap<K2, V> k2V_map : m_k1_k2V_map.values()) {
            result += k2V_map.size();
        }
        return result;
    }

    /**
     * 清除第一个键值为key1的所有内容
     *
     * @param key1
     */
    public void remove(K1 key1) {
        m_k1_k2V_map.remove(key1);
    }

    /**
     * 清除第一个键值为key1，第二个键值为key2的内容
     *
     * @param key1
     * @param key2
     */
    public void remove(K1 key1, K2 key2) {
        ConcurrentHashMap<K2, V> k2_v = m_k1_k2V_map.get(key1);
        if (k2_v != null) {
            k2_v.remove(key2);
            if (k2_v.isEmpty()) {
                remove(key1);
            }
        }
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        if (m_k1_k2V_map.size() > 0) {
            for (ConcurrentHashMap<K2, V> k2V_map : m_k1_k2V_map.values()) {
                k2V_map.clear();
            }
            m_k1_k2V_map.clear();
        }
    }
}
