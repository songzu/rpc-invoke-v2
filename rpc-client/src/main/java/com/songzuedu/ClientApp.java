package com.songzuedu;

import com.songzuedu.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
public class ClientApp {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        RpcProxyClient rpcProxyClient = context.getBean(RpcProxyClient.class);
        IPaymentService iPaymentService = rpcProxyClient.clientProxy(IPaymentService.class, "localhost", 8088);

        System.out.println(iPaymentService.doPay());
    }

}
