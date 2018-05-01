
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

package com.xuexiang.xutil.net;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * <pre>
 *     desc   :	json转化工具
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:53
 * </pre>
 */
public final class JsonUtil {

	/**
	 * Don't let anyone instantiate this class.
	 */
	private JsonUtil() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}


	/**
	 * 把 JSON 字符串 转换为 单个指定类型的对象
	 *
	 * @param json
	 *            包含了单个对象数据的JSON字符串
	 * @param classOfT
	 *            指定类型对象的Class
	 * @return 指定类型对象
	 */
	public static <T> T fromJson(String json, Class<T> classOfT) {
		try {
			return new Gson().fromJson(json, classOfT);
		} catch (JsonParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析Json字符串
	 * @param json Json字符串
	 * @param typeOfT 泛型类
	 * @param <T>
	 * @return
	 */
	public static <T> T fromJson(String json, Type typeOfT) {
		try {
			return new Gson().fromJson(json, typeOfT);
		} catch (JsonParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 把 单个指定类型的对象 转换为 JSON 字符串
	 * @param src
	 * @return
	 */
	public static String toJson(Object src) {
		return new Gson().toJson(src);
	}

	/**
	 * 把 单个指定类型的对象 转换为 JSONObject对象
	 * @param src
	 * @return
	 */
	public static JSONObject toJSONObject(Object src) {
		return toJSONObject(toJson(src));
	}

	/**
	 * 把 JSON 字符串 转换为 JSONObject对象
	 * @param json
	 * @return
	 */
	public static JSONObject toJSONObject(String json) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
