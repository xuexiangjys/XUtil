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

package com.xuexiang.xutil.system.wifi;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import com.xuexiang.xutil.common.StringUtils;
import com.xuexiang.xutil.common.logger.Logger;
import com.xuexiang.xutil.net.NetworkUtils;
import com.xuexiang.xutil.system.ThreadPoolManager;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     desc   :	该类用来管理Wifi连接，做一些断开、连接的操作
 *     author : xuexiang
 *     time   : 2018/4/28 上午1:14
 * </pre>
 */
public final class WifiHelper {
    /**
     * 默认连接超时时间
     */
    private final static int DEFAULT_CONNECT_TIME_OUT = 25;

    private static volatile WifiHelper sInstance;
    /**
     * wifi管理对象
     */
    private final WifiManager mWifiManager;
    /**
     * wifi信息
     */
    private WifiInfo mWifiInfo;
    /**
     * 连接尝试时间
     */
    private int mTimeOut;
    /**
     * wifi连接状态监听
     */
    private OnWifiConnectStateListener mOnWifiConnectStateListener;

    // 扫描出的网络连接列表
    private List<ScanResult> mWifiList;
    // 网络连接列表
    private List<WifiConfiguration> mWifiConfiguration;
    /**
     * wifi锁，防止休眠后wifi断开
     */
    private WifiLock mWifiLock;

    /**
     * 设置网络连接状态监听
     *
     * @param listener
     * @return
     */
    public WifiHelper setOnConnectWifiStateListener(OnWifiConnectStateListener listener) {
        mOnWifiConnectStateListener = listener;
        return this;
    }

    /**
     * 设置连接超时时间
     *
     * @param timeOut
     * @return
     */
    public WifiHelper setConnectTimeOut(int timeOut) {
        mTimeOut = timeOut;
        return this;
    }

    /**
     * 构造器
     */
    private WifiHelper() {
        // 取得WifiManager对象
        mWifiManager = NetworkUtils.getWifiManager();
        // 取得WifiInfo对象
        mWifiInfo = mWifiManager.getConnectionInfo();
        mTimeOut = DEFAULT_CONNECT_TIME_OUT;
    }

    /**
     * 获取wifi连接助手
     *
     * @return
     */
    public static WifiHelper get() {
        if (sInstance == null) {
            synchronized (WifiHelper.class) {
                if (sInstance == null) {
                    sInstance = new WifiHelper();
                }
            }
        }
        return sInstance;
    }


    /**
     * 连接指定WIFI
     *
     * @param wifiSsid     wifi用户名
     * @param wifiPassword wifi密码
     */
    public void connectWifi(String wifiSsid, String wifiPassword) {
        ThreadPoolManager.get().addTask(new WifiConnectRunnable(wifiSsid, wifiPassword));
    }

    /**
     * 连接指定WIFI网络的线程
     *
     * @author xx
     */
    private class WifiConnectRunnable implements Runnable {
        /**
         * 路由名称
         */
        private final String mWifiSsid;
        /**
         * 路由密码
         */
        private final String mWifiPassword;

        public WifiConnectRunnable(String wifiSsid, String wifiPassword) {
            mWifiSsid = wifiSsid;
            mWifiPassword = wifiPassword;
        }

        @Override
        public void run() {
            if (!checkState()) {
                openWifi();// 打开wifi
                checkWifiEnable();

                try {
                    Thread.sleep(2000); //延时等待wifi自动连接
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    onCheckWifiStateFinished(false);
                    return;
                }
            }
            // 检测现有的连接是否是配置中的路由
            if (checkSSIDState(mWifiSsid)) {// 如果配置中的网络已连接，直接上传数据
                checkConnectWifi(mWifiSsid);
            } else {
                mWifiConfiguration = startScan();
                WifiConfiguration wcg = isExistSSID(mWifiConfiguration, mWifiSsid);
                if (wcg != null) {// 如果该网络已经保存在配置中,直接连到该网络
                    connectConfigurationWifi(wcg);
                } else {
                    int encryptionType = 1;// 加密类型
                    if (!StringUtils.isEmpty(mWifiPassword)) {
                        encryptionType = 3;
                    }
                    addNetwork(createWifiInfo(mWifiSsid, mWifiPassword, encryptionType));
                }
                checkConnectWifi(mWifiSsid);
            }
        }
    }

