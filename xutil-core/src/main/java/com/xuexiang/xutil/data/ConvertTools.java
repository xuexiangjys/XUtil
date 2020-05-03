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

package com.xuexiang.xutil.data;

import com.xuexiang.xutil.common.StringUtils;

import java.util.Locale;

/**
 * <pre>
 *     desc   :	 <p>转换相关工具类</p>
 * 				(【小端】低位在前，高位在后)
 * 				<p>(【大端】高位在前，低位在后) 符合我们正常的阅读习惯，在默认情况下，一般都是大端存储。</p>
 *     author : xuexiang
 *     time   : 2018/4/30 下午12:19
 * </pre>
 */
public final class ConvertTools {

    private ConvertTools() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String byte2HexString(byte byte1) {
        return bytes2HexString(new byte[]{byte1});
    }

    /**
     * byte数组转16进制String
     * <p>
     * 例如：
     * </p>
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8
     *
     * @param bytes 字节数组
     * @return 16进制大写字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int len = bytes.length;
        if (len <= 0) {
            return null;
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /***
     * byte[] 转16进制字符串
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * byte[]数组转换为16进制的字符串
     *
     * @param data 要转换的字节数组
     * @return 转换后的结果
     */
    public static final String byteArrayToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte b : data) {
            int v = b & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.getDefault());
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param hexString 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }
        int len = hexString.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        byte[] d = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            d[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return d;
    }

    /**
     * hexString转byteArr
     *
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (StringUtils.isEmpty(hexString)) {
            return null;
        }
        if (hexString.length() % 2 != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    // =========================一位byte和int的转换==================================//

    /**
     * 一位byte转int【无符号】
     *
     * @param b
     * @return 【0 ~ 255】
     */
    public static int byteToIntUnSigned(byte b) {
        return b & 0xFF;
    }

    /**
     * 一位byte转int【有符号】
     *
     * @param b
     * @return 【-128 ~ 127】
     */
    public static int byteToIntSigned(byte b) {
        return b;
    }

    /**
     * int转Byte 【仅对0~255的整型有效】
     *
     * @param value 【0~255】
     * @return
     */
    public static byte intToByte(int value) {
        return (byte) value;
    }

    // ======================【byte数组<-->（无符号）int】=====================================//

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(【小端】低位在前，高位在后)的顺序。 和
     * {@link #bytesToIntLittleEndian(byte[], int)}配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytesLittleEndian(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) (value & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[3] = (byte) ((value >> 24) & 0xFF);
        return src;
    }

    /**
     * 将int数值填充至byte数组的指定位置，本方法适用于(【小端】低位在前，高位在后)的顺序
     *
     * @param value  填充的int值
     * @param src    需要填充的byte数组
     * @param offset 填充的位置
     */
    public static void fillIntToBytesLittleEndian(int value, byte[] src, int offset) {
        src[offset] = (byte) (value & 0xFF);
        src[offset + 1] = (byte) ((value >> 8) & 0xFF);
        src[offset + 2] = (byte) ((value >> 16) & 0xFF);
        src[offset + 3] = (byte) ((value >> 24) & 0xFF);
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(【大端】高位在前，低位在后)的顺序。 和
     * {@link #bytesToIntBigEndian(byte[], int)}配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytesBigEndian(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将int数值填充至byte数组的指定位置，本方法适用于(【大端】高位在前，低位在后)的顺序
     *
     * @param value  填充的int值
     * @param src    需要填充的byte数组
     * @param offset 填充的位置
     */
    public static void fillIntToBytesBigEndian(int value, byte[] src, int offset) {
        src[offset] = (byte) ((value >> 24) & 0xFF);
        src[offset + 1] = (byte) ((value >> 16) & 0xFF);
        src[offset + 2] = (byte) ((value >> 8) & 0xFF);
        src[offset + 3] = (byte) (value & 0xFF);
    }

    /**
     * byte数组中取int数值，本方法适用于(【小端】低位在前，高位在后)的顺序，和
     * {@link #intToBytesLittleEndian(int)}配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return int数值
     */
    public static int bytesToIntLittleEndian(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16) | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }

    /**
     * byte数组中取int数值，本方法适用于(【大端】高位在前，低位在后)的顺序。和{@link #intToBytesBigEndian(int)}
     * 配套使用
     */
    public static int bytesToIntBigEndian(byte[] src, int offset) {
        int value;
        value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16) | ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3] & 0xFF));
        return value;
    }

    // ======================【byte数组<-->（无符号）short】=====================================//

    /**
     * 将short数值转换为占两个字节的byte数组，本方法适用于(【小端】低位在前，高位在后)的顺序。 和
     * {@link #bytesToShortLittleEndian(byte[], int)}配套使用
     *
     * @param value 要转换的short值
     * @return byte数组
     */
    public static byte[] shortToBytesLittleEndian(short value) {
        byte[] src = new byte[2];
        src[0] = (byte) (value & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        return src;
    }

    /**
     * 将short数值填充至byte数组的指定位置，本方法适用于(【小端】低位在前，高位在后)的顺序
     *
     * @param value  填充的short值
     * @param src    需要填充的byte数组
     * @param offset 填充的位置
     */
    public static void fillShortToBytesLittleEndian(short value, byte[] src, int offset) {
        src[offset] = (byte) (value & 0xFF);
        src[offset + 1] = (byte) ((value >> 8) & 0xFF);
    }

    /**
     * 将无符号short数值填充至byte数组的指定位置，本方法适用于(【小端】低位在前，高位在后)的顺序
     * 【因为是无符号short数值，java中short默认是有符号的，不能满足无符号short数值的表示范围，因此使用int数值来表示】
     *
     * @param value  填充的无符号short数值
     * @param src    需要填充的byte数组
     * @param offset 填充的位置
     */
    public static void fillUnsignedShortToBytesLittleEndian(int value, byte[] src, int offset) {
        src[offset] = (byte) (value & 0xFF);
        src[offset + 1] = (byte) ((value >> 8) & 0xFF);
    }

    /**
     * 将short数值转换为占两个字节的byte数组，本方法适用于(【大端】高位在前，低位在后)的顺序。 和
     * {@link #bytesToShortBigEndian(byte[], int)}配套使用
     *
     * @param value 要转换的short值
     * @return byte数组
     */
    public static byte[] shortToBytesBigEndian(short value) {
        byte[] src = new byte[2];
        src[0] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * 将short数值填充至byte数组的指定位置，本方法适用于(【大端】高位在前，低位在后)的顺序
     *
     * @param value  填充的short值
     * @param src    需要填充的byte数组
     * @param offset 填充的位置
     */
    public static void fillShortToBytesBigEndian(short value, byte[] src, int offset) {
        src[offset] = (byte) ((value >> 8) & 0xFF);
        src[offset + 1] = (byte) (value & 0xFF);
    }

    /**
     * 将无符号short数值填充至byte数组的指定位置，本方法适用于(【大端】高位在前，低位在后)的顺序
     * 【因为是无符号short数值，java中short默认是有符号的，不能满足无符号short数值的表示范围，因此使用int数值来表示】
     *
     * @param value  填充的无符号short数值
     * @param src    需要填充的byte数组
     * @param offset 填充的位置
     */
    public static void fillUnsignedShortToBytesBigEndian(int value, byte[] src, int offset) {
        src[offset] = (byte) ((value >> 8) & 0xFF);
        src[offset + 1] = (byte) (value & 0xFF);
    }

    /**
     * byte数组中取short数值，本方法适用于(【小端】低位在前，高位在后)的顺序，和
     * {@link #shortToBytesLittleEndian(short)}配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return short数值
     */
    public static short bytesToShortLittleEndian(byte[] src, int offset) {
        short value;
        value = (short) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8));
        return value;
    }

    /**
     * byte数组中取short数值，本方法适用于(【大端】高位在前，低位在后)的顺序。和
     * {@link #shortToBytesBigEndian(short)}配套使用
     *
     * @param src    byte数组
     * @param offset 从数组的第offset位开始
     * @return short数值
     */
    public static short bytesToShortBigEndian(byte[] src, int offset) {
        short value;
        value = (short) (((src[offset] & 0xFF) << 8) | (src[offset + 1] & 0xFF));
        return value;
    }


}
