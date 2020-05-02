package com.pay.controller.weChat;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.wxpay.sdk.WXPayUtil;
import com.pay.config.weChat.WeChatConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 吴宸煊
 * Date: 2020/5/2 17:52
 * Description: 微信:小程序支付回掉
 */
@RestController
@RequestMapping("/notice")
@AllArgsConstructor
@Slf4j
public class WeChatPayNoticeController {

    //这里是支付回调接口，微信支付成功后会自动调用
    @PostMapping("/weChatPay")
    public String wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map orderMap = new HashMap();
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String notityXml = sb.toString();
        String resXml = "";
        Map resPrint = new HashMap();
        Map<String, String> resultMap = WXPayUtil.xmlToMap(notityXml);
        String returnCode = (String) resultMap.get("return_code");//业务结果
        String orderNo = resultMap.get("out_trade_no");//订单号
        String sign = resultMap.get("sign");//获取微信签名
        resultMap.remove("sign");//去除签名字段
        String signNew = WXPayUtil.generateSignature(resultMap, WeChatConfig.WECHAT_key); //重新签名
        if (signNew.equals(sign)) {
            if ("SUCCESS".equals(returnCode)) {
                System.out.println(signNew + "ppppp");
                resPrint.put("return_code", "SUCCESS");
                resPrint.put("return_msg", "ok");
                resXml = WXPayUtil.mapToXml(resPrint);
                orderMap.put("orderStatus", 1);
                orderMap.put("orderNo", orderNo);
                // 自己的业务逻辑

                return WxPayNotifyResponse.success("成功");
            } else {
                System.out.println("业务结果失败");
                return null;//WxPayNotifyResponse.success("code:" + 9999 + "微信回调结果异常,异常原因:");
            }

        } else {
            resPrint.put("return_code", "FAIL");
            resPrint.put("return_msg", "签名失败");
            resXml = WXPayUtil.mapToXml(resPrint);
        }
        log.info(resXml);
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
        br.close();
        return null;
    }
}
