package com.pay.controller.weChat;

import com.pay.bean.weChat.WeChatPayDto;
import com.pay.util.weChat.WeChatPayUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: 吴宸煊
 * Date: 2020/5/2 17:52
 * Description:
 */
@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class WeChatPayController {

    @Autowired
    private WeChatPayUtil weChatPayUtil;


    @PostMapping("/weChatPay")
    @SneakyThrows
    public Map<String, String> getPrePayInfo(@RequestBody WeChatPayDto param) {
        String payType = param.getPayType();
        if ("11".equals(payType)) {
            Map<String, String> resultMap = null;
            String openId = "ohQvm5U9H7BIuGrx_ierC9igTObQ";
            try {
                resultMap = weChatPayUtil.getPrePayInfo(param, openId);
                log.info(resultMap.toString());
            } catch (Exception e) {
                log.error("生成微信预支付订单失败", e);
            }
            // 处理公司业务
            return resultMap;

        }

        return null;
    }
}