    /**
     * 检验wifi开关是否成功开启
     */
    private void checkWifiEnable() {
        int timeCount = 0;
        while (!checkState()) {// 每隔1秒钟检查一次wifi是否启用成功
            if (timeCount >= mTimeOut) {// 如果打开时间超时，退出这次打开
                onCheckWifiStateFinished(false);
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                onCheckWifiStateFinished(false);
                return;
            }
            timeCount++;
        }
    }

    /**
     * 检验wifi是否连接成功
     */
    private void checkConnectWifi(String wifiSsid) {
        int timeCount = 0;
        while (!isConnectSuccess(wifiSsid)) {// 每隔1秒钟检查一次是否连接上指定的网络
            if (timeCount >= mTimeOut) {// 如果打开时间超过25秒，退出这次连接
                onCheckWifiStateFinished(false);
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                onCheckWifiStateFinished(false);
                return;
            }
            timeCount++;
        }
        Logger.d("连接路由花费时间：" + timeCount + "秒");
        onCheckWifiStateFinished(true);
    }

    private void onCheckWifiStateFinished(boolean isConnectSuccess) {
        if (mOnWifiConnectStateListener != null) {
            if (isConnectSuccess) {
                mOnWifiConnectStateListener.onWifiConnectSuccess();
            } else {
                mOnWifiConnectStateListener.onWifiConnectFailed();
            }
        }
    }

    /**
     * 资源释放
     */
    public void release() {
        if (mWifiList != null && mWifiList.size() > 0) {
            mWifiList.clear();
        }
        if (mWifiConfiguration != null && mWifiConfiguration.size() > 0) {
            mWifiConfiguration.clear();
        }
        mOnWifiConnectStateListener = null;
    }

    //============================================wifi连接、断开===============================================================//

    /**
     * 创建网络连接配置
     *
     * @param SSID     路由名称
     * @param Password 路由密码
     * @param Type     加密类型
     * @return
     */
    private WifiConfiguration createWifiInfo(String SSID, String Password, int Type) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\""; //

