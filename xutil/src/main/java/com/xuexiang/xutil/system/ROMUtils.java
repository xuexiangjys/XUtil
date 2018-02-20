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

import com.xuexiang.xutil.common.StringUtils;

/**
 * 定制系统识别工具
 * @author xuexiang
 * @date 2018/2/20 下午2:56
 */
public class ROMUtils {

    public static ROMType sROMType;


    //EMUI标识
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.version.emui";
    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    //MIUI标识
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    //Flyme标识
    private static final String KEY_FLYME_ID_FALG_KEY = "ro.build.display.id";
    private static final String KEY_FLYME_ID_FALG_VALUE_KEYWORD = "Flyme";
    private static final String KEY_FLYME_ICON_FALG = "persist.sys.use.flyme.icon";
    private static final String KEY_FLYME_SETUP_FALG = "ro.meizu.setupwizard.flyme";
    private static final String KEY_FLYME_PUBLISH_FALG = "ro.flyme.published";


    public static ROMType getROMType() {
        if (sROMType == null) {
            sROMType = parseROMType();
        }
        return sROMType;
    }
    /**
     * 解析ROM的类型
     * @param
     * @return ROMType ROM类型的枚举
     * @description: MIUI_ROM, FLYME_ROM, EMUI_ROM, OTHER_ROM
     */

    public static ROMType parseROMType() {
        ROMType romType = ROMType.OTHER;
        BuildProperties buildProperties = BuildProperties.getInstance();
        if (buildProperties.containsKey(KEY_EMUI_VERSION_CODE) || buildProperties.containsKey(KEY_EMUI_API_LEVEL) || buildProperties.containsKey(KEY_EMUI_CONFIG_HW_SYS_VERSION)) {
            return ROMType.EMUI;
        }
        if (buildProperties.containsKey(KEY_MIUI_VERSION_CODE) || buildProperties.containsKey(KEY_MIUI_VERSION_NAME) || buildProperties.containsKey(KEY_MIUI_INTERNAL_STORAGE)) {
            return ROMType.MIUI;
        }
        if (buildProperties.containsKey(KEY_FLYME_ICON_FALG) || buildProperties.containsKey(KEY_FLYME_SETUP_FALG) || buildProperties.containsKey(KEY_FLYME_PUBLISH_FALG)) {
            return ROMType.FLYME;
        }
        if (buildProperties.containsKey(KEY_FLYME_ID_FALG_KEY)) {
            String romName = buildProperties.getProperty(KEY_FLYME_ID_FALG_KEY);
            if (!StringUtils.isEmpty(romName) && romName.contains(KEY_FLYME_ID_FALG_VALUE_KEYWORD)) {
                return ROMType.FLYME;
            }
        }
        return romType;
    }

    /**
     * 手机rom类型
     */
    public enum ROMType {
        /**
         * 华为
         */
        EMUI,
        /**
         * 小米
         */
        MIUI,
        /**
         * 魅族
         */
        FLYME,
        /**
         * 其他
         */
        OTHER
    }

}
