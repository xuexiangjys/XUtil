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
 * <pre>
 *     desc   : 管理toast的类，整个app用该类来显示toast
 *     author : xuexiang
 *     time   : 2018/4/27 下午8:38
 * </pre>
 */
public final class ToastUtils {

    private static Toast sToast = null;

    /**
     * 显示toast在主线程中
     *
     * @param text     提示信息
     * @param duration 提示长度
     */
    public static void toast(final String text, final int duration) {
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
     *
     * @return
     */
    private static boolean isMainLooper() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 显示toast在主线程中
     *
     * @param text
     */
    public static void toast(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示toast在主线程中
     *
     * @param resId
     */
    public static void toast(int resId) {
        toast(ResUtils.getString(resId));
    }

    /**
     * 显示单一的toast
     *
     * @param text     提示信息
     * @param duration 提示长度
     */
    private static void showToast(String text, int duration) {
        if (sToast == null) {
            sToast = makeText(XUtil.getContext(), text, duration);
        } else {
            ((TextView) sToast.getView().findViewById(R.id.tv_info)).setText(text);
        }
        sToast.show();
    }


    /**
     * 构建Toast
     *
     * @param context
     * @param msg
     * @param duration
     * @return
     */
    private static Toast makeText(Context context, String msg, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.xutil_layout_toast, null);
        Toast toast = new Toast(context);
        toast.setView(view);
        TextView tv = view.findViewById(R.id.tv_info);
        if (tv != null) {
            tv.setText(msg);
            if (tv.getBackground() != null) {
                tv.getBackground().setAlpha(100);
            }
        }
        toast.setDuration(duration);
        return toast;
    }

    /**
     * 取消toast显示
     */
    public static void cancelToast() {
        if (sToast != null) {
            sToast.cancel();
        }
    }
}
