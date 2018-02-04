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
package com.xuexiang.xutil.tip;

import android.widget.Toast;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.resource.ResUtils;

/**
 * 管理toast的类，整个app用该类来显示toast
 * @author xuexiang
 * @date 2018/2/4 下午3:52
 */
public class ToastUtil {

    private static ToastUtil sInstance = null;
    private Toast mToast = null;

    private ToastUtil() {

    }

    public synchronized static ToastUtil get() {
        if (sInstance == null) {
            synchronized (ToastUtil.class) {
                if (sInstance == null) {
                    sInstance = new ToastUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 显示toast在主线程中
     * @param text 提示信息
     * @param duration 提示长度
     */
    public void toast(final String text, final int duration) {
        XUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(text, duration);
            }
        });
    }

    /**
     *  显示toast在主线程中
     * @param text
     */
    public void toast(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast在主线程中
     * @param resId
     */
    public void toast(int resId) {
        toast(ResUtils.getString(resId));
    }

    /**
     * 显示单一的toast
     * @param text 提示信息
     * @param duration 提示长度
     */
    public void showToast(String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(XUtil.getContext(), text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    public void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(int resId) {
        showToast(ResUtils.getString(resId), Toast.LENGTH_SHORT);
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
