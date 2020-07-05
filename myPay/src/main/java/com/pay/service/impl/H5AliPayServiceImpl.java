package com.pay.service.impl;


import com.pay.bean.constant.PayTypeEnum;
import com.pay.service.PaymentService;
import org.springframework.stereotype.Service;

/**
 * @Author: 吴宸煊
 * Date: 2020/7/5 14:28
 * Description:
 */
@Service
public class H5AliPayServiceImpl implements PaymentService {

    @Override
    public String pay(String type) {
        System.out.println("处理H5请求支付宝支付");
        return "处理H5请求支付宝支付";
    }

    @Override
    public PayTypeEnum getPayType() {
        return PayTypeEnum.H5ALIPAY;
    }
}
