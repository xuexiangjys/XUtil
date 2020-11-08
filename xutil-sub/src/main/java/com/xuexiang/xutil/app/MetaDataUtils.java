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

package com.xuexiang.xutil.app;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

import androidx.annotation.NonNull;

import com.xuexiang.xutil.XUtil;

/**
 * MetaData工具类
 *
 * @author xuexiang
 * @since 2018/6/27 下午2:33
 */
public final class MetaDataUtils {

    private MetaDataUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return the value of meta-data in application.
     *
     * @param key The key of meta-data.
     * @return the value of meta-data in application
     */
    public static String getMetaDataInApp(@NonNull final String key) {
        String value = "";
        PackageManager pm = XUtil.getContext().getPackageManager();
        String packageName = XUtil.getContext().getPackageName();
        try {
            ApplicationInfo ai = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            value = String.valueOf(ai.metaData.get(key));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Return the value of meta-data in activity.
     *
     * @param activity The activity.
     * @param key      The key of meta-data.
     * @return the value of meta-data in activity
     */
    public static String getMetaDataInActivity(@NonNull final Activity activity,
                                               @NonNull final String key) {
        return getMetaDataInActivity(activity.getClass(), key);
    }

    /**
     * Return the value of meta-data in activity.
     *
     * @param clz The activity class.
     * @param key The key of meta-data.
     * @return the value of meta-data in activity
     */
    public static String getMetaDataInActivity(@NonNull final Class<? extends Activity> clz,
                                               @NonNull final String key) {
        String value = "";
        PackageManager pm = XUtil.getContext().getPackageManager();
        ComponentName componentName = new ComponentName(XUtil.getContext(), clz);
        try {
            ActivityInfo ai = pm.getActivityInfo(componentName, PackageManager.GET_META_DATA);
            value = String.valueOf(ai.metaData.get(key));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Return the value of meta-data in service.
     *
     * @param service The service.
     * @param key     The key of meta-data.
     * @return the value of meta-data in service
     */
    public static String getMetaDataInService(@NonNull final Service service,
                                              @NonNull final String key) {
        return getMetaDataInService(service.getClass(), key);
    }

    /**
     * Return the value of meta-data in service.
     *
     * @param clz The service class.
     * @param key The key of meta-data.
     * @return the value of meta-data in service
     */
    public static String getMetaDataInService(@NonNull final Class<? extends Service> clz,
                                              @NonNull final String key) {
        String value = "";
        PackageManager pm = XUtil.getContext().getPackageManager();
        ComponentName componentName = new ComponentName(XUtil.getContext(), clz);
        try {
            ServiceInfo info = pm.getServiceInfo(componentName, PackageManager.GET_META_DATA);
            value = String.valueOf(info.metaData.get(key));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Return the value of meta-data in receiver.
     *
     * @param receiver The receiver.
     * @param key      The key of meta-data.
     * @return the value of meta-data in receiver
     */
    public static String getMetaDataInReceiver(@NonNull final BroadcastReceiver receiver,
                                               @NonNull final String key) {
        return getMetaDataInReceiver(receiver.getClass(), key);
    }

    /**
     * Return the value of meta-data in receiver.
     *
     * @param clz The receiver class.
     * @param key The key of meta-data.
     * @return the value of meta-data in receiver
     */
    public static String getMetaDataInReceiver(@NonNull final Class<? extends BroadcastReceiver> clz,
                                               @NonNull final String key) {
        String value = "";
        PackageManager pm = XUtil.getContext().getPackageManager();
        ComponentName componentName = new ComponentName(XUtil.getContext(), clz);
        try {
            ActivityInfo info = pm.getReceiverInfo(componentName, PackageManager.GET_META_DATA);
            value = String.valueOf(info.metaData.get(key));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }
}
