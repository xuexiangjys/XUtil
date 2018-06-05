package com.xuexiang.xutil;

import com.xuexiang.xutil.data.DateUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        System.out.println(DateUtils.date2String(DateUtils.getNowDate(), DateUtils.yyyyMMddHHmmss.get()));

        System.out.println(DateUtils.date2String(DateUtils.nMonthsAfterToday(2), DateUtils.yyyyMMddHHmmss.get()));

        System.out.println(DateUtils.date2String(DateUtils.nMonthsBeforeToday(2), DateUtils.yyyyMMddHHmmss.get()));
    }
}