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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import com.xuexiang.xutil.app.IntentUtils;

/**
 * <pre>
 *     desc   : 页面路由
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:24
 * </pre>
 */
public class Router {
    // #router
    public static final int ROUTER_ANIM_ENTER = Router.RES_NONE;
    public static final int ROUTER_ANIM_EXIT = Router.RES_NONE;

    private Intent intent;
    private Activity from;
    private Class<?> to;
    private Bundle data;
    private ActivityOptionsCompat options;
    private int requestCode = -1;
    private int enterAnim = Router.ROUTER_ANIM_ENTER;
    private int exitAnim = Router.ROUTER_ANIM_EXIT;

    public static final int RES_NONE = -1;

    private static RouterCallback callback;

    private Router() {
        intent = new Intent();
    }

    public static Router newIntent(Activity context) {
        Router router = new Router();
        router.from = context;
        return router;
    }

    public Router to(Class<?> to) {
        this.to = to;
        return this;
    }

    public Router addFlags(int flags) {
        if (intent != null) {
            intent.addFlags(flags);
        }
        return this;
    }

    public Router putBundle(Bundle data) {
        this.data = data;
        return this;
    }

    public Router putBundleParam(@NonNull String key, Object value) {
        if (data == null) {
            data = new Bundle();
        }
        data = IntentUtils.putBundle(data, key, value);
        return this;
    }

    public Router putExtraParam(@NonNull String key, Object value) {
        intent = IntentUtils.putExtra(intent, key, value);
        return this;
    }

    public Router options(ActivityOptionsCompat options) {
        this.options = options;
        return this;
    }

    public Router requestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Router anim(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
        return this;
    }

    public void launch() {
        try {
            if (intent != null && from != null && to != null) {

                if (callback != null) {
                    callback.onBefore(from, to);
                }

                intent.setClass(from, to);

                intent.putExtras(getBundleData());

                if (options == null) {
                    if (requestCode < 0) {
                        from.startActivity(intent);
                    } else {
                        from.startActivityForResult(intent, requestCode);
                    }

                    if (enterAnim > 0 && exitAnim > 0) {
                        from.overridePendingTransition(enterAnim, exitAnim);
                    }
                } else {
                    if (requestCode < 0) {
                        ActivityCompat.startActivity(from, intent, options.toBundle());
                    } else {
                        ActivityCompat.startActivityForResult(from, intent, requestCode, options.toBundle());
                    }
                }

                if (callback != null) {
                    callback.onNext(from, to);
                }
            }
        } catch (Throwable throwable) {
            if (callback != null) {
                callback.onError(from, to, throwable);
            }
        }
    }

    private Bundle getBundleData() {
        if (data == null) {
            data = new Bundle();
        }
        return data;
    }

    public static void pop(Activity activity) {
        activity.finish();
    }

    public static void setCallback(RouterCallback callback) {
        Router.callback = callback;
    }
}
