package com.sankuai.groupmeal.base.constant;

import java.nio.charset.Charset;

/**
 * 配置常量
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 19:27
 */
public class Constants {
    /**
     * 所有都是UTF-8编码
     */
    public static Charset UTF_8 = Charset.forName("UTF-8");
    /**
     * 签名算法类型
     */
    public static SignType SIGN_TYPE = SignType.MD5;

    /**
     * 连接超时时间 单位秒(默认10s)
     */
    public static int CONNECT_TIMEOUT = 10;
    /**
     * 写超时时间 单位秒(默认 0 , 不超时)
     */
    public static int WRITE_TIMEOUT = 0;
    /**
     * 回复超时时间 单位秒(默认30s)
     */
    public static int READ_TIMEOUT = 10;
    /**
     * 底层HTTP库所有的并发执行的请求数量
     */
    public static int DISPATCHER_MAX_REQUESTS = 64;
    /**
     * 底层HTTP库对每个独立的Host进行并发请求的数量
     */
    public static int DISPATCHER_MAX_REQUESTS_PER_HOST = 32;
    /**
     * 底层HTTP库中复用连接对象的最大空闲数量
     */
    public static int CONNECTION_POOL_MAX_IDLE_COUNT = 32;
    /**
     * 底层HTTP库中复用连接对象的回收周期（单位分钟）
     */
    public static int CONNECTION_POOL_MAX_IDLE_MINUTES = 5;
}
