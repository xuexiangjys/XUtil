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
import android.support.annotation.NonNull;

import com.xuexiang.xutil.file.FileUtils;
import com.xuexiang.xutil.tip.ToastUtils;

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
     * 分享类型
     */
    public enum ShareType {
        /**
         * 默认系统分享
         */
        DEFAULT,

        /**
         * 微信联系人分享
         */
        WE_CHAT_CONTACTS,

        /**
         * 微信朋友圈分享
         */
        WE_CHAT_CIRCLE,

    }

    /**
     * 获取媒体文件的uri集合
     *
     * @param filePaths
     * @return
     */
    @NonNull
    private static ArrayList<Uri> getMediaUrisFromPaths(List<String> filePaths) {
        ArrayList<Uri> fileUris = new ArrayList<>();
        for (String filePath : filePaths) {
            Uri uri = PathUtils.getMediaContentUri(FileUtils.getFileByPath(filePath));
            if (uri != null) {
                fileUris.add(uri);
            }
        }
        return fileUris;
    }

    //========================分享多图片==========================//

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

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getMediaUrisFromPaths(filePaths));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!ActivityUtils.startActivity(intent)) {
            ToastUtils.toast("未找到可进行分享的应用！");
        }
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
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType(IntentUtils.DocumentType.IMAGE);

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getMediaUrisFromPaths(filePaths));
        intent.putExtra("Kdescription", description);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!ActivityUtils.startActivity(intent)) {
            ToastUtils.toast("当前设备未安装微信，无法进行微信分享！");
        }
    }

    /**
     * 微信分享多图片到联系人
     *
     * @param description 分享描述
     * @param filePaths   图片文件路径
     */
    public static void shareMultiplePictureToWeChatContacts(String description, String... filePaths) {
        shareMultiplePictureToWeChatContacts(description, Arrays.asList(filePaths));
    }

    /**
     * 微信分享多图片到联系人
     *
     * @param description 分享描述
     * @param filePaths   图片文件路径
     */
    public static void shareMultiplePictureToWeChatContacts(String description, List<String> filePaths) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
        intent.setType(IntentUtils.DocumentType.IMAGE);

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, getMediaUrisFromPaths(filePaths));
        intent.putExtra("Kdescription", description);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!ActivityUtils.startActivity(intent)) {
            ToastUtils.toast("当前设备未安装微信，无法进行微信分享！");
        }
    }

    //========================系统的分享图片==========================//

    /**
     * 分享图片
     *
     * @param imgUri    图片的资源路径
     * @param shareType 分享的类型
     */
    public static void sharePicture(final Uri imgUri, ShareType shareType) {
        switch (shareType) {
            case DEFAULT:
                sharePicture(imgUri);
                break;
            case WE_CHAT_CIRCLE:
                sharePictureToWeChatCircle(imgUri);
                break;
            case WE_CHAT_CONTACTS:
                sharePictureToWeChatContacts(imgUri);
                break;
            default:
                break;
        }
    }

    /**
     * 分享单张图片到微信朋友圈
     *
     * @param imgUri 图片的资源路径
     */
    public static void sharePictureToWeChatCircle(final Uri imgUri) {
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        shareFile(comp, imgUri, IntentUtils.DocumentType.IMAGE, "分享图片");
    }

    /**
     * 分享单张图片到微信联系人
     *
     * @param imgUri 图片的资源路径
     */
    public static void sharePictureToWeChatContacts(final Uri imgUri) {
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareFile(comp, imgUri, IntentUtils.DocumentType.IMAGE, "分享图片");
    }

    /**
     * 分享单个图片（包括GIF）
     *
     * @param picture 分享图片文件
     */
    public static void sharePicture(final File picture) {
        shareFile(null, picture, IntentUtils.DocumentType.IMAGE, "分享图片");
    }

    /**
     * 分享单个图片（包括GIF）
     *
     * @param pictureUri 分享图片的资源路径
     */
    public static void sharePicture(final Uri pictureUri) {
        shareFile(null, pictureUri, IntentUtils.DocumentType.IMAGE, "分享图片");
    }

    //========================系统的分享视频==========================//

    /**
     * 分享视频
     *
     * @param videoUri  分享视频的资源路径
     * @param shareType 分享的类型
     */
    public static void shareVideo(final Uri videoUri, ShareType shareType) {
        switch (shareType) {
            case DEFAULT:
                shareVideo(videoUri);
                break;
            case WE_CHAT_CIRCLE:
                ToastUtils.toast("微信朋友圈只支持分享图片！");
                break;
            case WE_CHAT_CONTACTS:
                shareVideoToWeChatContacts(videoUri);
                break;
            default:
                break;
        }
    }

    /**
     * 分享视频到微信联系人
     *
     * @param videoUri 分享视频的资源路径
     */
    public static void shareVideoToWeChatContacts(final Uri videoUri) {
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareFile(comp, videoUri, IntentUtils.DocumentType.VIDEO, "分享视频");
    }

    /**
     * 分享视频
     *
     * @param videoFile 分享视频的文件
     */
    public static void shareVideo(final File videoFile) {
        shareFile(null, videoFile, IntentUtils.DocumentType.VIDEO, "分享视频");
    }

    /**
     * 分享视频
     *
     * @param videoUri 分享视频的资源路径
     */
    public static void shareVideo(final Uri videoUri) {
        shareFile(null, videoUri, IntentUtils.DocumentType.VIDEO, "分享视频");
    }

    //========================分享文件==========================//

    /**
     * 分享视频
     *
     * @param fileUri   分享的文件资源路径
     * @param shareType 分享的类型
     */
    public static void shareFile(final Uri fileUri, ShareType shareType) {
        switch (shareType) {
            case DEFAULT:
                shareFile(fileUri);
                break;
            case WE_CHAT_CIRCLE:
                ToastUtils.toast("微信朋友圈只支持分享图片！");
                break;
            case WE_CHAT_CONTACTS:
                shareFileToWeChatContacts(fileUri);
                break;
            default:
                break;
        }
    }

    /**
     * 分享单个文件到微信联系人
     *
     * @param fileUri 分享的文件资源路径
     */
    public static void shareFileToWeChatContacts(final Uri fileUri) {
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        shareFile(comp, fileUri, IntentUtils.DocumentType.ANY, "分享文件");
    }


    /**
     * 分享单个文件
     *
     * @param fileUri 分享的文件资源路径
     */
    public static void shareFile(final Uri fileUri) {
        shareFile(null, fileUri, IntentUtils.DocumentType.ANY, "分享文件");
    }

    /**
     * 分享单个文件
     *
     * @param componentName      指定分享的组件
     * @param fileToShare        分享的文件
     * @param mimeTypeForFile    文件的类型
     * @param subjectTextToShare 分享的文字
     */
    public static void shareFile(ComponentName componentName, @NonNull final File fileToShare, final @IntentUtils.DocumentType String mimeTypeForFile, final String subjectTextToShare) {
        shareFile(componentName, PathUtils.getUriForFile(fileToShare), mimeTypeForFile, subjectTextToShare);
    }


    /**
     * 分享单个文件
     *
     * @param componentName      指定分享的组件
     * @param fileUri            分享的文件资源路径
     * @param mimeTypeForFile    文件的类型
     * @param subjectTextToShare 分享的文字
     */
    public static void shareFile(ComponentName componentName, final Uri fileUri, @NonNull final @IntentUtils.DocumentType String mimeTypeForFile, @NonNull final String subjectTextToShare) {
        Intent intent = new Intent();
        if (componentName != null) {
            intent.setComponent(componentName);
        }
        intent.setAction(Intent.ACTION_SEND);
        intent.setType(mimeTypeForFile);
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectTextToShare);
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!ActivityUtils.startActivity(intent)) {
            ToastUtils.toast("未找到可进行分享的应用！");
        }
    }

}
