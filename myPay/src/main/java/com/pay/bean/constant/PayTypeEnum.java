package com.pay.bean.constant;

/**
 * @Author: 吴宸煊
 * Date: 2020/7/5 14:26
 * Description:
 */
public enum PayTypeEnum {
    WEIXIN, H5ALIPAY, APPALIPAY;

    public static PayTypeEnum para(String type){

        for(PayTypeEnum payTypeEnum:PayTypeEnum.values()){
            if(type.equalsIgnoreCase(payTypeEnum.name())){
                return  payTypeEnum;
            }
        }

        return null;
    }
}
