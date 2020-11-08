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
package com.xuexiang.xutil.resource;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.common.logger.Logger;
import com.xuexiang.xutil.display.ImageUtils;
import com.xuexiang.xutil.file.CloseUtils;
import com.xuexiang.xutil.file.FileIOUtils;
import com.xuexiang.xutil.file.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.xuexiang.xutil.common.StringUtils.EMPTY;

/**
 * <pre>
 *     desc   : 资源工具类
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:52
 * </pre>
 */
public final class ResourceUtils {

    /**
     * 换行符
     */
    private static final String LINE_BREAK = "\r\n";

    /**
     * Don't let anyone instantiate this class.
     */
    private ResourceUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 读取assert下的txt文件
     *
     * @param fileName 文件名
     * @return 文本内容
     */
    public static String readStringFromAssert(String fileName) {
        return readStringFromAssert(fileName, "utf-8");
    }

    /**
     * 读取assert下的txt文件
     *
     * @param fileName     文件名
     * @param encodingCode 字符编码
     * @return 文本内容
     */
    public static String readStringFromAssert(String fileName, String encodingCode) {
        InputStream inputStream = null;
        try {
            inputStream = openAssetsFile(fileName);
            if (inputStream != null) {
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                return new String(buffer, encodingCode);
            }
        } catch (Exception e) {
            Logger.e(e);
        } finally {
            CloseUtils.closeIO(inputStream);
        }
        return EMPTY;
    }

    /**
     * 打开Assets下的文件
     *
     * @param fileName Assets下的文件名
     * @return 文件流
     */
    public static InputStream openAssetsFile(String fileName) {
        try {
            return openAssetsFileWithException(fileName);
        } catch (IOException e) {
            Logger.e(e);
        }
        return null;
    }

    /**
     * 打开Assets下的文件
     *
     * @param fileName Assets下的文件名
     * @return 文件流
     * @throws IOException
     */
    public static InputStream openAssetsFileWithException(String fileName) throws IOException {
        return getAssetManager().open(fileName);
    }

    /**
     * 打开Raw下的资源
     *
     * @param resId
     * @return
     */
    public static InputStream openRawResource(int resId) {
        return ResUtils.getResources().openRawResource(resId);
    }

    /**
     * 获取AssetManager
     *
     * @return
     */
    public static AssetManager getAssetManager() {
        return ResUtils.getResources().getAssets();
    }

    /**
     * 获取Assets下文件的内容
     *
     * @param fileName 文件名
     * @return
     */
    public static String getFileFromAssets(String fileName) {
        return getFileFromAssets(fileName, true);
    }

    /**
     * 获取Assets下文件的内容
     *
     * @param fileName      文件名
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String getFileFromAssets(String fileName, boolean isNeedAddLine) {
        if (StringUtils.isEmpty(fileName)) {
            return EMPTY;
        }
        return readInputStream(openAssetsFile(fileName), isNeedAddLine);
    }


    /**
     * 读取raw下文件的内容
     *
     * @param resId 文件资源id
     * @return
     */
    public static String getFileFromRaw(int resId) {
        return getFileFromRaw(resId, true);
    }

    /**
     * 读取raw下文件的内容
     *
     * @param resId         文件资源id
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String getFileFromRaw(int resId, boolean isNeedAddLine) {
        return readInputStream(openRawResource(resId), isNeedAddLine);
    }

    /**
     * 读取输入流
     *
     * @param inputStream   输入流
     * @param isNeedAddLine 是否需要换行
     * @return
     */
    public static String readInputStream(InputStream inputStream, boolean isNeedAddLine) {
        StringBuilder s = new StringBuilder(EMPTY);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            if (isNeedAddLine) {
                while ((line = br.readLine()) != null) {
                    s.append(line).append(LINE_BREAK);
                }
            } else {
                while ((line = br.readLine()) != null) {
                    s.append(line);
                }
            }
        } catch (Exception e) {
            Logger.e(e);
        } finally {
            CloseUtils.closeIO(br);
        }
        return s.toString();
    }

    /**
     * 从Assets中读取图片
     *
     * @param fileName 文件名
     */
    @Nullable
    public static Bitmap getImageFromAssetsFile(String fileName) {
        InputStream is = null;
        AssetManager am = getAssetManager();
        try {
            is = am.open(fileName);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Logger.e(e);
        } finally {
            CloseUtils.closeIO(is);
        }
        return null;
    }

    /**
     * 从Assets中读取图片
     *
     * @param fileName 文件名
     */
    @Nullable
    public static Bitmap getImageFromAssets(String fileName) {
        InputStream is = null;
        try {
            is = openAssetsFileWithException(RUtils.DRAWABLE + "/" + fileName);
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Logger.e(e);
        } finally {
            CloseUtils.closeIO(is);
        }
        return null;
    }

    /**
     * 从Assets中读取图片
     */
    @Nullable
    public static Drawable getImageDrawableFromAssets(String fileName) {
        InputStream is = null;
        try {
            is = openAssetsFileWithException(RUtils.DRAWABLE + "/" + fileName);
            return ImageUtils.bitmap2Drawable(BitmapFactory.decodeStream(is));
        } catch (IOException e) {
            Logger.e(e);
        } finally {
            CloseUtils.closeIO(is);
        }
        return null;
    }

    /**
     * 从assets目录中复制整个文件夹内容
     *
     * @param oldPath 原文件路径  如：/aa
     * @param newPath 复制后路径  如：xx:/bb/cc
     */
    public static void copyFilesFromAssets(String oldPath, String newPath) {
        try {
            // 获取assets目录下的所有文件及目录名
            String[] fileNames = getAssetManager().list(oldPath);
            // 如果是目录
            if (fileNames != null && fileNames.length > 0) {
                if (FileUtils.createOrExistsDir(newPath)) {
                    for (String fileName : fileNames) {
                        copyFilesFromAssets(oldPath + File.separator + fileName, newPath + File.separator + fileName);
                    }
                }
            } else {// 如果是文件
                FileIOUtils.writeFileFromIS(newPath, getAssetManager().open(oldPath));
            }
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    /**
     * 从assets目录中复制指定文件至指定目录下
     *
     * @param fileName 需要复制的文件名
     * @param srcDir   文件在assets下的目录
     * @param destDir  复制后的目录
     */
    public static boolean copyFileFromAssets(String fileName, String srcDir, String destDir) {
        try {
            if (FileUtils.createOrExistsDir(destDir)) {
                return FileIOUtils.writeFileFromIS(destDir + File.separator + fileName, getAssetManager().open(srcDir + File.separator + fileName));
            }
        } catch (Exception e) {
            Logger.e(e);
        }
        return false;
    }

    /**
     * 从assets目录中复制指定文件至指定目录下
     *
     * @param fileName     需要复制的文件名
     * @param assetsSrcDir 文件在assets下的目录
     * @param destDir      复制后的目录
     * @return 复制后的文件路径
     */
    public static String getCopyFileFromAssets(String fileName, String assetsSrcDir, String destDir) {
        try {
            if (FileUtils.createOrExistsDir(destDir)) {
                String copyFilePath = destDir + File.separator + fileName;
                if (FileIOUtils.writeFileFromIS(copyFilePath, getAssetManager().open(assetsSrcDir + File.separator + fileName))) {
                    return copyFilePath;
                }
            }
        } catch (Exception e) {
            Logger.e(e);
        }
        return EMPTY;
    }

}
