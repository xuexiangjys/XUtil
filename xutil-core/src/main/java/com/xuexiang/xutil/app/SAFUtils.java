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

package com.xuexiang.xutil.app;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.StringDef;

import com.xuexiang.xutil.XUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * SAF（Storage Access Framework）使用工具类
 *
 * @author xuexiang
 * @since 2020/5/14 12:30 AM
 */
public final class SAFUtils {

    /**
     * 只读模式
     */
    public static final String MODE_READ_ONLY = "r";

    /**
     * 只写模式
     */
    public static final String MODE_WRITE_ONLY = "w";

    /**
     * 读写模式
     */
    public static final String MODE_READ_WRITE = "rw";

    /**
     * 文件读写模式
     */
    @StringDef({MODE_READ_ONLY, MODE_WRITE_ONLY, MODE_READ_WRITE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FileMode {
    }

    private SAFUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 从uri资源符中获取输入流
     *
     * @param uri 文本资源符
     * @return InputStream
     */
    public static InputStream openInputStream(Uri uri) throws FileNotFoundException {
        return XUtil.getContentResolver().openInputStream(uri);
    }

    /**
     * 从uri资源符中获取输出流
     *
     * @param uri 文本资源符
     * @return OutputStream
     */
    public static OutputStream openOutputStream(Uri uri) throws FileNotFoundException {
        return XUtil.getContentResolver().openOutputStream(uri);
    }

    /**
     * 从uri资源符中读取文件描述
     * <p>
     * 可接受的uri类型:
     * 1.content
     * 2.file
     *
     * @param uri 文本资源符
     * @return ParcelFileDescriptor
     */
    public static ParcelFileDescriptor openFileDescriptor(Uri uri) throws FileNotFoundException {
        return XUtil.getContentResolver().openFileDescriptor(uri, MODE_READ_ONLY);
    }

    /**
     * 从uri资源符中读取文件描述
     * <p>
     * 可接受的uri类型:
     * 1.content
     * 2.file
     *
     * @param uri  文本资源符
     * @param mode 文件读写模式
     * @return ParcelFileDescriptor
     */
    public static ParcelFileDescriptor openFileDescriptor(Uri uri, @FileMode String mode) throws FileNotFoundException {
        return XUtil.getContentResolver().openFileDescriptor(uri, mode);
    }

    /**
     * 从uri资源符中读取文件描述
     * <p>
     * 可接受的uri类型更广:
     * 1.content
     * 2.android.resource
     * 3.file
     *
     * @param uri 文本资源符
     * @return AssetFileDescriptor
     */
    public static AssetFileDescriptor openAssetFileDescriptor(Uri uri) throws FileNotFoundException {
        return XUtil.getContentResolver().openAssetFileDescriptor(uri, MODE_READ_ONLY);
    }

    /**
     * 从uri资源符中读取文件描述
     * <p>
     * 可接受的uri类型更广:
     * 1.content
     * 2.android.resource
     * 3.file
     *
     * @param uri  文本资源符
     * @param mode 文件读写模式
     * @return AssetFileDescriptor
     */
    public static AssetFileDescriptor openAssetFileDescriptor(Uri uri, @FileMode String mode) throws FileNotFoundException {
        return XUtil.getContentResolver().openAssetFileDescriptor(uri, mode);
    }




}
