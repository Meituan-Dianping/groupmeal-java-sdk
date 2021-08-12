package com.sankuai.groupmeal.base.logger;

import com.sankuai.groupmeal.base.GlobalConfig;

/**
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/11 20:17
 */
public final class MyLogger {
    public static void log(Class cls, String msg) {
        if (GlobalConfig.printTraceSwitch()) {
            LoggerFactory.getLogger(cls).info(msg);
        }
    }
}
