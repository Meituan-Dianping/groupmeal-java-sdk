package com.sankuai.groupmeal.business.pay;

import com.sankuai.groupmeal.base.Credential;
import com.sankuai.groupmeal.base.constant.EndPoint;
import com.sankuai.groupmeal.base.constant.EnvType;
import com.sankuai.groupmeal.base.exception.Preconditions;
import com.sankuai.groupmeal.base.http.ClientProxy;
import com.sankuai.groupmeal.base.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 支付接口
 * 使用时最好用单例模式使用,声明为类全局变量或者系统全局变量,不要每次请求都创建 PayClient
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @link {PayClientTest}
 * @created 2021/8/10 22:35
 */
public final class PayClient {
    private static ClientProxy client = ClientProxy.getInstance();
    private Credential credential;
    private EnvType envType;

    /**
     * @param credential 美团分配给渠道的接入凭证
     * @param envType    环境类型,需要三方自己控制环境参数,区分测试环境和线上环境
     */
    public PayClient(Credential credential, EnvType envType) {
        Preconditions.checkNotNull(credential);
        Preconditions.checkNotNull(envType);
        this.credential = credential;
        this.envType = envType;
    }

    /**
     * 三方支付成功回调美团侧请求
     *
     * @param param 回调业务参数
     * @return
     */
    public PayCallbackResult payNotifyCallback(PayCallbackParam param) {
        Preconditions.checkNotNull(param);
        String requestBody = JsonUtil.encode2UnderScore(param);
        String responseBody = client.call(EndPoint.getEndPoint(envType, EndPoint.PAY_NOTIFY), requestBody, credential);
        Preconditions.checkArgument(StringUtils.isNotBlank(responseBody), "支付回调接口返回为空");
        return JsonUtil.decode2Camel(responseBody, PayCallbackResult.class);
    }
}
