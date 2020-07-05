package com.pay.factory;



import com.pay.bean.constant.PayTypeEnum;
import com.pay.service.PaymentService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 吴宸煊
 * Date: 2020/7/5 14:47
 * Description:
 */
public final class PayFactory {

    public PayFactory() {
    }

    public static Map<PayTypeEnum, PaymentService> PAYMAP = new ConcurrentHashMap<>();

    static {
        Map<String,PaymentService> beansOfType = ApplicationContextHelper.getBeansOfType(PaymentService.class);
        for(Map.Entry<String,PaymentService> entry: beansOfType.entrySet()){
            PaymentService paymentService = entry.getValue();
            PAYMAP.put(paymentService.getPayType(), paymentService);
        }
    }

    public static PaymentService getPay(PayTypeEnum payTypeEnum){
        return PAYMAP.get(payTypeEnum);
    }

}
