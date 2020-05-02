package com.pay.util.weChat;

import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Maps;
import com.pay.bean.weChat.WeChatPayDto;

import com.pay.config.weChat.WeChatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import static com.github.wxpay.sdk.WXPayUtil.*;
import static com.pay.util.weChat.HttpUtils.getCurrentTimestamp;


/**
 * @Created by Wu Chenxuan
 * @Date 2020/4/27
 * @Description
 */
@Slf4j
@Component
public class WeChatPayUtil {


    public Map<String, String> getPrePayInfo(WeChatPayDto miniDTO, String openId) throws Exception {
        Map<String, String> map = Maps.newHashMap();
        map.put("appid", WeChatConfig.WECHAT_APPID);
        map.put("mch_id", WeChatConfig.WECHAT_MACH_ID);
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body", miniDTO.getBody());
        map.put("out_trade_no", miniDTO.getOutTradeNo());
        map.put("total_fee", miniDTO.getTotalFee());
        map.put("spbill_create_ip", getLocalIp());
        map.put("trade_type", WeChatConfig.tradeType);
        map.put("notify_url", WeChatConfig.NOTIFYURL);
        map.put("openid", openId);
        String unifiedorderUrl = WeChatConfig.UNIFIED_ORDER_URL; // 微信统一下单URL
        String sign = generateSignature(map, WeChatConfig.WECHAT_key);// 生成签名 PAY_API_SECRET=微信支付相关API调用时使用的秘钥
        map.put("sign", sign);  // 参数配置 我直接写成"sign"
        String xml = mapToXml(map);
        //请求微信统一下单接口
        String xmlStr = HttpUtils.httpRequest(unifiedorderUrl, "POST", xml);

        Map map1 = HttpUtils.doXMLParse(xmlStr);
        String return_code = (String) map1.get("return_code");//返回状态码
        String result_code = (String) map1.get("result_code");//返回状态码
        String err_code = (String) map1.get("err_code");//返回状态码
        String err_code_des = (String) map1.get("err_code_des");//返回状态码
        log.info(xmlStr);
        if (return_code.equals("SUCCESS") || return_code.equals(result_code)) {
            // 业务结果
            String prepay_id = (String) map1.get("prepay_id");//返回的预付单信息
            Map<String, String> payMap = new HashMap<>();
            payMap.put("appId", WeChatConfig.WECHAT_APPID);  // 参数配置
            payMap.put("timeStamp", getCurrentTimestamp() + "");  //时间
            payMap.put("nonceStr", generateNonceStr());  // 获取随机字符串
            payMap.put("signType", "MD5");
            payMap.put("package", "prepay_id=" + prepay_id);
            String paySign = generateSignature(payMap, WeChatConfig.WECHAT_key); //第二次生成签名
            payMap.put("paySign", paySign);
            payMap.put("prepayId", prepay_id);
            return payMap;   //返回给前端，让前端去调支付 ，完成后你去调支付查询接口，看支付结果，处理业务。
        } else {
            //打印失败日志
        }
        return null;

    }

    /**
     * 获取当前机器的ip
     *
     * @return String
     */
    public static String getLocalIp() {
        InetAddress ia = null;
        String localip = null;
        try {
            ia = ia.getLocalHost();
            localip = ia.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localip;

    }


}
