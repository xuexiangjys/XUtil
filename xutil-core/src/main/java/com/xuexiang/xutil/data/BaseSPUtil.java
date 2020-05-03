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

import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.net.JsonUtil;
import com.xuexiang.xutil.security.Base64Utils;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * <pre>
 *     desc   :	SharedPreferences管理工具基类
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:45
 * </pre>
 */
public class BaseSPUtil {

	private final SharedPreferences mSP;
	private final Context mContext;
	
	//=======================================初始化构造==================================================//
	/**
	 * 获取自定义的SharedPreferences
	 * @param context
	 * @param spName 自定义SharedPreferences名
	 */
	public BaseSPUtil(Context context, String spName) {
		mContext = context.getApplicationContext();
		mSP = mContext.getSharedPreferences(spName, Context.MODE_PRIVATE);
	}
	
	/**
	 * 获取系统默认的SharedPreferences
	 * @param context
	 */
	public BaseSPUtil(Context context) {
		mContext = context.getApplicationContext();
		mSP = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	
	//=======================================键值保存==================================================//
	/**
	 * 设置boolean值
	 * @param key
	 * @param value
	 */
	public boolean putBoolean(String key, boolean value) {
		return mSP.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 设置float值
	 * @param key
	 * @param value
	 */
	public boolean putFloat(String key, float value) {
		return mSP.edit().putFloat(key, value).commit();
	}
	
	/**
	 * 设置long值
	 * @param key
	 * @param value
	 */
	public boolean putLong(String key, long value) {
		return mSP.edit().putLong(key, value).commit();
	}
	
	/**
	 * 设置String值
	 * @param key
	 * @param value
	 */
	public boolean putString(String key, String value) {
		return mSP.edit().putString(key, value).commit();
	}
	
	/**
	 * 设置int值
	 * @param key
	 * @param value
	 */
	public boolean putInt(String key, int value) {
		return mSP.edit().putInt(key, value).commit();
	}

	/**
	 * 设置Object
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putObject(String key, Object value) {
		return mSP.edit().putString(key, JsonUtil.toJson(value)).commit();
	}

	/**
	 * 设置加密Object
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean putEncodeObject(String key, Object value) {
		String base64Obj = Base64Utils.encode(JsonUtil.toJson(value), "UTF-8");
		return mSP.edit().putString(key, base64Obj).commit();
	}


   /**
    * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
    * @param key
    * @param object
    */
	public boolean put(String key, Object object) {
        if (object instanceof String) {
			return mSP.edit().putString(key, (String) object).commit();
        } else if (object instanceof Integer) {
			return mSP.edit().putInt(key, (Integer) object).commit();
        } else if (object instanceof Boolean) {
			return mSP.edit().putBoolean(key, (Boolean) object).commit();
        } else if (object instanceof Float) {
			return mSP.edit().putFloat(key, (Float) object).commit();
        } else if (object instanceof Long) {
			return mSP.edit().putLong(key, (Long) object).commit();
        } else {
			return mSP.edit().putString(key, StringUtils.toString(object)).commit();
        }
    }

	//=======================================键值获取==================================================//
	/**
	 * 根据key获取boolean值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public boolean getBoolean(String key, boolean defValue) {
		try {
			return mSP.getBoolean(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}
	
	/**
	 * 根据key获取long值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public long getLong(String key, long defValue) {
		try {
			return mSP.getLong(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}
	
	/**
	 * 根据key获取float值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public float getFloat(String key, float defValue) {
		try {
			return mSP.getFloat(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}
	
	/**
	 * 根据key获取String值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String getString(String key, String defValue) {
		try {
			return mSP.getString(key, defValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}
	
	/**
	 * 根据key获取int值
	 * @param key
	 * @param defValue
	 * @return
	 */
	public int getInt(String key, int defValue) {
		try {
			return mSP.getInt(key, defValue);
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
	public <T> T getEncodeObject(String key, Type type) {
		String base64Obj = getString(key, "");
		if (StringUtils.isEmpty(base64Obj)) {
			return null;
		}
		// 对Base64格式的字符串进行解码
		String json = Base64Utils.decode(base64Obj, "UTF-8");
		return JsonUtil.fromJson(json, type);
	}

	/**
	 * 获取对象
	 * @param key
	 * @param type 泛型类
	 * @param <T>
	 * @return
	 */
	public <T> T getObject(String key, Type type) {
		return JsonUtil.fromJson(getString(key, ""), type);
	}
	
	/**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(String key, Object defaultObject) {
		try {
			if (defaultObject instanceof String) {
                return mSP.getString(key, (String) defaultObject);
            } else if (defaultObject instanceof Integer) {
                return mSP.getInt(key, (Integer) defaultObject);
            } else if (defaultObject instanceof Boolean) {
                return mSP.getBoolean(key, (Boolean) defaultObject);
            } else if (defaultObject instanceof Float) {
                return mSP.getFloat(key, (Float) defaultObject);
            } else if (defaultObject instanceof Long) {
                return mSP.getLong(key, (Long) defaultObject);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultObject;
    }
    
    /**
	 * 得到保存数据的方法
	 * 
	 * @param key
	 * @return
	 */
	public Object get(String key) {
		if (contains(key)) {
			Map<String, ?> map = getAll();
			if (map != null && map.containsKey(key)) {
				return map.get(key);
			}
		}
		return null;
	}
    
  //=======================================公共方法==================================================//
    /**
     * 查询某个key是否已经存在
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return mSP.contains(key);
    }
    
    /**
     * 返回所有的键值对
     * @return
     */
    public Map<String, ?> getAll() {
		try {
			return mSP.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    public boolean remove(String key) {
		return mSP.edit().remove(key).commit();
	}
    
	/**
	 * 清空销毁
	 */
	public boolean clear() {
		return mSP.edit().clear().commit();
	}
    
	/**
	 * 根据资源id获取String值
	 * @param resId 资源id
	 * @return
	 */
	public String getString(int resId) {
		return mContext.getResources().getString(resId);
	}
	
	/**
	 * apply方法
	 */
	public void apply() {
		mSP.edit().apply();
	}

}
