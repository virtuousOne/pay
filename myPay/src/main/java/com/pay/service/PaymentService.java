package com.pay.service;


import com.pay.bean.constant.PayTypeEnum;

/**
 * @Author: 吴宸煊
 * Date: 2020/7/5 14:26
 * Description:
 */

public interface PaymentService {
    String pay(String type);

    // 每家支付对应的类型
    PayTypeEnum getPayType();

}
