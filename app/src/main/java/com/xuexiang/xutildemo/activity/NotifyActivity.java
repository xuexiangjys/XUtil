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

package com.xuexiang.xutildemo.activity;

import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.xuexiang.xutil.app.notify.NotificationUtils;
import com.xuexiang.xutil.resource.ResourceUtils;
import com.xuexiang.xutildemo.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * @author xuexiang
 * @date 2018/3/20 下午9:33
 */
public class NotifyActivity extends BaseActivity {
    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_notify;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initViews() {

    }

    /**
     * 初始化监听
     */
    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_simple, R.id.btn_pic, R.id.btn_multi, R.id.btn_mailbox})
    void OnClick(View v) {
        switch(v.getId()) {
            case R.id.btn_simple:
                Map<String, Object> params = new HashMap<>();
                params.put("param1", "我是参数1");
                params.put("param2", 123);
                NotificationUtils.buildSimple(1000, R.mipmap.ic_launcher,"我是通知的标题","我是通知的内容",null)
                        .setHeadUp(true)
                        .addAction(R.mipmap.ic_launcher, "确定",  NotificationUtils.buildActivityIntent(TestRouterActivity.class, params))
                        .addAction(R.mipmap.ic_launcher, "取消",  NotificationUtils.buildActivityIntent(TestRouterActivity.class, params))
                        .show();
                break;
            case R.id.btn_pic:
                NotificationUtils.buildBigPic(1001, R.mipmap.ic_launcher,"我是通知的标题","我是图片的概要信息")
                        .setPicRes(R.mipmap.timg2)
                        .show();
                break;
            case R.id.btn_multi:
                NotificationUtils.buildBigText(1002, R.mipmap.ic_launcher,"我是通知的标题", ResourceUtils.getFileFromRaw(R.raw.test))
                        .show();
                break;

            case R.id.btn_mailbox:
                NotificationUtils.buildMailBox(1003, R.mipmap.ic_launcher,"我是通知的标题")
                        .addMsg("11111111111")
                        .addMsg("22222222222")
                        .addMsg("33333333333")
                        .show();
                break;
            default:
                break;
        }
    }
}
