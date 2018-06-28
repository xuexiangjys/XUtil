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

package com.xuexiang.xutil.app.router;

import android.content.Context;

/**
 * <pre>
 *     desc   : 简单的回调
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:24
 * </pre>
 */
public class SimpleRouterCallback implements RouterCallback {

    @Override
    public void onBefore(Context from, Class<?> to) {

    }

    @Override
    public void onNext(Context from, Class<?> to) {

    }

    @Override
    public void onError(Context from, Class<?> to, Throwable throwable) {

    }
}
