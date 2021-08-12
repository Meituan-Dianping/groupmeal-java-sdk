package com.sankuai.groupmeal.base.exception;

/**
 * 业务异常
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/9 19:23
 */
public class GroupMealSDKException extends RuntimeException {
    public GroupMealSDKException() {
        super();
    }

    public GroupMealSDKException(String message) {
        super(message);
    }

    public GroupMealSDKException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupMealSDKException(Throwable cause) {
        super(cause);
    }

    protected GroupMealSDKException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
