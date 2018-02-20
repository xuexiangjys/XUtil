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

package com.xuexiang.xutil.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.net.JsonUtil;
import com.xuexiang.xutil.security.Base64Util;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * SharedPreferences工具类
 * @author xuexiang
 * @date 2018/2/15 下午2:03
 */
public final class SPUtils {

    //==================================SharedPreferences实例获取==================================================//
    /**
     * 获取默认SharedPreferences实例
     * @return
     */
    public static SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(XUtil.getContext());
    }

    /**
     * 获取SharedPreferences实例
     * @param spName
     * @return
     */
    public static SharedPreferences getSharedPreferences(String spName) {
        return XUtil.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    //=======================================键值保存==================================================//
    /**
     * 设置boolean值
     * @param sp SharedPreferences实例
     * @param key
     * @param value
     */
    public static boolean putBoolean(SharedPreferences sp, String key, boolean value) {
        return sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 设置float值
     * @param sp SharedPreferences实例
     * @param key
     * @param value
     */
    public boolean putFloat(SharedPreferences sp, String key, float value) {
        return sp.edit().putFloat(key, value).commit();
    }

    /**
     * 设置long值
     * @param sp SharedPreferences实例
     * @param key
     * @param value
     */
    public boolean putLong(SharedPreferences sp, String key, long value) {
        return sp.edit().putLong(key, value).commit();
    }

    /**
     * 设置String值
     * @param sp SharedPreferences实例
     * @param key
     * @param value
     */
    public boolean putString(SharedPreferences sp, String key, String value) {
        return sp.edit().putString(key, value).commit();
    }

    /**
     * 设置int值
     * @param sp SharedPreferences实例
     * @param key
     * @param value
     */
    public boolean putInt(SharedPreferences sp, String key, int value) {
        return sp.edit().putInt(key, value).commit();
    }

    /**
     * 设置Object
     * @param sp SharedPreferences实例
     * @param key
     * @param value
     * @return
     */
    public boolean putObject(SharedPreferences sp, String key, Object value) {
        return sp.edit().putString(key, JsonUtil.toJson(value)).commit();
    }

    /**
     * 设置加密Object
     * @param sp SharedPreferences实例
     * @param key
     * @param value
     * @return
     */
    public boolean putEncodeObject(SharedPreferences sp, String key, Object value) {
        String base64Obj = Base64Util.encode(JsonUtil.toJson(value), "UTF-8");
        return sp.edit().putString(key, base64Obj).commit();
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param sp SharedPreferences实例
     * @param key
     * @param object
     */
    public boolean put(SharedPreferences sp, String key, Object object) {
        if (object instanceof String) {
            return sp.edit().putString(key, (String) object).commit();
        } else if (object instanceof Integer) {
            return sp.edit().putInt(key, (Integer) object).commit();
        } else if (object instanceof Boolean) {
            return sp.edit().putBoolean(key, (Boolean) object).commit();
        } else if (object instanceof Float) {
            return sp.edit().putFloat(key, (Float) object).commit();
        } else if (object instanceof Long) {
            return sp.edit().putLong(key, (Long) object).commit();
        } else {
            return sp.edit().putString(key, StringUtils.toString(object)).commit();
        }
    }

    //=======================================键值获取==================================================//
    /**
     * 根据key获取boolean值
     * @param sp SharedPreferences实例
     * @param key
     * @param defValue
     * @return
     */
    public boolean getBoolean(SharedPreferences sp, String key, boolean defValue) {
        try {
            return sp.getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 根据key获取long值
     * @param sp SharedPreferences实例
     * @param key
     * @param defValue
     * @return
     */
    public long getLong(SharedPreferences sp, String key, long defValue) {
        try {
            return sp.getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 根据key获取float值
     * @param sp SharedPreferences实例
     * @param key
     * @param defValue
     * @return
     */
    public float getFloat(SharedPreferences sp, String key, float defValue) {
        try {
            return sp.getFloat(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 根据key获取String值
     * @param sp SharedPreferences实例
     * @param key
     * @param defValue
     * @return
     */
    public String getString(SharedPreferences sp, String key, String defValue) {
        try {
            return sp.getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 根据key获取int值
     * @param sp SharedPreferences实例
     * @param key
     * @param defValue
     * @return
     */
    public int getInt(SharedPreferences sp, String key, int defValue) {
        try {
            return sp.getInt(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 获取加密的对象
     *
     * @param key
     * @param type 泛型类
     * @param <T>
     * @return
     */
    public <T> T getEncodeObject(SharedPreferences sp, String key, Type type) {
        String base64Obj = getString(sp, key, "");
        if (StringUtils.isEmpty(base64Obj)) {
            return null;
        }
        // 对Base64格式的字符串进行解码
        String json = Base64Util.decode(base64Obj, "UTF-8");
        return JsonUtil.fromJson(json, type);
    }

    /**
     * 获取对象
     * @param key
     * @param type 泛型类
     * @param <T>
     * @return
     */
    public <T> T getObject(SharedPreferences sp, String key, Type type) {
        return JsonUtil.fromJson(getString(sp, key, ""), type);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(SharedPreferences sp, String key, Object defaultObject) {
        try {
            if (defaultObject instanceof String) {
                return sp.getString(key, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return sp.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return sp.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return sp.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return sp.getLong(key, (Long) defaultObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultObject;
    }


    //=======================================公共方法==================================================//
    /**
     * 查询某个key是否已经存在
     * @param sp SharedPreferences实例
     * @param key
     * @return
     */
    public boolean contains(SharedPreferences sp, String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     * @param sp SharedPreferences实例
     * @return
     */
    public Map<String, ?> getAll(SharedPreferences sp) {
        try {
            return sp.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean remove(SharedPreferences sp, String key) {
        return sp.edit().remove(key).commit();
    }

    /**
     * 清空销毁
     * @param sp SharedPreferences实例
     */
    public boolean clear(SharedPreferences sp) {
        return sp.edit().clear().commit();
    }

}
