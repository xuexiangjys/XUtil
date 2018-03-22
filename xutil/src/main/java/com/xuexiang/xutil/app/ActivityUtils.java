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
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.LogUtils;

import java.util.Map;

/**
 * Activity页面跳转工具类
 *
 * @author xuexiang
 * @date 2018/3/18 下午2:13
 */
public final class ActivityUtils {

    /**
     * 页面跳转
     *
     * @param intent
     */
    public static void startActivity(Intent intent) {
        if (intent == null) {
            LogUtils.e("[startActivity failed]: intent == null");
            return;
        }
        if (AppUtils.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                XUtil.getContext().startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                LogUtils.e(e);
            }
        } else {
            LogUtils.e("[resolveActivity failed]: " + intent.getComponent().getClassName() + " do not register in manifest");
        }
    }

    /**
     * 页面跳转,返回数据
     *
     * @param fromActivity
     * @param intent
     * @param requestCode  请求码
     */
    public static void startActivityForResult(Activity fromActivity, Intent intent, int requestCode) {
        if (intent == null) {
            LogUtils.e("[startActivity failed]: intent == null");
            return;
        }
        if (AppUtils.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            try {
                fromActivity.startActivityForResult(intent, requestCode);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                LogUtils.e(e);
            }
        } else {
            LogUtils.e("[resolveActivity failed]: " + intent.getComponent().getClassName() + " do not register in manifest");
        }
    }

    //=================获取Activity跳转意图===============//

    /**
     * 获取Activity跳转意图
     *
     * @param cls    Activity类
     * @param action 隐式启动的动作
     * @return
     */
    @NonNull
    public static Intent getActivityIntent(Class<? extends Activity> cls, String action) {
        return IntentUtils.getIntent(XUtil.getContext(), cls, action, true);
    }


    /**
     * 获取Activity跳转显式意图
     *
     * @param cls Activity类
     * @return
     */
    @NonNull
    public static Intent getActivityIntent(Class<? extends Activity> cls) {
        return IntentUtils.getIntent(XUtil.getContext(), cls, null, true);
    }

    /**
     * 获取Activity跳转隐式意图
     *
     * @param action 隐式启动的动作
     * @return
     */
    @NonNull
    public static Intent getActivityIntent(String action) {
        return IntentUtils.getIntent(XUtil.getContext(), null, action, true);
    }

    /**
     * 获取Activity跳转意图
     *
     * @param context
     * @param cls     Activity类
     * @param action  隐式启动的动作
     * @return
     */
    @NonNull
    public static Intent getActivityIntent(Activity context, Class<? extends Activity> cls, String action) {
        return IntentUtils.getIntent(context, cls, action);
    }

    /**
     * 获取Activity跳转意图
     *
     * @param context
     * @param cls     Activity类
     * @return
     */
    @NonNull
    public static Intent getActivityIntent(Activity context, Class<? extends Activity> cls) {
        return IntentUtils.getIntent(context, cls, null);
    }

    /**
     * 获取Activity跳转意图
     *
     * @param context
     * @param action  隐式启动的动作
     * @return
     */
    @NonNull
    public static Intent getActivityIntent(Activity context, String action) {
        return IntentUtils.getIntent(context, null, action);
    }

    /**
     * 获取Activity跳转意图
     *
     * @param cls   Activity类
     * @param key
     * @param param
     * @return
     */
    public static Intent getActivityIntent(Class<? extends Activity> cls, String key, Object param) {
        Intent intent = getActivityIntent(cls);
        intent = IntentUtils.putExtra(intent, key, param);
        return intent;
    }

    /**
     * 获取Activity跳转意图
     *
     * @param cls Activity类
     * @param map 携带的数据
     * @return
     */
    public static Intent getActivityIntent(Class<? extends Activity> cls, Map<String, Object> map) {
        Intent intent = getActivityIntent(cls);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                intent = IntentUtils.putExtra(intent, entry.getKey(), entry.getValue());
            }
        }
        return intent;
    }

    //=====================显式启动==================//

    /**
     * 跳转至Activity页面
     *
     * @param cls Activity类
     */
    public static void startActivity(Class<? extends Activity> cls) {
        Intent intent = getActivityIntent(cls);
        startActivity(intent);
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param to          Activity类
     * @param requestCode 请求码
     */
    public static void startActivityForResult(Activity from, Class<? extends Activity> to, int requestCode) {
        Intent intent = getActivityIntent(from, to);
        startActivityForResult(from, intent, requestCode);
    }


    /**
     * 跳转至Activity页面（单数据）
     *
     * @param cls   Activity类
     * @param key
     * @param param
     */
    public static void startActivity(Class<? extends Activity> cls, String key, Object param) {
        startActivity(getActivityIntent(cls, key, param));
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param to          Activity类
     * @param requestCode 请求码
     * @param key
     * @param param
     */
    public static void startActivityForResult(Activity from, Class<? extends Activity> to, int requestCode, String key, Object param) {
        Intent intent = getActivityIntent(from, to);
        intent = IntentUtils.putExtra(intent, key, param);
        startActivityForResult(from, intent, requestCode);
    }


    /**
     * 跳转至Activity页面
     *
     * @param cls Activity类
     * @param map 携带的数据
     */
    public static void startActivity(Class<? extends Activity> cls, Map<String, Object> map) {
        Intent intent = getActivityIntent(cls, map);
        startActivity(intent);
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param to          Activity类
     * @param requestCode 请求码
     * @param map         携带的数据
     */
    public static void startActivityForResult(Activity from, Class<? extends Activity> to, int requestCode, Map<String, Object> map) {
        Intent intent = getActivityIntent(from, to);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                intent = IntentUtils.putExtra(intent, entry.getKey(), entry.getValue());
            }
        }
        startActivityForResult(from, intent, requestCode);
    }

    /**
     * 跳转至Activity页面（单数据）
     *
     * @param cls   Activity类
     * @param key
     * @param param
     */
    public static void startActivityWithBundle(Class<? extends Activity> cls, String key, Object param) {
        Intent intent = getActivityIntent(cls);
        Bundle bundle = new Bundle();
        bundle = IntentUtils.putBundle(bundle, key, param);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param to          Activity类
     * @param requestCode 请求码
     * @param key
     * @param param
     */
    public static void startActivityForResultWithBundle(Activity from, Class<? extends Activity> to, int requestCode, String key, Object param) {
        Intent intent = getActivityIntent(from, to);
        Bundle bundle = new Bundle();
        bundle = IntentUtils.putBundle(bundle, key, param);
        intent.putExtras(bundle);
        startActivityForResult(from, intent, requestCode);
    }

    /**
     * 跳转至Activity页面
     *
     * @param cls Activity类
     * @param map 携带的数据
     */
    public static void startActivityWithBundle(Class<? extends Activity> cls, Map<String, Object> map) {
        Intent intent = getActivityIntent(cls);
        if (map != null && !map.isEmpty()) {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                bundle = IntentUtils.putBundle(bundle, entry.getKey(), entry.getValue());
            }
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param to          Activity类
     * @param requestCode 请求码
     * @param map         携带的数据
     */
    public static void startActivityForResultWithBundle(Activity from, Class<? extends Activity> to, int requestCode, Map<String, Object> map) {
        Intent intent = getActivityIntent(from, to);
        if (map != null && !map.isEmpty()) {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                bundle = IntentUtils.putBundle(bundle, entry.getKey(), entry.getValue());
            }
            intent.putExtras(bundle);
        }
        startActivityForResult(from, intent, requestCode);
    }


    //=====================隐式启动==================//


    /**
     * 跳转至Activity页面
     *
     * @param action 隐式启动的动作
     */
    public static void startActivity(String action) {
        startActivity(getActivityIntent(action));
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param action      隐式启动的动作
     * @param requestCode 请求码
     */
    public static void startActivityForResult(Activity from, String action, int requestCode) {
        startActivityForResult(from, getActivityIntent(from, action), requestCode);
    }

    /**
     * 跳转至Activity页面
     *
     * @param action 隐式启动的动作
     * @param key
     * @param param
     */
    public static void startActivity(String action, String key, Object param) {
        Intent intent = getActivityIntent(action);
        intent = IntentUtils.putExtra(intent, key, param);
        startActivity(intent);
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param action      隐式启动的动作
     * @param requestCode 请求码
     * @param key
     * @param param
     */
    public static void startActivityForResult(Activity from, String action, int requestCode, String key, Object param) {
        Intent intent = getActivityIntent(from, action);
        intent = IntentUtils.putExtra(intent, key, param);
        startActivityForResult(from, intent, requestCode);
    }

    /**
     * 跳转至Activity页面
     *
     * @param action 隐式启动的动作
     * @param map    携带的数据
     */
    public static void startActivity(String action, Map<String, Object> map) {
        Intent intent = getActivityIntent(action);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                intent = IntentUtils.putExtra(intent, entry.getKey(), entry.getValue());
            }
        }
        startActivity(intent);
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param action      隐式启动的动作
     * @param requestCode 请求码
     * @param map         携带的数据
     */
    public static void startActivityForResult(Activity from, String action, int requestCode, Map<String, Object> map) {
        Intent intent = getActivityIntent(from, action);
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                intent = IntentUtils.putExtra(intent, entry.getKey(), entry.getValue());
            }
        }
        startActivityForResult(from, intent, requestCode);
    }

    /**
     * 跳转至Activity页面
     *
     * @param action 隐式启动的动作
     * @param key
     * @param param
     */
    public static void startActivityWithBundle(String action, String key, Object param) {
        Intent intent = getActivityIntent(action);
        Bundle bundle = new Bundle();
        bundle = IntentUtils.putBundle(bundle, key, param);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param action      隐式启动的动作
     * @param requestCode 请求码
     * @param key
     * @param param
     */
    public static void startActivityForResultWithBundle(Activity from, String action, int requestCode, String key, Object param) {
        Intent intent = getActivityIntent(from, action);
        Bundle bundle = new Bundle();
        bundle = IntentUtils.putBundle(bundle, key, param);
        intent.putExtras(bundle);
        startActivityForResult(from, intent, requestCode);
    }


    /**
     * 跳转至Activity页面
     *
     * @param action 隐式启动的动作
     * @param map    携带的数据
     */
    public static void startActivityWithBundle(String action, Map<String, Object> map) {
        Intent intent = getActivityIntent(action);
        if (map != null && !map.isEmpty()) {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                bundle = IntentUtils.putBundle(bundle, entry.getKey(), entry.getValue());
            }
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转至Activity页面
     *
     * @param from        Activity类
     * @param action      隐式启动的动作
     * @param requestCode 请求码
     * @param map         携带的数据
     */
    public static void startActivityForResultWithBundle(Activity from, String action, int requestCode, Map<String, Object> map) {
        Intent intent = getActivityIntent(from, action);
        if (map != null && !map.isEmpty()) {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                bundle = IntentUtils.putBundle(bundle, entry.getKey(), entry.getValue());
            }
            intent.putExtras(bundle);
        }
        startActivityForResult(from, intent, requestCode);
    }

}
