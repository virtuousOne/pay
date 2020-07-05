package com.pay.controller;


import com.pay.bean.constant.PayTypeEnum;
import com.pay.factory.PayFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: 吴宸煊
 * Date: 2020/7/5 14:56
 * Description:
 */
@RequestMapping("/StrategyPay")
@Controller
@Slf4j
public class PayController {


    @PostMapping("/pay")
    public String pay() {
        PayTypeEnum payType = PayTypeEnum.para("H5ALIPAY");
        String result = PayFactory.getPay(payType).pay("1");
        log.info("支付宝返回的信息是:" + result);
        return result;
    }
}