        if (Type == 1) {  // WIFICIPHER_NOPASS
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        } else if (Type == 2) { // WIFICIPHER_WEP
            config.hiddenSSID = true;
            config.wepKeys[0] = "\"" + Password + "\"";
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            config.wepTxKeyIndex = 0;
        } else if (Type == 3) { // WIFICIPHER_WPA
            config.preSharedKey = "\"" + Password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            config.status = WifiConfiguration.Status.ENABLED;
        }
        return config;
    }

    /**
     * 指定配置好的网络进行连接
     *
     * @param index
     */
    public void connectConfiguration(int index) {
        // 索引大于配置好的网络索引返回
        if (index > mWifiConfiguration.size()) {
            return;
        }
        // 连接配置好的指定ID的网络
        mWifiManager.enableNetwork(mWifiConfiguration.get(index).networkId, true);
    }

    /**
     * 添加一个网络并连接
     *
     * @param wcg
     * @return
     */
    public boolean addNetwork(WifiConfiguration wcg) {
        int wcgID = mWifiManager.addNetwork(wcg);
        boolean result = mWifiManager.enableNetwork(wcgID, true);
        return result;
    }

    /**
     * 连接配置好的Wifi
     */
    public boolean connectConfigurationWifi(WifiConfiguration wcg) {
        return mWifiManager.enableNetwork(wcg.networkId, true);
    }

    /**
     * 断开指定ID的网络
     *
     * @param netId
     */
    public void disconnectWifi(int netId) {
        mWifiManager.disableNetwork(netId);
        mWifiManager.disconnect();
    }


    //============================================wifi开关、状态获取===============================================================//

    /**
     * 打开WIFI
     */
    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }

    /**
     * 关闭WIFI
     */
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    /**
     * 检查当前WIFI状态
     */
    public boolean checkState() {
        return mWifiManager.isWifiEnabled();
    }

    //=====================WifiLock========================//

    /**
     * 锁定WifiLock
     */
    public void acquireWifiLock(String tag) {
        if (mWifiLock == null) {
            mWifiLock = mWifiManager.createWifiLock(tag);
        }
        mWifiLock.acquire();
    }

    /**
     * 解锁WifiLock
     */
    public void releaseWifiLock() {
        // 判断时候锁定
        if (mWifiLock != null && mWifiLock.isHeld()) {
            mWifiLock.release();
        }
    }

    //============================================wifi路由状态的判断===============================================================//

    /**
     * 判断某个网络是否已保存在配置中
     *
     * @return
     */
    public WifiConfiguration isExistSSID(List<WifiConfiguration> wifiConfigurations, String SSID) {
        for (WifiConfiguration existingConfig : wifiConfigurations) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"") || existingConfig.SSID.equals(SSID)) {
                return existingConfig;
            }
        }
        return null;
    }

    /**
     * 判断某个网络有没有连接
     *
     * @param ssid
     * @return
     */
    public boolean checkSSIDState(String ssid) {
        mWifiInfo = mWifiManager.getConnectionInfo();
        if (mWifiInfo != null && getSSID() != null) {
            return getSSID().equals("\"" + ssid + "\"") || getSSID().equals(ssid);
        }
        return false;
    }

    /**
     * 判断wifi是否连接成功
     *
     * @return
     */
    public boolean isConnectSuccess(String wifiSsid) {
        return checkState() && checkSSIDState(wifiSsid) && NetworkUtils.isHaveInternet();
    }

    //============================================wifi路由扫描===============================================================//

    /**
     * 开始扫描网络
     */
    public List<WifiConfiguration> startScan() {
        mWifiManager.startScan();
        // 得到扫描结果
        mWifiList = mWifiManager.getScanResults();
        // 得到配置好的网络连接
        return mWifiManager.getConfiguredNetworks();
    }

    /**
     * 得到网络列表
     */
    public List<ScanResult> getWifiList() {
        return mWifiList;
    }

    /**
     * 查看扫描结果
     */
    public StringBuilder lookUpScan() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mWifiList.size(); i++) {
            stringBuilder.append("Index_" + Integer.valueOf(i + 1).toString() + ":");
            // 将ScanResult信息转换成一个字符串包
            // 其中把包括：BSSID、SSID、capabilities、frequency、level
            stringBuilder.append((mWifiList.get(i).SSID).toString());
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }

    /**
     * 获取扫描的结果
     *
     * @return
     */
    public List<String> getScanResultList() {
        List<String> list = new ArrayList<>();
        if (mWifiList != null && mWifiList.size() > 0) {
            for (int i = 0; i < mWifiList.size(); i++) {
                list.add(i + "号--  " + mWifiList.get(i).SSID);
            }
        }
        return list;
    }

    /**
     * 获取已保存配置的网络
     *
     * @return
     */
    public List<String> getConfigWifiList() {
        List<String> list = new ArrayList<>();
        if (mWifiConfiguration != null && mWifiConfiguration.size() > 0) {
            for (int i = 0; i < mWifiConfiguration.size(); i++) {
                list.add(i + "号--  " + mWifiConfiguration.get(i).SSID);
            }
        }
        return list;
    }

    /**
     * 判断能不能搜索到指定的网络
     *
     * @param SSID
     * @return
     */
    public boolean checkScanResult(String SSID) {
        if (mWifiList != null && mWifiList.size() > 0) {
            for (int i = 0; i < mWifiList.size(); i++) {
                if (mWifiList.get(i).SSID.equals(SSID) || mWifiList.get(i).SSID.equals("\"" + SSID + "\"")) {
                    return true;
                }
            }
        }
        return false;
    }

    //============================================wifi信息详情===============================================================//

    /**
     * 得到MAC地址
     */
    private String getMacAddress() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getMacAddress();
    }

    /**
     * 得到接入点的BSSID
     */
    private String getBSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getBSSID();
    }

    /**
     * 得到IP地址
     */
    private int getIpAddress() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getIpAddress();
    }

    /**
     * 得到连接的ID
     */
    private int getNetworkId() {
        return (mWifiInfo == null) ? 0 : mWifiInfo.getNetworkId();
    }

    /**
     * 得到WifiInfo的所有信息包
     */
    private String getWifiInfo() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.toString();
    }

    /**
     * 得到接入点的SSID
     */
    private String getSSID() {
        return (mWifiInfo == null) ? "NULL" : mWifiInfo.getSSID();
    }

    /**
     * 得到配置好的网络
     */
    public List<WifiConfiguration> getConfiguration() {
        return mWifiConfiguration;
    }

    /**
     * wifi连接状态监听
     */
    public interface OnWifiConnectStateListener {
        /**
         * 连接成功时调用
         */
        void onWifiConnectSuccess();

        /**
         * 连接wifi失败时调用
         */
        void onWifiConnectFailed();
    }
}
