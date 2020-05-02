package com.pay.bean.ali;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: 吴宸煊
 * Date: 2020/5/2 17:46
 * Description: 支付需要前端传的参数
 */
@Data
public class AliPayDto implements Serializable {

    /**
     * 商品的标题
     */
    private String subject;

    /**
     * 订单号
     */
    @NotNull(message = "缺少请求参数")
    private String outTradeNo;

    /**
     * 金额
     */
    private String totalAmount;

    /**
     * 对一笔交易的具体描述信息
     */
    private String body;

    /**
     * 支付成功后回跳地址,H5支付特有
     */
    private String returnUrl;

    /**
     * 自己添加的，如果type ==1。是H5支付，如果type == 2是app支付
     */
    private Integer type;
}
