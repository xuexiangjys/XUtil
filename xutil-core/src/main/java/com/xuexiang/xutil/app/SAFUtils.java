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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.annotation.StringDef;

import com.xuexiang.constant.PathConstants;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.logger.Logger;
import com.xuexiang.xutil.display.ImageUtils;
import com.xuexiang.xutil.file.FileIOUtils;
import com.xuexiang.xutil.file.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.xuexiang.constant.MimeTypeConstants.JPEG;
import static com.xuexiang.constant.MimeTypeConstants.PNG;
import static com.xuexiang.constant.MimeTypeConstants.WEBP;

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
     * 当前应用是否是以兼容模式运行;
     *
     * @return true: 是，false: 不是
     */
    public static boolean isExternalStorageLegacy() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return Environment.isExternalStorageLegacy();
        }
        return false;
    }

    /**
     * 是否是分区存储模式：在公共目录下file的api无效了
     *
     * @return 是否是分区存储模式
     */
    public static boolean isScopedStorageMode() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy();
    }

    //================从uri资源符中获取输入/输出流====================//

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

    //================从uri资源符中读取文件描述====================//

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

    //============往下载目录中写文件===============//

    /**
     * 写文件到外部公共下载目录
     *
     * @param dirPath  外部公共下载目录中的相对目录，例如传入目录是：test/，对应的写入位置就是：/storage/emulated/0/Download/test/
     * @param fileName 文件名
     * @param mimeType 文件类型
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileToPublicDownloads(String dirPath, String fileName, String mimeType, InputStream inputStream) {
        if (isScopedStorageMode()) {
            Uri insertUri = getFileDownloadUri(dirPath, fileName, mimeType);
            if (insertUri != null) {
                return FileIOUtils.writeFileFromIS(inputStream, SAFUtils.openOutputStream(insertUri));
            }
            return false;
        } else {
            return FileIOUtils.writeFileFromIS(FileUtils.getFilePath(PathUtils.getExtDownloadsPath() + File.separator + dirPath, fileName), inputStream);
        }
    }

    /**
     * 获取文件保存到外部公共下载目录的uri
     *
     * @param dirPath  外部公共下载目录中的相对目录，例如传入目录是：test/，对应的写入位置就是：/storage/emulated/0/Download/test/
     * @param fileName 文件名
     * @return uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static Uri getFileDownloadUri(String dirPath, String fileName, String mimeType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, mimeType);
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + File.separator + dirPath);
        return XUtil.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
    }

    //============往公共相机目录中保存图片===============//

    private static final String _PNG = ".png";
    private static final String _WEBP = ".webp";
    private static final String _JPG = ".jpg";
    private static final String _JPEG = ".jpeg";

    /**
     * 保存图片到外部公共相机目录
     *
     * @param dirPath  外部公共相机目录中的相对目录，例如传入目录是：test/，对应的写入位置就是：/storage/emulated/0/DCIM/test/
     * @param fileName 文件名
     * @param format   图片文件类型
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean saveImageToPublicDCIM(String dirPath, String fileName, Bitmap bitmap, Bitmap.CompressFormat format) {
        if (isScopedStorageMode()) {
            Uri insertUri = getImageDCIMUri(dirPath, fileName, format);
            if (insertUri != null) {
                return ImageUtils.save(bitmap, SAFUtils.openOutputStream(insertUri), format);
            }
            return false;
        } else {
            return ImageUtils.save(bitmap, FileUtils.getFilePath(PathUtils.getExtDCIMPath() + File.separator + dirPath, fileName), format);
        }
    }

    /**
     * 获取保存图片到外部公共相机目录的uri
     *
     * @param dirPath  外部公共相机目录中的相对目录，例如传入目录是：test/，对应的写入位置就是：/storage/emulated/0/DCIM/test/
     * @param fileName 文件名
     * @param format   图片文件类型
     * @return 图片的uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static Uri getImageDCIMUri(String dirPath, String fileName, Bitmap.CompressFormat format) {
        ContentValues values = new ContentValues();
        String mimeType;
        switch (format) {
            case PNG:
                mimeType = PNG;
                if (!fileName.endsWith(_PNG)) {
                    fileName = FileUtils.changeFileExtension(fileName, _PNG);
                }
                break;
            case WEBP:
                mimeType = WEBP;
                if (!fileName.endsWith(_WEBP)) {
                    fileName = FileUtils.changeFileExtension(fileName, _WEBP);
                }
                break;
            case JPEG:
            default:
                mimeType = JPEG;
                if (!(fileName.endsWith(_JPEG) || fileName.endsWith(_JPG))) {
                    fileName = FileUtils.changeFileExtension(fileName, _JPEG);
                }
                break;
        }
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.ORIENTATION, 0);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + dirPath);
        return XUtil.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    //============往公共相机目录中写入图片===============//

    /**
     * 写图片文件到外部公共相机目录
     *
     * @param dirPath     外部公共相机目录中的相对目录，例如传入目录是：test/，对应的写入位置就是：/storage/emulated/0/DCIM/test/
     * @param fileName    文件名
     * @param mimeType    图片文件类型
     * @param inputStream 图片输入流
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeImageToPublicDCIM(String dirPath, String fileName, String mimeType, InputStream inputStream) {
        if (isScopedStorageMode()) {
            Uri insertUri = getImageDCIMUri(dirPath, fileName, mimeType);
            if (insertUri != null) {
                return FileIOUtils.writeFileFromIS(inputStream, SAFUtils.openOutputStream(insertUri));
            }
            return false;
        } else {
            return FileIOUtils.writeFileFromIS(FileUtils.getFilePath(PathUtils.getExtDCIMPath() + File.separator + dirPath, fileName), inputStream);
        }
    }

    /**
     * 获取保存图片到外部公共相机目录的uri
     *
     * @param dirPath  外部公共相机目录中的相对目录，例如传入目录是：test/，对应的写入位置就是：/storage/emulated/0/DCIM/test/
     * @param fileName 文件名
     * @param mimeType 图片文件类型
     * @return 图片的uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private static Uri getImageDCIMUri(String dirPath, String fileName, String mimeType) {
        ContentValues values = new ContentValues();
        switch (mimeType) {
            case PNG:
                if (!fileName.endsWith(_PNG)) {
                    fileName = FileUtils.changeFileExtension(fileName, _PNG);
                }
                break;
            case WEBP:
                if (!fileName.endsWith(_WEBP)) {
                    fileName = FileUtils.changeFileExtension(fileName, _WEBP);
                }
                break;
            case JPEG:
                if (!(fileName.endsWith(_JPEG) || fileName.endsWith(_JPG))) {
                    fileName = FileUtils.changeFileExtension(fileName, _JPEG);
                }
                break;
            default:
                break;
        }
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.ORIENTATION, 0);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM + File.separator + dirPath);
        return XUtil.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    //================任意公有目录写文件========================//


    /**
     * 写文件到任意公有目录写文件
     *
     * @param dirPath     任意公有目录写文件
     * @param fileName    文件名
     * @param mimeType    文件类型
     * @param inputStream 输入流
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileAny(String dirPath, String fileName, String mimeType, InputStream inputStream) {
        if (isScopedStorageMode() && PathUtils.isPublicPath(dirPath)) {
            Uri insertUri = getPublicFileUri(dirPath, fileName, mimeType);
            if (insertUri != null) {
                return FileIOUtils.writeFileFromIS(inputStream, SAFUtils.openOutputStream(insertUri));
            }
            return false;
        } else {
            return FileIOUtils.writeFileFromIS(FileUtils.getFilePath(dirPath, fileName), inputStream);
        }
    }

    /**
     * 根据目录、文件名和文件类型获取对应的uri
     *
     * @param dirPath  文件目录
     * @param fileName 文件名
     * @param mimeType 文件类型
     * @return uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Uri getPublicFileUri(String dirPath, String fileName, String mimeType) {
        Uri uri;
        if (dirPath.startsWith(PathConstants.EXT_DOWNLOADS_PATH)) {
            uri = getPublicDownloadFileUri(dirPath, fileName, mimeType);
        } else if (dirPath.startsWith(PathConstants.EXT_PICTURES_PATH) || dirPath.startsWith(PathConstants.EXT_DCIM_PATH)) {
            uri = getPublicMediaFileUri(dirPath, fileName, mimeType);
        } else {
            uri = getPublicNormalFileUri(dirPath, fileName, mimeType);
        }
        return uri;
    }

    /**
     * 获取文件保存到外部公共下载目录的uri
     *
     * @param dirPath  文件目录
     * @param fileName 文件名
     * @param mimeType 文件类型
     * @return uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Uri getPublicDownloadFileUri(String dirPath, String fileName, String mimeType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.TITLE, fileName);
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, mimeType);
        values.put(MediaStore.Downloads.RELATIVE_PATH, getRelativePath(dirPath));
        return XUtil.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * 获取外部公共多媒体的uri
     *
     * @param dirPath  文件目录
     * @param fileName 文件名
     * @param mimeType 文件类型
     * @return 图片的uri
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static Uri getPublicMediaFileUri(String dirPath, String fileName, String mimeType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        values.put(MediaStore.Images.Media.ORIENTATION, 0);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, getRelativePath(dirPath));
        if ("image".equals(mimeType)) {
            return XUtil.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else if ("audio".equals(mimeType)) {
            return XUtil.getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
        } else if ("video".equals(mimeType)) {
            return XUtil.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return XUtil.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
    }

    /**
     * 获取外部公共普通文件的uri
     *
     * @param dirPath  文件目录
     * @param fileName 文件名
     * @param mimeType 文件类型
     * @return 普通文件的uri
     */
    public static Uri getPublicNormalFileUri(String dirPath, String fileName, String mimeType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, FileUtils.getFilePath(dirPath, fileName));
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType);
        return XUtil.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);
    }

    private static String getRelativePath(String dirPath) {
        int index = dirPath.indexOf(PathConstants.EXT_STORAGE_DIR);
        if (index != -1) {
            return dirPath.substring(PathConstants.EXT_STORAGE_DIR.length());
        }
        return dirPath;
    }


}
