package com.sankuai.groupmeal.business.pay;

import com.sankuai.groupmeal.base.constant.Code;
import com.sankuai.groupmeal.business.Result;

/**
 * 渠道支付成功回调美团结果
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/10 22:36
 */
public class PayCallbackResult implements Result {
    /**
     * 0	成功	无需重试
     * 10100	回调单支付失败,不用重试了	渠道需要自己进行资金返还
     */
    private int code;
    /**
     * 请求成功返回的业务数据,无需关注
     */
    private Object data;
    /**
     * 异常原因描述
     */
    private String message;

    @Override
    public boolean success() {
        return getCode() == Code.OK.getValue();
    }

    @Override
    public Code code() {
        return Code.of(this.code);
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
