package com.sankuai.groupmeal.base.logger;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/12 16:54
 */
public class LoggerFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return String.format("[%s]--[%s] %s#%s %s \n", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"),
                record.getLevel(), Thread.currentThread().getName(), record.getLoggerName(), record.getMessage());
    }
}
