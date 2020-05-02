package com.pay.controller.ali;

import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import com.pay.config.ali.AliPayConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 吴宸煊
 * Date: 2020/5/2 17:44
 * Description: H5支付和APP支付回掉
 */
@RestController
@RequestMapping("/notice/pay")
@AllArgsConstructor
@Slf4j
public class AliPayNoticeController {


    @RequestMapping("/payNotice")
    public String orderNotify(HttpServletRequest request) {
        String alipayNotice = aliPayNotify(request);
        return alipayNotice;
    }

    /**
     * 功能描述 支付回调
     *
     * @param request
     * @return java.lang.String
     */
    public String aliPayNotify(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        try {
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            log.info("支付宝回调，sign:{},trade_status:{},参数:{},out_trade_no:{},参数:{}", params.get("sign"),
                    params.get("trade_status"), params.get("out_trade_no"), params.toString());
            //调用SDK验证签名，验证是阿里回调，而不是其他恶意回调
            boolean flag = AlipaySignature.rsaCheckV1(params, AliPayConfig.ALIPAY_PUBLIC_KEY, "UTF-8", "RSA2");
            log.info("flag的值是:" + flag);
            // 验证成功
            if (flag) { // 验证成功
                if (null == params.get("out_trade_no")) {
                    return "failue";
                }
                // 处理业务逻辑
                return "success";
            }
            return "fail";

        } catch (Exception e) {
            log.info("订单回调异常:{}" + params.get("out_trade_no"));
            return "fail";
        }
    }
}
