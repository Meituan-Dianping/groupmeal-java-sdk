package com.sankuai.groupmeal.business.pay;

import com.sankuai.groupmeal.base.util.JsonUtil;

/**
 * 支付成功回调参数
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 20:04
 */
public class PayCallbackParam {
    /**
     * 0：成功；1失败
     */
    private int status;
    /**
     * 描述
     */
    private String message;
    /**
     * 美团测交易流水号
     */
    private String tradeNo;
    /**
     * 渠道侧的支付号
     */
    private String paymentId;
    /**
     * 回调时间戳 unixtime 秒时间戳
     */
    private String notifyTime;
    /**
     * 支付金额(分)
     */
    private String totalAmount;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return JsonUtil.encode2UnderScore(this);
    }
}
