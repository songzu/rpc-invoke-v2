package com.songzuedu.impl;

import com.songzuedu.IPaymentService;
import com.songzuedu.annotation.RpcService;

/**
 * <p></p>
 *
 * @author gengen.wang
 * @version $$ Id: PaymentServiceImpl.java, V 0.1 2020/3/11 下午4:34 wanggengen Exp $$
 **/
@RpcService(value = IPaymentService.class)
public class PaymentServiceImpl implements IPaymentService {

    @Override
    public String doPay() {
        System.out.println("执行doPay方法");
        return "success";
    }

}
