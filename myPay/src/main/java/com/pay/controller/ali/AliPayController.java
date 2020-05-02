package com.pay.controller.ali;

import com.pay.bean.ali.AliPayDto;
import com.pay.util.ali.AliPayUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: 吴宸煊
 * Date: 2020/5/2 17:43
 * Description: 请求支付宝接口支付
 */
@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class AliPayController {
    @Autowired
    private AliPayUtil aliPayUtil;

    /**
     * 请求支付宝接口支付
     *
     * @param payDto
     * @return
     */
    @PostMapping("/pay")
    @SneakyThrows
    public String pay(@RequestBody AliPayDto payDto) {
        Integer type = payDto.getType();
        if (type == 1) {
            // 走H5请求支付宝支付接口
            String result = aliPayUtil.aliPayH5(payDto).getBody();
            log.info("支付宝返回的信息是:" + result);
            return result;
        }
        if (type == 2) {
            // 走APP请求支付宝支付接口
            Map<String, Object> map = aliPayUtil.aliPayOrder(payDto);
            Object obj = map.get("msg");
            String resJson = obj.toString();
            log.info("支付宝返回的信息是:" + resJson);
            return resJson;

        }
        return null;

    }

}
