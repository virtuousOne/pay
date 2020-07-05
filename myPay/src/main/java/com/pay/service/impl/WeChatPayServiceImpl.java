package com.pay.service.impl;


import com.pay.bean.constant.PayTypeEnum;
import com.pay.service.PaymentService;
import org.springframework.stereotype.Service;

/**
 * @Author: 吴宸煊
 * Date: 2020/7/5 14:27
 * Description:
 */
@Service
public class WeChatPayServiceImpl implements PaymentService {
    @Override
    public String pay(String type) {
        System.out.println("处理微信支付业务");
        return "处理微信支付业务";
    }

    @Override
    public PayTypeEnum getPayType() {
        return PayTypeEnum.WEIXIN;
    }
}
