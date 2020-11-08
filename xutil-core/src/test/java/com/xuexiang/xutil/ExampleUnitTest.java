package com.xuexiang.xutil;

import androidx.annotation.NonNull;

import com.xuexiang.xutil.common.MapUtils;
import com.xuexiang.xutil.data.DateUtils;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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


        Map<String, String> map = new HashMap<>();
        map.put("1", "12345");
        map.put("2", "23456");
        map.put("3", "34567");
        map.put("4", "45678");
        map.put("5", "56789");
        map.put("6", "56789");

        MapUtils.modifyMap(map, new MapUtils.OnModifyMapListener<String, String>() {
            @Override
            public void onModifyMap(@NonNull Iterator<Map.Entry<String, String>> it, @NonNull Map.Entry<String, String> entry) {
                String key = entry.getKey();
                if ("4".equals(key)) {
                    it.remove();
                }
            }
        });

        System.out.println(Arrays.toString(MapUtils.mapKeyToArray(map)));

        System.out.println(MapUtils.getMapKeyByValue(map, "56789"));
    }
}