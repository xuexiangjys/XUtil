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

package com.xuexiang.xutil.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresPermission;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.app.AppUtils;
import com.xuexiang.xutil.common.ShellUtils;
import com.xuexiang.xutil.common.StringUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.INTERNET;

/**
 * <pre>
 *     desc   : 设备相关工具类
 *     author : xuexiang
 *     time   : 2018/4/27 下午8:40
 * </pre>
 */
public final class DeviceUtils {
    private final static String ANDROID = "Android";

    private DeviceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取设备的所有信息
     * @return
     */
    public static Map<String, String> getDeviceInfos() {
        Map<String, String> deviceInfos = new TreeMap<>();
        // 反射机制
        Field[] fields = Build.class.getDeclaredFields();
        // 迭代Build的字段key-value 此处的信息主要是为了在服务器端手机各种版本手机报错的原因
        try {
            // 获得包管理器
            PackageManager packageManager = AppUtils.getPackageManager();
            // 得到该应用的信息，即主Activity
            PackageInfo packageInfo = packageManager.getPackageInfo(AppUtils.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                deviceInfos.put("packageName", packageInfo.packageName);
                deviceInfos.put("versionName", packageInfo.versionName);
                deviceInfos.put("versionCode", String.valueOf(packageInfo.versionCode));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                deviceInfos.put(field.getName(), StringUtils.toString(field.get("")));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return deviceInfos;
    }

    /**
     * 获取设备的所有信息
     * @return
     */
    public static String getDisplayDeviceInfos() {
        Map<String, String> deviceInfos = getDeviceInfos();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : deviceInfos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\r\n");
        }
        return sb.toString();
    }

    /**
     * 获取安卓系统版本
     *
     * @return
     */
    public static String getAndroidVersionName() {
        return ANDROID + " " + getSDKVersionName() + ", API " + getSDKVersionCode();
    }

    /**
     * 获取设备系统版本号
     *
     * @return 设备系统版本号
     */
    public static String getSDKVersionName() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备系统版本码
     *
     * @return 设备系统版本码
     */
    public static int getSDKVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取设备品牌
     *
     * @return
     */
    public static String getDeviceBrand() {
        return Build.BOARD + "  " + Build.MANUFACTURER;
    }

    /**
     * 获取设备 AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        return Settings.Secure.getString(
                XUtil.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
    }

    /**
     * 获取设备厂商
     * <p>如 Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取硬件信息
     * @return
     */
    public static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * 获取产品信息
     * @return
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取设备信息
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * 获取系统版本号
     * @return
     */
    public static String getDisplayVersion() {
        return Build.DISPLAY;
    }

    /**
     * 获取语言
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取国家
     * @param ctx
     * @return
     */
    public static String getCountry(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        Locale locale = Locale.getDefault();
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY ? tm.getSimCountryIso().toLowerCase(Locale.getDefault()) : locale.getCountry().toLowerCase(locale);
    }

    /**
     * 获取设备型号
     * <p>如 MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getDeviceModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim();
        } else {
            model = "";
        }
        return model;
    }

    /**
     * 获取设备 MAC 地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @return MAC 地址
     */
    @RequiresPermission(anyOf = {ACCESS_WIFI_STATE, INTERNET})
    public static String getMacAddress() {
        String macAddress = getMacAddressByWifiInfo();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByFile();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        return "please open wifi";
    }

    /**
     * 获取设备 MAC 地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     *
     * @return MAC 地址
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    @RequiresPermission(ACCESS_WIFI_STATE)
    private static String getMacAddressByWifiInfo() {
        try {
            @SuppressLint("WifiManagerLeak")
            WifiManager wifi = (WifiManager) XUtil.getContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) {
                    return info.getMacAddress();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备 MAC 地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @return MAC 地址
     */
    @RequiresPermission(INTERNET)
    private static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!"wlan0".equalsIgnoreCase(ni.getName())) {
                    continue;
                }
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备 MAC 地址
     *
     * @return MAC 地址
     */
    private static String getMacAddressByFile() {
        ShellUtils.CommandResult result = ShellUtils.execCommand("getprop wifi.interface", false);
        if (result.result == 0) {
            String name = result.successMsg;
            if (name != null) {
                result = ShellUtils.execCommand("cat /sys/class/net/" + name + "/address", false);
                if (result.result == 0) {
                    if (result.successMsg != null) {
                        return result.successMsg;
                    }
                }
            }
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 判断设备是否 root
     *
     * @return the boolean{@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 关机
     * <p>需要 root 权限或者系统权限 {@code <android:sharedUserId="android.uid.system" />}</p>
     */
    public static void shutdown() {
        ShellUtils.execCommand("reboot -p", true);
        Intent intent = new Intent("android.intent.action.ACTION_REQUEST_SHUTDOWN");
        intent.putExtra("android.intent.extra.KEY_CONFIRM", false);
        XUtil.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 重启
     * <p>需要 root 权限或者系统权限 {@code <android:sharedUserId="android.uid.system" />}</p>
     */
    public static void reboot() {
        ShellUtils.execCommand("reboot", true);
        Intent intent = new Intent(Intent.ACTION_REBOOT);
        intent.putExtra("nowait", 1);
        intent.putExtra("interval", 1);
        intent.putExtra("window", 0);
        XUtil.getContext().sendBroadcast(intent);
    }

    /**
     * 重启
     * <p>需系统权限 {@code <android:sharedUserId="android.uid.system" />}</p>
     *
     * @param reason 传递给内核来请求特殊的引导模式，如"recovery"
     */
    public static void reboot(final String reason) {
        PowerManager mPowerManager =
                (PowerManager) XUtil.getContext().getSystemService(Context.POWER_SERVICE);
        try {
            if (mPowerManager == null) {
                return;
            }
            mPowerManager.reboot(reason);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启到 recovery
     * <p>Requires root permission.</p>
     */
    public static void reboot2Recovery() {
        ShellUtils.execCommand("reboot recovery", true);
    }

    /**
     * 重启到 bootloader
     * <p>Requires root permission.</p>
     */
    public static void reboot2Bootloader() {
        ShellUtils.execCommand("reboot bootloader", true);
    }

}
