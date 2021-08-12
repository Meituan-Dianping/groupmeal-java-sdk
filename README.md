# Java SDK

## 源码目录结构
**Java SDK主要目录结构**

    com.sankuai.groupmeal
           ├── base                                //基础相关
           │       └── http                        //http通信相关类
           │       └── util                        //工具类
           │       └── logger                      //日志
           │       └── constant                    //常量
           │       └── exception                   //异常相关
           │       └── GlobalConfig                //全局配置
           │       └── Credential                  //接入凭证
           ├── business                            //业务入口
           │       └── pay                         //支付

## 安装
使用 Maven:
```xml
<dependency>
    <groupId>com.sankuai.groupmeal</groupId>
    <artifactId>groupmeal-java-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 运行环境

JDK 1.8 及以上

## 使用方法

### 支付成功回调
```Java
//打开全局日志开关,打开后会打印请求header、body信息，以及响应的header
GlobalConfig.printTraceSwitch(true);
//appId,appSecret 修改为自己的配置信息
String appId = "tcqd_demo03";
String appSecret = "7EptKX5BG6h6bNDE8gGxAkXvtyG8CUeY";
//环境，需要区分测试联调和线上环境
EnvType envType = EnvType.TEST;
//构造支付接口实例,最后单例
PayClient payClient = new PayClient(new Credential(appId, appSecret), envType);
//构造支付回调业务参数
PayCallbackParam param = new PayCallbackParam();
param.setTradeNo("604557080286524400");
param.setStatus(0);
param.setPaymentId("1");
param.setTotalAmount("1300");
param.setNotifyTime(System.currentTimeMillis() / 1000 + "");

PayCallbackResult result = payClient.payNotifyCallback(param);
System.out.println("result: " + JsonUtil.encode2UnderScore(result));
if (result.success()) {
    //回调成功处理逻辑
    System.out.println("回调成功");
    System.out.println("paymentId: " + result.getData());
}

switch (result.code()) {
    case PAY_CALL_FAIL:
        System.out.println("支付回调失败,无需重试,需要双方排查原因");
        break;
    case SIGN_NOT_MATCH:
        System.out.println("签名不匹配,需要排查");
        break;
    case UNKNOWN:
        System.out.println("未知异常,可以尝试间隔重试,设置最大重试次数");
        break;
}

```

## 详细使用文档

参考[美团企业用餐开放平台](https://eps.meituan.com/open/2_channel/sdk)

