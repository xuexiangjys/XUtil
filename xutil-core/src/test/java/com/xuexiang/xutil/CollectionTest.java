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

package com.xuexiang.xutil;

import com.xuexiang.xutil.common.CollectionUtils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author xuexiang
 * @since 2018/6/28 上午10:29
 */
public class CollectionTest {
    @Test
    public void mainTest() throws Exception {
        assertEquals(4, 2 + 2);

        List<String> list = new ArrayList<>();
        list.add("12");
        list.add("22");
        list.add("21");
        list.add("12");
        list.add("1234");

        System.out.println(list);

        CollectionUtils.sort(list);

        System.out.println("排序后的list：" + list);

        CollectionUtils.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o1) - Integer.parseInt(o2);
            }
        });

        System.out.println("排序后的list：" + list);

        List<String> uniqueList = CollectionUtils.makeListUnique(list);

        System.out.println(uniqueList);

        List<String> uniqueLinkedList = CollectionUtils.makeListUniqueLinked(list);

        System.out.println(uniqueLinkedList);

        System.out.println(CollectionUtils.arrayIndexOf(list, "21"));

        System.out.println(CollectionUtils.concatSpiltWith(list, "!"));

        CollectionUtils.deleteItem(list, "1234");

        System.out.println(list);

        CollectionUtils.deleteItems(list, "12");

        System.out.println(list);
    }
}
