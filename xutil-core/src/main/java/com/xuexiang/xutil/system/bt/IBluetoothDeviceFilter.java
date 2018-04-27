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

package com.xuexiang.xutil.system.bt;

import android.bluetooth.BluetoothDevice;

/**
 * 蓝牙设备过滤器
 * @author xuexiang
 * @date 2018/2/20 上午1:05
 */
public interface IBluetoothDeviceFilter {

    /**
     * 是否是指定的蓝牙设备
     *
     * @param device 蓝牙设备
     * @return
     */
    boolean isCorrect(BluetoothDevice device);
}
