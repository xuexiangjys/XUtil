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

package com.xuexiang.xutil.system;

import android.app.Service;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.xuexiang.xutil.XUtil;

/**
 * <pre>
 *     desc   :	输入框震动效果帮助类
 *     author : xuexiang
 *     time   : 2018/4/27 下午8:40
 * </pre>
 */
public class EditTextShakeHelper {

	private static EditTextShakeHelper sInstance;
	/**
	 * 震动动画
	 */
	private Animation mShakeAnimation;
	/**
	 * 振动器
	 */
	private final Vibrator mShakeVibrator;

	/**
	 * 获取实例
	 * @return
	 */
	public static EditTextShakeHelper get() {
		if (sInstance == null) {
			synchronized (EditTextShakeHelper.class) {
				if (sInstance == null) {
					sInstance = new EditTextShakeHelper();
				}
			}
		}
		return sInstance;
	}

	public EditTextShakeHelper() {
		// 初始化振动器
		mShakeVibrator = (Vibrator) XUtil.getContext().getSystemService(Service.VIBRATOR_SERVICE);
		// 初始化震动动画
		mShakeAnimation = new TranslateAnimation(0, 10, 0, 0);
		mShakeAnimation.setDuration(300);
		mShakeAnimation.setInterpolator(new CycleInterpolator(8));
	}

	/**
	 * 设置震动动画
	 * @param shakeAnimation
	 * @return
	 */
	public EditTextShakeHelper setShakeAnimation(Animation shakeAnimation) {
		mShakeAnimation = shakeAnimation;
		return this;
	}

	/**
	 * 开始震动
	 * 
	 * @param editTexts 震动的输入框
	 */
	public void shake(EditText... editTexts) {
		if (editTexts != null && editTexts.length > 0) {
			for (EditText editText : editTexts) {
				editText.startAnimation(mShakeAnimation);
			}
			mShakeVibrator.vibrate(new long[] { 0, 500 }, -1);
		}
	}

}
