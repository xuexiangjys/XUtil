/*
 * Copyright (C) 2019 xuexiangjys(xuexiangjys@163.com)
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

package com.xuexiang.xutil.system;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.xuexiang.xutil.app.PathUtils;
import com.xuexiang.xutil.file.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 相机拍照工具类
 *
 * @author XUE
 * @since 2019/3/30 11:58
 */
public final class CameraUtils {

    private static final String DEFAULT_PIC_PREFIX = "XUtil_Picture";
    public static final String PIC_POSTFIX = ".JPEG";
    public static final String VIDEO_POSTFIX = ".mp4";

    public final static int REQUEST_CAMERA = 1212;

    public final static int TYPE_IMAGE = 1;
    public final static int TYPE_VIDEO = 2;

    private static String sPicPrefix = DEFAULT_PIC_PREFIX;


    private CameraUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置拍照生成图片的前缀
     *
     * @param sPicPrefix
     */
    public static void setPicPrefix(String sPicPrefix) {
        CameraUtils.sPicPrefix = sPicPrefix;
    }

    /**
     * 打开照相机
     *
     * @param listener 打开相机的监听
     */
    public static void startOpenCamera(Fragment fragment, OnOpenCameraListener listener) {
        startOpenCamera(fragment, FileUtils.getDiskCacheDir("CameraImage"), listener);
    }


    /**
     * 打开照相机
     *
     * @param dirPath  照片存储的根路径
     * @param listener 打开相机的监听
     */
    public static void startOpenCamera(Fragment fragment, String dirPath, OnOpenCameraListener listener) {
        startOpenCamera(fragment, TYPE_IMAGE, dirPath, PIC_POSTFIX, REQUEST_CAMERA, listener);
    }


    /**
     * 打开照相机
     *
     * @param fragment
     * @param type        格式
     * @param dirPath     照片存储的根路径
     * @param format      保存格式
     * @param requestCode 请求码
     * @param listener
     */
    public static void startOpenCamera(Fragment fragment, int type, String dirPath, String format, int requestCode, OnOpenCameraListener listener) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            File cameraFile = createCameraFile(type, dirPath, format);
            Uri imageUri = PathUtils.getUriForFile(cameraFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            if (listener != null) {
                listener.onOpenCamera(cameraFile);
            }
            fragment.startActivityForResult(cameraIntent, requestCode);
        }
    }

    /**
     * 打开照相机
     *
     * @param listener 打开相机的监听
     */
    public static void startOpenCamera(Activity activity, OnOpenCameraListener listener) {
        startOpenCamera(activity, FileUtils.getDiskCacheDir("CameraImage"), listener);
    }

    /**
     * 打开照相机
     *
     * @param dirPath  照片存储的根路径
     * @param listener 打开相机的监听
     */
    public static void startOpenCamera(Activity activity, String dirPath, OnOpenCameraListener listener) {
        startOpenCamera(activity, TYPE_IMAGE, dirPath, PIC_POSTFIX, REQUEST_CAMERA, listener);
    }


    /**
     * 打开照相机
     *
     * @param activity
     * @param type        格式
     * @param dirPath     照片存储的根路径
     * @param format      保存格式
     * @param requestCode 请求码
     * @param listener    打开相机的监听
     */
    public static void startOpenCamera(Activity activity, int type, String dirPath, String format, int requestCode, OnOpenCameraListener listener) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
            File cameraFile = createCameraFile(type, dirPath, format);
            Uri imageUri = PathUtils.getUriForFile(cameraFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            if (listener != null) {
                listener.onOpenCamera(cameraFile);
            }
            activity.startActivityForResult(cameraIntent, requestCode);
        }
    }

    /**
     * @param type
     * @param outputCameraPath
     * @param format
     * @return
     */
    public static File createCameraFile(int type, String outputCameraPath, String format) {
        String path = !TextUtils.isEmpty(outputCameraPath)
                ? outputCameraPath : FileUtils.getDiskCacheDir("CameraImage");

        return createMediaFile(path, type, format);
    }

    private static File createMediaFile(String parentPath, int type, String format) {
        File folderDir = new File(parentPath);
        if (!folderDir.exists()) {
            folderDir.mkdirs();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = CameraUtils.sPicPrefix + "_" + timeStamp;
        File tmpFile = null;
        String postfixType;
        switch (type) {
            case TYPE_IMAGE:
                postfixType = TextUtils.isEmpty(format) ? PIC_POSTFIX : format;
                tmpFile = new File(folderDir, fileName + postfixType);
                break;
            case TYPE_VIDEO:
                tmpFile = new File(folderDir, fileName + VIDEO_POSTFIX);
                break;
        }
        return tmpFile;
    }

    /**
     * 调用系统照相机拍摄的监听
     */
    public interface OnOpenCameraListener {
        /**
         * 开启照相机
         *
         * @param cameraFile
         */
        void onOpenCamera(File cameraFile);
    }


}
