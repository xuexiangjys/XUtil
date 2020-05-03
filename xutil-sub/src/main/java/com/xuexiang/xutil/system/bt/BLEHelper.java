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

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.CountDownTimer;

import com.xuexiang.xutil.system.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     desc   :	低功耗蓝牙连接助手(单例)
 *     author : xuexiang
 *     time   : 2018/4/28 上午1:16
 * </pre>
 */
@SuppressLint("NewApi")
public final class BLEHelper {
    /**
     * 默认超时时间
     */
    private final static int DEFAULT_CONNECT_TIMEOUT = 15;
    private static volatile BLEHelper sInstance;
    /**
     * 系统蓝牙适配器
     */
    private final BluetoothAdapter mBluetoothAdapter;
    /**
     * 已绑定蓝牙设备集合
     */
    private List<BluetoothDevice> mBondedList;
    /**
     * 新发现的蓝牙设备集合（未绑定）
     */
    private List<BluetoothDevice> mNewList;

    /**
     * 蓝牙设备扫描监听器
     */
    private OnSearchDeviceListener mListener;
    /**
     * 蓝牙设备过滤器
     */
    private IBluetoothDeviceFilter mBluetoothDeviceFilter;

    private BLESearchCountDownTimer mTimeOutTimer;

    /**
     * 扫描超时时间
     */
    private long mTimeout;

    // ================================================初始化================================================//

