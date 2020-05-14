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

import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.StringDef;

import com.xuexiang.constant.MimeTypeConstants;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.logger.Logger;
import com.xuexiang.xutil.file.FileIOUtils;
import com.xuexiang.xutil.file.FileUtils;

import java.io.File;
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
    public static InputStream openInputStream(Uri uri) {
        try {
            return openInputStreamWithException(uri);
        } catch (FileNotFoundException e) {
            Logger.e(e);
        }
        return null;
    }

    /**
     * 从uri资源符中获取输入流
     *
     * @param uri 文本资源符
     * @return InputStream
     */
    public static InputStream openInputStreamWithException(Uri uri) throws FileNotFoundException {
        return XUtil.getContentResolver().openInputStream(uri);
    }

    /**
     * 从uri资源符中获取输出流
     *
     * @param uri 文本资源符
     * @return OutputStream
     */
    public static OutputStream openOutputStream(Uri uri) {
        try {
            return openOutputStreamWithException(uri);
        } catch (FileNotFoundException e) {
            Logger.e(e);
        }
        return null;
    }

    /**
     * 从uri资源符中获取输出流
     *
     * @param uri 文本资源符
     * @return OutputStream
     */
    public static OutputStream openOutputStreamWithException(Uri uri) throws FileNotFoundException {
        return XUtil.getContentResolver().openOutputStream(uri);
    }

    //====================================//

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
    public static ParcelFileDescriptor openFileDescriptor(Uri uri) {
        return openFileDescriptor(uri, MODE_READ_ONLY);
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
    public static ParcelFileDescriptor openFileDescriptorWithException(Uri uri) throws FileNotFoundException {
        return openFileDescriptorWithException(uri, MODE_READ_ONLY);
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
    public static ParcelFileDescriptor openFileDescriptor(Uri uri, @FileMode String mode) {
        try {
            return openFileDescriptorWithException(uri, mode);
        } catch (FileNotFoundException e) {
            Logger.e(e);
        }
        return null;
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
    public static ParcelFileDescriptor openFileDescriptorWithException(Uri uri, @FileMode String mode) throws FileNotFoundException {
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
    public static AssetFileDescriptor openAssetFileDescriptor(Uri uri) {
        return openAssetFileDescriptor(uri, MODE_READ_ONLY);
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
    public static AssetFileDescriptor openAssetFileDescriptorWithException(Uri uri) throws FileNotFoundException {
        return openAssetFileDescriptorWithException(uri, MODE_READ_ONLY);
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
    public static AssetFileDescriptor openAssetFileDescriptor(Uri uri, @FileMode String mode) {
        try {
            return openAssetFileDescriptorWithException(uri, mode);
        } catch (FileNotFoundException e) {
            Logger.e(e);
        }
        return null;
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
    public static AssetFileDescriptor openAssetFileDescriptorWithException(Uri uri, @FileMode String mode) throws FileNotFoundException {
        return XUtil.getContentResolver().openAssetFileDescriptor(uri, mode);
    }


    //===========================//

    /**
     * 写文件到外部公共下载目录
     *
     * @param dirPath 外部公共下载目录中的相对目录，例如传入目录是：test/，对应的写入位置就是：/storage/emulated/0/Download/test/
     * @param name    文件名
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileToPublicDownloads(String dirPath, String name, InputStream inputStream) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Downloads.DISPLAY_NAME, name);
            values.put(MediaStore.Downloads.MIME_TYPE, MimeTypeConstants.TXT);
            values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + File.separator + dirPath);
            Uri insertUri = XUtil.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
            if (insertUri != null) {
                return FileIOUtils.writeFileFromIS(inputStream, SAFUtils.openOutputStream(insertUri));
            }
            return false;
        } else {
            return FileIOUtils.writeFileFromIS(FileUtils.getFilePath(PathUtils.getExtDownloadsPath() + File.separator + dirPath, name), inputStream);
        }
    }

}
