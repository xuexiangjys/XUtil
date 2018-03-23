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

import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.xuexiang.xutil.app.PendingIntentUtils;
import com.xuexiang.xutil.app.notify.NotificationUtils;
import com.xuexiang.xutil.resource.ResourceUtils;
import com.xuexiang.xutildemo.R;
import com.xuexiang.xutildemo.base.BaseActivity;
import com.xuexiang.xutildemo.receiver.NotifyBroadCastReceiver;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

/**
 * @author xuexiang
 * @date 2018/3/20 下午9:33
 */
public class NotifyActivity extends BaseActivity {

    private int progresses = 0;

    private Handler mHandler = new Handler();
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
    protected void initListeners() {

    }

    @OnClick({R.id.btn_simple, R.id.btn_pic, R.id.btn_multi, R.id.btn_mailbox, R.id.btn_progress})
    void OnClick(View v) {
        switch(v.getId()) {
            case R.id.btn_simple:
                NotificationUtils.buildSimple(1000, R.mipmap.ic_launcher,"我是通知的标题","我是通知的内容",null)
                        .setHeadUp(true)
                        .addAction(R.mipmap.ic_launcher, "确定",  PendingIntentUtils.buildBroadcastIntent(NotifyBroadCastReceiver.class, NotifyBroadCastReceiver.ACTION_SUBMIT, 0))
                        .addAction(R.mipmap.ic_launcher, "取消",  PendingIntentUtils.buildBroadcastIntent(NotifyBroadCastReceiver.class, NotifyBroadCastReceiver.ACTION_CANCEL, 0))
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setIsPolling(true)
                        .show();
                break;
            case R.id.btn_pic:
                Map<String, Object> params = new HashMap<>();
                params.put("param1", "我是参数1");
                params.put("param2", 123);
                NotificationUtils.buildBigPic(1001, R.mipmap.ic_launcher,"我是通知的标题","我是图片的概要信息")
                        .setPicRes(R.mipmap.timg2)
                        .setContentIntent(PendingIntentUtils.buildActivityIntent(TestRouterActivity.class, params))
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
            case R.id.btn_progress:
                progresses = 0;
                mHandler.removeCallbacksAndMessages(null);
                showProgress();
//                NotificationUtils.buildProgress(1004, R.mipmap.ic_launcher, "正在下载", 100, progresses).setIndeterminate(true).show();
                break;
            default:
                break;
        }
    }


    private void showProgress() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progresses >= 100){
                    return;
                }
                progresses++;

                NotificationUtils.buildProgress(1004, R.mipmap.ic_launcher, "正在下载", 100, progresses).show();
                showProgress();

            }
        },100);

    }
}
