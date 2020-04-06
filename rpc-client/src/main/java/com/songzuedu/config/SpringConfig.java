package com.songzuedu.config;

import com.songzuedu.RpcProxyClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author gengen.wang
 **/
@Configuration
public class SpringConfig {

    @Bean(name = "rpcPRoxyClient")
    public RpcProxyClient proxyClient() {
        return new RpcProxyClient();
    }

}
