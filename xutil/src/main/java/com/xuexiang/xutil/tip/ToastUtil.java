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

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xuexiang.xutil.R;
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

    public static ToastUtil get() {
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
        if (isMainLooper()) {
            showToast(text, duration);
        } else {
            XUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showToast(text, duration);
                }
            });
        }
    }

    /**
     * 是否是主线程
     * @return
     */
    private boolean isMainLooper() {
        return Looper.getMainLooper() == Looper.myLooper();
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
    private void showToast(String text, int duration) {
        if (mToast == null) {
            mToast = makeText(XUtil.getContext(), text, duration);
        } else {
            ((TextView) mToast.getView().findViewById(R.id.tv_info)).setText(text);
        }
        mToast.show();
    }


    /**
     * 构建Toast
     * @param context
     * @param msg
     * @param duration
     * @return
     */
    private Toast makeText(Context context, String msg, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        Toast toast = new Toast(context);
        toast.setView(view);
        TextView tv = (TextView) view.findViewById(R.id.tv_info);
        tv.getBackground().setAlpha(100);
        tv.setText(msg);
        toast.setDuration(duration);
        return toast;
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
