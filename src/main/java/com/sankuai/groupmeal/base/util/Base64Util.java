package com.sankuai.groupmeal.base.util;

import org.apache.commons.codec.binary.Base64;

import static com.sankuai.groupmeal.base.constant.Constants.UTF_8;

/**
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 11:07
 */
public class Base64Util {
    public static String encode(String content) {
        return encode(content.getBytes(UTF_8));
    }

    public static String encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static String decode(String base64) {
        return new String(Base64.decodeBase64(base64), UTF_8);
    }

    public static String decode(byte[] base64Bytes) {
        return new String(Base64.decodeBase64(base64Bytes), UTF_8);
    }
}
