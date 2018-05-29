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
import android.os.Bundle;

import com.xuexiang.xpage.base.XPageActivity;
import com.xuexiang.xutil.app.IntentUtils;
import com.xuexiang.xutil.tip.ToastUtils;
import com.xuexiang.xutildemo.fragment.MainFragment;

public class MainActivity extends XPageActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openPage(MainFragment.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            toast("请求码：" + requestCode + "， 返回码：" + resultCode + "， 返回内容：" + IntentUtils.getStringExtra(data, "back"));
        }
    }

    protected void toast(String msg) {
        ToastUtils.toast(msg);
    }
}
