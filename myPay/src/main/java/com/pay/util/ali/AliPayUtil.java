package com.pay.util.ali;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.Maps;
import com.pay.bean.ali.AliPayDto;
import com.pay.config.ali.AliPayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 吴宸煊
 * Date: 2020/2/18 11:36
 * Description: APP请求支付宝
 */
@Slf4j
@Component
public class AliPayUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    @Value("${pay.notifyurl}")
    public String notifyurl;

    /**
     * 功能描述  下单支付
     *
     * @param aliPayDto
     * @return Map<String, Object>
     */
    public Map<String, Object> aliPayOrder(AliPayDto aliPayDto) {
        Map map = Maps.newHashMap();
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //(否)对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
        model.setBody(aliPayDto.getBody());
        //(是)商品的标题/交易标题/订单标题/订单关键字等
        model.setSubject(aliPayDto.getSubject());
        //(是)商户网站唯一订单号
        model.setOutTradeNo(aliPayDto.getOutTradeNo());
        //(是)订单总金额，单位为元，精确到小数点后两位
        model.setTotalAmount(aliPayDto.getTotalAmount());
        //(否)设置未付款支付宝交易的超时时间，一旦超时，该笔交易就会自动被关闭。
        model.setTimeoutExpress("30m");
        //(是)销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(notifyurl);
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse responseStr = AliPayConfig.getAlipayClient().sdkExecute(request);
            //可以直接给客户端请求，无需再做处理。
            map.put("msg", responseStr.getBody());
        } catch (AlipayApiException e) {
            log.info("AliPayOrder exception={}", e);
        }
        return map;
    }


    /**
     * H5请求支付宝支付
     *
     * @param
     * @return
     */
    public AlipayTradeWapPayResponse aliPayH5(AliPayDto h5PayDto) {
        try {
            AlipayClient alipayClient = AliPayConfig.getAlipayClient();
            AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
            request.setNotifyUrl(notifyurl);
            request.setBizContent(getAliPayRequest(h5PayDto).toString());
            request.setReturnUrl(h5PayDto.getReturnUrl());
            AlipayTradeWapPayResponse responseStr = alipayClient.pageExecute(request);
            log.info(responseStr.getBody());
            return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private ObjectNode getAliPayRequest(AliPayDto h5PayDto) throws UnsupportedEncodingException {
        ObjectNode obj = mapper.createObjectNode();
        //obj.put("subject", URLEncoder.encode(payRequest.getBody(), "GBK"));
        obj.put("subject", h5PayDto.getSubject());
        obj.put("out_trade_no", h5PayDto.getOutTradeNo());
        obj.put("timeout_express", 30d);
        //obj.put("time_expire", DateTimeUtils.getYmdhmFormatDate(System.currentTimeMillis() + DateUtils.DAY_IN_MILLS));
        obj.put("total_amount", h5PayDto.getTotalAmount());
        obj.put("quit_url", h5PayDto.getReturnUrl());
        obj.put("product_code", "QUICK_WAP_PAY");
        obj.put("body", h5PayDto.getBody());
        return obj;
    }


}