    /**
     * 获取实例
     *
     * @return
     */
    public static BLEHelper get() {
        if (sInstance == null) {
            synchronized (BLEHelper.class) {
                if (sInstance == null) {
                    sInstance = new BLEHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * private constructor for singleton
     */
    private BLEHelper() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mTimeout = DEFAULT_CONNECT_TIMEOUT;
    }

    /**
     * BLE扫描回调
     */
    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (isCorrectDevice(device)) {
                if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    if (mNewList != null) {
                        mNewList.add(device);
                    }
                } else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    if (mBondedList != null) {
                        mBondedList.add(device);
                    }
                }
                if (mListener != null) {
                    if (mListener.onNewDeviceFound(device)) {
                        stopSearch();
                        mListener.onSearchCompleted(mBondedList, mNewList);
                    }
                }
            }
        }
    };

    /**
     * 设置扫描超时时间
     *
     * @param timeout
     */
    public BLEHelper setTimeout(long timeout) {
        mTimeout = timeout;
        return this;
    }

    /**
     * 低功耗蓝牙扫描倒计时
     *
     * @author XUE
     */
    private class BLESearchCountDownTimer extends CountDownTimer {

        public BLESearchCountDownTimer(long timeout) {
            super(timeout * 1000, 1000);
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

        @Override
        public void onFinish() {
            stopSearch();
            if (mListener != null) {
                mListener.onSearchCompleted(mBondedList, mNewList);
            }
        }
    }

    /**
     * 开始扫描倒计时
     */
    public void startScanListener() {
        if (mTimeOutTimer == null) {
            mTimeOutTimer = new BLESearchCountDownTimer(mTimeout);
        }
        mTimeOutTimer.start();
    }

    /**
     * 取消倒计时
     */
    public void cancelScanListener() {
        if (mTimeOutTimer != null) {
            mTimeOutTimer.cancel();
            mTimeOutTimer = null;
        }
    }

    /**
     * 是否是指定的蓝牙设备
     *
     * @param device
     * @return
     */
    public boolean isCorrectDevice(BluetoothDevice device) {
        boolean isCorrectDevice = true;
        if (mBluetoothDeviceFilter != null) {
            isCorrectDevice = mBluetoothDeviceFilter.isCorrect(device);
        }
        return isCorrectDevice;
    }

    // ================================================蓝牙开启================================================//

    /**
     * 开启蓝牙
     */
    public void openBluetooth() {
        openBluetooth(null);
    }

    /**
     * 开启蓝牙
     */
    public void openBluetooth(final OnBluetoothOpenListener listener) {
        ThreadPoolManager.get().addTask(new Runnable() {
            @Override
            public void run() {
                boolean openResult = getOpenBluetoothResult();
                if (openResult) {
                    if (listener != null) {
                        listener.onBluetoothOpened();
                    }
                }
            }
        });
    }

    /**
     * 获取开启蓝牙的结果【耗时操作】
     */
    private boolean getOpenBluetoothResult() {
        boolean result = false;
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isEnabled()) {
                return true;
            } else {
                result = enableBluetooth();
            }
        }
        return result;
    }

    /**
     * 打开蓝牙开关【耗时操作】
     *
     * @return
     */
    private boolean enableBluetooth() {
        boolean result = false;
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.enable(); // 重新开启蓝牙
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (mBluetoothAdapter.isEnabled()) {
                result = true;
            }
        }
        return result;
    }
    // ================================================蓝牙扫描================================================//

    /**
     * 开始扫描蓝牙
     */
    public void startSearch() {
        stopSearch();
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        startScanListener();
    }

    /**
     * 停止扫描蓝牙
     */
    public void stopSearch() {
        if (mBluetoothAdapter == null) {
            return;
        }
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        cancelScanListener();
    }

    /**
     * 扫描蓝牙设备
     *
     * @param listener 蓝牙设备监听器
     */
    public void searchDevices(OnSearchDeviceListener listener) {
        mListener = listener;
        if (mBluetoothAdapter == null) {
            return;
        }
        if (mBondedList == null) {
            mBondedList = new ArrayList<>();
        }
        if (mNewList == null) {
            mNewList = new ArrayList<>();
        }
        mBondedList.clear();
        mNewList.clear();

        startSearch();
        if (mListener != null) {
            mListener.onStartDiscovery();
        }
    }

    // ================================================对象销毁================================================//

    /**
     * 资源释放
     */
    public void release() {
        stopSearch();

        mNewList = null;
        mBondedList = null;
        mListener = null;
        mBluetoothDeviceFilter = null;
    }

    // ================================================set/get================================================//

    /**
     * 是否已经开启蓝牙
     *
     * @return
     */
    public boolean isOpenBluetooth() {
        return mBluetoothAdapter.isEnabled();
    }

    /**
     * 设备蓝牙设备扫描监听
     *
     * @param listener
     */
    public BLEHelper setOnSearchDeviceListener(OnSearchDeviceListener listener) {
        mListener = listener;
        return this;
    }

    /**
     * 设置蓝牙设备过滤器
     *
     * @param deviceFilter
     */
    public BLEHelper setBluetoothDeviceFilter(IBluetoothDeviceFilter deviceFilter) {
        mBluetoothDeviceFilter = deviceFilter;
        return this;
    }

    public IBluetoothDeviceFilter getBluetoothDeviceFilter() {
        return mBluetoothDeviceFilter;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    /**
     * 根据地址获取蓝牙设备
     *
     * @param address
     * @return
     */
    public BluetoothDevice getBluetoothDevice(String address) {
        if (mBluetoothAdapter != null) {
            return mBluetoothAdapter.getRemoteDevice(address);
        } else {
            return BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
        }
    }

    /**
     * --------------------------------------------接口回调------------------------
     * --------------------------------
     **/
    /**
     * 设备扫描的监听
     *
     * @author xx
     */
    public interface OnSearchDeviceListener {

        /**
         * 开始扫描设备
         */
        void onStartDiscovery();

        /**
         * 发现一个新设备时回调
         *
         * @param device
         * @return true：已发现需要的设备，无需继续扫描
         */
        boolean onNewDeviceFound(BluetoothDevice device);

        /**
         * 扫描设备结束时回调
         */
        void onSearchCompleted(List<BluetoothDevice> bondedList, List<BluetoothDevice> newList);

    }


    /**
     * <pre>
     *     desc   : 蓝牙打开监听
     *     author : xuexiang
     *     time   : 2018/5/8 下午3:38
     * </pre>
     */
    public interface OnBluetoothOpenListener {
        /**
         * 蓝牙打开监听
         */
        void onBluetoothOpened();
    }

}


