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

package com.xuexiang.xutil.app.notify.builder;

import android.graphics.Bitmap;

import androidx.annotation.DrawableRes;
import androidx.core.app.NotificationCompat;

import com.xuexiang.xutil.display.ImageUtils;

/**
 * <pre>
 *     desc   : 附带图片的通知
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:25
 * </pre>
 */
public class BigPicBuilder extends BaseBuilder {

    private Bitmap mBitmap;
    /**
     * 图片的资源id
     */
    @DrawableRes
    private int mBigPicResId;


    public BigPicBuilder setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        return this;
    }


    public BigPicBuilder setPicRes(@DrawableRes int bigPicResId) {
        mBigPicResId = bigPicResId;
        return this;
    }

    @Override
    public void beforeBuild() {
        NotificationCompat.BigPictureStyle picStyle = new NotificationCompat.BigPictureStyle();
        if (mBitmap == null || mBitmap.isRecycled()) {
            mBitmap = ImageUtils.getBitmap(mBigPicResId);
        }
        picStyle.bigPicture(mBitmap);
        picStyle.setBigContentTitle(mContentTitle);
        picStyle.setSummaryText(mSummaryText);
        setStyle(picStyle);
    }

}
