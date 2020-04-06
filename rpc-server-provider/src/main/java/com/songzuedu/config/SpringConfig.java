package com.songzuedu.config;

import com.songzuedu.RpcServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
@Configuration
@ComponentScan(basePackages = "com.songzuedu.impl")
public class SpringConfig {

    @Bean(name = "rpcServer")
    public RpcServer getRpcServer() {
        return new RpcServer(8088);
    }

}
