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

package com.xuexiang.xutil.common;

import android.view.View;

public class ClickUtils {

	/**
	 * 最近一次点击的时间
	 */
	private static long sLastClickTime;
	/**
	 * 最近一次点击的控件ID
	 */
	private static int sLastClickViewId;

	/**
	 * 是否是快速点击
	 * 
	 * @param v
	 *            点击的控件
	 * @return true:是，false:不是
	 */
	public static boolean isFastDoubleClick(View v) {
		return isFastDoubleClick(v, 1F);
	}

	/**
	 * 是否是快速点击
	 * 
	 * @param v
	 *            点击的控件
	 * @param interval
	 *            时间间期（秒）
	 * @return
	 */
	public static boolean isFastDoubleClick(View v, float interval) {
		long time = System.currentTimeMillis();
		int viewId = v.getId();
		long timeD = time - sLastClickTime;
		if (0 < timeD && timeD < (interval * 1000) && viewId == sLastClickViewId) {
			return true;
		} else {
			sLastClickTime = time;
			sLastClickViewId = viewId;
			return false;
		}
	}
}
