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
package com.xuexiang.xutil.security;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * <pre>
 *     desc   :	Base64工具类
 *     author : xuexiang
 *     time   : 2018/4/28 上午1:12
 * </pre>
 */
public final class Base64Utils {

	private static final Charset UTF_8 = Charset.forName("UTF-8");

	/**
	 * Don't let anyone instantiate this class.
	 */
	private Base64Utils() {
		throw new UnsupportedOperationException("u can't instantiate me...");
	}


	/** The Constant base64EncodeChars. */
    private static final char[] BASE_64_ENCODE_CHARS = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
			'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
	
    /** The Constant base64DecodeChars. */
    private static final byte[] BASE_64_DECODE_CHARS = new byte[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1,
			-1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
			21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
			39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

	/**
	 * Encode.(加密）
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String encode(String str) {
		return encode(str, UTF_8.name(), 0);
	}

	/**
	 * Decode.(解密）
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String decode(String str) {
		return decode(str, UTF_8.name());
	}

    /**
     * Encode.
     *
     * @param str the str
     * @param charsetName the charset name
     * @return the string
     */
    public static String encode(String str, String charsetName) {
		return encode(str, charsetName, 0);
	}
	
    /**
     * Encode.
     *
     * @param str the str
     * @param charsetName the charset name
     * @param width the width
     * @return the string
     */
    public static String encode(String str, String charsetName, int width) {
		byte[] data = null;
		try {
			data = str.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		int length = data.length;
		int size = (int) Math.ceil(length * 1.36);
		int splitsize = width > 0 ? size / width : 0;
		StringBuffer sb = new StringBuffer(size + splitsize);
		int r = length % 3;
		int len = length - r;
		int i = 0;
		int c;
		while (i < len) {
			c = (0x000000ff & data[i++]) << 16 | (0x000000ff & data[i++]) << 8 | (0x000000ff & data[i++]);
			sb.append(BASE_64_ENCODE_CHARS[c >> 18]);
			sb.append(BASE_64_ENCODE_CHARS[c >> 12 & 0x3f]);
			sb.append(BASE_64_ENCODE_CHARS[c >> 6 & 0x3f]);
			sb.append(BASE_64_ENCODE_CHARS[c & 0x3f]);
		}
		if (r == 1) {
			c = 0x000000ff & data[i++];
			sb.append(BASE_64_ENCODE_CHARS[c >> 2]);
			sb.append(BASE_64_ENCODE_CHARS[(c & 0x03) << 4]);
			sb.append("==");
		} else if (r == 2) {
			c = (0x000000ff & data[i++]) << 8 | (0x000000ff & data[i++]);
			sb.append(BASE_64_ENCODE_CHARS[c >> 10]);
			sb.append(BASE_64_ENCODE_CHARS[c >> 4 & 0x3f]);
			sb.append(BASE_64_ENCODE_CHARS[(c & 0x0f) << 2]);
			sb.append("=");
		}
		if (splitsize > 0) {
			char split = '\n';
			for (i = width; i < sb.length(); i++) {
				sb.insert(i, split);
				i += width;
			}
		}
		return sb.toString();
	}
	
	/**
	 * Decode.(解密）
	 *
	 * @param str the str
	 * @param charsetName the charset name
	 * @return the string
	 */
	public static String decode(String str, String charsetName) {
		byte[] data = null;
		try {
			data = str.getBytes(charsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream((int) (len * 0.67));
		int i = 0;
		int b1, b2, b3, b4;
		while (i < len) {
			do {
				if (i >= len) {
					b1 = -1;
					break;
				}
				b1 = BASE_64_DECODE_CHARS[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1) {
				break;
			}
			do {
				if (i >= len) {
					b2 = -1;
					break;
				}
				b2 = BASE_64_DECODE_CHARS[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1) {
				break;
			}
			buf.write((b1 << 2) | ((b2 & 0x30) >>> 4));
			do {
				if (i >= len) {
					b3 = -1;
					break;
				}
				b3 = data[i++];
				if (b3 == 61) {
					b3 = -1;
					break;
				}
				b3 = BASE_64_DECODE_CHARS[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1) {
				break;
			}
			buf.write(((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2));
			do {
				if (i >= len) {
					b4 = -1;
					break;
				}
				b4 = data[i++];
				if (b4 == 61) {
					b4 = -1;
					break;
				}
				b4 = BASE_64_DECODE_CHARS[b4];
			} while (b4 == -1);
			if (b4 == -1) {
				break;
			}
			buf.write(((b3 & 0x03) << 6) | b4);
		}
		try {
			return buf.toString(charsetName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}