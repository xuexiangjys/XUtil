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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import com.xuexiang.xutil.XUtil;
import com.xuexiang.xutil.common.ShellUtils;
import com.xuexiang.xutil.common.logger.Logger;
import com.xuexiang.xutil.file.CloseUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.INTERNET;

/**
 * <pre>
 *     desc   : 网络工具类
 *     author : xuexiang
 *     time   : 2018/4/28 上午12:53
 * </pre>
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
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager manager = getConnectivityManager();
        if (manager == null) {
            return null;
        }
        return manager.getActiveNetworkInfo();
    }

    /**
     * 判断网络连接是否打开,包括移动数据连接
     *
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return 是否联网
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable() {
        boolean netState = false;
        ConnectivityManager manager = getConnectivityManager();
        if (manager != null) {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == State.CONNECTED) {
                        netState = true;
                        break;
                    }
                }
            }
        }
        return netState;
    }

    /**
     * 判断当前是否有网
     *
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: 有网<br>{@code false}: 无网
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
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
     * @return {@code true}: enabled<br>{@code false}: disabled
     */
    @SuppressLint("MissingPermission")
    @RequiresPermission(ACCESS_FINE_LOCATION)
    public static boolean isGpsEnabled() {
        LocationManager lm = (LocationManager) XUtil.getContext().getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    /**
     * 判断当前网络是否是移动数据网络.
     *
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: enabled<br>{@code false}: disabled
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isMobile() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 检测当前打开的网络类型是否是WIFI
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: connected<br>{@code false}: disconnected
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isWifi() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 检测当前打开的网络类型是否是3G
     *
     * @return 是否是3G上网
     */
    public static boolean is3G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 检测当前开打的网络类型是否是4G
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return 是否是4G上网
     */
    public static boolean is4G() {
        NetworkInfo activeNetInfo = getActiveNetworkInfo();
        return activeNetInfo != null && activeNetInfo.isConnectedOrConnecting() && activeNetInfo.getType() == TelephonyManager.NETWORK_TYPE_LTE;
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
        if (manager == null) {
            return;
        }
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
            // 阿里巴巴公共 ip
            ip = "223.5.5.5";
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
            if (tm == null) {
                return false;
            }
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
     * 获取WifiManager
     *
     * @return
     */
    public static WifiManager getWifiManager() {
        return (WifiManager) XUtil.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取获取连接管理者ConnectivityManager
     *
     * @return 获取连接管理者
     */
    public static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) XUtil.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    //==================================================================IP相关====================================================================//

    /**
     * 遍历获取本地Ip地址
     *
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />}</p>
     *
     * @return 设备本地Ip地址
     */
    @RequiresPermission(ACCESS_WIFI_STATE)
    public static InetAddress getLocalInetAddress() {
        WifiManager wifiManager = getWifiManager();
        if (wifiManager != null && isWifi()) {
            return getWifiInetAddress(wifiManager);
        } else {
            return getInetAddressByEnumerate();
        }
    }

    /**
     * 通过枚举网络接口获取ip地址
     *
     * @return
     */
    @Nullable
    private static InetAddress getInetAddressByEnumerate() {
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (networkInterfaces != null) {
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
        }
        return null;
    }

    /**
     * 获取wifi的ip地址
     *
     * @param wifiManager
     * @return
     */
    @Nullable
    private static InetAddress getWifiInetAddress(WifiManager wifiManager) {
        int ipAsInt = wifiManager.getConnectionInfo().getIpAddress();
        if (ipAsInt == 0) {
            return null;
        } else {
            return intToInet(ipAsInt);
        }
    }

    private static InetAddress intToInet(int value) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = byteOfInt(value, i);
        }
        try {
            return InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * IP转化成int数字
     *
     * @param address IP地址
     * @return Integer
     */
    public static int ipToInt(String address) {
        String[] addressArray = address.split("\\.");
        int num = 0;
        for (int i = 0; i < addressArray.length; i++) {
            int power = 3 - i;
            num += ((Integer.parseInt(addressArray[i]) % 256 * Math.pow(256, power)));
        }
        return num;
    }

    /**
     * IP转化成String显示
     *
     * @param address ip地址
     * @return
     */
    public static String ipToString(int address) {
        if (address == 0) {
            return null;
        }
        return ipToString(address, ".");
    }

    private static String ipToString(int address, String sep) {
        if (address > 0) {
            StringBuffer buf = new StringBuffer();
            buf.
                    append(byteOfInt(address, 0)).append(sep).
                    append(byteOfInt(address, 1)).append(sep).
                    append(byteOfInt(address, 2)).append(sep).
                    append(byteOfInt(address, 3));
            return buf.toString();
        } else {
            return null;
        }
    }

    private static byte byteOfInt(int value, int which) {
        int shift = which * 8;
        return (byte) (value >> shift);
    }

    /**
     * Return the ip address.
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param useIPv4 True to use ipv4, false otherwise.
     * @return the ip address
     */
    @RequiresPermission(INTERNET)
    public static String getIPAddress(final boolean useIPv4) {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> adds = new LinkedList<>();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                // To prevent phone of xiaomi return "10.0.2.15"
                if (!ni.isUp() || ni.isLoopback()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    adds.addFirst(addresses.nextElement());
                }
            }
            for (InetAddress add : adds) {
                if (!add.isLoopbackAddress()) {
                    String hostAddress = add.getHostAddress();
                    boolean isIPv4 = hostAddress.indexOf(':') < 0;
                    if (useIPv4) {
                        if (isIPv4) {
                            return hostAddress;
                        }
                    } else {
                        if (!isIPv4) {
                            int index = hostAddress.indexOf('%');
                            return index < 0
                                    ? hostAddress.toUpperCase()
                                    : hostAddress.substring(0, index).toUpperCase();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Return the ip address of broadcast.
     *
     * @return the ip address of broadcast
     */
    public static String getBroadcastIpAddress() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            LinkedList<InetAddress> adds = new LinkedList<>();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isUp() || ni.isLoopback()) {
                    continue;
                }
                List<InterfaceAddress> ias = ni.getInterfaceAddresses();
                for (int i = 0; i < ias.size(); i++) {
                    InterfaceAddress ia = ias.get(i);
                    InetAddress broadcast = ia.getBroadcast();
                    if (broadcast != null) {
                        return broadcast.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取域名 ip 地址
     * <p>Must hold {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param domain The name of domain.
     * @return the domain address
     */
    @RequiresPermission(INTERNET)
    public static String getDomainAddress(final String domain) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(domain);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
    }

    //==============================================================网络状态==========================================================================//

    /**
     * 枚举网络状态  NET_NO：没有网络 , NET_2G:2g网络 , NET_3G：3g网络, NET_4G：4g网络, NET_5G：5g网络, NET_WIFI：wifi, NET_ETHERNET：有线网络, NET_UNKNOWN：未知网络
     */
    public enum NetState {
        /**
         * 没有网络
         */
        NET_NO,
        /**
         * 2g网络
         */
        NET_2G,
        /**
         * 3g网络
         */
        NET_3G,
        /**
         * 4g网络
         */
        NET_4G,
        /**
         * 5g网络
         */
        NET_5G,
        /**
         * wifi
         */
        NET_WIFI,
        /**
         * 有线网络
         */
        NET_ETHERNET,
        /**
         * 未知网络
         */
        NET_UNKNOWN
    }

    /**
     * 判断当前是否网络连接,返回当前网络状态的类型
     * * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return type of network
     * <ul>
     * <li>{@link NetworkUtils.NetState#NET_WIFI   } </li>
     * <li>{@link NetworkUtils.NetState#NET_5G     } </li>
     * <li>{@link NetworkUtils.NetState#NET_4G     } </li>
     * <li>{@link NetworkUtils.NetState#NET_3G     } </li>
     * <li>{@link NetworkUtils.NetState#NET_2G     } </li>
     * <li>{@link NetworkUtils.NetState#NET_UNKNOWN} </li>
     * <li>{@link NetworkUtils.NetState#NET_NO     } </li>
     * </ul>
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static NetState getNetStateType() {
        if (isEthernet()) {
            return NetState.NET_ETHERNET;
        }
        NetworkInfo info = getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NetState.NET_WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetState.NET_2G;

                    case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetState.NET_3G;

                    case TelephonyManager.NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetState.NET_4G;

                    case TelephonyManager.NETWORK_TYPE_NR:
                        return NetState.NET_5G;
                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA")
                                || subtypeName.equalsIgnoreCase("CDMA2000")) {
                            return NetState.NET_3G;
                        } else {
                            return NetState.NET_UNKNOWN;
                        }
                }
            } else {
                return NetState.NET_UNKNOWN;
            }
        }
        return NetState.NET_NO;
    }

    /**
     * 是否是以太网
     * <p>Must hold
     * {@code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    @RequiresPermission(ACCESS_NETWORK_STATE)
    private static boolean isEthernet() {
        final ConnectivityManager cm = getConnectivityManager();
        if (cm == null) {
            return false;
        }
        final NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (info == null) {
            return false;
        }
        NetworkInfo.State state = info.getState();
        if (null == state) {
            return false;
        }
        return state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING;
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
            switch (operator) {
                case "46000":
                case "46002":
                case "46007":
                    networkOperatorName = "中国移动";
                    break;
                case "46001":
                case "46006":
                    networkOperatorName = "中国联通";
                    break;
                case "46003":
                case "46005":
                case "46011":
                    networkOperatorName = "中国电信";
                    break;
                default:
                    break;
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
                params = new HashMap<>();
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
     * 解析网络请求的url
     * 解析前：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult?appId=10101
     * 解析后：https://xxx.xxx.xxx/app/chairdressing/skinAnalyzePower/skinTestResult
     *
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
     * 从Url中下载文件
     *
     * <p>需添加权限
     * {@code <uses-permission android:name="android.permission.INTERNET" />}</p>
     *
     * @param urlStr   资源地址
     * @param fileName 文件名
     * @param savePath 文件保存路径
     */
    @RequiresPermission(INTERNET)
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
        CloseUtils.closeIO(fos, inputStream);
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
