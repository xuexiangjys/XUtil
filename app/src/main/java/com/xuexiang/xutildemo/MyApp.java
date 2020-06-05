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

package com.xuexiang.xutildemo;

import android.app.Application;
import android.content.Context;

import com.xuexiang.xaop.XAOP;
import com.xuexiang.xaop.util.PermissionUtils;
import com.xuexiang.xpage.AppPageConfig;
import com.xuexiang.xpage.PageConfig;
import com.xuexiang.xpage.PageConfiguration;
import com.xuexiang.xpage.model.PageInfo;
import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.tip.ToastUtils;

import java.util.List;

/**
 * @author xuexiang
 * @date 2018/2/4 下午6:25
 */
public class MyApp extends Application {

//    @Override
//    protected void attachBaseContext(Context base) {
//        // 这个地方关闭自动初始化
//        XUtil.disableAutoInit();
//        super.attachBaseContext(base);
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        XUtil.debug(isDebug());

        PageConfig.getInstance().setPageConfiguration(new PageConfiguration() {
            @Override
            public List<PageInfo> registerPages(Context context) {
                return AppPageConfig.getInstance().getPages();
            }
        }).debug(isDebug() ? "PageLog" : null).init(this);


        //初始化插件
        XAOP.init(this);
        //日志打印切片开启
        XAOP.debug(isDebug());
        //设置动态申请权限切片 申请权限被拒绝的事件响应监听
        XAOP.setOnPermissionDeniedListener(new PermissionUtils.OnPermissionDeniedListener() {
            @Override
            public void onDenied(List<String> permissionsDenied) {
                ToastUtils.toast("权限申请被拒绝:" + StringUtils.listToString(permissionsDenied, ","));
            }

        });
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

}
