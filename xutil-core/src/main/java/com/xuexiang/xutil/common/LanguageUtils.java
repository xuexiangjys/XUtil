/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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
 *
 */

package com.xuexiang.xutil.common;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.xuexiang.xutil.resource.ResUtils;

import java.util.Locale;

/**
 * 语言工具类
 *
 * @author xuexiang
 * @since 2020/6/7 11:27 PM
 */
public final class LanguageUtils {

    /**
     * 中文
     */
    private static final String CHINESE_LANGUAGE = "zh";
    /**
     * 阿拉伯语
     */
    private static final String ARABIC_LANGUAGE = "ar";
    /**
     * 波斯语
     */
    private static final String FARSI_LANGUAGE = "fa";
    /**
     * 希伯来文
     */
    private static final String IW_LANGUAGE = "iw";
    /**
     * 乌尔都语
     */
    private static final String URDU_LANGUAGE = "ur";
    /**
     *
     */
    private static final String UG_LANGUAGE = "ug";
    /**
     * 英语
     */
    private static final String EN_LANGUAGE = "en";

    private LanguageUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //========系统级语言=======//

    /**
     * @return 获取系统设置的默认语言
     */
    public static Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    /**
     * 获取系统设置的默认语言代码
     *
     * @return 语言代码
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取系统设置的默认语言所在国家的代码
     *
     * @return 语言所在国家的代码
     */
    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    /**
     * @return 是否是中文
     */
    public static boolean isZh() {
        String lang = getLanguage();
        return CHINESE_LANGUAGE.equals(lang);
    }

    /**
     * @return 是否是阿拉伯语
     */
    public static boolean isArabic() {
        String lang = getLanguage();
        return ARABIC_LANGUAGE.equals(lang);
    }

    /**
     * @return 是否是英语
     */
    public static boolean isEn() {
        String lang = getLanguage();
        return EN_LANGUAGE.equals(lang);
    }

    public static boolean isUrdu() {
        String lang = getLanguage();
        return URDU_LANGUAGE.equals(lang);
    }

    /**
     * @return 是否是镜像语言
     */
    public static boolean isRTL() {
        String lang = getLanguage();
        return ARABIC_LANGUAGE.equals(lang) || FARSI_LANGUAGE.equals(lang) || IW_LANGUAGE.equals(lang) || URDU_LANGUAGE.equals(lang) || UG_LANGUAGE.equals(lang);
    }

    //========应用级语言=======//

    /**
     * @return 获取当前应用设置的语言
     */
    public static Locale getAppLocale() {
        return ResUtils.getResources().getConfiguration().locale;
    }

    public static String getI18N() {
        Locale locale = getAppLocale();
        return getLocaleLanguage(locale) + '_' + getLocaleCountry(locale);
    }

    private static String getLocaleLanguage(Locale locale) {
        String language = locale.getLanguage();
        if (null != language && language.length() > 2) {
            language = StringUtils.cutString(language, 0, 2);
        }
        return StringUtils.getStringTrim(language);
    }

    private static String getLocaleCountry(Locale locale) {
        String country = locale.getCountry();
        if (null != country && country.length() > 2) {
            country = StringUtils.cutString(country, 0, 2);
        }
        return StringUtils.getStringTrim(country);
    }

    /**
     * @return 当前应用的语言是否是简体中文
     */
    public static boolean isSimplifiedChinese() {
        return Locale.SIMPLIFIED_CHINESE.equals(getAppLocale());
    }

    /**
     * 设置应用语言为简体中文
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setSimplifiedChinese() {
        setAppLocale(Locale.SIMPLIFIED_CHINESE);
    }

    /**
     * 设置应用所处的语言
     *
     * @param locale 需要切换的语言
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void setAppLocale(Locale locale) {
        Resources resource = ResUtils.getResources();
        Configuration config = resource.getConfiguration();
        config.setLocale(locale);
        resource.updateConfiguration(config, null);
    }


}
