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
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

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
    private Activity fromActivity;

    private Fragment fromFragment;
    private androidx.fragment.app.Fragment fromFragmentV4;

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
        router.fromActivity = context;
        return router;
    }

    public static Router newIntent(Fragment fragment) {
        Router router = new Router();
        router.fromFragment = fragment;
        return router;
    }

    public static Router newIntent(androidx.fragment.app.Fragment fragment) {
        Router router = new Router();
        router.fromFragmentV4 = fragment;
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
        Context context = getFromContext();
        try {
            if (intent != null && context != null && to != null) {
                if (callback != null) {
                    callback.onBefore(context, to);
                }
                intent.setClass(context, to);
                intent.putExtras(getBundleData());

                if (options == null) {
                    startActivity();
                    if (enterAnim > 0 && exitAnim > 0) {
                        ((Activity)context).overridePendingTransition(enterAnim, exitAnim);
                    }
                } else {
                    if (requestCode < 0) {
                        ActivityCompat.startActivity(context, intent, options.toBundle());
                    } else {
                        ActivityCompat.startActivityForResult((Activity) context, intent, requestCode, options.toBundle());
                    }
                }
                if (callback != null) {
                    callback.onNext(context, to);
                }
            }
        } catch (Throwable throwable) {
            if (callback != null) {
                callback.onError(context, to, throwable);
            }
        }
    }

    private void startActivity() {
        if (requestCode < 0) {
            if (fromActivity != null) {
                fromActivity.startActivity(intent);
            } else if (fromFragment != null) {
                fromFragment.startActivity(intent);
            } else if (fromFragmentV4 != null) {
                fromFragmentV4.startActivity(intent);
            }
        } else {
            if (fromActivity != null) {
                fromActivity.startActivityForResult(intent, requestCode);
            } else if (fromFragment != null) {
                fromFragment.startActivityForResult(intent, requestCode);
            } else if (fromFragmentV4 != null) {
                fromFragmentV4.startActivityForResult(intent, requestCode);
            }
        }
    }

    private Context getFromContext() {
        if (fromActivity != null) {
            return fromActivity;
        } else if (fromFragment != null) {
            return fromFragment.getActivity();
        } else if (fromFragmentV4 != null) {
            return fromFragmentV4.getContext();
        }
        return null;
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
