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

import org.junit.Test;

import static com.xuexiang.xutil.data.ArrayUtils.indexOf;
import static com.xuexiang.xutil.data.ArrayUtils.lastIndexOf;
import static org.junit.Assert.assertEquals;

/**
 * @author xuexiang
 * @since 2018/6/27 下午6:24
 */
public class ArrayUtilsTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        byte[] org = "kadeadedkkcfdededghkk".getBytes();
        byte[] search = "kk".getBytes();
        int firstIndex = indexOf(org, search);
        int lastIndex = lastIndexOf(org, search);

        System.out.println("firstIndex=" + firstIndex + ", lastIndex=" + lastIndex);

        long t1 = 0;
        long t2 = 0;
        int f1 = 0;
        int f2 = 0;
        for (int i = 0; i < 10000; i++) {
            long s1 = System.nanoTime();
            f1 = indexOf(org, search, 0);
            long s2 = System.nanoTime();
            f2 = indexOf(org, search);
            long s3 = System.nanoTime();
            t1 = t1 + (s2 - s1);
            t2 = t2 + (s3 - s2);
        }
        System.out.println("kmp=" + t1 / 10000 + ",ali=" + t2 / 10000);
        System.out.println("f1=" + f1 + ",f2=" + f2);
    }
}
