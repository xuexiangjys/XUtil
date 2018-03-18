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

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.xuexiang.xutil.app.ActivityUtils;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.app.router.Router;
import com.xuexiang.xutil.resource.ResourceUtils;
import com.xuexiang.xutil.tip.ToastUtil;
import com.xuexiang.xutildemo.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    /**
     * 布局的资源id
     *
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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

    @OnClick({R.id.button, R.id.router})
    void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                ToastUtil.get().toast("我们都爱学习！");
                Log.e("xuexiang", ResourceUtils.getFileFromRaw(R.raw.test));
                break;
            case R.id.router:
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
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            toast("请求码：" + requestCode + "， 返回码：" + resultCode + "， 返回内容：" + IntentUtils.getStringExtra(data, "back"));
        }
    }
}
