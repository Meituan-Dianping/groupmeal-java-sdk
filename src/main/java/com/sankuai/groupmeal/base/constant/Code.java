package com.sankuai.groupmeal.base.constant;

/**
 * 接口code码
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/12 11:11
 */
public enum Code {
    UNKNOWN(-1, "未知原因,需要重试"),
    OK(0, "成功"),
    PAY_CALL_FAIL(10100, "回调单支付失败,不用重试了"),
    SIGN_NOT_MATCH(40001, "签名结果不匹配"),
    ;

    private int value;
    private String text;

    Code(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static Code of(int code) {
        for (Code c : Code.values()) {
            if (c.getValue() == code) {
                return c;
            }
        }
        return Code.UNKNOWN;
    }
}
