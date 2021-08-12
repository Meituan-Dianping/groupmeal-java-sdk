package com.sankuai.groupmeal.base;

/**
 * 全局配置
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/11 19:32
 */
public final class GlobalConfig {
    /**
     * true 打印过程日志,false 不打印过程日志,建议接入初期可以打开日志,稳定后可以关掉
     */
    private static boolean printTrace = true;

    /**
     * 响应结果签名验证开关,默认关闭,打开后会对响应结果进行签名验证
     */
    private static boolean respSignVerifySwitch = false;

    public static void printTraceSwitch(boolean printTraceLog) {
        GlobalConfig.printTrace = printTraceLog;
    }

    public static boolean printTraceSwitch() {
        return GlobalConfig.printTrace;
    }

    public static void respSignVerifySwitch(boolean respSignVerifySwitch) {
        GlobalConfig.respSignVerifySwitch = respSignVerifySwitch;
    }

    public static boolean respSignVerifySwitch() {
        return respSignVerifySwitch;
    }
}
