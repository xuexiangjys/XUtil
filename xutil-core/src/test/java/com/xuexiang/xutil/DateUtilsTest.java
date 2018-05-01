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

import com.xuexiang.constant.DateFormatConstants;
import com.xuexiang.constant.TimeConstants;
import com.xuexiang.xutil.data.DateUtils;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * <pre>
 *     desc   :
 *     author : xuexiang
 *     time   : 2018/4/30 下午12:30
 * </pre>
 */
public class DateUtilsTest {

    @Test
    public void mainTest() throws Exception {
        assertTrue(DateUtils.isDateFormatRight("2018-04-26", DateUtils.yyyyMMdd.get()));


        println(DateUtils.date2String(DateUtils.string2Date("2018-4-26", DateUtils.yyyyMMdd.get()), DateUtils.yyyyMMddHHmmssSSS.get()));

        println(DateUtils.date2String(DateUtils.string2Date("2018-04-26", DateUtils.yyyyMMddNoSep.get()), DateUtils.yyyyMMddHHmmssSSS.get()));

        println("-------------------------------------");

        println(DateUtils.getNowString(DateUtils.yyyyMMdd.get()));
        println(DateUtils.getNowString(DateUtils.yyyyMMddNoSep.get()));
        println(DateUtils.getNowString(DateUtils.HHmm.get()));
        println(DateUtils.getNowString(DateUtils.HHmmss.get()));
        println(DateUtils.getNowString(DateUtils.yyyyMMddHHmm.get()));
        println(DateUtils.getNowString(DateUtils.yyyyMMddHHmmss.get()));
        println(DateUtils.getNowString(DateUtils.yyyyMMddHHmmssNoSep.get()));
        println(DateUtils.getNowString(DateUtils.yyyyMMddHHmmssSSS.get()));

        println("-------------------------------------");

        println(DateUtils.nDaysBeforeToday(1, false));
        println(DateUtils.nDaysAfterToday(1, false));

        println(DateUtils.date2String(DateUtils.nDaysAfterToday(1), DateUtils.yyyyMMddHHmmssSSS.get()));

        println("-------------------------------------");

        long time = DateUtils.string2Date("2018-4-26 11:14:12:298", DateUtils.yyyyMMddHHmmssSSS.get()).getTime();
        println(Long.toString(time));
        println(DateUtils.date2String(DateUtils.millis2Date(time), DateUtils.yyyyMMddHHmmssSSS.get()));

        println("-------------------------------------");

        println(DateUtils.translateDateFormat("2018-4-26 11:14:12:298", DateUtils.yyyyMMddHHmmss.get(), DateUtils.yyyyMMddHHmmssNoSep.get()));
        println(DateUtils.translateDateFormat("2018-4-26 11:14:12:298", DateFormatConstants.yyyyMMddHHmmss, DateFormatConstants.yyyyMMdd));

        println("-------------------------------------");

        println(DateUtils.getFriendlyTimeSpanByNow("2018-4-26 14:59:12:298", DateUtils.yyyyMMddHHmmssSSS.get()));

        System.out.println(DateUtils.getTimeSpanByNow("2018-4-26 14:59:12:298", DateUtils.yyyyMMddHHmmssSSS.get(), TimeConstants.MIN));

        println(DateUtils.getFuzzyTimeDescriptionByNow("2018-4-26 14:59:12:298", DateUtils.yyyyMMddHHmmssSSS.get()));

        println(DateUtils.convertTimeToFileName("2018-4-26 14:59:12:298", ".txt"));

        println(DateUtils.getChineseZodiac("1994-08-06", DateUtils.yyyyMMdd.get()));

        println(DateUtils.getZodiac("1994-08-06", DateUtils.yyyyMMdd.get()));

        println("年龄:" + DateUtils.getAgeByBirthDay("1994-08-06", DateUtils.yyyyMMdd.get()));
    }

    private void println(String s) {
        System.out.println(s);
    }


}
