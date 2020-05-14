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
import android.view.KeyEvent;
import android.view.View;

import com.xuexiang.xaop.annotation.Permission;
import com.xuexiang.xpage.annotation.Page;
import com.xuexiang.xpage.base.XPageSimpleListFragment;
import com.xuexiang.xpage.utils.TitleBar;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.router.Router;
import com.xuexiang.xutil.common.ClickUtils;
import com.xuexiang.xutil.system.CameraUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xuexiang.xutildemo.activity.TestRouterActivity;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.xuexiang.xaop.consts.PermissionConsts.CAMERA;
import static com.xuexiang.xutil.system.CameraUtils.REQUEST_CAMERA;

/**
 * 主界面
 *
 * @author xuexiang
 * @date 2018/4/14 上午1:42
 */
@Page(name = "XUtil")
public class MainFragment extends XPageSimpleListFragment {
    private File mCameraFile;

    /**
     * 初始化例子
     *
     * @param lists
     * @return
     */
    @Override
    protected List<String> initSimpleData(List<String> lists) {
        lists.add("路由测试");
        lists.add("通知");
        lists.add("社会化分享");
        lists.add("拍照");
        lists.add("uri获取文件路径测试");
        lists.add("测试SAFUtils");
        return lists;
    }

    /**
     * 条目点击
     *
     * @param position
     */
    @Override
    protected void onItemClick(int position) {
        switch (position) {
            case 0:
//                ActivityUtils.startActivity(TestRouterActivity.class, "param", "我是内容");
                Map<String, Object> params = new HashMap<>();
                params.put("param1", "我是参数1");
                params.put("param2", 123);
//                ActivityUtils.startActivity(TestRouterActivity.class, params);
//                ActivityUtils.startActivity("com.xuexiang.TestRouter", "param", "我是内容");
//                ActivityUtils.startActivityForResult(this, "com.xuexiang.TestRouter", 100);
//                ActivityUtils.startActivityForResult(this, "com.xuexiang.TestRouter", 100, params);
//                ActivityUtils.startActivityForResult(this, TestRouterActivity.class, 100, params);
                Router.newIntent(this).to(TestRouterActivity.class).putExtraParam("param1", "我是参数1").requestCode(100).launch();
                break;
            case 1:
                openPage(NotifyFragment.class);
                break;
            case 2:
                openPage(SocialShareFragment.class);
                break;
            case 3:
                openCamera();
                break;
            case 4:
                openPage(UriPathFragment.class);
                break;
            case 5:
                openPage(SAFFragment.class);
                break;
            default:
                break;
        }
    }

    @Permission({CAMERA})
    private void openCamera() {
        CameraUtils.startOpenCamera(this, new CameraUtils.OnOpenCameraListener() {
            @Override
            public void onOpenCamera(File cameraFile) {
                mCameraFile = cameraFile;
            }
        });
    }


    @Override
    protected TitleBar initTitleBar() {
        return super.initTitleBar().setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickUtils.exitBy2Click();
            }
        });
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ClickUtils.exitBy2Click();
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                ToastUtils.toast("文件路径:" + mCameraFile.getPath());
            } else {
                ToastUtils.toast("请求码：" + requestCode + "， 返回码：" + resultCode + "， 返回内容：" + IntentUtils.getStringExtra(data, "back"));
            }
        }
    }

}
