package com.sankuai.groupmeal.base.constant;

/**
 * 环境
 *
 * @author zhengxiaoluo
 * @version 1.0
 * @created 2021/8/11 11:38
 */
public enum EnvType {
    TEST("测试联调环境"), PROD("线上正式环境");

    private String desc;

    EnvType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
