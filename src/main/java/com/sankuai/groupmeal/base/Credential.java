package com.sankuai.groupmeal.base;

/**
 * 接入凭证,配置有美团分配,需要区分线下联调和线上环境配置
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 20:00
 */
public class Credential {
    /**
     * 接入应用id
     */
    private String appId;
    /**
     * 接入应用签名秘钥
     */
    private String appSecret;

    public Credential(){
    }

    public Credential(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
