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

package com.xuexiang.xutil.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ShellUtils;
import com.xuexiang.xutil.common.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络工具类
 *
 * @author xuexiang
 * @date 2018/2/17 下午6:16
 */
public final class NetworkUtils {

    /**
     * Don't let anyone instantiate this class.
     */
    private NetworkUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 打开网络设置界面
     */
    public static void openWirelessSettings() {
        XUtil.getContext().startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 获取活动网络信息
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return NetworkInfo
     */
    @SuppressLint("MissingPermission")
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager manager = getConnectivityManager();
        if (manager == null) return null;
        return manager.getActiveNetworkInfo();
    }

    /**
     * 判断网络连接是否打开,包括移动数据连接
     *
     * @return 是否联网
     */
    public static boolean isNetworkAvailable() {
        boolean netState = false;
        ConnectivityManager manager = getConnectivityManager();
        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == State.CONNECTED) {
                        netState = true;
                        break;
                    }
                }
            }
        }
        return netState;
    }

    /**
     * 判断是否有网
     *
     * @return
     */
    public static boolean isHaveInternet() {
        try {
            ConnectivityManager connectivity = getConnectivityManager();
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Gps是否打开
     * 需要<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />权限
     *
     * @return true, if is gps enabled
     */
    public static boolean isGpsEnabled() {
        LocationManager lm = (LocationManager) XUtil.getContext().getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    /**
     * 判断当前网络是否是移动数据网络.
     *
     * @return boolean
     */
    public static boolean isMobile() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 检测当前打开的网络类型是否WIFI
     *
     * @return 是否是Wifi上网
     */
    public static boolean isWifi() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 检测当前打开的网络类型是否3G
     *
     * @return 是否是3G上网
     */
    public static boolean is3G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 检测当前开打的网络类型是否4G
     *
     * @return 是否是4G上网
     */
    public static boolean is4G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isConnectedOrConnecting()) {
            if (activeNetInfo.getType() == TelephonyManager.NETWORK_TYPE_LTE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 只是判断WIFI
     *
     * @return 是否打开Wifi
     */
    public static boolean isWiFi() {
        ConnectivityManager manager = getConnectivityManager();
        State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断 wifi 是否打开
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isWifiEnabled() {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = getWifiManager();
        return manager != null && manager.isWifiEnabled();
    }

    /**
     * 打开或关闭 wifi
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />}</p>
     *
     * @param enabled {@code true}: 打开<br>{@code false}: 关闭
     */
    @SuppressLint("MissingPermission")
    public static void setWifiEnabled(final boolean enabled) {
        @SuppressLint("WifiManagerLeak")
        WifiManager manager = getWifiManager();
        if (manager == null) return;
        if (enabled) {
            if (!manager.isWifiEnabled()) {
                manager.setWifiEnabled(true);
            }
        } else {
            if (manager.isWifiEnabled()) {
                manager.setWifiEnabled(false);
            }
        }
    }

    /**
     * 判断网络是否可用
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     * <p>需要异步 ping，如果 ping 不通就说明网络不可用</p>
     * <p>ping 的 ip 为阿里巴巴公共 ip：223.5.5.5</p>
     *
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isAvailableByPing() {
        return isAvailableByPing(null);
    }

    /**
     * 判断网络是否可用
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     * <p>需要异步 ping，如果 ping 不通就说明网络不可用</p>
     *
     * @param ip ip 地址（自己服务器 ip），如果为空，ip 为阿里巴巴公共 ip
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isAvailableByPing(String ip) {
        if (ip == null || ip.length() <= 0) {
            ip = "223.5.5.5";// 阿里巴巴公共 ip
        }
        ShellUtils.CommandResult result = ShellUtils.execCommand(String.format("ping -c 1 %s", ip), false);
        boolean ret = result.result == 0;
        if (result.errorMsg != null) {
            Logger.dTag("NetworkUtils", "isAvailableByPing() called" + result.errorMsg);
        }
        if (result.successMsg != null) {
            Logger.dTag("NetworkUtils", "isAvailableByPing() called" + result.successMsg);
        }
        return ret;
    }

    /**
     * 判断移动数据是否打开
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean getMobileDataEnabled() {
        try {
            TelephonyManager tm =
                    (TelephonyManager) XUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) return false;
            @SuppressLint("PrivateApi")
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 打开或关闭移动数据
     * <p>需系统应用 需添加权限
     * {@code <uses-permission android:name="android.permission.MODIFY_PHONE_STATE" />}</p>
     *
     * @param enabled {@code true}: 打开<br>{@code false}: 关闭
     */
    public static void setMobileDataEnabled(final boolean enabled) {
        try {
            TelephonyManager tm =
                    (TelephonyManager) XUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) return;
            Method setMobileDataEnabledMethod =
                    tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            if (null != setMobileDataEnabledMethod) {
                setMobileDataEnabledMethod.invoke(tm, enabled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取WifiManager
     * @return
     */
    public static WifiManager getWifiManager() {
        return (WifiManager) XUtil.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取ConnectivityManager
     *
     * @return
     */
    public static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) XUtil.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    //==================================================================IP相关====================================================================//

    /**
     * 获取本地Ip地址
     *
     * @return local ip adress or null if not found
     */
    public static InetAddress getLocalInetAddress() {
        WifiManager wifiMgr = (WifiManager) XUtil.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (isWiFi()) {
            int ipAsInt = wifiMgr.getConnectionInfo().getIpAddress();
            if (ipAsInt == 0) {
                return null;
            } else {
                return intToInet(ipAsInt);
            }
        } else {
            try {
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();
                    Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress address = addresses.nextElement();
                        if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
                            return address;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * IP地址校验
     *
     * @param ip 待校验是否是IP地址的字符串
     * @return 是否是IP地址
     */
    public static boolean isIP(String ip) {
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * IP转化成int数字
     *
     * @param addr IP地址
     * @return Integer
     */
    public static int ipToInt(String addr) {
        String[] addrArray = addr.split("\\.");
        int num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;
            num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
        }
        return num;
    }

    /**
     * IP转化成String显示
     *
     * @param addr
     * @return
     */
    public static String ipToString(int addr) {
        if (addr == 0) {
            return null;
        }
        return ipToString(addr, ".");
    }

    public static InetAddress intToInet(int value) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = byteOfInt(value, i);
        }
        try {
            return InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    private static byte byteOfInt(int value, int which) {
        int shift = which * 8;
        return (byte) (value >> shift);
    }

    private static String ipToString(int addr, String sep) {
        if (addr > 0) {
            StringBuffer buf = new StringBuffer();
            buf.
                    append(byteOfInt(addr, 0)).append(sep).
                    append(byteOfInt(addr, 1)).append(sep).
                    append(byteOfInt(addr, 2)).append(sep).
                    append(byteOfInt(addr, 3));
            return buf.toString();
        } else {
            return null;
        }
    }

    //==============================================================网络状态==========================================================================//

    /**
     * 枚举网络状态  NET_NO：没有网络 , NET_2G:2g网络 , NET_3G：3g网络, NET_4G：4g网络, NET_WIFI：wifi, NET_ETHERNET：有线网络, NET_UNKNOWN：未知网络
     */
    public static enum NetState {
        NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_ETHERNET, NET_UNKNOWN
    }


    /**
     * 判断当前是否网络连接
     *
     * @return 状态码
     */
    public static NetState isConnected() {
        NetState stateCode = NetState.NET_NO;
        ConnectivityManager cm = getConnectivityManager();
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    stateCode = NetState.NET_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            stateCode = NetState.NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            stateCode = NetState.NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            stateCode = NetState.NET_4G;
                            break;
                        default:
                            stateCode = NetState.NET_UNKNOWN;
                            break;
                    }
                    break;
                case ConnectivityManager.TYPE_ETHERNET:
                    stateCode = NetState.NET_ETHERNET;
                    break;
                default:
                    stateCode = NetState.NET_UNKNOWN;
                    break;
            }

        }
        return stateCode;
    }

    /**
     * 获取移动运营商
     *
     * @param context
     * @return
     */
    public static String getNetworkOperatorName(Context context) {
        String networkOperatorName = "未知";
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                networkOperatorName = "中国移动";
            } else if (operator.equals("46001") || operator.equals("46006")) {
                networkOperatorName = "中国联通";
            } else if (operator.equals("46003") || operator.equals("46005") || operator.equals("46011")) {
                networkOperatorName = "中国电信";
            }
        }
        return networkOperatorName;
    }

    /**
     * 获取网络运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return 运营商名称
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm =
                (TelephonyManager) XUtil.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 获取URL中参数 并返回Map
     *
     * @param url
     * @return
     */
    public static Map<String, String> getUrlParams(String url) {
        Map<String, String> params = null;
        try {
            String[] urlParts = url.split("\\?");
            if (urlParts.length > 1) {
                params = new HashMap<String, String>();
                String query = urlParts[1];
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    String key = URLDecoder.decode(pair[0], "UTF-8");
                    String value = "";
                    if (pair.length > 1) {
                        value = URLDecoder.decode(pair[1], "UTF-8");
                    }
                    params.put(key, value);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return params;
    }

    /**
     * 解析前：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult?appId=10101
     * 解析后：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult
     * @param url
     * @return
     */
    public static String parseUrl(String url) {
        if (!"".equals(url) && url.contains("?")) {// 如果URL不是空字符串
            url = url.substring(0, url.indexOf('?'));
        }
        return url;
    }


    /**
     * url是否有效合法
     *
     * @param url 匹配 http://www.allkins.com | http://255.255.255.255 | http://allkins.com/page.asp?action=1
     *            不匹配 http://test.testing
     * @return
     */
    public static boolean isUrlValid(String url) {
        String expr = "^(http|https|ftp)\\://(((25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])\\.){3}(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[1-9][0-9]|[0-9])|([a-zA-Z0-9_\\-\\.])+\\.(com|cn|net|org|edu|int|mil|gov|arpa|biz|aero|name|coop|info|pro|museum|uk|me))((:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&%\\$#\\=~])*)$";
        return url.matches(expr);
    }

    /**
     * 从Url中下载文件
     *
     * @param urlStr   资源地址
     * @param fileName 文件名
     * @param savePath 文件保存路径
     */
    public static void downLoadFileByUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        // 防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
        // 得到输入流
        InputStream inputStream = conn.getInputStream();
        // 获取自己数组
        byte[] getData = readInputStream(inputStream);
        // 文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
