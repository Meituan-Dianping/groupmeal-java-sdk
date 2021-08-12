package com.sankuai.groupmeal.base.http.interceptor;

import com.sankuai.groupmeal.base.logger.MyLogger;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 日志拦截器
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/12 16:44
 */
public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        String requestId = request.header("MT-SDK-RequestId");
        String req =
                ("send request, request_id: " + requestId + " \n" + request.headers());
        MyLogger.log(this.getClass(), req);
        long start = System.currentTimeMillis();
        Response response = chain.proceed(request);
        String resp =
                ("recieve response, request_id: " + requestId + " cost:" + (System.currentTimeMillis() - start) + "\n" + response.headers());
        MyLogger.log(this.getClass(), resp);
        return response;
    }
}
