package com.sankuai.groupmeal.base.http;

import com.sankuai.groupmeal.base.constant.Constants;
import com.sankuai.groupmeal.base.exception.GroupMealSDKException;
import com.sankuai.groupmeal.base.http.interceptor.LogInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

/**
 * 封装http请求客户端
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 20:35
 */
public class Client {
    private final OkHttpClient httpClient;

    /**
     * 构建一个默认配置的 HTTP Client 类
     */
    public Client() {
        this(Constants.CONNECT_TIMEOUT, Constants.READ_TIMEOUT, Constants.WRITE_TIMEOUT,
                Constants.DISPATCHER_MAX_REQUESTS, Constants.DISPATCHER_MAX_REQUESTS_PER_HOST,
                Constants.CONNECTION_POOL_MAX_IDLE_COUNT, Constants.CONNECTION_POOL_MAX_IDLE_MINUTES);
    }

    /**
     * 构建一个自定义配置的 HTTP Client 类
     */
    public Client(Configuration cfg) {
        this(
                cfg.connectTimeout, cfg.readTimeout, cfg.writeTimeout,
                cfg.dispatcherMaxRequests, cfg.dispatcherMaxRequestsPerHost,
                cfg.connectionPoolMaxIdleCount, cfg.connectionPoolMaxIdleMinutes);
    }

    /**
     * 构建一个自定义配置的 HTTP Client 类
     */
    public Client(int connTimeout, int readTimeout, int writeTimeout, int dispatcherMaxRequests,
                  int dispatcherMaxRequestsPerHost, int connectionPoolMaxIdleCount,
                  int connectionPoolMaxIdleMinutes) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(dispatcherMaxRequests);
        dispatcher.setMaxRequestsPerHost(dispatcherMaxRequestsPerHost);
        ConnectionPool connectionPool = new ConnectionPool(connectionPoolMaxIdleCount,
                connectionPoolMaxIdleMinutes, TimeUnit.SECONDS);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.dispatcher(dispatcher);
        builder.connectionPool(connectionPool);
        builder.connectTimeout(connTimeout, TimeUnit.SECONDS);
        builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        builder.addInterceptor(new LogInterceptor());
        httpClient = builder.build();
    }

    public Response getRequest(String url, Headers headers) {
        try {
            Request request = new Request.Builder().url(url).get().headers(headers).build();
            return this.doRequest(request);
        } catch (IllegalArgumentException e) {
            throw new GroupMealSDKException(e);
        }
    }


    public Response postRequest(String url, String body, Headers headers) {
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(contentType, body))
                    .headers(headers)
                    .build();
            return this.doRequest(request);

        } catch (Exception e) {
            throw new GroupMealSDKException(e);
        }

    }

    public Response postRequest(String url, byte[] body, Headers headers) {
        MediaType contentType = MediaType.parse(headers.get("Content-Type"));
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .post(RequestBody.create(contentType, body))
                    .headers(headers)
                    .build();
            return this.doRequest(request);
        } catch (IllegalArgumentException e) {
            throw new GroupMealSDKException(e);
        }
    }

    private Response doRequest(Request request) {
        try {
            return this.httpClient.newCall(request).execute();
        } catch (Exception e) {
            throw new GroupMealSDKException(e);
        }
    }
}
