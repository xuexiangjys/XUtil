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

package com.xuexiang.xutil.net.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * <pre>
 *     desc   : 获取范型的类型
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:51
 * </pre>
 */
public abstract class TypeToken<T> {
    private Type type;

    public TypeToken() {
        Type superclass = getClass().getGenericSuperclass();
        if(superclass instanceof Class) {
            throw new TypeException("No generics found!");
        } else {
            ParameterizedType type = (ParameterizedType)superclass;
            if (type != null) {
                this.type = type.getActualTypeArguments()[0];
            }
        }
    }

    public Type getType() {
        return type;
    }
}
