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

import android.content.Intent;
import android.net.Uri;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.xuexiang.xutil.system.ClipboardUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ClipboardUtilsTest {
    static {
        XUtil.init(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testText() throws Exception {
        ClipboardUtils.copyText("test");
        assertEquals("test", ClipboardUtils.getText());
    }

    @Test
    public void testUri() throws Exception {
        ClipboardUtils.copyUri(Uri.parse("http://www.blankj.com"));
        System.out.println((ClipboardUtils.getUri()));
    }

    @Test
    public void testIntent() throws Exception {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_DIAL);
        ClipboardUtils.copyIntent(intent);
        System.out.println(ClipboardUtils.getText());
    }
}
