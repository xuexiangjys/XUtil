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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

    //==========判空==========//

    /**
     * 集合是否不为空
     *
     * @param collection 集合
     * @return true: 不为空，false：为空
     */
    public static <E> boolean isNotEmpty(final Collection<E> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 集合是否为空
     *
     * @param collection 集合
     * @return true: 为空，false：不为空
     */
    public static <E> boolean isEmpty(final Collection<E> collection) {
        return collection == null || collection.isEmpty();
    }

    //==========常用方法==========//

    /**
     * 获取集合的大小
     *
     * @param collection 集合
     * @return 集合的大小
     */
    public static <E> int getSize(final Collection<E> collection) {
        return collection != null ? collection.size() : 0;
    }

    /**
     * 集合的索引是否有效
     *
     * @param collection 集合
     * @param index      索引
     * @return true: 有效，false：无效
     */
    public static <E> boolean isIndexValid(final Collection<E> collection, final int index) {
        return collection != null && index >= 0 && index < collection.size();
    }

    /**
     * 获取集合指定索引的元素
     *
     * @param list  集合
     * @param index 索引
     * @param <E>
     * @return 集合指定索引的元素
     */
    @Nullable
    public static <E> E getListItem(final List<E> list, final int index) {
        return isIndexValid(list, index) ? list.get(index) : null;
    }

    /**
     * 向集合设置指定索引的元素
     *
     * @param list  集合
     * @param index 索引
     * @param e     设置的元素
     * @param <E>
     * @return 之前位于该指定位置的元素
     */
    @Nullable
    public static <E> E setListItem(final List<E> list, final int index, final E e) {
        return (isIndexValid(list, index) && e != null) ? list.set(index, e) : null;
    }

    /**
     * 向集合指定索引位置增加元素
     *
     * @param list  集合
     * @param index 索引
     * @param e     增加的元素
     * @param <E>
     */
    public static <E> void addListItem(final List<E> list, final int index, final E e) {
        if (e != null && list != null && index >= 0 && index <= list.size()) {
            list.add(index, e);
        }
    }


    /**
     * 向集合指定索引位置增加元素
     *
     * @param list 集合
     * @param e    增加的元素
     * @param <E>
     */
    public static <E> void addListItem(final List<E> list, final E e) {
        if (list != null && e != null) {
            list.add(e);
        }
    }

    /**
     * 删除集合中指定索引位置的元素
     *
     * @param list  集合
     * @param index 索引
     * @param <E>
     * @return 之前位于该指定位置的元素
     */
    @Nullable
    public static <E> E removeListItem(final List<E> list, final int index) {
        return isIndexValid(list, index) ? list.remove(index) : null;
    }

    /**
     * 清空集合
     *
     * @param collection 集合
     * @param <E>
     */
    public static <E> void clear(final Collection<E> collection) {
        if (isNotEmpty(collection)) {
            collection.clear();
        }
    }

    //==========排序==========//

    /**
     * 对List集合进行排序
     *
     * @param list 需要排序的list集合
     */
    public static <T extends Comparable<? super T>> List<T> sort(@NonNull final List<T> list) {
        Collections.sort(list);
        return list;
    }

    /**
     * 对List集合进行排序
     *
     * @param list       需要排序的list集合
     * @param comparator 比较器
     */
    public static <T extends Comparable<? super T>> List<T> sort(@NonNull final List<T> list, Comparator<? super T> comparator) {
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * 对数组进行排序
     *
     * @param array 需要排序的数组
     */
    public static <T extends Comparable<? super T>> T[] sort(@NonNull final T[] array) {
        Arrays.sort(array);
        return array;
    }

    /**
     * 对数组进行排序
     *
     * @param array      需要排序的数组
     * @param comparator 比较器
     */
    public static <T extends Comparable<? super T>> T[] sort(@NonNull final T[] array, Comparator<? super T> comparator) {
        Arrays.sort(array, comparator);
        return array;
    }

    /**
     * 反转集合
     *
     * @param list 需要反转的集合
     * @return 反转后的集合
     */
    public static <E> List<E> reverse(final List<E> list) {
        if (isNotEmpty(list)) {
            Collections.reverse(list);
        }
        return list;
    }

    /**
     * 交换集合中两个指定位置的元素
     *
     * @param list   集合
     * @param index1 索引1
     * @param index2 索引2
     * @param <E>
     */
    public static <E> void swap(final List<E> list, int index1, int index2) {
        if (index1 != index2 && isIndexValid(list, index1) && isIndexValid(list, index2)) {
            Collections.swap(list, index1, index2);
        }
    }

    /**
     * 集合拷贝
     *
     * @param dest 目标集合
     * @param src  被拷贝的源集合
     * @param <T>
     */
    public static <T> void copy(List<? super T> dest, List<? extends T> src) {
        if (dest != null && src != null && dest.size() >= src.size()) {
            Collections.copy(dest, src);
        }
    }

    //=========去除重复项===========//

    /**
     * 去除集合里面重复的项
     *
     * @param collection 需要去除重复的集合
     * @return 无重复项的集合
     */
    @NonNull
    public static <E> List<E> makeListUnique(@NonNull final Collection<E> collection) {
        return new ArrayList<>(new HashSet<>(collection));
    }

    /**
     * 去除集合里面重复的项,顺序不变
     *
     * @param collection 需要去除重复的集合
     * @return 无重复项的集合
     */
    @NonNull
    public static <E> List<E> makeListUniqueLinked(@NonNull final Collection<E> collection) {
        return new ArrayList<>(new LinkedHashSet<>(collection));
    }

    //==========搜索条目索引位置==========//

    /**
     * 搜索条目在集合数组中的索引位置
     *
     * @param array  搜索的数组
     * @param search 搜索的内容
     * @return 索引位置 or `-1`
     */
    public static <E> int arrayIndexOf(@NonNull final E[] array, @NonNull final E search) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null && array[i].equals(search)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 搜索条目在集合中的索引位置
     *
     * @param collection 搜索的集合
     * @param search     搜索的内容
     * @return 索引位置 or `-1`
     */
    public static <E> int arrayIndexOf(@NonNull final Collection<E> collection, @NonNull final E search) {
        return arrayIndexOf(collection.toArray(), search);
    }

    /**
     * 使用二分法进行搜索
     *
     * @param list       搜索的数组
     * @param search     搜索的内容
     * @param comparator 比较器
     * @return 索引位置 or `-1`
     */
    public static <E> int binarySearch(final List<? extends E> list, final E search, Comparator<? super E> comparator) {
        return (list != null && search != null) ? Collections.binarySearch(list, search, comparator) : -1;
    }

    /**
     * 搜索条目在集合中的索引位置
     *
     * @param list   搜索的集合
     * @param search 搜索的内容
     * @return 索引位置 or `-1`
     */
    public static <E> int indexOf(final List<E> list, final E search) {
        return list != null ? list.indexOf(search) : -1;
    }

    /**
     * 搜索条目在集合中最后匹配的索引位置
     *
     * @param list   搜索的集合
     * @param search 搜索的内容
     * @return 索引位置 or `-1`
     */
    public static <E> int lastIndexOf(final List<E> list, final E search) {
        return list != null ? list.lastIndexOf(search) : -1;
    }

    //=========拼接集合===========//

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

    //=========删除、修改条目===========//

    /**
     * 对象锁
     */
    private static final Object sLock = new Object();

    /**
     * 删除第一个满足条件的条目
     *
     * @param collection 集合
     * @param e          删除项
     * @param <E>
     */
    public static <E> boolean deleteItem(@NonNull final Collection<E> collection, @NonNull final E e) {
        Iterator<E> it = collection.iterator();
        synchronized (sLock) {
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
     * @param collection 集合
     * @param e          删除项
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
     * @param collection 集合
     * @param listener   遍历监听
     * @param <E>
     */
    public static <E> void modifyCollection(@NonNull final Collection<E> collection, @NonNull final OnModifyCollectionListener<E> listener) {
        Iterator<E> it = collection.iterator();
        synchronized (sLock) {
            while (it.hasNext()) {
                E e = it.next();
                listener.onModifyCollection(it, e);
            }
        }
    }

    /**
     * 获取非空元素的list集合
     *
     * @param list 集合
     * @param <T>
     * @return 非空元素的list集合
     */
    @NonNull
    public static <T> List<T> getNonNullList(List<T> list) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        } else {
            List<T> res = new ArrayList<>(list);
            List<T> toRemoved = new ArrayList<>(list.size());

            for (T t : list) {
                if (null == t) {
                    toRemoved.add(null);
                }
            }
            res.removeAll(toRemoved);
            return res;
        }
    }


    /**
     * 获取集合的子集合
     *
     * @param source 源集合
     * @param start  开始索引
     * @param end    结束索引
     * @param <T>
     * @return 集合的子集合
     */
    public static <T> List<T> getSubList(List<T> source, int start, int end) {
        return source != null && start >= 0 && end >= start && end <= source.size() ? source.subList(start, end) : null;
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
         * @param it   集合迭代器
         * @param item 遍历元素
         */
        void onModifyCollection(@NonNull Iterator<E> it, E item);
    }

}
