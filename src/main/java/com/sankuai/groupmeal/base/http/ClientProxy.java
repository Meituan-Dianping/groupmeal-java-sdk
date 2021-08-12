package com.sankuai.groupmeal.base.http;

import com.sankuai.groupmeal.base.Credential;
import com.sankuai.groupmeal.base.GlobalConfig;
import com.sankuai.groupmeal.base.constant.Constants;
import com.sankuai.groupmeal.base.constant.SignType;
import com.sankuai.groupmeal.base.exception.GroupMealSDKException;
import com.sankuai.groupmeal.base.exception.Preconditions;
import com.sankuai.groupmeal.base.logger.MyLogger;
import com.sankuai.groupmeal.base.util.SignUtil;
import okhttp3.Headers;
import okhttp3.Response;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/10 21:56
 */
public final class ClientProxy {
    private static final String SDK_VERSION = "SDK_JAVA_1.0.0";
    public static final int HTTP_RSP_OK = 200;
    private static final Client CLIENT = new Client();
    private static final ClientProxy INSTANCE = new ClientProxy();

    private ClientProxy() {
    }

    public static ClientProxy getInstance() {
        return INSTANCE;
    }

    /**
     * post 请求，body为json格式字符串
     *
     * @param url         请求地址
     * @param jsonPayload 请求体json串
     * @param credential  三方接入凭证
     * @return
     */
    public String call(String url, String jsonPayload, Credential credential) {
        String contentType = "application/json; charset=utf-8";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        byte[] requestPayload = jsonPayload.getBytes(StandardCharsets.UTF_8);
        String sign = SignUtil.sign(credential.getAppId(), credential.getAppSecret(), Constants.SIGN_TYPE, requestPayload, timestamp);
        String requestId = System.currentTimeMillis() + "." + RandomUtils.nextInt(1, 100000);
        Headers headers = new Headers.Builder()
                .add("M-SpanName", getPath(url))
                .add("Content-Type", contentType)
                .add("MT-SDK-Version", SDK_VERSION)
                .add("MT-SDK-RequestId", requestId)
                .add("App-Id", credential.getAppId())
                .add("Sign", sign)
                .add("Sign-Type", Constants.SIGN_TYPE.name())
                .add("Timestamp", timestamp)
                .build();
        MyLogger.log(this.getClass(), "req requestId:" + requestId + " body: " + jsonPayload);
        Response response = CLIENT.postRequest(url, requestPayload, headers);
        if (response.code() != HTTP_RSP_OK) {
            throw new GroupMealSDKException("接口请求失败");
        }

        try {
            byte[] responseBody = response.body().bytes();
            String responseBodyStr = new String(responseBody);
            MyLogger.log(this.getClass(), "res requestId:" + requestId + " body: " + responseBodyStr);
            respSignVerify(credential, response.headers(), responseBody);
            return responseBodyStr;
        } catch (IOException e) {
            String msg = "Cannot transfer response body to string, because Content-Length is too large, or Content-Length and stream length disagree.";
            throw new GroupMealSDKException(msg, e);
        } catch (Exception e) {
            throw new GroupMealSDKException(e);
        }
    }

    private void respSignVerify(Credential credential, Headers headers, byte[] responseBody) {
        if (!GlobalConfig.respSignVerifySwitch()) {
            return;
        }
        String respSign = headers.get("Sign");
        String respTimestamp = headers.get("Timestamp");
        SignType signType = SignType.of(headers.get("Sign-Type"));
        String respCalSign = SignUtil.sign(credential.getAppId(), credential.getAppSecret(), signType, responseBody,
                respTimestamp);
        String mtTraceId = headers.get("M-TraceId");
        boolean signVerify = StringUtils.equals(respCalSign, respSign);
        if (GlobalConfig.printTraceSwitch() && !signVerify) {
            MyLogger.log(this.getClass(),
                    "res sign not match M-TraceId:" + mtTraceId + " respSign|calSign:" + respSign + "|" + respCalSign);
        }
        Preconditions.checkArgument(signVerify, "响应结果签名不匹配");
    }

    private String getPath(String url) {
        try {
            return new URL(url).getPath();
        } catch (Exception e) {
            MyLogger.log(this.getClass(), e.getMessage());
            return "";
        }
    }
}
