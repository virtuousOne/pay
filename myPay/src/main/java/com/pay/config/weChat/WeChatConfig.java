package com.pay.config.weChat;

/**
 * @Author: 吴宸煊
 * Date: 2020/5/2 17:53
 * Description:
 */
public class WeChatConfig {

    /**
     * 微信appId
     */
    public static final String WECHAT_APPID = "***";

    /**
     * 微信商户号
     */
    public static final String WECHAT_MACH_ID = "***";

    /**
     * key
     */
    public static final String WECHAT_key = "****";

    /**
     * 支付类型，小程序用:JSAPI
     */
    public static final String tradeType = "JSAPI";

    /**
     * 回掉地址
     */
    public static final String NOTIFYURL = "http://localhost:8080/notice/weChatPay/notify";

    /**
     * 统一下单地址
     */
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
