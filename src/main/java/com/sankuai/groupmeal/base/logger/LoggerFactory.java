package com.sankuai.groupmeal.base.logger;

import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * 日志工程,配置文件自定义
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/12 17:16
 */
public class LoggerFactory {
    static {
        try {
            // 读取配置文件
            ClassLoader cl = LoggerFactory.class.getClassLoader();
            InputStream inputStream;
            if (cl != null) {
                inputStream = cl.getResourceAsStream("log.properties");
            } else {
                inputStream = ClassLoader.getSystemResourceAsStream("log.properties");
            }
            // 重新初始化日志属性并重新读取日志配置
            LogManager.getLogManager().readConfiguration(inputStream);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * 获取日志对象
     *
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class clazz) {
        Logger logger = Logger.getLogger(clazz.getName());
        return logger;
    }
}
