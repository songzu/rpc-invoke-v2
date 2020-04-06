package com.songzuedu;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;

    private int port;

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //请求进入代理类
        System.out.println("请求进入代理类");

        //1.请求数据的包装
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion("");

        //2.远程通信
        RpcNetTransport netTransport = new RpcNetTransport(host, port);
        Object result = netTransport.send(rpcRequest);

        return result;
    }
}
