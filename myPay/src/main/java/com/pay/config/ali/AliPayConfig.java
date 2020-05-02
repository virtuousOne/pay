package com.pay.config.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.pay.util.ali.AliPayUtil;
import org.springframework.stereotype.Component;

/**
 * @Author: 吴宸煊
 * Date: 2020/5/2 17:50
 * Description: 支付宝配置信息
 */
@Component
public class AliPayConfig {
    /**
     * 支付宝的appId
     */
    public static final String ALIPAY_APP_APPID = "****";

    /**
     *  商户私钥
     */
    public static final String ALIPAY_PRIVATE_KEY = "***";

    /**
     * 支付宝公钥
     */
    public static final String ALIPAY_PUBLIC_KEY = "***";
    /**
     * 支付宝网关地址： 沙箱环境用：https://openapi.alipaydev.com/gateway.do
     */
    public static final String ALIPAY_GATEWAY_URL = "https://openapi.alipay.com/gateway.do";

    public static final String SIGN_TYPE = "RSA2";

    public static final String FORMAT = "json";

    public static final String CHARSET = "UTF-8";

    private static AlipayClient alipayClient = null;

    public static AlipayClient getAlipayClient() throws AlipayApiException {
        if (alipayClient == null) {
            synchronized (AliPayUtil.class) {
                if (null == alipayClient) {
                    alipayClient = new DefaultAlipayClient(ALIPAY_GATEWAY_URL, ALIPAY_APP_APPID, ALIPAY_PRIVATE_KEY,
                            FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
                }
            }
        }
        return alipayClient;
    }
}
