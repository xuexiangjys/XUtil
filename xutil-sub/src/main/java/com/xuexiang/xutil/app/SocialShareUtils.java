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

package com.xuexiang.xutil.app;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 社会化分享工具类
 *
 * @author xuexiang
 * @since 2018/5/18 上午10:51
 */
public final class SocialShareUtils {

    private SocialShareUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 微信分享多图片到朋友圈
     *
     * @param description 分享描述
     * @param filePaths   图片文件路径
     */
    public static void shareMultiplePictureToWeChatCircle(String description, String... filePaths) {
        shareMultiplePictureToWeChatCircle(description, Arrays.asList(filePaths));
    }

    /**
     * 微信分享多图片到朋友圈
     *
     * @param description 分享描述
     * @param filePaths   图片文件路径
     */
    public static void shareMultiplePictureToWeChatCircle(String description, List<String> filePaths) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm",
                "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType(IntentUtils.DocumentType.IMAGE);

        ArrayList<Uri> imageUris = new ArrayList<>();
        for (String f : filePaths) {
            imageUris.add(Uri.fromFile(new File(f)));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        intent.putExtra("Kdescription", description);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityUtils.startActivity(intent);
    }


    /**
     * 分享多图片
     *
     * @param filePaths 图片文件路径
     */
    public static void shareMultiplePicture(String... filePaths) {
        shareMultiplePicture(Arrays.asList(filePaths));
    }

    /**
     * 分享多图片
     *
     * @param filePaths 图片文件路径
     */
    public static void shareMultiplePicture(List<String> filePaths) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType(IntentUtils.DocumentType.IMAGE);
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (String f : filePaths) {
            imageUris.add(Uri.fromFile(new File(f)));
        }
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ActivityUtils.startActivity(intent);
    }


    /**
     * 分享文件
     *
     * @param fileToShare        分享的文件
     * @param mimeTypeForFile    文件的类型
     * @param subjectTextToShare 分享的文字
     */
    public static void shareFile(final File fileToShare, final @IntentUtils.DocumentType String mimeTypeForFile, final String subjectTextToShare) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType(mimeTypeForFile);
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectTextToShare);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileToShare));
        ActivityUtils.startActivity(intent);
    }

    /**
     * 分享文件
     *
     * @param fileUri            分享的文件资源路径
     * @param mimeTypeForFile    文件的类型
     * @param subjectTextToShare 分享的文字
     */
    public static void shareFile(final Uri fileUri, final @IntentUtils.DocumentType String mimeTypeForFile, final String subjectTextToShare) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setType(mimeTypeForFile);
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectTextToShare);
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 分享单个图片（包括GIF）
     *
     * @param picture            分享图片文件
     * @param subjectTextToShare 分享的文字
     */
    public static void sharePicture(final File picture, final String subjectTextToShare) {
        shareFile(picture, IntentUtils.DocumentType.IMAGE, subjectTextToShare);
    }

    /**
     * 分享单个图片（包括GIF）
     *
     * @param pictureUri         分享图片的资源路径
     * @param subjectTextToShare 分享的文字
     */
    public static void sharePicture(final Uri pictureUri, final String subjectTextToShare) {
        shareFile(pictureUri, IntentUtils.DocumentType.IMAGE, subjectTextToShare);
    }

    /**
     * 分享视频
     *
     * @param videoFile          分享视频的文件
     * @param subjectTextToShare 分享的文字
     */
    public static void shareVideo(final File videoFile, final String subjectTextToShare) {
        shareFile(videoFile, IntentUtils.DocumentType.VIDEO, subjectTextToShare);
    }

    /**
     * 分享视频
     *
     * @param videoUri           分享视频的资源路径
     * @param subjectTextToShare 分享的文字
     */
    public static void shareVideo(final Uri videoUri, final String subjectTextToShare) {
        shareFile(videoUri, IntentUtils.DocumentType.VIDEO, subjectTextToShare);
    }

}
