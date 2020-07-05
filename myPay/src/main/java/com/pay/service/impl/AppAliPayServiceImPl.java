package com.pay.service.impl;


import com.pay.bean.constant.PayTypeEnum;
import com.pay.service.PaymentService;
import org.springframework.stereotype.Service;

/**
 * @Author: 吴宸煊
 * Date: 2020/7/5 14:29
 * Description:
 */
@Service
public class AppAliPayServiceImPl implements PaymentService {
    @Override
    public String pay(String type) {
        System.out.println("APP请求支付宝支付");
        return "APP请求支付宝支付";
    }

    @Override
    public PayTypeEnum getPayType() {
        return PayTypeEnum.APPALIPAY;
    }
}
