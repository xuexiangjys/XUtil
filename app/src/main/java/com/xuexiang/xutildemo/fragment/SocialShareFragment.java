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

package com.xuexiang.xutildemo.fragment;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.luck.picture.lib.PictureSelectionModel;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.SocialShareUtils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.xuexiang.xutil.app.IntentUtils.DocumentType.ANY;
import static com.xuexiang.xutil.app.IntentUtils.DocumentType.IMAGE;
import static com.xuexiang.xutil.app.IntentUtils.DocumentType.VIDEO;
import static com.xuexiang.xutil.app.SocialShareUtils.ShareType.DEFAULT;
import static com.xuexiang.xutil.app.SocialShareUtils.ShareType.WE_CHAT_CIRCLE;
import static com.xuexiang.xutil.app.SocialShareUtils.ShareType.WE_CHAT_CONTACTS;

/**
 * @author xuexiang
 * @since 2018/6/22 下午4:51
 */
@Page(name = "社会化分享")
public class SocialShareFragment extends XPageSimpleListFragment {

    public static final int REQUEST_CODE_SHARE_PICTURE = 99;

    public static final int REQUEST_CODE_MULTIPLE_PICTURE = 100;

    public static final int REQUEST_CODE_SHARE_FILE = 102;

    public static final int REQUEST_CODE_SHARE_VIDEO = 103;

    private SocialShareUtils.ShareType mShareType = DEFAULT;


    @Override
    protected List<String> initSimpleData(List<String> lists) {
        lists.add("分享单图片");
        lists.add("分享单图片到微信朋友圈");
        lists.add("分享单图片到微信联系人");
        lists.add("分享多图片");
        lists.add("分享多图片到微信朋友圈");
        lists.add("分享多图片到微信联系人");
        lists.add("分享视频");
        lists.add("分享视频到微信联系人");
        lists.add("分享文件");
        lists.add("分享文件到微信联系人");
        return lists;
    }

    @Override
    protected void onItemClick(int position) {
        switch (position) {
            //分享单张图片
            case 0:
                share(DEFAULT, IMAGE);
                break;
            case 1:
                share(WE_CHAT_CIRCLE, IMAGE);
                break;
            case 2:
                share(WE_CHAT_CONTACTS, IMAGE);
                break;

            //分享多张图片
            case 3:
                mShareType = DEFAULT;
                getPictureSelector(this)
                        .forResult(REQUEST_CODE_MULTIPLE_PICTURE);
                break;
            case 4:
                mShareType = WE_CHAT_CIRCLE;
                getPictureSelector(this)
                        .forResult(REQUEST_CODE_MULTIPLE_PICTURE);
                break;
            case 5:
                mShareType = WE_CHAT_CONTACTS;
                getPictureSelector(this)
                        .forResult(REQUEST_CODE_MULTIPLE_PICTURE);
                break;

            //分享视频
            case 6:
                share(DEFAULT, VIDEO);
                break;
            case 7:
                share(WE_CHAT_CONTACTS, VIDEO);
                break;

            //分享文件
            case 8:
                share(DEFAULT, ANY);
                break;
            case 9:
                share(WE_CHAT_CONTACTS, ANY);
                break;
            default:
                break;
        }
    }

    /**
     * 分享
     *
     * @param shareType
     * @param documentType
     */
    private void share(SocialShareUtils.ShareType shareType, @IntentUtils.DocumentType String documentType) {
        mShareType = shareType;
        switch (documentType) {
            case IMAGE:
                startActivityForResult(IntentUtils.getDocumentPickerIntent(documentType), REQUEST_CODE_SHARE_PICTURE);
                break;
            case VIDEO:
                startActivityForResult(IntentUtils.getDocumentPickerIntent(documentType), REQUEST_CODE_SHARE_VIDEO);
                break;
            case ANY:
                startActivityForResult(IntentUtils.getDocumentPickerIntent(documentType), REQUEST_CODE_SHARE_FILE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SHARE_PICTURE:
                    if (data != null) {
                        SocialShareUtils.sharePicture(getActivity(), data.getData(), mShareType);
                    }
                    break;
                case REQUEST_CODE_MULTIPLE_PICTURE:
                    if (data != null) {
                        List<LocalMedia> photoDatas = PictureSelector.obtainMultipleResult(data);
                        List<String> photos = new ArrayList<>();
                        for (LocalMedia localMedia : photoDatas) {
                            photos.add(localMedia.getPath());
                        }
                        switch (mShareType) {
                            case DEFAULT:
                                SocialShareUtils.shareMultiplePicture(getActivity(), photos);
                                break;
                            case WE_CHAT_CIRCLE:
                                SocialShareUtils.shareMultiplePictureToWeChatCircle(getActivity(), "单图片分享", photos);
                                break;
                            case WE_CHAT_CONTACTS:
                                SocialShareUtils.shareMultiplePictureToWeChatContacts(getActivity(), "单图片分享", photos);
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case REQUEST_CODE_SHARE_VIDEO:
                    if (data != null) {
                        SocialShareUtils.shareVideo(getActivity(), data.getData(), mShareType);
                    }
                    break;
                case REQUEST_CODE_SHARE_FILE:
                    if (data != null) {
                        SocialShareUtils.shareFile(getActivity(), data.getData(), mShareType);
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 获取图片选择的配置
     *
     * @param fragment
     * @return
     */
    public static PictureSelectionModel getPictureSelector(Fragment fragment) {
        return PictureSelector.create(fragment)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(9)
                .minSelectNum(1)
                .selectionMode(PictureConfig.MULTIPLE)
                .previewImage(true)
                .isCamera(true)
                .enableCrop(false)
                .compress(true)
                .previewEggs(true);
    }


}
