package com.sankuai.groupmeal.base.constant;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.sankuai.groupmeal.base.exception.Preconditions;

/**
 * 业务接口
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/11 11:56
 */
public enum EndPoint {
    PAY_NOTIFY("支付成功回调",
            "https://openapi.test.meituan.com/oapi/channel/pay/notify",
            "https://openapi.waimai.meituan.com/oapi/channel/pay/notify");

    private String desc;
    private String test;
    private String prod;

    EndPoint(String desc, String test, String prod) {
        this.desc = desc;
        this.test = test;
        this.prod = prod;
    }

    public String getTest() {
        return test;
    }

    public String getProd() {
        return prod;
    }

    /**
     * 接口地址与环境配置
     */
    public static Table<EnvType, EndPoint, String> END_POINT_TABLE = HashBasedTable.create();

    static {
        END_POINT_TABLE.put(EnvType.TEST, EndPoint.PAY_NOTIFY, EndPoint.PAY_NOTIFY.getTest());
        END_POINT_TABLE.put(EnvType.PROD, EndPoint.PAY_NOTIFY, EndPoint.PAY_NOTIFY.getProd());
    }

    public static String getEndPoint(EnvType envType, EndPoint endPoint) {
        String endPointValue = END_POINT_TABLE.get(envType, endPoint);
        Preconditions.checkNotNull(endPointValue, "获取不到接口地址[%s]", endPoint.desc);
        return endPointValue;
    }
}
