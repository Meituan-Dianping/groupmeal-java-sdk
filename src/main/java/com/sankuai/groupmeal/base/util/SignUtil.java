package com.sankuai.groupmeal.base.util;

import com.sankuai.groupmeal.base.constant.SignType;
import com.sankuai.groupmeal.base.exception.GroupMealSDKException;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 签名工具
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 11:07
 */
public class SignUtil {
    /**
     * 签名
     * 模板： app_id=%s&app_secret=%s&sign=%s&timestamp
     *
     * @param signType
     * @param appId
     * @param appSecret
     * @param bytes
     * @param timestamp
     * @return
     */
    public static String sign(String appId, String appSecret, SignType signType,
                              byte[] bytes, String timestamp) {
        String signStr = String.format("app_id=%s&app_secret=%s&sign=%s&timestamp=%s",
                appId, appSecret, Base64Util.encode(bytes), timestamp);
        switch (signType) {
            case SHA256:
                return sha256Signature(signStr);
            case MD5:
                return MD5(signStr);
            default:
                throw new GroupMealSDKException("未知签名方式");
        }
    }

    public static String sha256Signature(String data) {
        return DigestUtils.sha256Hex(data);
    }

    public static String MD5(String data) {
        return DigestUtils.md5Hex(data);
    }
}
