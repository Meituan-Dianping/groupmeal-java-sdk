package com.sankuai.groupmeal.base.constant;

import com.sankuai.groupmeal.base.exception.GroupMealSDKException;

/**
 * 签名算法类型
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 19:25
 */
public enum SignType {
    MD5("MD5"), SHA256("SHA256");

    SignType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public static SignType of(String value) {
        switch (value) {
            case "MD5":
                return MD5;
            case "SHA256":
                return SHA256;
            default:
                throw new GroupMealSDKException("不支持的签名算法");
        }
    }
}
