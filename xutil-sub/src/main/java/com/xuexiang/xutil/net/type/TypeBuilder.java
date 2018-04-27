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

import com.xuexiang.xutil.net.type.impl.ParameterizedTypeImpl;
import com.xuexiang.xutil.net.type.impl.WildcardTypeImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 泛型生成工具，用法详细参见：https://github.com/ikidou/TypeBuilder
 * <li>用法：</li>
 **************************************************
 *
 * 1.Example for List<String> :
 *   Type type = TypeBuilder
 *      .newInstance(List.class)
 *      .addTypeParam(String.class)
 *      .build();
 *
 **************************************************
 *
 * 2.Example for List<? super String>:
 *   Type type = TypeBuilder
 *      .newInstance(List.class)
 *      .addTypeParamSuper(String.class)
 *      .build();
 *
 **************************************************
 *
 * 3.Example for List<? extends CharSequence>:
 *   Type type = TypeBuilder
 *      .newInstance(List.class)
 *      .addTypeParamExtends(CharSequence.class)
 *      .build();
 *
 **************************************************
 *
 * 4.Example for Map<String, String[]>:
 *   Type type = TypeBuilder
 *      .newInstance(HashMap.class)
 *      .addTypeParam(String.class)
 *      .addTypeParam(String[].class)
 *      .build();
 *
 **************************************************
 *
 * 5.Example for Map<String, List<String>>:
 *   Type type = TypeBuilder
 *      .newInstance(Map.class)
 *      .addTypeParam(String.class)
 *      .beginSubType(List.class) //开始 List<String> 部分
 *      .addTypeParam(String.class) //设置List的泛型值
 *      .endSubType() //结束 List<String> 部分
 *      .build();
 **************************************************
 *
 * <pre>
 *     desc   : 泛型生成工具
 *     author : xuexiang
 *     time   : 2018/4/27 下午8:42
 * </pre>
 */
public class TypeBuilder {
    private final TypeBuilder parent;
    private final Class raw;
    private final List<Type> args = new ArrayList<>();


    private TypeBuilder(Class raw, TypeBuilder parent) {
        assert raw != null;
        this.raw = raw;
        this.parent = parent;
    }

    public static TypeBuilder newInstance(Class raw) {
        return new TypeBuilder(raw, null);
    }

    private static TypeBuilder newInstance(Class raw, TypeBuilder parent) {
        return new TypeBuilder(raw, parent);
    }


    public TypeBuilder beginSubType(Class raw) {
        return newInstance(raw, this);
    }

    public TypeBuilder endSubType() {
        if (parent == null) {
            throw new TypeException("expect beginSubType() before endSubType()");
        }

        parent.addTypeParam(getType());

        return parent;
    }

    public TypeBuilder addTypeParam(Class clazz) {
        return addTypeParam((Type) clazz);
    }

    public TypeBuilder addTypeParamExtends(Class... classes) {
        if (classes == null) {
            throw new NullPointerException("addTypeParamExtends() expect not null Class");
        }

        WildcardTypeImpl wildcardType = new WildcardTypeImpl(null, classes);

        return addTypeParam(wildcardType);
    }

    public TypeBuilder addTypeParamSuper(Class... classes) {
        if (classes == null) {
            throw new NullPointerException("addTypeParamSuper() expect not null Class");
        }

        WildcardTypeImpl wildcardType = new WildcardTypeImpl(classes, null);

        return addTypeParam(wildcardType);
    }

    public TypeBuilder addTypeParam(Type type) {
        if (type == null) {
            throw new NullPointerException("addTypeParam expect not null Type");
        }

        args.add(type);

        return this;
    }

    public Type build() {
        if (parent != null) {
            throw new TypeException("expect endSubType() before build()");
        }

        return getType();
    }

    private Type getType() {
        if (args.isEmpty()) {
            return raw;
        }
        return new ParameterizedTypeImpl(raw, args.toArray(new Type[args.size()]), null);
    }
}
