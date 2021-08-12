import com.sankuai.groupmeal.base.Credential;
import com.sankuai.groupmeal.base.GlobalConfig;
import com.sankuai.groupmeal.base.constant.EnvType;
import com.sankuai.groupmeal.base.util.JsonUtil;
import com.sankuai.groupmeal.business.pay.PayCallbackParam;
import com.sankuai.groupmeal.business.pay.PayCallbackResult;
import com.sankuai.groupmeal.business.pay.PayClient;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/11 15:15
 */
public class PayClientTest {
    private PayClient payClient;

    @Before
    public void setUp() {
        /**
         * 打开全局日志开关,打开后会打印请求header、body信息，以及响应的header
         */
        GlobalConfig.printTraceSwitch(true);
        /**
         * appId,appSecret 修改为自己的接入配置
         */
        String appId = "tcqd_demo03";
        String appSecret = "7EptKX5BG6h6bNDE8gGxAkXvtyG8CUeY";
        /**
         * 环境，需要区分测试联调和线上环境
         */
        EnvType envType = EnvType.TEST;
        payClient = new PayClient(new Credential(appId, appSecret), envType);
    }

    @Test
    public void payCallback() {
        // 构造业务参数
        PayCallbackParam param = new PayCallbackParam();
        param.setTradeNo("604557080286524400");
        param.setStatus(0);
        param.setPaymentId("1");
        param.setTotalAmount("1300");
        param.setNotifyTime(System.currentTimeMillis() / 1000 + "");

        PayCallbackResult result = payClient.payNotifyCallback(param);
        System.out.println("result: " + JsonUtil.encode2UnderScore(result));
        if (result.success()) {
            /**
             * 回调成功处理逻辑
             */
            System.out.println("回调成功");
            System.out.println("paymentId: " + result.getData());
        }

        switch (result.code()) {
            case PAY_CALL_FAIL:
                System.out.println("支付回调失败,无需重试,需要双方排查原因");
                break;
            case SIGN_NOT_MATCH:
                System.out.println("签名不匹配,需要排查");
                break;
            case UNKNOWN:
                System.out.println("未知异常,可以尝试间隔重试,设置最大重试次数");
                break;
        }
    }
}
