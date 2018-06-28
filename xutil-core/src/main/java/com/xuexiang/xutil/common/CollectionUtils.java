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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 集合工具类{@link Collection}
 *
 * @author xuexiang
 * @since 2018/6/28 上午1:00
 */
public final class CollectionUtils {

    private CollectionUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 去除List集合里面重复的项
     *
     * @param list 需要去除重复的集合
     * @return 无重复项的集合
     */
    @NonNull
    public static <E> ArrayList<E> makeListUnique(@NonNull final List<E> list) {
        return new ArrayList<E>(new HashSet<E>(list));
    }

    /**
     * 去除List集合里面重复的项,顺序不变
     *
     * @param list 需要去除重复的集合
     * @return 无重复项的集合
     */
    @NonNull
    public static <E> ArrayList<E> makeListUniqueLinked(@NonNull final List<E> list) {
        return new ArrayList<E>(new LinkedHashSet<E>(list));
    }

    /**
     * 搜索条目在集合数组中的索引位置
     *
     * @param array  搜索的数组
     * @param search 搜索的内容
     * @return 索引位置 or `-1`
     */
    public static <E> int arrayIndexOf(@NonNull final E[] array, @NonNull final E search) {
        for (int i = 0; i < array.length; i++) {
            if ((array[i] != null && array[i].equals(search)) || (array[i] == null && search == null)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 搜索条目在集合中的索引位置
     *
     * @param collection 搜索的数组
     * @param search     搜索的内容
     * @return 索引位置 or `-1`
     */
    public static <E> int arrayIndexOf(@NonNull final Collection<E> collection, @NonNull final E search) {
        return arrayIndexOf(collection.toArray(), search);
    }

    /**
     * 拼接集合为String
     *
     * @param collection 集合
     * @param delimiter  分割字符
     * @return 拼接后的字符串
     */
    public static <E> String concatSpiltWith(@NonNull final Collection<E> collection, @NonNull final String delimiter) {
        final StringBuilder out = new StringBuilder();
        int counter = 0;
        for (Object obj : collection) {
            if (obj != null) {
                if (counter > 0) {
                    out.append(delimiter);
                }
                if (obj instanceof String) {
                    out.append(obj);
                } else {
                    out.append(obj.toString());
                }
                counter++;
            }
        }
        return out.toString();
    }

    /**
     * 拼接集合为String
     *
     * @param iterable  集合的迭代器
     * @param delimiter 分割字符
     * @return 拼接后的字符串
     */
    public static String concatSpiltWith(@NonNull final Iterable<?> iterable, @NonNull final String delimiter) {
        final StringBuilder out = new StringBuilder();
        int counter = 0;
        for (Object obj : iterable) {
            if (obj != null) {
                if (counter > 0) {
                    out.append(delimiter);
                }
                if (obj instanceof String) {
                    out.append(obj);
                } else {
                    out.append(obj.toString());
                }
                counter++;
            }
        }
        return out.toString();
    }

    /**
     * 删除第一个满足条件的条目
     *
     * @param collection
     * @param e
     * @param <E>
     */
    public static <E> boolean deleteItem(@NonNull final Collection<E> collection, @NonNull final E e) {
        Iterator<E> it = collection.iterator();
        synchronized (it) {
            while (it.hasNext()) {
                E item = it.next();
                if (e.equals(item)) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除满足条件的所有条目
     *
     * @param collection
     * @param e
     * @param <E>
     */
    public static <E> void deleteItems(@NonNull final Collection<E> collection, @NonNull final E e) {
        modifyCollection(collection, new OnModifyCollectionListener<E>() {
            @Override
            public void onModifyCollection(@NonNull Iterator<E> it, E item) {
                if (e.equals(item)) {
                    it.remove();
                }
            }
        });
    }

    /**
     * 修改Collection集合
     *
     * @param collection
     * @param listener
     * @param <E>
     */
    public static <E> void modifyCollection(@NonNull final Collection<E> collection, @NonNull final OnModifyCollectionListener<E> listener) {
        Iterator<E> it = collection.iterator();
        synchronized (it) {
            while (it.hasNext()) {
                E e = it.next();
                listener.onModifyCollection(it, e);
            }
        }
    }

    /**
     * 遍历修改Collection的监听
     *
     * @param <E>
     */
    public interface OnModifyCollectionListener<E> {
        /**
         * 修改Collection
         *
         * @param it
         * @param item
         */
        void onModifyCollection(@NonNull Iterator<E> it, E item);
    }

}
