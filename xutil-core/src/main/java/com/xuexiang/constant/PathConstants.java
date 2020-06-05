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

package com.xuexiang.constant;

import com.xuexiang.xutil.app.PathUtils;

import java.io.File;

/**
 * 文件路径常量
 *
 * @author xuexiang
 * @since 2020/6/6 1:51 AM
 */
public final class PathConstants {

    public static final String EXT_STORAGE_PATH = PathUtils.getExtStoragePath();

    public static final String EXT_STORAGE_DIR = EXT_STORAGE_PATH + File.separator;

    public static final String APP_EXT_STORAGE_PATH = EXT_STORAGE_DIR + "Android";

    public static final String EXT_DOWNLOADS_PATH = PathUtils.getExtDownloadsPath();

    public static final String EXT_PICTURES_PATH = PathUtils.getExtPicturesPath();

    public static final String EXT_DCIM_PATH = PathUtils.getExtDCIMPath();
}
