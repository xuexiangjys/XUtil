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

import android.util.Log;
import android.view.View;

import com.xuexiang.xutil.resource.ResourceUtils;
import com.xuexiang.xutil.tip.ToastUtil;
import com.xuexiang.xutildemo.R;

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

    @OnClick(R.id.button)
    void onClick(View v) {
        switch(v.getId()) {
            case R.id.button:
                ToastUtil.get().toast("我们都爱学习！");
                Log.e("xuexiang", ResourceUtils.getFileFromRaw(R.raw.test));
                break;
            default:
                break;
        }
    }
}
