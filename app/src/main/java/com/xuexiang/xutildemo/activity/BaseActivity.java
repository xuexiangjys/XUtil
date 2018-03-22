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

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xuexiang.xutil.tip.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基础Activity
 * @author xuexiang
 * @date 2018/1/11 上午10:37
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initArgs();
        initViews();
        initListeners();
    }

    /**
     * 布局的资源id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 初始化参数
     */
    protected void initArgs() {

    }

    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 初始化监听
     */
    protected abstract void initListeners();

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (isFinishing()) {
            release();
        }
        super.onStop();
    }

    /**
     * 资源释放
     */
    protected void release() {

    }

    protected void toast(String msg) {
        ToastUtil.get().toast(msg);
    }
}