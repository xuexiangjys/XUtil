/*
 * Copyright (C) 2020 xuexiangjys(xuexiangjys@163.com)
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
 *
 */

package androidx.core.content;

import android.app.Application;

import androidx.annotation.Keep;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ObjectUtils;

/**
 * 提供FileProvider能力，并执行自动初始化
 *
 * @author xuexiang
 * @since 2020/6/5 11:26 PM
 */
@Keep
public final class FileProvider4Utils extends FileProvider {

    @Override
    public boolean onCreate() {
        if (XUtil.isAutoInit()) {
            Application application = ObjectUtils.cast(getContext(), Application.class);
            if (application != null) {
                XUtil.init(application.getApplicationContext());
            }
        }
        return super.onCreate();
    }
}
