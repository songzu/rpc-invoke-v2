package com.songzuedu.impl;

import com.songzuedu.User;
import com.songzuedu.annotation.RpcService;
import com.songzuedu.IHelloService;

/**
 * <p></p>
 *
 * @author gengen.wang
 * @version $$ Id: HelloServiceImpl2.java, V 0.1 2020/3/11 下午4:35 wanggengen Exp $$
 **/
@RpcService(value = IHelloService.class, version = "v2.0")
public class HelloServiceImpl2 implements IHelloService {

    @Override
    public String sayHello(String content) {
        System.out.println("【v2.0】request in sayHello:" + content);
        return "【v2.0】Say Hello:" + content;
    }

    @Override
    public String saveUser(User user) {
        System.out.println("【V1.0】request in saveUser:" + user);
        return "【v2.0】SUCCESS";
    }
}
